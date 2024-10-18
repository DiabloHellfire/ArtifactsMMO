package com.ArtifactsMMO.ArtifactsMMO.controller;

import com.ArtifactsMMO.ArtifactsMMO.model.place.CopperRocks;
import com.ArtifactsMMO.ArtifactsMMO.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/doSomething")
    public void doSomething() {
        mainService.main();
    }
}
