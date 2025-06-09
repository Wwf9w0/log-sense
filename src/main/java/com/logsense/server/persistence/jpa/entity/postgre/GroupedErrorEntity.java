package com.logsense.server.persistence.jpa.entity.postgre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Entity
@Table(name = "grouped_errors")
public class GroupedErrorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String errorHash;

    @Column(columnDefinition = "TEXT")
    private String errorMessageTemplate;

    @Column
    private long totalOccurrences = 1;

    @Column
    private Instant firstSeenAt = Instant.now();

    @Column
    private Instant lastSeenAt = Instant.now();

    @Column
    private String serviceName;

}
