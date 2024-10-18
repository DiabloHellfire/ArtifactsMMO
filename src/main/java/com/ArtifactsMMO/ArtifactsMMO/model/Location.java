package com.ArtifactsMMO.ArtifactsMMO.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private int x;
    private int y;

    public static Location of(int x, int y) {
        return new Location(x, y);
    }
}
