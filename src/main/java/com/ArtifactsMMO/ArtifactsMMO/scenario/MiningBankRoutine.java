package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Copper;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Forge;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class MiningBankRoutine extends Scenario {
    private final Forge forge;
    private final Copper copper;
    private final Bank bank;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final CharacterService characterService;

    @Autowired
    public MiningBankRoutine(Forge forge,
                             Copper copper,
                             Bank bank,
                             MovementAction movementAction,
                             GatheringAction gatheringAction,
                             CharacterService characterService) {
        this.forge = forge;
        this.copper = copper;
        this.bank = bank;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.characterService = characterService;
        this.scenarioName = "miningBankRoutine";
    }

    public void copperRoutine() {
        log.info("Beginning copper mining routine");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(1);

        // Move to copper rocks
        var characterReponse = movementAction.move(Location.of(2,0), character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Mine copper rocks until inventory is full
        characterReponse = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to forge
        characterReponse = movementAction.move(forge, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Smelt copper ores
        characterReponse = forge.getAction(CraftingAction.class).craft(copper, ItemsToCraftUtils.getItemsCraftable(copper,character.getInventoryQuantity("copper_ore")));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Store copper bars in bank
        characterReponse = bank.getAction(DepositAction.class).deposit(copper, character.getInventoryQuantity(copper));
        if(characterReponse != null) {
            character = characterReponse;
        }

        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Copper mining routine finished");
    }
}
