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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final FightAction fightAction;
    private final Bank bank;
    private final DepositAction depositAction;
    private final EquipAction equipAction;
    private final UnequipAction unequipAction;
    @Autowired
    public MiningRecycleRoutine(Forge forge,
                                Copper copper,
                                WeaponCrafting weaponCrafting,
                                MovementAction movementAction,
                                GatheringAction gatheringAction,
                                CharacterService characterService,
                                GearCrafting gearCrafting,
                                FightAction fightAction,
                                Bank bank,
                                DepositAction depositAction,
                                EquipAction equipAction,
                                UnequipAction unequipAction) {
        this.forge = forge;
        this.copper = copper;
        this.weaponCrafting = weaponCrafting;
        this.movementAction = movementAction;
        this.gatheringAction = gatheringAction;
        this.characterService = characterService;
        this.gearCrafting = gearCrafting;
        this.fightAction = fightAction;
        this.bank = bank;
        this.depositAction = depositAction;
        this.equipAction = equipAction;
        this.unequipAction = unequipAction;
        this.scenarioName = "miningRecycleRoutine";
    }
    
    public void copperRoutine(Item item, BuildingBase building) {
        log.info("Beginning copper mining & recycling routine");

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Equip pickaxe
        try {
            if(StringUtils.isNotBlank(character.getWeaponSlot()) && !character.getWeaponSlot().contains("pickaxe")) {
                try {
                    unequipAction.unequip("weapon");
                } catch (Exception e) {
                    log.error("No weapon to unequip", e);
                }
            }
            equipAction.equip(new IronPickaxe());
        } catch (Exception e) {
            log.error("No pickaxe to equip", e);
        }

        // Move to copper rocks
        character = movementAction.move(Location.of(2,0), character);

        // Mine copper rocks until inventory is full
        character = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots

        // Move to forge
        character = movementAction.move(forge, character);

        // Smelt copper ores
        character = forge.getAction(CraftingAction.class).craft(copper, ItemsToCraftUtils.getItemsCraftable(copper,character.getInventoryQuantity("copper_ore")));

        // Move to workshop
        character = movementAction.move(building, character);

        // Craft & recycle weapon until no more materials
        while(ItemsToCraftUtils.getItemsCraftable(item,character.getInventoryQuantity(copper)) > 0) {
            character = building.getAction(CraftingAction.class).craft(item, ItemsToCraftUtils.getItemsCraftable(item,character.getInventoryQuantity(copper)));
            character = building.getAction(RecycleAction.class).recycle(item, character);
        }

        // Move to bank
        character = movementAction.move(bank, character);

        // Deposit everything but ores and ingot
        depositAction.depositEverythingBut(character, List.of(new Copper(), new CopperOre(), new IronPickaxe(), new IronSword()));

        log.info("Copper mining & recycling routine finished");
    }

    public void ironRoutine(Item item, BuildingBase building) {
        log.info("Beginning iron mining & recycling routine");

        // Unequip current weapon
        try {
            unequipAction.unequip("weapon");
        } catch (Exception e) {
            log.error("No weapon to unequip", e);
        }

        // Equip pickaxe
        try {
            equipAction.equip(new IronPickaxe());
        } catch (Exception e) {
            log.error("No pickaxe to equip", e);
        }

        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Move to iron rocks
        character = movementAction.move(Location.of(1,7), character);

        // Mine iron rocks until inventory is full
        character = gatheringAction.gather(character.getMaxFreeInventorySlot() - 2); // - 2 to keep 2 slots for rare drops TODO : update character every turn to get the right amount of free slots

        // Move to forge
        character = movementAction.move(forge, character);

        // Smelt iron ores
        character = forge.getAction(CraftingAction.class).craft(new Iron(), ItemsToCraftUtils.getItemsCraftable(new Iron(),character.getInventoryQuantity("iron_ore")));

        // Move to chicken
        character = movementAction.move(Location.of(0,1), character);

        // Unequip pickaxe
        try {
            unequipAction.unequip("weapon");
        } catch (Exception e) {
            log.error("No weapon to unequip", e);
        }

        // Equip sword
        try {
            equipAction.equip(new IronSword());
        } catch (Exception e) {
            log.error("No weapon to equip", e);
        }

        var totalIron = character.getInventoryQuantity("iron");
        try {
            // Gather feather until we have enough
            while(character.getInventoryQuantity(new Feather()) < (totalIron / item.getItemsForCraft()) * item.getItemsForCraft2() + 6) {
                character = fightAction.fight();
            }
        } catch (Exception e) {
            log.error("Inventory might be full", e);
        }

        // Move to workshop
        character = movementAction.move(building, character);

        // Craft & recycle until no more materials
        try {
            while (ItemsToCraftUtils.getItemsCraftable(item, character.getInventoryQuantity(new Iron())) > 0 && character.getInventoryQuantity("feather") >= item.getItemsForCraft2()) {
                log.info("CRAFTING {}, {}", ItemsToCraftUtils.getItemsCraftable(item, character.getInventoryQuantity(new Iron())), character);
                character = building.getAction(CraftingAction.class).craft(item, ItemsToCraftUtils.getItemsCraftable(item, character.getInventoryQuantity(new Iron())));
                character = building.getAction(RecycleAction.class).recycle(item, character);
            }
        } catch (Exception e) {
            log.error("Not enough items to keep crafting", e);
        }

        // Move to bank
        character = movementAction.move(bank, character);

        // Deposit everything not needed
        depositAction.depositEverythingBut(character, List.of(new Iron(), new IronOre(), new IronPickaxe(), new IronSword()));

        log.info("Iron mining & recycling routine finished");
    }
}
