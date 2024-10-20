package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CopperBoots extends Item {

        public CopperBoots() {
            this.code = "copper_boots";
            this.name = "Copper Boots";
            this.itemsForCraft = 8;
            this.minCorrectPrice = 110;
        }
}
