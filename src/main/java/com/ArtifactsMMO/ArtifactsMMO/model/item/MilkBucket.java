package com.ArtifactsMMO.ArtifactsMMO.model.item;

import org.springframework.stereotype.Component;

@Component
public class MilkBucket extends Item{
    public MilkBucket() {
        this.code = "milk_bucket";
        this.name = "Milk bucket";
        this.itemsForCraft = 1;
        this.minCorrectPrice = 48; // TODO
    }
}
