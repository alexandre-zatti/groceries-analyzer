package org.example.groceriesanalyzer.service;

import org.example.groceriesanalyzer.dto.PurchaseRequestDTO;
import org.example.groceriesanalyzer.entity.Purchase;
import org.example.groceriesanalyzer.mapper.PurchaseMapper;
import org.example.groceriesanalyzer.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public void savePurchase(PurchaseRequestDTO purchase) {
        List<Purchase> flatPurchase = PurchaseMapper.toFlatPurchases(purchase);
        purchaseRepository.saveAll(flatPurchase);
    }

}
