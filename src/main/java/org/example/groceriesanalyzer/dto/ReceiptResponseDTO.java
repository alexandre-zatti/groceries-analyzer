package org.example.groceriesanalyzer.dto;

import java.util.List;

public record ReceiptResponseDTO(
        Double totalPrice,
        List<PurchaseItemDTO> items
) {
}
