package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.item.CopperOre;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class CopperRocks extends PlaceBase {
    private static final CopperOre copperOre = new CopperOre();
    public CopperRocks() {
        super("copper_rocks", Location.of(2,0), List.of(copperOre));
    }
}
