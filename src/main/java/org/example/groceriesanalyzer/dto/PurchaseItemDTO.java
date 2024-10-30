package org.example.groceriesanalyzer.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PurchaseItemDTO(
        @NotNull(message = "Code is required")
        String code,

        @NotNull(message = "Description is required")
        @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
        String description,

        @NotNull(message = "Unit value is required")
        @Min(value = 0, message = "Unit value must be a positive number")
        Double unitValue,

        @NotNull(message = "Unit identifier is required")
        String unitIdentifier,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Double quantity,

        @NotNull(message = "Total value is required")
        Double totalValue,

        @NotNull(message = "Categories are required")
        List<String> categories
) {
}
