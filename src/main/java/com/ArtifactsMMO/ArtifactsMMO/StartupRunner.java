package com.ArtifactsMMO.ArtifactsMMO;

import com.ArtifactsMMO.ArtifactsMMO.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupRunner {
    @Value("${config.user.name}")
    private String username;
    private final MainService mainService;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        log.info("Application started! Executing {} routines !", username);

        mainService.main();
    }
}
