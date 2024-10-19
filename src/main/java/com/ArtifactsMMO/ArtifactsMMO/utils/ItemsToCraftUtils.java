package com.ArtifactsMMO.ArtifactsMMO.utils;

import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemsToCraftUtils {

    public static int getItemsCraftable(Item item, int quantity) {
        return quantity / item.getItemsForCraft();
    }
}
