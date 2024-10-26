package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Copper;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.place.*;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final List<PlaceBase> places;

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
        this.places = List.of(new CopperRocks(), new IronRocks());
    }

    public void itemRoutine(Item item) {
        log.info("Beginning {} mining routine", item.getCode()+"_ore");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        var place = places.stream()
                .filter(p -> p.getName().equals(item.getCode()+"_rocks"))
                .findFirst()
                .get();

        // Move to mining spot
        character = movementAction.move(place.getLocation(), character);

        // Mine rocks until inventory is full
        character = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots

        // Move to forge
        character = movementAction.move(forge, character);

        // Smelt ores
        character = forge.getAction(CraftingAction.class).craft(item, ItemsToCraftUtils.getItemsCraftable(item,character.getInventoryQuantity(item.getCode()+"_ore")));

        // Move to bank
        character = movementAction.move(bank, character);

        // Store bars in bank
        character = bank.getAction(DepositAction.class).depositEverythingButOresOf(character, item);

        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("{} mining routine finished", item.getCode()+"_ore");
    }
}
