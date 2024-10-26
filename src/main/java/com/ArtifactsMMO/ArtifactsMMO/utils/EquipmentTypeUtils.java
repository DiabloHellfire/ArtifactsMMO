package com.ArtifactsMMO.ArtifactsMMO.utils;

import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EquipmentTypeUtils {
    public static String getEquipmentType(Item item) {
        if(item.getCode().contains("sword") || item.getCode().contains("pickaxe")) {
            return "weapon";
        } else {
            return "consumable1"; // TODO: Add more types
        }
    }
}
