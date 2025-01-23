package org.example.groceriesanalyzer.controller;


import jakarta.validation.Valid;
import org.example.groceriesanalyzer.dto.PurchaseRequestDTO;
import org.example.groceriesanalyzer.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody PurchaseRequestDTO purchase) {
        purchaseService.savePurchase(purchase);
        return ResponseEntity.ok().build();
    }
}
