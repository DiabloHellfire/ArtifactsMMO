package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.GrandExchangeApiResponse;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.GrandExchangeApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.GrandExchangeListApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class GrandExchangeAction extends Action {
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

        var grandExchangeResponse = baseWebClient.get()
                .uri(GRAND_EXCHANGE_URL + "/" +item.getCode())
                .retrieve()
                .bodyToMono(GrandExchangeApiWrapper.class)
                .map(GrandExchangeApiWrapper::getData)
                .block();

        return grandExchangeResponse.getSellPrice();
    }

    public int getItemMaxQuantity(Item item) {
        log.info("Getting GrandExchange max sell quantity for {}", item.getName());

        var grandExchangeResponse = baseWebClient.get()
                .uri(GRAND_EXCHANGE_URL + "/" +item.getCode())
                .retrieve()
                .bodyToMono(GrandExchangeApiWrapper.class)
                .map(GrandExchangeApiWrapper::getData)
                .block();

        return grandExchangeResponse.getMaxQuantity();
    }

    private int getQuantityToSell(int quantityAvailable, int maxQuantity) {
        return Math.min(quantityAvailable, maxQuantity);
    }

    public List<GrandExchangeApiResponse> getAllGrandExchangeItemsPrice() {
        var result = new ArrayList<GrandExchangeApiResponse>();
        for(int i = 1; i < 4; i++) {
            var grandExchangeResponse = baseWebClient.get()
                    .uri(GRAND_EXCHANGE_URL + "?page=" + i + "&size=100")
                    .retrieve()
                    .bodyToMono(GrandExchangeListApiWrapper.class)
                    .map(GrandExchangeListApiWrapper::getData)
                    .block();
            result.addAll(grandExchangeResponse);
            log.info("All items in grand exchange : {}", result);
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                log.error("Error while sleeping inbetween grand exchange list api calls", e);
            }
        }
        return result;
    }
}
