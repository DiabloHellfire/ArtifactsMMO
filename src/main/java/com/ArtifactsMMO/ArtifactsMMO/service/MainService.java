package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperDagger;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningBankRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningGrandExchangeRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningRecycleRoutine;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final CharacterService characterService;
    private final MiningBankRoutine miningBankRoutine;
    private final MiningGrandExchangeRoutine miningGrandExchangeRoutine;
    private final MiningRecycleRoutine miningRecycleRoutine;
    private final CopperDagger copperDagger;

    public void main() {
        // Execute indefinitly
        main(-1);
    }

    public void main(int times) {
        var character = characterService.getCharacter();

        if(character.getName().contains("Mining")) { // Farming character
            while(true) {
                miningGrandExchangeRoutine.copperOreRoutine();
            }
        } else { // Main character
            if (times < 0) {
                log.info("Mining copper rocks indefinitly");
                while (true) {
                    miningRecycleRoutine.copperRoutine(copperDagger);
                }
            } else {
                for (int i = 0; i < times; i++) {
                    miningRecycleRoutine.copperRoutine(copperDagger);
                }
            }
        }
    }


}
