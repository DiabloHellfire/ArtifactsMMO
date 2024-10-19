package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
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
public class DepositAction implements Action {
    private final WebClient webClient;

    public void deposit(Item item, int quantity) {
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
        }
    }

    public void depositGold(int quantity) {
        if(quantity > 0) {
            log.info("Depositing {} gold", quantity);
            for (int i = 0; i < quantity; i++) {
                var craftingResponse = webClient.post()
                        .uri(DEPOSIT_GOLD_URL)
                        .bodyValue(BodyPayload.getBodyPayload(Map.of("quantity", quantity)))
                        .retrieve()
                        .bodyToMono(CommonApiWrapper.class)
                        .map(CommonApiWrapper::getData)
                        .block();

                // Process cooldown
                CooldownUtils.cooldown(craftingResponse.getCooldown());
            }
        }
    }
}
