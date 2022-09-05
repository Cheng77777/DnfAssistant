package com.slvrmn.DNFAssistant.GamePackage;

import com.slvrmn.DNFAssistant.Model.Rectangle;
import com.slvrmn.DNFAssistant.Model.Robot;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

public class Battle extends Thread {

    int buffCoolDown = 0;

    @Override
    public void run() {
        try {
            mainLoop:
            while (!Assistant.getInstance().isStopped()) {
                buffCoolDown--;
                if (!BattleController.isBattling || Assistant.getInstance().isPausing()) {
                    sleep(3000);
                    continue;
                }
                MLog.info("Battle: Looping " + BattleController.isPathfinding + " " + BattleController.isAttacking);
                if (!BattleController.isPathfinding && !BattleController.isAttacking) {
                    sleep(ScreenCheck.CHECK_INTERVAL);
                    continue;
                }
                if (BattleController.isPathfinding) {
                    if (!ScreenCheck.hasMonster && ScreenCheck.hasRepair.isValid()) {
                        MLog.info("Battle: 需要修理");
                        if (!ScreenCheck.inHell && !ScreenCheck.inBoss) {
                            Actions.RepairEquipments();
                        }
                    }
                    if (buffCoolDown <= 0) {
                        if (Actions.UseBuff()) {
                            buffCoolDown = 20;
                            continue;
                        }
                    }
                    MLog.info("Battle: 自动寻路中");
                    int pressTime = Utility.RandomInt(7500, 8000);
                    Actions.PressLongAttack(pressTime, ScreenCheck.CHECK_INTERVAL, "Battle.39");

                    ScreenCheck.hasRepair = Rectangle.INVALID_RECTANGLE;

                    if (!Actions.SleepCheckIsPathfinding(pressTime)) {
                        MLog.info("Battle: ----------停止寻路----------");
                        continue;
                    }
                } else if (BattleController.isAttacking) {
                    MLog.info("Battle: 自动攻击中");
                    if (ScreenCheck.skills[ScreenCheck.skills.length - 4] &&
                            (ScreenCheck.inBoss || ScreenCheck.inLion ||
                                    ScreenCheck.inHell || ScreenCheck.dailyNonMapDungeon)) {
                        MLog.info("Battle: ----------使用觉醒----------");
                        Robot.Press(Presets.skillRecs[ScreenCheck.skills.length - 4], Utility.RandomInt(1, 2));
                        continue;
                    }
                    int pressTime = Utility.RandomInt(3, 4);
                    Actions.PressMultipleAttacks(pressTime, "Battle.57");
                    for (int i = 0; i < ScreenCheck.skills.length - 4; i++) {
                        if (!Assistant.getInstance().isRunning()) {
                            return;
                        }
                        if (!BattleController.isAttacking) {
                            MLog.info("Battle: ----------停止技能----------");
                            sleep(1000);
                            pressTime = Utility.RandomInt(3, 4);
                            Actions.PressMultipleAttacks(pressTime, "Battle.66");
                            continue mainLoop;
                        }
                        if (ScreenCheck.skills[i]) {
                            Robot.Press(Presets.skillRecs[i], Utility.RandomInt(2, 3));
//                            pressTime = Utility.RandomInt(3, 4);
//                            Actions.PressMultipleAttacks(pressTime, "Battle.69");
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
