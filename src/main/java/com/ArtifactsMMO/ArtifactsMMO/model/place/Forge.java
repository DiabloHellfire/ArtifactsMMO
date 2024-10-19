package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Forge extends BuildingBase {

    public Forge(@Autowired CraftingAction craftingAction) {
        super("forge", Location.of(1,5));
        actions.add(craftingAction);
    }
}
