package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class IronBoots extends Item {

        public IronBoots() {
            this.code = "iron_boots";
            this.name = "Iron Boots";
            this.itemsForCraft = 5;
            this.itemsForCraft2 = 3;
            this.minCorrectPrice = 110; // TODO
        }
}
