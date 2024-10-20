package com.ArtifactsMMO.ArtifactsMMO.utils;

import com.ArtifactsMMO.ArtifactsMMO.action.CraftingAction;
import com.ArtifactsMMO.ArtifactsMMO.action.DepositAction;
import com.ArtifactsMMO.ArtifactsMMO.action.FightAction;
import com.ArtifactsMMO.ArtifactsMMO.model.Action;
import com.ArtifactsMMO.ArtifactsMMO.scenario.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ScenarioListUtils {
    public static List<Scenario> getActions(@Autowired FightingLoopRoutine fightingLoopRoutine,
                                            @Autowired MiningBankRoutine miningBankRoutine,
                                            @Autowired MiningGrandExchangeRoutine miningGrandExchangeRoutine,
                                            @Autowired MiningRecycleRoutine miningRecycleRoutine) {
        return List.of(fightingLoopRoutine, miningBankRoutine, miningGrandExchangeRoutine, miningRecycleRoutine);
    }
}
