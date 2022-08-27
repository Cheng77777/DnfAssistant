package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressLongAttack;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressMultipleAttacks;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.UseSkills;
import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasMonster;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isBattling;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isDamaging;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;

import com.slvrmn.DnfAssistant.Tools.Utility;

public class AutoBattle extends Thread {

    @Override
    public void run() {
        try {
            while (RUN) {
                if (!isBattling) {
                    sleep(CHECK_INTERVAL);
                    continue;
                }
                if (hasMonster && isDamaging) {
                    int random = Utility.RandomInt(300,500);
                    PressLongAttack(random,random);
                    UseSkills();
                }
                if (!hasMonster || !isDamaging) {
                    isBattling = false;
                    isPathFinding = true;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
