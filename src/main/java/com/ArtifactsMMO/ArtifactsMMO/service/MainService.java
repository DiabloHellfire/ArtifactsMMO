package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperDagger;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.RedSlime;
import com.ArtifactsMMO.ArtifactsMMO.scenario.FightingLoopRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningBankRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningGrandExchangeRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningRecycleRoutine;
import com.ArtifactsMMO.ArtifactsMMO.utils.ScenarioDeciderUtils;
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
    private final FightingLoopRoutine fightingLoopRoutine;
    private final RedSlime redSlime;
    private final ScenarioDeciderUtils scenarioDeciderUtils;

    public void main() {
        // Execute indefinitly
        main(-1);
    }

    public void main(int times) {
        var character = characterService.getCharacter();

        if(character.getName().contains("Mining")) { // Farming character
            while(true) {
                var itemsToFarm = scenarioDeciderUtils.decideItemsForScenario();
                if(character.getName().contains("2") || character.getName().contains("3")) { // Farmer 2 and 3
                    if(!itemsToFarm.isEmpty()) {
                        scenarioDeciderUtils.getScenarioFromItem(itemsToFarm.get(0)).run();
                    } else {
                        scenarioDeciderUtils.getScenarioFromItem(null).run();
                    }
                } else { // Farmer 1 and 4
                    if(itemsToFarm.size() >= 2) {
                        scenarioDeciderUtils.getScenarioFromItem(itemsToFarm.get(1)).run();
                    } else {
                        scenarioDeciderUtils.getScenarioFromItem(null).run();
                    }
                }
            }

//            if(character.getName().contains("2")) { // Farmer 2
//                log.info("Mining & selling copper indefinitly");
//                while (true) {
//                    miningGrandExchangeRoutine.copperRoutine();
//                }
//            } else if (character.getName().contains("3")) { // Farmer 3
//                log.info("Mining, crafting & selling copper dagger indefinitly");
//                while (true) {
//                    miningGrandExchangeRoutine.copperDaggerRoutine();
//                }
//            } else if (character.getName().contains("4")) { // Farmer 4
//                log.info("Mining, crafting & selling copper boots indefinitly");
//                while (true) {
//                    miningGrandExchangeRoutine.copperBootsRoutine();
//                }
//            } else { // Farmer 1
//                log.info("Mining & selling copper rocks indefinitly");
//                while (true) {
//                    miningGrandExchangeRoutine.copperOreRoutine();
//                }
//            }
        } else { // Main character
            if (times < 0) {
                log.info("Mining copper rocks indefinitly");
                while (true) {
//                    fightingLoopRoutine.fightingLoop(redSlime);
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
