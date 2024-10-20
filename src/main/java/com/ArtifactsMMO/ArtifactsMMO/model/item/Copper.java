package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Copper extends Item {

    public Copper() {
        this.code = "copper";
        this.name = "copper";
        this.itemsForCraft = 8;
        this.minCorrectPrice = 43;
    }
}
