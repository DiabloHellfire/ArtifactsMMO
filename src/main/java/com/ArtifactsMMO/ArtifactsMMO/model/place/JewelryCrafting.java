package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.RecycleAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JewelryCrafting extends BuildingBase{

    public JewelryCrafting(@Autowired CraftingAction craftingAction, @Autowired RecycleAction recycleAction) {
        super("jewelry_crafting", Location.of(1,3));
        actions.add(craftingAction);
        actions.add(recycleAction);
    }

}
