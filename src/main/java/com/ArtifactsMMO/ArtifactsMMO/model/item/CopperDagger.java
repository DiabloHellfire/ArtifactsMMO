package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CopperDagger extends Item {
    public CopperDagger() {
        this.code = "copper_dagger";
        this.name = "CopperDagger";
        this.itemsForCraft = 6;
        this.minCorrectPrice = 60;
    }
}
