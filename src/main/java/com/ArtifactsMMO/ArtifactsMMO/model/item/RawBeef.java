package com.ArtifactsMMO.ArtifactsMMO.model.item;

import org.springframework.stereotype.Component;

@Component
public class RawBeef extends Item{
    public RawBeef() {
        this.code = "raw_beef";
        this.name = "Raw beef";
        this.itemsForCraft = 1;
        this.minCorrectPrice = 48; // TODO
    }
}
