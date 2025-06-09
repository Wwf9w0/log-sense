package com.logsense.server.service;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(LogConsumerService.class);
    private final ErrorGroupingService errorGroupingService;

    @KafkaListener(topics = "${kafka.topic.ingestion}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload LogEventElasticDocument logEvent) {
        logger.info("Received log event from Kafka: {}", logEvent.getId());
        try {
            errorGroupingService.processLogEvent(logEvent);
        } catch (Exception e) {
            logger.error("Error processing log event: " + logEvent.getId(), e);
        }
    }

}
