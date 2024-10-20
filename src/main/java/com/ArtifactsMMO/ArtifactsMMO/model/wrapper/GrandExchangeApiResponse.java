package com.ArtifactsMMO.ArtifactsMMO.model.wrapper;

import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.utils.Cooldown;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GrandExchangeApiResponse {
    private String code;
    private int stock;
    @JsonProperty("sell_price")
    private int sellPrice;
    @JsonProperty("buy_price")
    private int buyPrice;
    @JsonProperty("max_quantity")
    private int maxQuantity;
}
