package com.slvrmn.DNFAssistant.GamePackage;

import com.slvrmn.DNFAssistant.Model.Robot;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

public class Battle extends Thread {

    boolean buffUsed = false;

    @Override
    public void run() {
        try {
            mainLoop:
            while (Assistant.getInstance().isRunning()) {
                if (!BattleController.isBattling || (!BattleController.isPathfinding && !BattleController.isAttacking)) {
                    sleep(ScreenCheck.CHECK_INTERVAL);
                    continue;
                }
                if (BattleController.isPathfinding) {
                    if (Actions.UseBuff() && !buffUsed) {
                        buffUsed = true;
                        continue;
                    }
                    buffUsed = false;
                    MLog.info("PathFind: 自动寻路中");
                    int pressTime = Utility.RandomInt(4400, 4500);
                    Actions.PressLongAttack(pressTime, ScreenCheck.CHECK_INTERVAL);

                    if (!Actions.SleepCheckIsPathfinding(pressTime)) {
                        MLog.info("PathFind: ----------停止寻路----------");
                        continue;
                    }
                } else if (BattleController.isAttacking) {
                    MLog.info("PathFind: 自动攻击中");
                    if (ScreenCheck.skills[ScreenCheck.skills.length - 4] &&
                            (ScreenCheck.inBoss || ScreenCheck.inLion ||
                                    ScreenCheck.inHell || ScreenCheck.dailyNonMapDungeon)) {
                        MLog.info("PathFind: ----------使用觉醒----------");
                        Robot.Press(Presets.skillRecs[ScreenCheck.skills.length - 4], Utility.RandomInt(1, 2));
                        continue;
                    }
                    for (int i = 0; i < ScreenCheck.skills.length - 4; i++) {
                        if (!Assistant.getInstance().isRunning()) {
                            return;
                        }
                        if (ScreenCheck.skills[i]) {
                            if (!BattleController.isAttacking) {
                                MLog.info("PathFind: ----------停止技能----------");
                                continue mainLoop;
                            }
                            Robot.Press(Presets.skillRecs[i], Utility.RandomInt(2, 3));
                            sleep(300);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
