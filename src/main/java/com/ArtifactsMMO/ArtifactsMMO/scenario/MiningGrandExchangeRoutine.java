package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.*;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Copper;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperBoots;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperDagger;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperOre;
import com.ArtifactsMMO.ArtifactsMMO.model.place.*;
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
    private final Copper copper;
    private final CopperDagger copperDagger;
    private final CopperBoots copperBoots;
    private final Bank bank;
    private final Forge forge;
    private final CopperRocks copperRocks;
    private final WeaponCrafting weaponCrafting;
    private final GearCrafting gearCrafting;

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

    public void copperRoutine() {
        log.info("Beginning copper grand exchange mining routine");

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

        // Move to grand exchange
        characterReponse = movementAction.move(grandExchange, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Sell copper to grand exchange
        characterReponse = grandExchange.getAction(GrandExchangeAction.class).sell(copper, character.getInventoryQuantity(copper));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank & deposit gold
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Copper grand exchange mining routine finished");
    }

    public void copperDaggerRoutine() {
        log.info("Beginning copper dagger grand exchange mining routine");

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

        // Move to weapon workshop
        characterReponse = movementAction.move(weaponCrafting, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Craft weapon until no more materials
        while(ItemsToCraftUtils.getItemsCraftable(copperDagger,character.getInventoryQuantity(copper)) > 0) {
            characterReponse = weaponCrafting.getAction(CraftingAction.class).craft(copperDagger, ItemsToCraftUtils.getItemsCraftable(copperDagger,character.getInventoryQuantity(copper)));
            if(characterReponse != null) {
                character = characterReponse;
            }
        }

        // Move to grand exchange
        characterReponse = movementAction.move(grandExchange, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Sell copper to grand exchange
        characterReponse = grandExchange.getAction(GrandExchangeAction.class).sell(copperDagger, character.getInventoryQuantity(copperDagger));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank & deposit gold
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Copper dagger grand exchange mining routine finished");
    }

    public void copperBootsRoutine() {
        log.info("Beginning copper dagger grand exchange mining routine");

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

        // Move to gear workshop
        characterReponse = movementAction.move(gearCrafting, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Craft copper boots until no more materials
        while(ItemsToCraftUtils.getItemsCraftable(copperBoots, character.getInventoryQuantity(copper)) > 0) {
            characterReponse = weaponCrafting.getAction(CraftingAction.class).craft(copperBoots, ItemsToCraftUtils.getItemsCraftable(copperBoots,character.getInventoryQuantity(copper)));
            if(characterReponse != null) {
                character = characterReponse;
            }
        }

        // Move to grand exchange
        characterReponse = movementAction.move(grandExchange, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Sell copper boots to grand exchange
        characterReponse = grandExchange.getAction(GrandExchangeAction.class).sell(copperBoots, character.getInventoryQuantity(copperBoots));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank & deposit gold
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Copper dagger grand exchange mining routine finished");
    }
}
