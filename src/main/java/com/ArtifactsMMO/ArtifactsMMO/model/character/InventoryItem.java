package com.ArtifactsMMO.ArtifactsMMO.model.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class InventoryItem {
    private int slot;
    private String code;
    private int quantity;
}
