package org.example.groceriesanalyzer.repository;

import org.example.groceriesanalyzer.entity.Purchase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PurchaseRepository extends ElasticsearchRepository<Purchase, String> {
}
