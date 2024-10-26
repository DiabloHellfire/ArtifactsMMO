package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CopperLegsArmor extends Item {

        public CopperLegsArmor() {
            this.code = "copper_legs_armor";
            this.name = "Copper legs armor";
            this.itemsForCraft = 6;
            this.minCorrectPrice = 1000; // TODO: Set correct price
        }
}
