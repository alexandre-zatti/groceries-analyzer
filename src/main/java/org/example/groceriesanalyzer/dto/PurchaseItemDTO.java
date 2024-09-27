package org.example.groceriesanalyzer.dto;

import java.util.List;

public record PurchaseItemDTO(
        String code,
        String description,
        Double unitValue,
        String unitIdentifier,
        Double quantity,
        Double totalValue,
        List<String> categories
) {
}
