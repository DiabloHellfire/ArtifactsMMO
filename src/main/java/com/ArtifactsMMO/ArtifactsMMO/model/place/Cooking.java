package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.CookingAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Cooking extends BuildingBase{
    public Cooking(@Autowired CookingAction cookingAction) {
        super("cooking", Location.of(1,1));
        this.actions.add(cookingAction);
    }
}
