package com.ArtifactsMMO.ArtifactsMMO.controller;

import com.ArtifactsMMO.ArtifactsMMO.service.DoSomethingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final DoSomethingService doSomethingService;

    @GetMapping("/doSomething")
    public void doSomething() {
        doSomethingService.doSomething();
    }
}
