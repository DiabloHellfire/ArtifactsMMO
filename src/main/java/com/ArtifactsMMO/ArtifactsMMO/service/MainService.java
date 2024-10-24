package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperBoots;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperDagger;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.Cow;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.RedSlime;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.Wolf;
import com.ArtifactsMMO.ArtifactsMMO.scenario.*;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
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
    private final CopperBoots copperBoots;
    private final FightingLoopRoutine fightingLoopRoutine;
    private final RedSlime redSlime;
    private final ScenarioDeciderUtils scenarioDeciderUtils;
    private final ChickenCookRoutine chickenCookRoutine;
    private final Cow cow;
    private final DepositEverythingRoutine depositEverythingRoutine;
    private final AshTreeBankRoutine ashTreeBankRoutine;
    private final SpruceTreeBankRoutine spruceTreeBankRoutine;
    private final HardwoodPlankBankRoutine hardwoodPlankBankRoutine;

    public void main() {
        // Execute indefinitly
        main(-1);
    }

    private void farmers() {
        while(true) {
            var character = characterService.getCharacter();
            if(character.getWoodcuttingLevel() < 20)
                spruceTreeBankRoutine.farmSpruceWood();
            else {
                hardwoodPlankBankRoutine.farmBirchWood();
            }
//                var itemsToFarm = scenarioDeciderUtils.decideItemsForScenario();
//                if(!itemsToFarm.isEmpty()) {
//                    scenarioDeciderUtils.getScenarioFromItem(itemsToFarm.get(0)).run();
//                } else {
//                    scenarioDeciderUtils.getScenarioFromItem(null).run();
//                }
        }
    }

    private void mainCharacter(int times) {
        if (times < 0) {
            log.info("Mining copper rocks indefinitly");
            while (true) {
                try {
                    fightingLoopRoutine.fightingLoop(new Wolf());
                } catch(Exception e) {
                    depositEverythingRoutine.depositEverything();
                }
                //miningRecycleRoutine.copperRoutine(copperBoots);
//                    scenarioDeciderUtils.getScenarioFromItem(new Iron()).run();
            }
        } else {
            for (int i = 0; i < times; i++) {
                miningRecycleRoutine.copperRoutine(copperDagger);
            }
        }
    }

    public void main(int times) {
        var character = characterService.getCharacter();

        while(true) {
            try {
                if(character.getName().contains("Mining")) { // Farming characters
                    farmers();
                } else { // Main character
                    mainCharacter(times);
                }
            } catch(Exception e) {
                log.error("Connexion error",e);
                CooldownUtils.cooldown(3);
            }
        }
    }


}
