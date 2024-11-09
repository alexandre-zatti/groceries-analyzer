package org.example.groceriesanalyzer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "purchases")
public record Purchase(
        @Id String id,
        @Field(type = FieldType.Date, format = DateFormat.date)
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
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