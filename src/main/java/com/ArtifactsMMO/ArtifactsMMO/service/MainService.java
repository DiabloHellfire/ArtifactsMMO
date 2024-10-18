package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final CharacterService characterService;

    public void main() {
        // Mine copper rocks indefinitly
        main(-1);
    }

    public void main(int times) {
        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(character.getCooldown());

        if(times < 0) {
            while (true) {
                copperMiningRoutine();
            }
        } else {
            for (int i = 0; i < times; i++) {
                copperMiningRoutine();
            }
        }
    }

    // TODO : Implement copper mining routine
    public void copperMiningRoutine() {
        log.info("Beginning copper mining routine");
        // Move to copper rocks

        // Mine copper rocks until inventory is full

        // Move to forge

        // Smelt copper ores

        // Move to bank

        // Store copper bars in bank
    }
}
