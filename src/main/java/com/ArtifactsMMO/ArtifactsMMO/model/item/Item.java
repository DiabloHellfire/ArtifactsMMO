package com.ArtifactsMMO.ArtifactsMMO.model.item;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import lombok.Data;

import java.util.Optional;

@Data
public class Item {
    String name;
    String code;
    int itemsForCraft;
    int minCorrectPrice;
}
