package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Actions.BackJump;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressMultipleAttacks;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.UseSkills;
import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasMonster;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isBattling;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isDamaging;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;

public class AutoBattle extends Thread {
    private static final int backJumpCD = 30;
    private int backJump;

    @Override
    public void run() {
        try {
            while (RUN) {
                if (!isBattling) {
                    sleep(CHECK_INTERVAL);
                    continue;
                }
                if (hasMonster && isDamaging) {
                    /** 后跳 **/
                    if (backJump >= backJumpCD) {
                        BackJump();
                        backJump = 0;
                    }
                    PressMultipleAttacks(2);
                    /** 使用技能 **/
                    UseSkills();
                    backJump++;
                }
                if (!hasMonster || !isDamaging) {
                    isBattling = false;
                    isPathFinding = true;
                    continue;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
