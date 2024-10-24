package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SprucePlanks extends Item {

    public SprucePlanks() {
        this.code = "spruce_plank";
        this.name = "Spruce Planks";
        this.itemsForCraft = 8;
        this.minCorrectPrice = 166; // TODO
    }
}
