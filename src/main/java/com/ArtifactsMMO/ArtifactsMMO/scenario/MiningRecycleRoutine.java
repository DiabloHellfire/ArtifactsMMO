package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.*;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Copper;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperDagger;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Forge;
import com.ArtifactsMMO.ArtifactsMMO.model.place.GearCrafting;
import com.ArtifactsMMO.ArtifactsMMO.model.place.WeaponCrafting;
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
public class MiningRecycleRoutine extends Scenario {
    private final Forge forge;
    private final Copper copper;
    private final WeaponCrafting weaponCrafting;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final CharacterService characterService;
    private final GearCrafting gearCrafting;

    public MiningRecycleRoutine(@Autowired Forge forge,
                                @Autowired Copper copper,
                                @Autowired WeaponCrafting weaponCrafting,
                                @Autowired MovementAction movementAction,
                                @Autowired GatheringAction gatheringAction,
                                @Autowired CharacterService characterService,
                                @Autowired GearCrafting gearCrafting) {
        this.forge = forge;
        this.copper = copper;
        this.weaponCrafting = weaponCrafting;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.characterService = characterService;
        this.gearCrafting = gearCrafting;
        this.scenarioName = "miningRecycleRoutine";
    }
    
    public void copperRoutine(Item item) {
        log.info("Beginning copper mining & recycling routine");

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

        // Move to weapon workshop
        characterReponse = movementAction.move(gearCrafting, character);
        if(characterReponse != null) {
            character = characterReponse;
        }

        // Craft & recycle weapon until no more materials
        while(ItemsToCraftUtils.getItemsCraftable(item,character.getInventoryQuantity(copper)) > 0) {
            characterReponse = weaponCrafting.getAction(CraftingAction.class).craft(item, ItemsToCraftUtils.getItemsCraftable(item,character.getInventoryQuantity(copper)));
            if(characterReponse != null) {
                character = characterReponse;
            }
            characterReponse = weaponCrafting.getAction(RecycleAction.class).recycle(item, character);
            if(characterReponse != null) {
                character = characterReponse;
            }
        }

        log.info("Copper mining & recycling routine finished");
    }
}
