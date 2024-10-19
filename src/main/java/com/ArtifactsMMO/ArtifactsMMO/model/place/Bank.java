package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bank extends BuildingBase {

        public Bank(@Autowired DepositAction depositAction) {
            super("bank", Location.of(4, 1));
            this.actions.add(depositAction);
        }
}
