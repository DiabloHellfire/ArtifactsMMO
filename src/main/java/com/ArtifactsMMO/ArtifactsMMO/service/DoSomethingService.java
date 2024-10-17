package com.ArtifactsMMO.ArtifactsMMO.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class DoSomethingService {

    private final WebClient webClient;

    public void doSomething() {
        System.out.println("Doing something");
        webClient.get().retrieve().bodyToMono(String.class).subscribe(System.out::println); // TODO : Call going through by unable to load io.netty.resolver
    }
}
