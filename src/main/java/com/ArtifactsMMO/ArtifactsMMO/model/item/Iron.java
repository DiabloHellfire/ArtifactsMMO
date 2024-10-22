package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Iron extends Item {

    public Iron() {
        this.code = "iron";
        this.name = "iron";
        this.itemsForCraft = 8;
        this.minCorrectPrice = 140;
    }
}
