package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class BirchTree extends PlaceBase {
    public BirchTree() {
        super("birch_tree", Location.of(3,5), List.of());
    }
}
