package com.logsense.server.persistence.jpa.entity.postgre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import java.time.Instant;

@Data
@Entity
@Table(name = "trace_log-s")
@NoArgsConstructor
@AllArgsConstructor
public class TraceLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long traceId;

    @Column(nullable = false)
    private String errorHash;

    @Column
    private Instant createdDate = Instant.now();

    @Column
    private Instant lastModifiedDate = Instant.now();
}
