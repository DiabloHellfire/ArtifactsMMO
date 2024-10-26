package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class IronPickaxe extends Item {

    public IronPickaxe() {
        this.code = "iron_pickaxe";
        this.name = "iron pickaxe";
        this.itemsForCraft = 0;
        this.minCorrectPrice = 50000;
    }
}
