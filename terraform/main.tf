# Configure the Azure provider
terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "4.6.0"
    }
  }
}

provider "azurerm" {
  features {}
}

locals {
  ga_container_app_volume_name = "ga-container-app-volume"
}

# Create Resource Group
resource "azurerm_resource_group" "ga" {
  name     = "groceries-analyzer"
  location = "East US"
}

# Create Virtual Network
resource "azurerm_virtual_network" "ga_vnet" {
  name                = "ga-vnet"
  address_space = ["10.0.0.0/16"]
  location            = "East US"
  resource_group_name = azurerm_resource_group.ga.name
}

# Create Subnet
resource "azurerm_subnet" "ga_subnet" {
  name                 = "ga-subnet"
  resource_group_name  = azurerm_resource_group.ga.name
  virtual_network_name = azurerm_virtual_network.ga_vnet.name
  address_prefixes = ["10.0.1.0/24"]
  service_endpoints = ["Microsoft.Storage"]
}

# Create Storage Account for Persistent Volumes
resource "azurerm_storage_account" "ga_storage" {
  name                     = "groceriesanalizerstorage"
  resource_group_name      = azurerm_resource_group.ga.name
  location                 = azurerm_resource_group.ga.location
  account_tier             = "Standard"
  account_replication_type = "LRS"

  network_rules {
    virtual_network_subnet_ids = [azurerm_subnet.ga_subnet.id]
    default_action = "Deny"
  }
}

# Create Azure File Share for Elasticsearch Data
resource "azurerm_storage_share" "ga_storage_share" {
  name                 = "groceries-analyzer-storage-share"
  storage_account_name = azurerm_storage_account.ga_storage.name
  quota                = 1 #GB
}

# Create Container App Environment
resource "azurerm_container_app_environment" "ga_container_env" {
  name                     = "ga-container-env"
  resource_group_name      = azurerm_resource_group.ga.name
  location                 = azurerm_resource_group.ga.location
  infrastructure_subnet_id = azurerm_subnet.ga_subnet.id
}

resource "azurerm_container_app" "ga_container_app" {
  name                         = "ga-container-app"
  resource_group_name          = azurerm_resource_group.ga.name
  container_app_environment_id = azurerm_container_app_environment.ga_container_env.id
  revision_mode                = "Single"

  template {
    min_replicas = 1
    max_replicas = 1

    volume {
      name         = local.ga_container_app_volume_name
      storage_name = azurerm_storage_share.ga_storage_share.name
      storage_type = "AzureFile"
    }

    container {
      name   = "elasticsearch"
      image  = "docker.io/elasticsearch:8.15.2"
      cpu    = "0.5"
      memory = "1.0Gi"

      env {
        name  = "discovery.type"
        value = "single-node"
      }

      env {
        name  = "xpack.security.enabled"
        value = "false"
      }

      env {
        name  = "xpack.security.enrollment.enabled"
        value = "false"
      }

      volume_mounts {
        name = local.ga_container_app_volume_name
        path = "/usr/share/elasticsearch/data"
      }

    }

    container {
      name   = "kibana"
      image  = "docker.io/kibana:8.15.2"
      cpu    = "0.5"
      memory = "1.0Gi"

      env {
        name  = "ELASTICSEARCH_HOSTS"
        value = "http://elasticsearch:9200"
      }

      volume_mounts {
        name = local.ga_container_app_volume_name
        path = "/usr/share/kibana/data"
      }

      # Startup probe to ensure Kibana waits for Elasticsearch
      startup_probe {
        transport               = "HTTP"
        port                    = 9200
        path                    = "/"
        interval_seconds        = 5
        timeout                 = 2
        failure_count_threshold = 3
      }
    }
  }
}



