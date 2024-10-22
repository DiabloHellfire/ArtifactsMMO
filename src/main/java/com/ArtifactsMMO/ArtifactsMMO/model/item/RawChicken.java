package com.ArtifactsMMO.ArtifactsMMO.model.item;

import org.springframework.stereotype.Component;

@Component
public class RawChicken extends Item{
    public RawChicken() {
        this.code = "raw_chicken";
        this.name = "Raw chicken";
        this.itemsForCraft = 1;
        this.minCorrectPrice = 48;
    }
}
