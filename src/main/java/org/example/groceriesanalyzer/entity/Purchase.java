package org.example.groceriesanalyzer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "purchases")
public record Purchase(
        @Id String id,
        LocalDate date,
        String code,
        String description,
        Double unitValue,
        String unitIdentifier,
        Double quantity,
        Double totalValue,
        List<String> categories
) {
}