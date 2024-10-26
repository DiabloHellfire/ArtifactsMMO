package com.ArtifactsMMO.ArtifactsMMO.model.mob;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.stereotype.Component;

@Component
public class YellowSlime extends Mob {
    public YellowSlime() {
        this.setName("YellowSlime");
        this.location = new Location(4, -1);
    }
}
