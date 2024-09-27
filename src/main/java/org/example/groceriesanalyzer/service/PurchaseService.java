package org.example.groceriesanalyzer.service;

import org.example.groceriesanalyzer.dto.PurchaseItemDTO;
import org.example.groceriesanalyzer.dto.PurchaseResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    public PurchaseResponseDTO processPurchase(LocalDate purchaseDate, List<PurchaseItemDTO> purchaseItems) {
        Double totalPurchasePrice = purchaseItems.stream().mapToDouble(PurchaseItemDTO::totalValue).sum();
        return new PurchaseResponseDTO(purchaseDate, totalPurchasePrice, purchaseItems);
    }
}
