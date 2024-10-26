package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class IronSword extends Item {

    public IronSword() {
        this.code = "iron_sword";
        this.name = "iron sword";
        this.itemsForCraft = 6;
        this.itemsForCraft2 = 2;
        this.minCorrectPrice = 140; // TODO
    }
}
