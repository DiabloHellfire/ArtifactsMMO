package com.ArtifactsMMO.ArtifactsMMO.model.mob;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.stereotype.Component;

@Component
public class Cow extends Mob {
    public Cow() {
        this.setName("Cow");
        this.location = new Location(0, 2);
    }
}
