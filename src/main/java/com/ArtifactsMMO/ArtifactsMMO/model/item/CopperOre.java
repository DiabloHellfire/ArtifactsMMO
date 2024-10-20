package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CopperOre extends Item {

    public CopperOre() {
        this.code = "copper_ore";
        this.name = "copper ore";
        this.itemsForCraft = 0;
        this.minCorrectPrice = 14;
    }
}
