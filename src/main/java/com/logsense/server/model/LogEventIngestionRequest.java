package com.logsense.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class LogEventIngestionRequest implements Serializable {

    private String message;
    private String serviceName;
    private String logLevel;
    private String stackTrace;
    private String threadName;
    private Instant timestamp;
    private String loggerName;
    private Map<String, String> mdc;

}
