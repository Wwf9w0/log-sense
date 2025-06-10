package com.logsense.server.controller;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import com.logsense.server.service.LogQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/query")
@RequiredArgsConstructor
public class LogQueryController {

    private final LogQueryService logQueryService;

    @GetMapping("/logs/{errorHash}")
    public ResponseEntity<List<LogEventElasticDocument>> getLogEventsByHash(@PathVariable String errorHash) {
        return ResponseEntity.ok(logQueryService.findLogsByErrorHash(errorHash));
    }

    @GetMapping("/events")
    public ResponseEntity<Iterable<LogEventElasticDocument>> getLogEvents() {
        return ResponseEntity.ok(logQueryService.findAllLogEvent());
    }

    @GetMapping("/grouped-errors")
    public ResponseEntity<List<GroupedErrorEntity>> getGroupedErrors() {
        return ResponseEntity.ok(logQueryService.findAllGroupedErrors());
    }

    @GetMapping("/search")
    public ResponseEntity<List<LogEventElasticDocument>> searchLog(@RequestParam("text") String searchText,
                                                                   @RequestParam("service") String serviceName) {
        return ResponseEntity.ok(logQueryService.searchLogs(searchText, serviceName));
    }

    @GetMapping("/serviceName")
    public ResponseEntity<List<LogEventElasticDocument>> searchLogsByServiceName(@RequestParam("service") String serviceName) {
        return ResponseEntity.ok(logQueryService.searchLogsByServiceName(serviceName));
    }
}
