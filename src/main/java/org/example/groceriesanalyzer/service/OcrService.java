package org.example.groceriesanalyzer.service;

import com.azure.ai.vision.imageanalysis.ImageAnalysisClient;
import com.azure.ai.vision.imageanalysis.ImageAnalysisClientBuilder;
import com.azure.ai.vision.imageanalysis.models.DetectedTextLine;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class OcrService {
    private final ImageAnalysisClient client;

    public OcrService(@Value("${ocr.endpoint}") String endpointOCR, @Value("${ocr.key}") String keyOCR) {
        this.client = new ImageAnalysisClientBuilder()
                .endpoint(endpointOCR)
                .credential(new AzureKeyCredential(keyOCR))
                .buildClient();
    }

    public String analyzeImage(MultipartFile imageFile) throws IOException {
        // Convert the file to BinaryData and call the OCR service
        BinaryData imageData = BinaryData.fromBytes(imageFile.getBytes());
        ImageAnalysisResult result = client.analyze(
                imageData,
                Collections.singletonList(VisualFeatures.READ),
                null
        );

        String OcrResult = "";

        for (DetectedTextLine line : result.getRead().getBlocks().getFirst().getLines()) {
            OcrResult = OcrResult.concat(line.getText() + "\n");
        }

        return OcrResult;
    }
}
