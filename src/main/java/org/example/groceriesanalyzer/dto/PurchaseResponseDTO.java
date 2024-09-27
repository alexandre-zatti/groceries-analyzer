package org.example.groceriesanalyzer.dto;

import java.time.LocalDate;
import java.util.List;

public record PurchaseResponseDTO(
        LocalDate date,
        Double totalPrice,
        List<PurchaseItemDTO> items
) {
}
