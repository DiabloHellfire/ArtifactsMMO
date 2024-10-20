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
import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.RECYCLE_URL;

@Slf4j
@RequiredArgsConstructor
@Component
public class RecycleAction implements Action {
    private final WebClient webClient;
    public Character recycle(Item item, Character character) {
        var quantity = character.getInventoryQuantity(item);
        if(quantity > 0) {
            log.info("Recycling {} {}", quantity, item.getName());
            var craftingResponse = webClient.post()
                    .uri(RECYCLE_URL)
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
}
