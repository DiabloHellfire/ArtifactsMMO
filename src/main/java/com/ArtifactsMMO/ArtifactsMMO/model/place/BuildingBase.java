package com.ArtifactsMMO.ArtifactsMMO.model.place;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Slf4j
public class BuildingBase extends PlaceBase {
    List<Action> actions;
    public BuildingBase(String name, Location location) {
        super(name, location);
        this.actions = new ArrayList<>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public <T extends Action> T getAction(Class<T> type) {
        Optional<T> action = actions.stream()
                .filter(type::isInstance) // Check if the action is an instance of the specified type
                .map(type::cast) // Cast the Action to the subtype
                .findFirst();

        if(action.isPresent()) {
            return action.get();
        }

        log.error("Action {} not found !", type.getSimpleName());
        return null;
    }
}
