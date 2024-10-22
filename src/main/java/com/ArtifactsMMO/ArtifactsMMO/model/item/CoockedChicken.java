package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CoockedChicken extends Item {
    public CoockedChicken() {
        this.code = "cooked_chicken";
        this.name = "Coocked chicken";
        this.itemsForCraft = 1;
        this.minCorrectPrice = 60;
    }
}
