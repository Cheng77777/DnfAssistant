package com.noxen.FarmKing.GamePackage;

import com.noxen.FarmKing.Model.Robot;
import com.noxen.FarmKing.Tools.MLog;
import com.noxen.FarmKing.Tools.Utility;

public class Attack extends Thread {

    @Override
    public void run() {
        try {
            mainLoop:
            while (Assistant.RUN) {
                if (!BattleController.isBattling || !BattleController.isAttacking) {
                    sleep(ScreenCheck.CHECK_INTERVAL);
                    continue;
                }
                MLog.info("自动战斗中");
                int random = Utility.RandomInt(2, 5);
                Actions.PressMultipleAttacks(random);
                if (ScreenCheck.skills[ScreenCheck.skills.length - 4] &&
                        (ScreenCheck.inBoss || ScreenCheck.inLion ||
                                ScreenCheck.inHell || ScreenCheck.dailyNonMapDungeon)) {
                    Robot.Press(Presets.skillRecs[ScreenCheck.skills.length - 4], Utility.RandomInt(1, 2));
                    return;
                }
                for (int i = 0; i < ScreenCheck.skills.length - 4; i++) {
                    if (!Assistant.RUN) {
                        return;
                    }
                    if (ScreenCheck.skills[i]) {
                        if (BattleController.isPathfinding) {
                            continue mainLoop;
                        }
                        Robot.Press(Presets.skillRecs[i], Utility.RandomInt(2, 3));
                        sleep(300);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
