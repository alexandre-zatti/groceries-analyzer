package org.example.groceriesanalyzer.service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {
    private static final Logger logger = LoggerFactory.getLogger(AiService.class);

    private final OpenAIClient openAIClient;
    private final String deploymentAi;

    public AiService(@Value("${ai.endpoint}") String endpointAi, @Value("${ai.key}") String keyAi,
                     @Value("${ai.deployment}") String deploymentAi) {
        this.deploymentAi = deploymentAi;

        this.openAIClient = new OpenAIClientBuilder()
                .endpoint(endpointAi)
                .credential(new AzureKeyCredential(keyAi))
                .buildClient();
    }

    public String analyzeReceiptContent(String receiptContent) {
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatRequestSystemMessage(
                "You will receive an OCR text from a grocery receipt in Brazilian Portuguese. It lists purchased " +
                        "items with the fields: CODIGO (code), QTD or Quantidade (quantity), VL UNIT (price), and " +
                        "DESCRICAO (description). Convert these items into a JSON array with {code, description, " +
                        "unitValue, unitIdentifier, quantity}, and use the first letter for unitIdentifier, e.g., " +
                        "UNID becomes 'U', KG becomes 'K'. Do not include anything else in the response, only the " +
                        "JSON array."));
        chatMessages.add(new ChatRequestUserMessage(receiptContent));

        ChatCompletions chatCompletions = this.openAIClient.getChatCompletions(deploymentAi,
                new ChatCompletionsOptions(chatMessages));

        logger.info(chatCompletions.getModel());
        CompletionsUsage usage = chatCompletions.getUsage();
        logger.info(
                "Usage: number of prompt token is {}, number of completion token is {}, and number of total " +
                        "tokens is {}.",
                usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());

        return chatCompletions.getChoices().getFirst().getMessage().getContent();
    }

}
