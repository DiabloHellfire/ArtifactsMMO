package com.ArtifactsMMO.ArtifactsMMO.model.item;

import org.springframework.stereotype.Component;

@Component
public class Cheese extends Item{
    public Cheese() {
        this.code = "cheese";
        this.name = "Cheese";
        this.itemsForCraft = 3;
        this.minCorrectPrice = 48; // TODO
    }
}
