package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlaceBase {
    String name;
    Location location;
}
