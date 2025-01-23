package org.example.groceriesanalyzer.controller;

import org.example.groceriesanalyzer.dto.PurchaseItemDTO;
import org.example.groceriesanalyzer.dto.ReceiptResponseDTO;
import org.example.groceriesanalyzer.service.AiService;
import org.example.groceriesanalyzer.service.OcrService;
import org.example.groceriesanalyzer.service.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final OcrService ocrService;
    private final AiService aiService;
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(OcrService ocrService, AiService aiService, ReceiptService receiptService) {
        this.ocrService = ocrService;
        this.aiService = aiService;
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponseDTO> processReceipt(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.warn("file was empty");
            return ResponseEntity.badRequest().body(null);
        }

        String ocrResult = ocrService.analyzeImage(file);
        logger.info(ocrResult);

        List<PurchaseItemDTO> aiResult = aiService.extractReceiptItems(ocrResult);

        return ResponseEntity.ok(receiptService.processReceipt(aiResult));
    }

}
