package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.RecycleAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeaponCrafting extends BuildingBase{

    public WeaponCrafting(@Autowired CraftingAction craftingAction, @Autowired RecycleAction recycleAction) {
        super("weaponcrafting", Location.of(2,1));
        actions.add(craftingAction);
        actions.add(recycleAction);
    }
}
