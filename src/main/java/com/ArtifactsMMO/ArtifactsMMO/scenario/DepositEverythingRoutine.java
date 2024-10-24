package com.ArtifactsMMO.ArtifactsMMO.scenario;

import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.MovementAction;
import com.ArtifactsMMO.ArtifactsMMO.model.character.Character;
import com.ArtifactsMMO.ArtifactsMMO.model.place.Bank;
import com.ArtifactsMMO.ArtifactsMMO.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositEverythingRoutine extends Scenario {
    private final MovementAction movementAction;
    private final CharacterService characterService;
    private final Bank bank;

    public Character depositEverything() {
        // Retrieve our character informations
        var character = characterService.getCharacter();

        // Move to bank
        character = movementAction.move(bank, character);

        // Deposit everything
        return bank.getAction(DepositAction.class).depositEverything(character);
    }
}
