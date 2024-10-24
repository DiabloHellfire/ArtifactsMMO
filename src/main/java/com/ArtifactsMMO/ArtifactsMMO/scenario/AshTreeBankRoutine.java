package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.AshPlanks;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.place.AshTree;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.WoodCutting;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AshTreeBankRoutine extends Scenario {
    private final CharacterService characterService;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final Bank bank;
    private final WoodCutting woodCutting;

    @Autowired
    public AshTreeBankRoutine(CharacterService characterService, MovementAction movementAction, GatheringAction gatheringAction, Bank bank, WoodCutting woodCutting) {
        this.characterService = characterService;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.bank = bank;
        this.woodCutting = woodCutting;
        this.scenarioName = "ashTreeBankRoutine";
    }
    public void farmAshWood() {
        log.info("Beginning Ash Wood mining routine");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(1);

        // Move to mining spot
        character = movementAction.move(new AshTree().getLocation(), character);

        // Mine rocks until inventory is full
        var tmpCharacter = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots
        if(tmpCharacter != null) {
            character = tmpCharacter;
        }

        // Move to woodcutting spot
        character = movementAction.move(woodCutting, character);

        // Craft Ash Planks
        character = woodCutting.getAction(CraftingAction.class).craft(new AshPlanks(), ItemsToCraftUtils.getItemsCraftable(new AshPlanks(),character.getInventoryQuantity("ash_wood")));

        // Move to bank
        character = movementAction.move(bank, character);

        // Store bars in bank
        character = bank.getAction(DepositAction.class).depositEverything(character);
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("{} mining routine finished", "ash_wood");
    }
}
