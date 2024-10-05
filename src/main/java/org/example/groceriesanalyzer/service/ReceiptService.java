package org.example.groceriesanalyzer.service;

import org.example.groceriesanalyzer.dto.PurchaseItemDTO;
import org.example.groceriesanalyzer.dto.ReceiptResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {

    private Double calculateTotalReceiptValue(List<PurchaseItemDTO> purchaseItems) {
        return purchaseItems.stream().mapToDouble(PurchaseItemDTO::totalValue).sum();
    }

    public ReceiptResponseDTO processReceipt(List<PurchaseItemDTO> purchaseItems) {
        return new ReceiptResponseDTO(calculateTotalReceiptValue(purchaseItems), purchaseItems);
    }
}
