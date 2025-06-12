package com.logsense.server.service;

import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import com.logsense.server.persistence.jpa.entity.postgre.TraceLogEntity;
import com.logsense.server.persistence.jpa.repository.TraceLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraceLogService {

    private static final Logger logger = LoggerFactory.getLogger(TraceLogService.class);

    private final TraceLogRepository traceLogRepository;

    private final LogQueryService logQueryService;

    public void persistTraceId(String errorHash) {
        Optional<TraceLogEntity> traceLog = traceLogRepository.findByErrorHash(errorHash)
                .stream()
                .filter(trace -> trace.getErrorHash().equals(errorHash))
                .findFirst();
        if (traceLog.isPresent()) {
            buildAndSaveTraceLogEntity(errorHash, traceLog.get().getTraceId());
        } else {
            Long traceId = generateTraceId();
            buildAndSaveTraceLogEntity(errorHash, traceId);
        }
    }

    private TraceLogEntity buildAndSaveTraceLogEntity(String hash, Long traceId) {
        TraceLogEntity newTraceLog = new TraceLogEntity();
        newTraceLog.setTraceId(traceId);
        newTraceLog.setErrorHash(hash);
        newTraceLog.setCreatedDate(Instant.now());
        newTraceLog.setLastModifiedDate(Instant.now());
        traceLogRepository.save(newTraceLog);
    }

    public List<TraceLogEntity> getAllTraceByHash(String hash) {
        return traceLogRepository.findByErrorHash(hash);
    }

    public void delete(Long id) {
        traceLogRepository.deleteById(id);
    }

    public List<TraceLogEntity> findAll() {
        return traceLogRepository.findAll();
    }

    public List<String> getServicesBySameTraceId(Long traceId) {
        List<String> errorHashList = getTraceLogsByTraceId(traceId)
                .stream()
                .map(TraceLogEntity::getErrorHash)
                .collect(Collectors.toList());

        List<GroupedErrorEntity> errorEntities = logQueryService.getAllGroupedErrorByErrorHash(errorHashList);

        return errorEntities
                .stream()
                .map(GroupedErrorEntity::getServiceName)
                .collect(Collectors.toList());
    }

    public List<TraceLogEntity> getTraceLogsByTraceId(Long traceId) {
        return traceLogRepository.findByTraceId(traceId);
    }

    private Long generateTraceId() {
        int tryCount = 10;
        for (int i = 0; i <= tryCount; i++) {
            long traceId = ThreadLocalRandom.current().nextLong(1000000, 4999999);
            boolean isExist = traceLogRepository.existsByTraceId(traceId);
            if (isExist) {
                logger.info("Genereted traceId {} is exist, will continue to try {} times", traceId, tryCount--);
                continue;
            }else {
                return traceId;
            }
        }
        logger.error("TraceId Generation is failed");
        throw new RuntimeException();
    }
}
