package com.ArtifactsMMO.ArtifactsMMO.model.mob;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.stereotype.Component;

@Component
public class MushMush extends Mob {
    public MushMush() {
        this.setName("MushMush");
        this.location = new Location(5, 3);
    }
}
