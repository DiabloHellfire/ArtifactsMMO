package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.GrandExchangeApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrandExchangeAction implements Action {
    private final WebClient webClient;
    private final WebClient baseWebClient;

    public Character sell(Item item, int quantityAvailable) {
        log.info("Selling {} {} to grand exchange", quantityAvailable, item.getName());
        Character character = null;

        var grandExchangeItemMaxQuantity = getItemMaxQuantity(item);

        while(getQuantityToSell(quantityAvailable, grandExchangeItemMaxQuantity) > 0) {
            var grandExchangeItemPrice = getItemPrice(item);
            var grandExchangeResponse = webClient.post()
                    .uri(GRANG_EXCHANGE_SELL_URL)
                    .bodyValue(BodyPayload.getBodyPayload(Map.of("code", item.getCode(), "quantity", getQuantityToSell(quantityAvailable, grandExchangeItemMaxQuantity), "price", grandExchangeItemPrice)))
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();
            quantityAvailable -= getQuantityToSell(quantityAvailable, grandExchangeItemMaxQuantity);

            // Process cooldown
            CooldownUtils.cooldown(grandExchangeResponse.getCooldown());
            character = grandExchangeResponse.getCharacter();
        }

        return character;
    }

    public int getItemPrice(Item item) {
        log.info("Getting GrandExchange price for {}", item.getName());
        var grandExchangegResponse = baseWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(GRAND_EXCHANGE_GET_PRICE_URL)
                        .queryParam("itemCode", item.getCode())
                        .build())
                .bodyValue("")
                .retrieve()
                .bodyToMono(GrandExchangeApiWrapper.class)
                .map(GrandExchangeApiWrapper::getData)
                .block();

        return grandExchangegResponse.getSellPrice();
    }

    public int getItemMaxQuantity(Item item) {
        log.info("Getting GrandExchange max sell quantity for {}", item.getName());
        var grandExchangegResponse = baseWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(GRAND_EXCHANGE_GET_PRICE_URL)
                        .queryParam("itemCode", item.getCode())
                        .build())
                .bodyValue("")
                .retrieve()
                .bodyToMono(GrandExchangeApiWrapper.class)
                .map(GrandExchangeApiWrapper::getData)
                .block();

        return grandExchangegResponse.getMaxQuantity();
    }

    private int getQuantityToSell(int quantityAvailable, int maxQuantity) {
        return Math.min(quantityAvailable, maxQuantity);
    }
}
