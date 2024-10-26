package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.CRAFT_URL;
import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.EQUIP_URL;
import static com.ArtifactsMMO.ArtifactsMMO.utils.EquipmentTypeUtils.getEquipmentType;

@Slf4j
@Component
@RequiredArgsConstructor
public class EquipAction extends Action {
    private final WebClient webClient;

    public Character equip(Item item) {
        return equip(item, 1);
    }

    public Character equip(Item item, int quantity) {
        if(quantity > 0) {
            log.info("Equiping {} {}", quantity, item.getName());

            var type = getEquipmentType(item);

            var equipResponse = webClient.post()
                    .uri(EQUIP_URL)
                    .bodyValue(BodyPayload.getBodyPayload(Map.of("code", item.getCode(), "quantity", quantity, "slot", type)))
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(equipResponse.getCooldown());

            return equipResponse.getCharacter();
        }
        return null;
    }
}
