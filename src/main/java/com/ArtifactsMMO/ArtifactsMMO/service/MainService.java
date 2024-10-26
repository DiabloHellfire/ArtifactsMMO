package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.action.CookingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.*;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.*;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Cooking;
import com.ArtifactsMMO.ArtifactsMMO.model.place.GearCrafting;
import com.ArtifactsMMO.ArtifactsMMO.model.place.JewelryCrafting;
import com.ArtifactsMMO.ArtifactsMMO.model.place.WeaponCrafting;
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
    private final WeaponCrafting weaponCrafting;
    private final JewelryCrafting jewelryCrafting;
    private final GearCrafting gearCrafting;
    private final MovementAction movementAction;
    private final Cooking cooking;

    public void main() {
        // Execute indefinitly
        main(-1);
    }

    private void farmers() {
        var character = characterService.getCharacter();
        while(true) {
            try {
                if(!character.getName().contains("2") && !character.getName().contains("3") && !character.getName().contains("4")) {
                    // Farmer 1 -> JewelryCrafting
                    if(character.getJewelrycraftingLevel() >= 10) {
                        miningRecycleRoutine.ironRoutine(new IronRing(), jewelryCrafting);
                    } else {
                        miningRecycleRoutine.copperRoutine(new CopperRing(), jewelryCrafting);
                    }
                } else if (character.getName().contains("2")) {
                    // Farmer 2 -> GearCrafting
                    if(character.getGearcraftingLevel() >= 10) {
                        miningRecycleRoutine.ironRoutine(new IronBoots(), gearCrafting);
                    } else {
                        miningRecycleRoutine.copperRoutine(copperBoots, gearCrafting);
                    }
                } else if (character.getName().contains("3")) {
                    // Farmer 3 -> WeaponCrafting
                    if(character.getWeaponcraftingLevel() >= 10) {
                        miningRecycleRoutine.ironRoutine(new IronSword(), weaponCrafting);
                    } else {
                        miningRecycleRoutine.copperRoutine(copperDagger, weaponCrafting);
                    }
                } else {
                    character = fightingLoopRoutine.fightingLoop(cow);
                    character = movementAction.move(cooking,character);
                    var itemQuantity = character.getInventoryQuantity(new RawBeef());
                    character = cooking.getAction(CookingAction.class).cook(new CookedBeef(), itemQuantity);
                    if(character.getCookingLevel() >= 10) {
                        var possibleCheese = character.getInventoryQuantity(new MilkBucket()) / 3;
                        cooking.getAction(CookingAction.class).cook(new Cheese(), possibleCheese);
                    }
                    depositEverythingRoutine.depositEverything();
                }
            } catch (Exception e) {
                log.error("Connexion error",e);
            }
        }
//                var itemsToFarm = scenarioDeciderUtils.decideItemsForScenario();
//                if(!itemsToFarm.isEmpty()) {
//                    scenarioDeciderUtils.getScenarioFromItem(itemsToFarm.get(0)).run();
//                } else {
//                    scenarioDeciderUtils.getScenarioFromItem(null).run();
//                }
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
                miningRecycleRoutine.copperRoutine(copperDagger, weaponCrafting);
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
