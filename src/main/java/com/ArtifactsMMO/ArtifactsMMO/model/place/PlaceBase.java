package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
@Slf4j
public class PlaceBase {
    String name;
    Location location;
    List<Item> items;

    public Item getItem(String itemCode) {
        Optional<Item> item = items.stream()
                .filter(i -> i.getCode().equals(itemCode)) // Check if the action is an instance of the specified type
                .findFirst();

        if(item.isPresent()) {
            return item.get();
        }

        log.error("Item {} not found on {} !", itemCode, location);
        return null;
    }
}
