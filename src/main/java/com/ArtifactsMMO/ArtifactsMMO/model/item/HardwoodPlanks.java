package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class HardwoodPlanks extends Item {

    public HardwoodPlanks() {
        this.code = "hardwood_plank";
        this.name = "Hardwood Plank";
        this.itemsForCraft = 3;
        this.minCorrectPrice = 415;
        this.itemsForCraft2 = 5;
    }
}
