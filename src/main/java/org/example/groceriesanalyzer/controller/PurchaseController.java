package org.example.groceriesanalyzer.controller;

import org.example.groceriesanalyzer.dto.PurchaseRequestDTO;
import org.example.groceriesanalyzer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    //    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);
    private final PurchaseService purchaseService;

    @Autowired
    PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody PurchaseRequestDTO purchase) {
        purchaseService.savePurchase(purchase);
        return ResponseEntity.ok().build();
    }

}
