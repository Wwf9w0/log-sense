package com.logsense.server.controller;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import com.logsense.server.service.LogProducerService;
import com.logsense.server.service.LogQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogIngestionController {

    private final LogProducerService logProducerService;
    private final LogQueryService logQueryService;

    @PostMapping
    public ResponseEntity<Void> ingestLog(@RequestBody LogEventElasticDocument logEvent) {
        if (Objects.isNull(logEvent.getId())) {
            logEvent.setId(UUID.randomUUID().toString());
        }
        logProducerService.sendLogEvent(logEvent);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/grouped-logs")
    public ResponseEntity<List<GroupedErrorEntity>> getGroupedErrors() {
        return ResponseEntity.ok(logQueryService.findAllGroupedErrors());
    }

    @GetMapping("/detail/{errorHash}")
    public ResponseEntity<List<LogEventElasticDocument>> getLogEventsByHash(@PathVariable String errorHash) {
        return ResponseEntity.ok(logQueryService.findLogsByErrorHash(errorHash));
    }

    @GetMapping("/events")
    public ResponseEntity<Iterable<LogEventElasticDocument>> getLogEventsByHash() {
        return ResponseEntity.ok(logQueryService.findAllLogEvent());
    }
}
