package com.ArtifactsMMO.ArtifactsMMO.model.item;

import org.springframework.stereotype.Component;

@Component
public class Egg extends Item {
    public Egg() {
        this.code = "egg";
        this.name = "Egg";
        this.itemsForCraft = 1;
        this.minCorrectPrice = 15;
    }
}
