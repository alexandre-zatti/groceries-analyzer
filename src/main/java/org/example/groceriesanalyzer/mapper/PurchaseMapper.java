package org.example.groceriesanalyzer.mapper;

import org.example.groceriesanalyzer.dto.PurchaseRequestDTO;
import org.example.groceriesanalyzer.entity.Purchase;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseMapper {

    private PurchaseMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Purchase> toFlatPurchases(PurchaseRequestDTO purchaseRequestDTO) {
        return purchaseRequestDTO.items().stream()
                .map(item -> new Purchase(
                        null, // Elasticsearch will generate the ID automatically
                        purchaseRequestDTO.date(),
                        item.code(),
                        item.description(),
                        item.unitValue(),
                        item.unitIdentifier(),
                        item.quantity(),
                        item.totalValue(),
                        item.categories()
                ))
                .collect(Collectors.toList());
    }
}