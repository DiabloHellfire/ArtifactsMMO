package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.Location;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.place.PlaceBase;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.GatheringWrapper;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.MovementWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.BodyPayload;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.GATHER_URL;
import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.MOVEMENT_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatheringAction implements Action {
    private final WebClient webClient;

    public void gather(int times) {
        gather(times, "");
    }

    public void gather(int times, String item) {
        log.info("Gathering {} {}", times, item);
        for(int i = 0; i < times; i++) {
            var gatheringResponse = webClient.post()
                    .uri(GATHER_URL)
                    .bodyValue("")
                    .retrieve()
                    .bodyToMono(GatheringWrapper.class)
                    .map(GatheringWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(gatheringResponse.getCooldown());
        }
    }
}
