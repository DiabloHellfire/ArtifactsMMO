package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CopperRocks extends PlaceBase {
    public CopperRocks() {
        super("copper_rocks", Location.of(2,0));
    }
}
