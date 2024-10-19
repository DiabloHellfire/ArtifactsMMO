package com.ArtifactsMMO.ArtifactsMMO.service;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Copper;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Forge;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import com.ArtifactsMMO.ArtifactsMMO.utils.ItemsToCraftUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final CharacterService characterService;
    private final MovementAction movementAction;
    private final GatheringAction gatheringAction;
    private final Forge forge;
    private final Copper copper;
    private final Bank bank;

    private Character character;

    public void main() {
        // Mine copper rocks indefinitly
        main(-1);
    }

    public void main(int times) {
        // Retrieve our character informations
        character = characterService.getCharacter();

        // Wait for character to be able to take requests
        CooldownUtils.cooldown(1);

        if(times < 0) {
            log.info("Mining copper rocks indefinitly");
            while (true) {
                copperMiningRoutine();
            }
        } else {
            for (int i = 0; i < times; i++) {
                copperMiningRoutine();
            }
        }
    }

    // TODO : when bank is full, craft copper gear and recycle them
    public void copperMiningRoutine() {
        log.info("Beginning copper mining routine");

        // Move to copper rocks
        movementAction.move(Location.of(2,0), character);

        // Mine copper rocks until inventory is full
        gatheringAction.gather(character.getMaxFreeInventorySlot());
        gatheringAction.gather(1);

        // Move to forge
        movementAction.move(forge, character);

        // Smelt copper ores
        forge.getAction(CraftingAction.class).craft(copper, ItemsToCraftUtils.getItemsCraftable(copper,character.getInventoryQuantity("copper_ore")));

        // Move to bank
        movementAction.move(bank, character);

        // Store copper bars in bank
        bank.getAction(DepositAction.class).deposit(copper, character.getInventoryQuantity(copper));
        bank.getAction(DepositAction.class).depositGold(character.getGold());

        log.info("Copper mining routine finished");
    }
}
