package com.logsense.server.service;

import com.logsense.server.configuration.KafkaProperties;
import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogProducerService {

    private static final Logger logger = LoggerFactory.getLogger(LogProducerService.class);
    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, LogEventElasticDocument> kafkaTemplate;

    @Async
    public void sendLogEvent(LogEventElasticDocument logEvent) {
        logger.info("Sending log event to Kafka topic '{}': {}", kafkaProperties.getTopic().getIngestion(), logEvent);
        kafkaTemplate.send(kafkaProperties.getTopic().getIngestion(), logEvent);
    }
}
