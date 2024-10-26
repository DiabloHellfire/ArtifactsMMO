package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CopperRing extends Item {

        public CopperRing() {
            this.code = "copper_ring";
            this.name = "Copper ring";
            this.itemsForCraft = 6;
            this.minCorrectPrice = 110; // TODO
        }
}
