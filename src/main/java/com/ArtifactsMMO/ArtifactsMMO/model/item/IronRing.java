package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class IronRing extends Item {

        public IronRing() {
            this.code = "iron_ring";
            this.name = "Iron Ring";
            this.itemsForCraft = 6;
            this.itemsForCraft2 = 2;
            this.minCorrectPrice = 110; // TODO
        }
}
