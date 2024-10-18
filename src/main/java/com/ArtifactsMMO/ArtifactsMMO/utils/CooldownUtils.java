package com.ArtifactsMMO.ArtifactsMMO.utils;

import com.ArtifactsMMO.ArtifactsMMO.model.utils.Cooldown;

public class CooldownUtils {
    public static void cooldown(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void cooldown(Cooldown cooldown) {
        try {
            Thread.sleep(cooldown.getRemainingSeconds() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
