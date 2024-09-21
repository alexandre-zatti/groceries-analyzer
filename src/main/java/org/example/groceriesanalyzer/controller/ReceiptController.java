package org.example.groceriesanalyzer.controller;

import org.example.groceriesanalyzer.service.AiService;
import org.example.groceriesanalyzer.service.OcrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    private final OcrService ocrService;
    private final AiService aiService;

    @Autowired
    public ReceiptController(OcrService ocrService, AiService aiService) {
        this.ocrService = ocrService;
        this.aiService = aiService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.warn("file was empty");
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // Analyze the image with OCR
            String ocrResult = ocrService.analyzeImage(file);
            logger.info(ocrResult);

            // Process the text with AI
            String aiResult = aiService.analyzeReceiptContent(ocrResult);
            logger.info(aiResult);

            // Optionally, process the result as needed
            return ResponseEntity.ok("OCR result processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the image");
        }
    }

}