package com.ArtifactsMMO.ArtifactsMMO.model.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Cooldown {
    @JsonProperty("total_seconds")
    private int totalSeconds;
    @JsonProperty("remaining_seconds")
    private int remainingSeconds;
    @JsonProperty("started_at")
    private String startedAt;
    private String expiration;
    private String reason;
}
