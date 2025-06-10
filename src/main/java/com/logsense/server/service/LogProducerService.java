package com.logsense.server.service;

import com.logsense.server.configuration.properties.KafkaProperties;
import com.logsense.server.model.LogEvent;
import com.logsense.server.model.LogEventIngestionRequest;
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
    private final KafkaTemplate<String, LogEvent> kafkaTemplate;

    @Async
    public void sendLogEvent(LogEventIngestionRequest request) {
        logger.info("Sending log event to Kafka topic '{}': {}", kafkaProperties.getTopic().getIngestion(), request);
        kafkaTemplate.send(kafkaProperties.getTopic().getIngestion(), converttoEvent(request));
    }

    private LogEvent converttoEvent(LogEventIngestionRequest request) {
        LogEvent event = new LogEvent();
        event.setMessage(request.getMessage());
        event.setServiceName(request.getServiceName());
        event.setLogLevel(request.getLogLevel());
        event.setStackTrace(request.getStackTrace());
        event.setThreadName(request.getThreadName());
        event.setTimestamp(request.getTimestamp());
        event.setLoggerName(request.getLoggerName());
        event.setMdc(request.getMdc());
        return event;


    }
}
