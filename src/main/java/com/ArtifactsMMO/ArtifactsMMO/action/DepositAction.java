package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.character.InventoryItem;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class DepositAction extends Action {
    private final WebClient webClient;

    public Character deposit(Item item, int quantity) {
        if(quantity > 0) {
            log.info("Depositing {} {}", quantity, item.getName());
            var craftingResponse = webClient.post()
                    .uri(DEPOSIT_URL)
                    .bodyValue(BodyPayload.getBodyPayload(Map.of("code", item.getCode(), "quantity", quantity)))
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(craftingResponse.getCooldown());

            return craftingResponse.getCharacter();
        }
        return null;
    }

    public Character deposit(String itemCode, int quantity) {
        if(quantity > 0) {
            log.info("Depositing {} {}", quantity, itemCode);
            var craftingResponse = webClient.post()
                    .uri(DEPOSIT_URL)
                    .bodyValue(BodyPayload.getBodyPayload(Map.of("code", itemCode, "quantity", quantity)))
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(craftingResponse.getCooldown());

            return craftingResponse.getCharacter();
        }
        return null;
    }

    public Character depositEverything(Character character) {
        Character lastCharacterInfo = null; // Assuming deposit() returns an Object or another type
        for (InventoryItem item : character.getInventory()) {
            lastCharacterInfo = deposit(item.getCode(), item.getQuantity());
        }
        return lastCharacterInfo;
    }

    public Character depositEverythingButOresOf(Character character, Item item) {
        Character lastCharacterInfo = null; // Assuming deposit() returns an Object or another type
        for (InventoryItem inventoryItem : character.getInventory()) {
            if(!inventoryItem.getCode().equals(item.getCode()+"_ore"))
                lastCharacterInfo = deposit(inventoryItem.getCode(), inventoryItem.getQuantity());
        }
        return lastCharacterInfo;
    }

    public Character depositGold(int quantity) {
        if(quantity > 0) {
            log.info("Depositing {} gold", quantity);
            var craftingResponse = webClient.post()
                    .uri(DEPOSIT_GOLD_URL)
                    .bodyValue(BodyPayload.getBodyPayload(Map.of("quantity", quantity)))
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(craftingResponse.getCooldown());

            return craftingResponse.getCharacter();
        }
        return null;
    }

    public Character depositGold(Character character) {
        var quantity = character.getGold();
        return depositGold(quantity);
    }
}
