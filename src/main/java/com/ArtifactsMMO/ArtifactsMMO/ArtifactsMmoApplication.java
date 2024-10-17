package com.ArtifactsMMO.ArtifactsMMO;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.ArtifactsMMO.ArtifactsMMO"})
@RequiredArgsConstructor
public class ArtifactsMmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtifactsMmoApplication.class, args);
	}

}
