package com.logsense.server.controller;

import com.logsense.server.model.LogEventIngestionRequest;
import com.logsense.server.service.LogProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogIngestionController {

    private final LogProducerService logProducerService;

    @PostMapping
    public ResponseEntity<Void> ingestLog(@RequestBody LogEventIngestionRequest request) {
        logProducerService.sendLogEvent(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
