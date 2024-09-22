package org.example.groceriesanalyzer.dto;

import java.math.BigDecimal;

public record ReceiptItemDTO(
        String code,
        String description,
        BigDecimal unitValue,
        String unitIdentifier,
        BigDecimal quantity
) {
}
