package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.item.IronOre;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class AshTree extends PlaceBase {
    public AshTree() {
        super("ash_tree", Location.of(-1,0), List.of());
    }
}
