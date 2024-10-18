package com.ArtifactsMMO.ArtifactsMMO.model.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Cooldown {
    private int totalSeconds;
    private int remainingSeconds;
    private String startedAt;
    private String expiration;
    private String reason;
}
