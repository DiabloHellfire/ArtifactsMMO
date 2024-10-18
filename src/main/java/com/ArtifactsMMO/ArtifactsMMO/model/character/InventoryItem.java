package com.ArtifactsMMO.ArtifactsMMO.model.character;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class InventoryItem {
    private int slot;
    private String code;
    private int quantity;
}
