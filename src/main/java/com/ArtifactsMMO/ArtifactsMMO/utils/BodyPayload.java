package com.ArtifactsMMO.ArtifactsMMO.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class BodyPayload {
    public static String getBodyPayload(Map<String, Object> bodyParameters) {
        try {
            var objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            var body = objectMapper.writeValueAsString(bodyParameters);
            log.info("Body parameters converted to JSON : {}", body);
            return body;
        } catch (Exception e) {
            log.error("Error while converting body parameters to JSON", e);
            return null;
        }
    }
}
