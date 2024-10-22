package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.item.IronOre;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class IronRocks extends PlaceBase {
    public IronRocks() {
        super("iron_rocks", Location.of(1,7), List.of(new IronOre()));
    }
}
