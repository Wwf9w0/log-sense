package com.logsense.server.service;

import com.logsense.server.persistence.jpa.entity.elasticsearch.LogEventElasticDocument;
import com.logsense.server.persistence.jpa.entity.postgre.GroupedErrorEntity;
import com.logsense.server.persistence.jpa.repository.GroupedErrorEntityRepository;
import com.logsense.server.persistence.jpa.repository.LogEventElasticRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ErrorGroupingService {

    private static final Logger logger = LoggerFactory.getLogger(ErrorGroupingService.class);
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}|\\d+");

    private final GroupedErrorEntityRepository groupedErrorRepository;
    private final LogEventElasticRepository logEventRepository;


    @Transactional
    public void processLogEvent(LogEventElasticDocument logEvent) {
        String template = createTemplate(logEvent.getMessage());
        String hash = createHash(template);

        logEvent.setErrorHash(hash);
        logEvent.setTimestamp(Instant.now());

        Optional<GroupedErrorEntity> existingErrorOpt = groupedErrorRepository.findByErrorHash(hash);
        GroupedErrorEntity groupedError = existingErrorOpt.orElseGet(() -> {
            GroupedErrorEntity newError = new GroupedErrorEntity();
            newError.setErrorHash(hash);
            newError.setErrorMessageTemplate(template);
            newError.setServiceName(logEvent.getServiceName());
            return newError;
        });

        groupedError.setTotalOccurrences(groupedError.getTotalOccurrences() + 1);
        groupedError.setLastSeenAt(Instant.now());
        groupedErrorRepository.save(groupedError);
        logEventRepository.save(logEvent);
        logger.info("Saved Log");
    }

    private String createTemplate(String message) {
        if (Objects.isNull(message)) {
            return "";
        }
        return VARIABLE_PATTERN.matcher(message).replaceAll("*");
    }

    private String createHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hasString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (Objects.equals(hex.length(), 1)) {
                    hasString.append('0');
                }
                hasString.append(hex);
            }
            return hasString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);

        }

    }

}
