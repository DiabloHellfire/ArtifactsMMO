package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.FightAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.Mob;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FightingLoopRoutine {
    private final MovementAction movementAction;
    private final CharacterService characterService;
    private final FightAction fightAction;
    public Character fightingLoop(Mob mob) {
        log.info("Beginning {} fighting loop", mob.getName());

        return fight(mob,-1);
    }

    public Character fight(Mob mob, int times) {
        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Move to mob location
        character = movementAction.move(mob, character);

        if(times < 0) {
            // Fight mob indefinitely
            log.info("Fighting {} indefinitly",mob.getName());
            while (true) {
                fightAction.fight();
            }
        } else {
            log.info("Fighting {} {} times",mob.getName(),times);
            for (int i = 0; i < times; i++) {
                character = fightAction.fight();
            }
        }
        return character;
    }
}
