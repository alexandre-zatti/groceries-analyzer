package org.example.groceriesanalyzer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record PurchaseRequestDTO(
        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Items list cannot be null")
        @Size(min = 1, message = "At least one item is required")
        List<@Valid PurchaseItemDTO> items
) {
}
