package com.ArtifactsMMO.ArtifactsMMO.model.mob;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.stereotype.Component;

@Component
public class Wolf extends Mob {
    public Wolf() {
        this.setName("Wolf");
        this.location = new Location(-2, 1);
    }
}
