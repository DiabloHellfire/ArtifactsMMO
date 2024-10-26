package com.ArtifactsMMO.ArtifactsMMO.model.item;

import lombok.Data;

@Data
public class Item {
    String name;
    String code;
    int itemsForCraft;
    int itemsForCraft2;
    int minCorrectPrice;
}
