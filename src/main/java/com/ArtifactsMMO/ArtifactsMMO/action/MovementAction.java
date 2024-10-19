package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.place.PlaceBase;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.MovementWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.MOVEMENT_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovementAction implements Action {
    private final WebClient webClient;
    public void move(Location location, Character character) {
        if(!isAlreadyAtLocation(location, character)) {
            move(location.getX(), location.getY());
            character.setLocation(location);
        } else {
            log.info("Player already at location [{},{}]", location.getX(), location.getY());
        }
    }

    public void move(PlaceBase place, Character character) {
        move(place.getLocation(), character);
    }

    private void move(int x, int y) {
        log.info("Moving to location [{},{}]", x, y);

        var movementResponse = webClient.post()
                .uri(MOVEMENT_URL)
                .bodyValue(BodyPayload.getBodyPayload(Map.of("x",x,"y",y)))
                .retrieve()
                .bodyToMono(MovementWrapper.class)
                .map(MovementWrapper::getData)
                .block();

        // Process cooldown
        CooldownUtils.cooldown(movementResponse.getCooldown());
    }

    private boolean isAlreadyAtLocation(Location location, Character character) {
        return character.getLocation().equals(location);
    }
}
