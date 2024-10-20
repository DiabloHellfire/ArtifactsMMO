package com.ArtifactsMMO.ArtifactsMMO.model.mob;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RedSlime extends Mob {
    public RedSlime() {
        this.setName("Red Slime");
        this.location = new Location(1, -1);
    }
}
