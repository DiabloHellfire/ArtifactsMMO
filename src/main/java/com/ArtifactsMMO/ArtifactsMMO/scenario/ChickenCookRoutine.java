package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.CookingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.FightAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CoockedChicken;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Egg;
import com.ArtifactsMMO.ArtifactsMMO.model.item.FriedEggs;
import com.ArtifactsMMO.ArtifactsMMO.model.item.RawChicken;
import com.ArtifactsMMO.ArtifactsMMO.model.mob.Chicken;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Cooking;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class ChickenCookRoutine extends Scenario {
    private final MovementAction movementAction;
    private final CharacterService characterService;
    private final FightAction fightAction;
    private final Cooking cooking;
    private final Bank bank;

    @Autowired
    public ChickenCookRoutine(MovementAction movementAction,
                              CharacterService characterService,
                              FightAction fightAction,
                              Cooking cooking,
                              Bank bank) {
        this.movementAction = movementAction;
        this.characterService = characterService;
        this.fightAction = fightAction;
        this.cooking = cooking;
        this.bank = bank;
        this.scenarioName = "chickenCookRoutine";
    }

    public void chickenCookRoutine() {
        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Move to chicken location
        character = movementAction.move(new Chicken(), character);

        // Fight chicken indefinitely
        log.info("Fighting chicken indefinitly");
        while (character.getMaxFreeInventorySlot() > 2) {
            character = fightAction.fight();
        }

        // Move to cooking workshop
        character = movementAction.move(cooking, character);

        // Cook chicken
        var rawChickenQuantity = character.getInventoryQuantity(new RawChicken());
        cooking.getAction(CookingAction.class).cook(new CoockedChicken(), rawChickenQuantity);
        var eggQuantity = character.getInventoryQuantity(new Egg());
        try { // Prevent the code from breaking if cooking level is not sufficient
            cooking.getAction(CookingAction.class).cook(new FriedEggs(), eggQuantity);
        } catch (Exception e) {
            log.error("Cooking level is not enough to cook eggs", e.getMessage());
        }

        // Move to bank
        character = movementAction.move(bank, character);

        // Store all items
        bank.getAction(DepositAction.class).depositEverything(character);
        bank.getAction(DepositAction.class).depositGold(character);
    }
}
