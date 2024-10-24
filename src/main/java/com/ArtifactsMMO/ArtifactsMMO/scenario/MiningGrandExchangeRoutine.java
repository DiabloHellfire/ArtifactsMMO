package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.*;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.*;
import com.ArtifactsMMO.ArtifactsMMO.model.place.*;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Data
public class MiningGrandExchangeRoutine extends Scenario {
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
    private List<PlaceBase> places;

    @Autowired
    public MiningGrandExchangeRoutine(GrandExchange grandExchange,
                                      MovementAction movementAction,
                                      GatheringAction gatheringAction,
                                      CharacterService characterService,
                                      CopperOre copperOre,
                                      Copper copper,
                                      CopperDagger copperDagger,
                                      CopperBoots copperBoots,
                                      Bank bank,
                                      Forge forge,
                                      CopperRocks copperRocks,
                                      WeaponCrafting weaponCrafting,
                                      GearCrafting gearCrafting) {
        this.grandExchange = grandExchange;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.characterService = characterService;
        this.copperOre = copperOre;
        this.copper = copper;
        this.copperDagger = copperDagger;
        this.copperBoots = copperBoots;
        this.bank = bank;
        this.forge = forge;
        this.copperRocks = copperRocks;
        this.weaponCrafting = weaponCrafting;
        this.gearCrafting = gearCrafting;
        this.scenarioName = "miningGrandExchangeRoutine";
        this.places = List.of(new CopperRocks(), new IronRocks());
    }

    public void copperDaggerRoutine() {
        log.info("Beginning copper dagger grand exchange mining routine");

        Character characterReponse = null;
        var character = mineCopperIngot();

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
        log.info("Beginning copper boots grand exchange mining routine");

        Character characterReponse = null;
        var character = mineCopperIngot();

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

    public void itemOreRoutine(Item item) { // Iron or Copper
        log.info("Beginning {} grand exchange mining routine", item.getCode());

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(1);

        var place = places.stream()
                .filter(p -> p.getName().equals(item.getCode().replace("_ore","")+"_rocks"))
                .findFirst()
                .get();

        // Move to rocks
        var characterReponse = movementAction.move(place.getLocation(), character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Mine ore until inventory is full
        characterReponse = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to grand exchange
        characterReponse = movementAction.move(grandExchange, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Sell ores to grand exchange
        characterReponse = grandExchange.getAction(GrandExchangeAction.class).sell(item.getCode(), character.getInventoryQuantity(item.getCode()));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank & deposit gold
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }
        bank.getAction(DepositAction.class).depositEverythingButOresOf(character, item);
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Iron ore grand exchange mining routine finished");
    }

    public void materialRoutine(Item item) { // Iron or Copper
        log.info("Beginning {} grand exchange mining routine", item.getCode());

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(1);

        var place = places.stream()
                .filter(p -> p.getName().equals(item.getCode()+"_rocks"))
                .findFirst()
                .get();

        // Move to copper rocks
        var characterReponse = movementAction.move(place.getLocation(), character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Mine ore until inventory is full
        characterReponse = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to forge
        characterReponse = movementAction.move(forge, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Smelt ores
        characterReponse = forge.getAction(CraftingAction.class).craft(item, ItemsToCraftUtils.getItemsCraftable(item,character.getInventoryQuantity(item.getCode()+"_ore")));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to grand exchange
        characterReponse = movementAction.move(grandExchange, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Sell material to grand exchange
        characterReponse = grandExchange.getAction(GrandExchangeAction.class).sell(item.getCode(), character.getInventoryQuantity(item.getCode()));
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Move to bank & deposit gold
        characterReponse = movementAction.move(bank, character);
        if(characterReponse != null) {
            character = characterReponse;
        }
        bank.getAction(DepositAction.class).depositEverythingButOresOf(character, item);
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Iron ore grand exchange mining routine finished");
    }

    private Character mineCopperIngot() {
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
        return forge.getAction(CraftingAction.class).craft(copper, ItemsToCraftUtils.getItemsCraftable(copper,character.getInventoryQuantity("copper_ore")));
    }
}
