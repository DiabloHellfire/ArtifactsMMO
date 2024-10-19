package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiResponse;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.GatheringWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.CRAFT_URL;
import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.GATHER_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class CraftingAction implements Action {
    private final WebClient webClient;

    public void craft(Item item, int quantity) {
        log.info("Crafting {} {}", quantity, item.getName());
        for(int i = 0; i < quantity; i++) {
            var craftingResponse = webClient.post()
                    .uri(CRAFT_URL)
                    .bodyValue(BodyPayload.getBodyPayload(Map.of("code",item.getCode(),"quantity",quantity)))
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(craftingResponse.getCooldown());
        }
    }
}
