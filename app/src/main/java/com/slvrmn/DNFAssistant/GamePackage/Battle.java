package com.slvrmn.DNFAssistant.GamePackage;

import com.slvrmn.DNFAssistant.Model.Rectangle;
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
                    if (!ScreenCheck.hasMonster && ScreenCheck.hasRepair.isValid()) {
                        MLog.info("BattleController: 需要修理");
                        Actions.RepairEquipments();
                    }
                    if (Actions.UseBuff() && !buffUsed) {
                        buffUsed = true;
                        continue;
                    }
                    buffUsed = false;
                    MLog.info("PathFind: 自动寻路中");
                    int pressTime = Utility.RandomInt(5400, 5500);
                    Actions.PressLongAttack(pressTime, ScreenCheck.CHECK_INTERVAL);

                    ScreenCheck.hasRepair = Rectangle.INVALID_RECTANGLE;

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
                            int pressTime = Utility.RandomInt(3, 4);
                            Actions.PressMultipleAttacks(pressTime);
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
