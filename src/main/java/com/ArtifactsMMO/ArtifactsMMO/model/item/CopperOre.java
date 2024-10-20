package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CopperOre extends Item {

    public CopperOre() {
        this.code = "copper ore";
        this.name = "copper_ore";
        this.itemsForCraft = 0;
    }
}
