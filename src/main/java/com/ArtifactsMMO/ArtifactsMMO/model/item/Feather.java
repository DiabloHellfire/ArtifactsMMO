package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Feather extends Item {

    public Feather() {
        this.code = "feather";
        this.name = "Feather";
        this.itemsForCraft = 0;
        this.minCorrectPrice = 140; // TODO
    }
}
