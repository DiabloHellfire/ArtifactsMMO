package com.ArtifactsMMO.ArtifactsMMO.action;

import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.wrapper.CommonApiWrapper;
import com.ArtifactsMMO.ArtifactsMMO.utils.CooldownUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.ArtifactsMMO.ArtifactsMMO.utils.ActionUrlUtils.FIGHT_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class FightAction implements Action {

    private final WebClient webClient;

    public Character fight() {
        var fightResponse = webClient.post()
                .uri(FIGHT_URL)
                .bodyValue("")
                .retrieve()
                .bodyToMono(CommonApiWrapper.class)
                .map(CommonApiWrapper::getData)
                .block();

        // Process cooldown
        CooldownUtils.cooldown(fightResponse.getCooldown());

        return fightResponse.getCharacter();
    }
}
