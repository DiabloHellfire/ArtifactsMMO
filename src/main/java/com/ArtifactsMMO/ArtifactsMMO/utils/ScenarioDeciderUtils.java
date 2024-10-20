package com.ArtifactsMMO.ArtifactsMMO.utils;

import com.ArtifactsMMO.ArtifactsMMO.action.GrandExchangeAction;
import com.ArtifactsMMO.ArtifactsMMO.model.item.*;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningBankRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.MiningGrandExchangeRoutine;
import com.ArtifactsMMO.ArtifactsMMO.scenario.Scenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScenarioDeciderUtils {
    private GrandExchangeAction grandExchangeAction;

    private List<Item> sellableItems;
    private List<Scenario> scenarii;

    public ScenarioDeciderUtils(@Autowired GrandExchangeAction grandExchangeAction,
                                @Autowired Copper copper,
                                @Autowired CopperBoots copperBoots,
                                @Autowired CopperDagger copperDagger,
                                @Autowired CopperOre copperOre,
                                @Autowired MiningGrandExchangeRoutine miningGrandExchangeRoutine,
                                @Autowired MiningBankRoutine miningBankRoutine) {
        this.grandExchangeAction = grandExchangeAction;
        this.sellableItems = List.of(copper, copperBoots, copperDagger, copperOre);
        this.scenarii = List.of(miningGrandExchangeRoutine, miningBankRoutine);
    }

    public List<Item> decideItemsForScenario() {
        Map<String, Item> sellableItemMap = sellableItems.stream()
                .collect(Collectors.toMap(Item::getCode, item -> item));

        var priceList = grandExchangeAction.getAllGrandExchangeItemsPrice().stream()
                .filter(item -> sellableItemMap.containsKey(item.getCode()) && item.getSellPrice() > sellableItemMap.get(item.getCode()).getMinCorrectPrice())
                .sorted((item1, item2) -> {
                    var minCorrectPrice1 = sellableItemMap.get(item1.getCode()).getMinCorrectPrice();
                    var minCorrectPrice2 = sellableItemMap.get(item2.getCode()).getMinCorrectPrice();

                    // Sort by descending order of price difference
                    return Integer.compare(
                            (item2.getSellPrice() - minCorrectPrice2),
                            (item1.getSellPrice() - minCorrectPrice1)
                    );
                })
                .limit(2) // Only the 2 most profitable items
                .toList();

        log.info("Most profitable items are : {}", priceList);

        // By default, mine for the bank
        if (priceList.isEmpty()) {
            return Collections.emptyList(); // or return default mining task for bank
        }

        // If only 1 item is profitable, add copper ore to the list
        if (priceList.size() == 1) {
            return List.of(
                    sellableItemMap.get(priceList.get(0).getCode()),
                    sellableItemMap.get("copper_ore")
            );
        }

        // Return the two most profitable items
        return List.of(
                sellableItemMap.get(priceList.get(0).getCode()),
                sellableItemMap.get(priceList.get(1).getCode())
        );
    }

    public Runnable getScenarioFromItem(Item item) {
        if(null == item) { // Nothing is interesting to sell. Mine for the bank
            return ((MiningBankRoutine) getScenarioFromName("miningBankRoutine"))::copperRoutine;
        }
        return switch (item.getCode()) {
            case "copper" ->
                    ((MiningGrandExchangeRoutine) getScenarioFromName("miningGrandExchangeRoutine"))::copperRoutine;
            case "copper_boots" ->
                    ((MiningGrandExchangeRoutine) getScenarioFromName("miningGrandExchangeRoutine"))::copperBootsRoutine;
            case "copper_dagger" ->
                    ((MiningGrandExchangeRoutine) getScenarioFromName("miningGrandExchangeRoutine"))::copperDaggerRoutine;
            case "copper_ore" ->
                    ((MiningGrandExchangeRoutine) getScenarioFromName("miningGrandExchangeRoutine"))::copperOreRoutine;
            default -> ((MiningBankRoutine) getScenarioFromName("miningBankRoutine"))::copperRoutine;
        };
    }

    private Scenario getScenarioFromName(String name) {
        return scenarii.stream()
                .filter(scenario -> scenario.getScenarioName().equals(name)).findFirst().get();
    }
}
