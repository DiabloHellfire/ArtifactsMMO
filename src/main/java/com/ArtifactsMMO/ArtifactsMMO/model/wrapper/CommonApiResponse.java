package com.ArtifactsMMO.ArtifactsMMO.model.wrapper;

import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.utils.Cooldown;
import lombok.Data;

@Data
public class CommonApiResponse {
    private Cooldown cooldown;
    private Character character;
}
