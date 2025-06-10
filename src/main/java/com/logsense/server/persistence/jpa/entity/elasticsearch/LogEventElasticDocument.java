package com.logsense.server.persistence.jpa.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "logsense-events")
public class LogEventElasticDocument {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String errorHash;

    @Field(type = FieldType.Text)
    private String message;

    @Field(type = FieldType.Text)
    private String stackTrace;

    @Field(type = FieldType.Keyword)
    private String serviceName;

    @Field(type = FieldType.Keyword)
    private String logLevel;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    @Field(type = FieldType.Object)
    private Map<String, String> metadata;
}
