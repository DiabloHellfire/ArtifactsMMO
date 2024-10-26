package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.HardwoodPlanks;
import com.ArtifactsMMO.ArtifactsMMO.model.item.SprucePlanks;
import com.ArtifactsMMO.ArtifactsMMO.model.place.*;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HardwoodPlankBankRoutine extends Scenario {
    private final CharacterService characterService;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final Bank bank;
    private final WoodCutting woodCutting;

    @Autowired
    public HardwoodPlankBankRoutine(CharacterService characterService, MovementAction movementAction, GatheringAction gatheringAction, Bank bank, WoodCutting woodCutting) {
        this.characterService = characterService;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.bank = bank;
        this.woodCutting = woodCutting;
        this.scenarioName = "spruceTreeBankRoutine";
    }
    public void farmBirchWood() {
        log.info("Beginning HardWoodPlank mining routine");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        int comboTotalItemsNeeded = new HardwoodPlanks().getItemsForCraft() + new HardwoodPlanks().getItemsForCraft2();
        int nbrOfPossibleCraft = (character.getMaxFreeInventorySlot() - 2) / comboTotalItemsNeeded;

        // Move to mining spot
        movementAction.move(new BirchTree().getLocation(), character);

        // Harvest item 1
        character = gatheringAction.gather(nbrOfPossibleCraft * new HardwoodPlanks().getItemsForCraft2());

        // Move to mining spot
        movementAction.move(new AshTree().getLocation(), character);

        // Harvest item 2
        character = gatheringAction.gather(nbrOfPossibleCraft * new HardwoodPlanks().getItemsForCraft());

        // Move to woodcutting spot
        movementAction.move(woodCutting, character);

        // Craft Hardwood Planks
        character = woodCutting.getAction(CraftingAction.class).craft(new HardwoodPlanks(), nbrOfPossibleCraft);

        // Move to bank
        character = movementAction.move(bank, character);

        // Store in bank
        character = bank.getAction(DepositAction.class).depositEverything(character);
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("{} mining routine finished", "HardwoodPlank");
    }
}
