package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.GrandExchangeAction;
import com.ArtifactsMMO.ArtifactsMMO.action.RecycleAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrandExchange extends BuildingBase{

    public GrandExchange(@Autowired GrandExchangeAction grandExchangeAction) {
        super("grand_exchange", Location.of(5,1));
        actions.add(grandExchangeAction);
    }

}
