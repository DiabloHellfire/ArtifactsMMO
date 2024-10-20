package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.*;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Copper;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperOre;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.CopperRocks;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Forge;
import com.ArtifactsMMO.ArtifactsMMO.model.place.GrandExchange;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MiningGrandExchangeRoutine {
    private final GrandExchange grandExchange;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final CharacterService characterService;
    private final CopperOre copperOre;
    private final Bank bank;
    private final CopperRocks copperRocks;

    public void copperOreRoutine() {
        log.info("Beginning copper ore grand exchange mining routine");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(1);

        // Move to copper rocks
        var characterReponse = movementAction.move(copperRocks, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Mine copper ore until inventory is full
        characterReponse = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to grand exchange
        characterReponse = movementAction.move(grandExchange, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Sell copper ores to grand exchange
        characterReponse = grandExchange.getAction(GrandExchangeAction.class).sell(copperOre, character.getInventoryQuantity(copperOre));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank & deposit gold
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Copper ore grand exchange mining routine finished");
    }
}
