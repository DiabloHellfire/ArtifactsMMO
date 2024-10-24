package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.CookingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GatheringAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class WoodCutting extends BuildingBase{
    public WoodCutting(@Autowired GatheringAction gatheringAction, @Autowired CraftingAction craftingAction) {
        super("wood_cutting", Location.of(-2,-3));
        this.actions.add(gatheringAction);
        this.actions.add(craftingAction);
    }
}
