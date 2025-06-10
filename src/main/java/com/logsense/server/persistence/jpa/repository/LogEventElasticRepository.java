package com.logsense.server.persistence.jpa.repository;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogEventElasticRepository extends ElasticsearchRepository<LogEventElasticDocument, String> {

    List<LogEventElasticDocument> findByErrorHash(String errorHash);
}
