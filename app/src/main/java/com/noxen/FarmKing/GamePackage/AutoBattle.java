package com.noxen.FarmKing.GamePackage;

import static com.noxen.FarmKing.GamePackage.Actions.PressLongAttack;
import static com.noxen.FarmKing.GamePackage.Actions.UseSkills;
import static com.noxen.FarmKing.GamePackage.Assistant.RUN;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasMonster;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isBattling;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isDamaging;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isPathFinding;

import com.noxen.FarmKing.Tools.MLog;
import com.noxen.FarmKing.Tools.Utility;

public class AutoBattle extends Thread {

    @Override
    public void run() {
        try {
            while (RUN) {
                if (!isBattling) {
                    sleep(CHECK_INTERVAL);
                    continue;
                }
                MLog.info("自动战斗中");
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
