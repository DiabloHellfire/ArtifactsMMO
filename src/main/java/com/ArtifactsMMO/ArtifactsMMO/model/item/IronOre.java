package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class IronOre extends Item {

    public IronOre() {
        this.code = "iron_ore";
        this.name = "iron ore";
        this.itemsForCraft = 0;
        this.minCorrectPrice = 68;
    }
}