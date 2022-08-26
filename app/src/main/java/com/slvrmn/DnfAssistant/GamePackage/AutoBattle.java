package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Actions.BackJump;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressMultipleAttacks;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.UseSkills;
import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasMonster;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inBoss;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inHell;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inLion;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isBattling;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isDamaging;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.skills;

import com.slvrmn.DnfAssistant.Model.Robot;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.Utility;

public class AutoBattle extends Thread {
    private static int backJumpCD = 30;
    private int backJump;

    @Override
    public void run() {
        try {
            while (RUN) {
                if (isBattling) {
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
                    if (!hasMonster||!isDamaging) {
                        isBattling = false;
                        isPathFinding = true;
                        continue;
                    }
                } else {
                    sleep(CHECK_INTERVAL);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
