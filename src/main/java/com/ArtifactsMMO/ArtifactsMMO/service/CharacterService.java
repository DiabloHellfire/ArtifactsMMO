package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CharacterResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {
    private final WebClient characterWebClient;

    public Character getCharacter() {
        try {
            var character = characterWebClient.get()
                    .retrieve()
                    .bodyToMono(CharacterResponseWrapper.class)
                    .map(CharacterResponseWrapper::getData)
                    .block();
            log.info("Character infos retrieved ! {}",character);

            return character;
        } catch (Exception e) {
            log.error("Error while getting character infos : {}", e.getMessage());
        }
        return null;
    }
}
