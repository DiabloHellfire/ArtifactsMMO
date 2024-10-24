package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AshPlanks extends Item {

    public AshPlanks() {
        this.code = "ash_plank";
        this.name = "Ash Planks";
        this.itemsForCraft = 8;
        this.minCorrectPrice = 166;
    }
}
