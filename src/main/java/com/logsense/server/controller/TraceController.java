package com.logsense.server.controller;

import com.logsense.server.persistence.jpa.entity.postgre.TraceLogEntity;
import com.logsense.server.service.TraceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trace")
@RequiredArgsConstructor
public class TraceController {

    private final TraceLogService traceLogService;

    @GetMapping("/services")
    public ResponseEntity<List<String>> getGroupLogByTraceId(@RequestParam Long traceId) {
        return ResponseEntity.ok(traceLogService.getServicesBySameTraceId(traceId));
    }

    @GetMapping("/{hash}")
    public ResponseEntity<List<TraceLogEntity>> getTraceLogsByHash(@PathVariable String hash){
        return ResponseEntity.ok(traceLogService.getAllTraceByHash(hash));
    }

    @GetMapping()
    public ResponseEntity<List<TraceLogEntity>> getAllTraceLog() {
        return ResponseEntity.ok(traceLogService.findAll());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        traceLogService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
