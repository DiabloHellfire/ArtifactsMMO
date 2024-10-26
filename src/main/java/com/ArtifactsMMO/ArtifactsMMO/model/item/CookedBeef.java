package com.ArtifactsMMO.ArtifactsMMO.model.item;

import org.springframework.stereotype.Component;

@Component
public class CookedBeef extends Item{
    public CookedBeef() {
        this.code = "cooked_beef";
        this.name = "Cooked beef";
        this.itemsForCraft = 1;
        this.minCorrectPrice = 48; // TODO
    }
}
