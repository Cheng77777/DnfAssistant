package com.slvrmn.DNFAssistant.GamePackage;

import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.canRecover;

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
                if (canRecover) {
                    BattleController.StopAll("Battle.25");
                    Actions.FindAndTapTilDisappear(Presets.recoverModels[1]);
                    sleep(500);
                    BattleController.StartAttacking();
                    canRecover = false;
                    continue;
                }
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
                            buffCoolDown = 5;
                            continue;
                        }
                    }
                    MLog.info("Battle: 自动寻路中");
                    int pressTime = Utility.RandomInt(1, 2);
                    Actions.PressMultipleAttacks(pressTime, "Battle.52");
                    pressTime = Utility.RandomInt(7500, 8000);
                    sleep(100);
                    Actions.PressLongAttack(pressTime, ScreenCheck.CHECK_INTERVAL, "Battle.54");

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
                    for (int i = 0; i < ScreenCheck.skills.length - 4; i++) {
                        if (!Assistant.getInstance().isRunning()) {
                            return;
                        }
                        if (!BattleController.isAttacking) {
                            MLog.info("Battle: ----------停止技能----------");
//                            sleep(1000);
                            continue mainLoop;
                        }
                        if (ScreenCheck.skills[i]) {
                            Robot.Press(Presets.skillRecs[i], Utility.RandomInt(1, 2));
                            int pressTime = Utility.RandomInt(1900, 2000);
                            Actions.PressLongAttack(pressTime, 800, "Battle.83");
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
