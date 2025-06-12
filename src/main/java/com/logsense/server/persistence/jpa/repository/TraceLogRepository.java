package com.logsense.server.persistence.jpa.repository;

import com.logsense.server.persistence.jpa.entity.postgre.TraceLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TraceLogRepository extends JpaRepository<TraceLogEntity, Long> {

    boolean existsByTraceId(Long traceId);
    List<TraceLogEntity> findByErrorHash(String errorHash);

    List<TraceLogEntity> findByTraceId(Long traceId);

    List<TraceLogEntity> findAllByErrorHash(String errorHash);

}
