package com.logsense.server.persistence.jpa.repository;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogEventElasticRepository extends ElasticsearchRepository<LogEventElasticDocument, String> {

    List<LogEventElasticDocument> findByErrorHash(String errorHash);

    @Query("{\"bool\":{\"must\":[{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"message\",\"stackTrace\"]}}],\"filter\":[{\"match\":{\"serviceName\":\"?1\"}}]}}")
    List<LogEventElasticDocument> searchLogs(String searchText, String serviceName);

    List<LogEventElasticDocument> findByServiceName(String serviceName);

}
