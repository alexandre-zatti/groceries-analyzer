package org.example.groceriesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "purchases")
public record Purchase(
        @Id String id,
        @JsonFormat(pattern = "yyyy-MM-dd")  // Ensures LocalDate is serialized correctly
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