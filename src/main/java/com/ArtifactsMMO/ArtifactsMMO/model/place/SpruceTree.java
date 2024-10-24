package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class SpruceTree extends PlaceBase {
    public SpruceTree() {
        super("spruce_tree", Location.of(2,6), List.of());
    }
}
