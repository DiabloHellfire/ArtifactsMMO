package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.GATHER_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatheringAction implements Action {
    private final WebClient webClient;

    public Character gather(int times) {
        return gather(times, "");
    }

    public Character gather(int times, String item) {
        log.info("Gathering {} {}", times, item);
        Character character = null;
        for(int i = 0; i < times; i++) {
            var gatheringResponse = webClient.post()
                    .uri(GATHER_URL)
                    .bodyValue("")
                    .retrieve()
                    .bodyToMono(CommonApiWrapper.class)
                    .map(CommonApiWrapper::getData)
                    .block();

            // Process cooldown
            CooldownUtils.cooldown(gatheringResponse.getCooldown());
            character = gatheringResponse.getCharacter();
        }
        return character;
    }
}
