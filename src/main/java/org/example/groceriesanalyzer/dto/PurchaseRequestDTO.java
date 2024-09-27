package org.example.groceriesanalyzer.dto;

import java.time.LocalDate;
import java.util.List;

public record PurchaseRequestDTO(
        LocalDate date,
        List<PurchaseItemDTO> items
) {
}
