package com.logsense.server.service;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import com.logsense.server.persistence.jpa.repository.GroupedErrorEntityRepository;
import com.logsense.server.persistence.jpa.repository.LogEventElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogQueryService {

    private final GroupedErrorEntityRepository groupedErrorEntityRepository;
    private final LogEventElasticRepository logEventElasticRepository;
    public List<GroupedErrorEntity> findAllGroupedErrors() {
        return groupedErrorEntityRepository.findAll();
    }

    public List<GroupedErrorEntity> getAllGroupedErrorByErrorHash(List<String> errorHashList) {
        return groupedErrorEntityRepository.findByErrorHashIn(errorHashList);
    }

    public List<LogEventElasticDocument> findLogsByErrorHash(String errorHash) {
        return logEventElasticRepository.findByErrorHash(errorHash);
    }

    public Iterable<LogEventElasticDocument> findAllLogEvent() {
        return logEventElasticRepository.findAll();
    }

    public List<LogEventElasticDocument> searchLogs(String searchText, String serviceName) {
        return logEventElasticRepository.searchLogs(searchText, serviceName);
    }

    public List<LogEventElasticDocument> searchLogsByServiceName(String serviceName) {
        return logEventElasticRepository.findByServiceName(serviceName);
    }
}
