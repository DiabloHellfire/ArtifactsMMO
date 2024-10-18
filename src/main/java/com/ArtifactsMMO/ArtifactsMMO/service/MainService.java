package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final CharacterService characterService;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;

    private Character character;

    public void main() {
        // Mine copper rocks indefinitly
        main(-1);
    }

    public void main(int times) {
        // Retrieve our character informations
        character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(3);

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
        //movementAction.move(Location.of(2,0), character);

        // Mine copper rocks until inventory is full
        gatheringAction.gather(3);

        // Move to forge

        // Smelt copper ores

        // Move to bank

        // Store copper bars in bank
    }
}
