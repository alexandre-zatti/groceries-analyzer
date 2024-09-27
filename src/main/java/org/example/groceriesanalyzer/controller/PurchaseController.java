package org.example.groceriesanalyzer.controller;

import org.example.groceriesanalyzer.dto.PurchaseRequestDTO;
import org.example.groceriesanalyzer.dto.PurchaseResponseDTO;
import org.example.groceriesanalyzer.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @PostMapping("/save")
    public ResponseEntity<PurchaseResponseDTO> save(@RequestBody PurchaseRequestDTO purchase) {
        return ResponseEntity.ok(purchaseService.processPurchase(purchase.date(), purchase.items()));
    }

}
