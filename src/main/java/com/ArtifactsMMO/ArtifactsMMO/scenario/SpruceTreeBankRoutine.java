package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.AshPlanks;
import com.ArtifactsMMO.ArtifactsMMO.model.item.SprucePlanks;
import com.ArtifactsMMO.ArtifactsMMO.model.place.AshTree;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.SpruceTree;
import com.ArtifactsMMO.ArtifactsMMO.model.place.WoodCutting;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpruceTreeBankRoutine extends Scenario {
    private final CharacterService characterService;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final Bank bank;
    private final WoodCutting woodCutting;

    @Autowired
    public SpruceTreeBankRoutine(CharacterService characterService, MovementAction movementAction, GatheringAction gatheringAction, Bank bank, WoodCutting woodCutting) {
        this.characterService = characterService;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.bank = bank;
        this.woodCutting = woodCutting;
        this.scenarioName = "spruceTreeBankRoutine";
    }
    public void farmSpruceWood() {
        log.info("Beginning Spruce Wood mining routine");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Move to mining spot
        character = movementAction.move(new SpruceTree().getLocation(), character);

        // Mine rocks until inventory is full
        character = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots

        // Move to woodcutting spot
        character = movementAction.move(woodCutting, character);

        // Craft Spruce Planks
        character = woodCutting.getAction(CraftingAction.class).craft(new SprucePlanks(), ItemsToCraftUtils.getItemsCraftable(new SprucePlanks(),character.getInventoryQuantity("spruce_wood")));

        // Move to bank
        character = movementAction.move(bank, character);

        // Store bars in bank
        character = bank.getAction(DepositAction.class).depositEverything(character);
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("{} mining routine finished", "spruce_wood");
    }
}
