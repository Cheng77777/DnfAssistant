package com.slvrmn.DNFAssistant.GamePackage;


import static com.slvrmn.DNFAssistant.GamePackage.Actions.MoveAround;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PressJoystick;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PressLongAttack;
import static com.slvrmn.DNFAssistant.GamePackage.Presets.continueConfirmRec;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.InitializeDailyQuestParameters;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.beforeLion;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.hasDailyContinue;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.hasDailySelect;

import com.slvrmn.DNFAssistant.Model.Robot;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

public class BattleController extends Thread {
    public static volatile boolean isBattling = false;
    public static volatile boolean isFarming = false;
    public static volatile boolean isAttacking = false;
    public static volatile boolean isPathfinding = false;
    private boolean initialized;

    static void GetReward() throws InterruptedException {
        MLog.info("BattleController: 翻牌并进入下次战斗");
        sleep(1000);
        int random = Utility.RandomInt(7, 9);
        Robot.Press(Presets.skillRecs[0], random);
        sleep(3000);

        while (!ScreenCheck.hasContinue.isValid()) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            sleep(ScreenCheck.CHECK_INTERVAL);
        }

        Actions.PickDrops();

        if (ScreenCheck.hasRepair.isValid()) {
            MLog.info("BattleController: 需要修理");
            Actions.RepairEquipments();
        }

        if (ScreenCheck.isInventoryFull.isValid()) {
            MLog.info("BattleController: 背包满");
            Actions.CleanInventory();
        }

        if (ScreenCheck.isEnergyEmpty) {
            isBattling = false;
            MLog.info("BattleController: 疲劳为0,切换角色");
            Robot.Press(ScreenCheck.hasGoBack, Utility.RandomInt(2, 3));
            sleep(3000);
            Actions.SwitchCharacter();
            return;
        } else {
            MLog.info("BattleController: 疲劳不为0,继续副本");
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            Robot.Press(ScreenCheck.hasContinue);
            sleep(1000);
            Robot.Press(Presets.continueConfirmRec);
            sleep(1000);
            ScreenCheck.InitializeFarmingParameters();
            sleep(1000);
        }
    }

    public static synchronized void StartFarming() throws InterruptedException {
        isFarming = true;
        sleep(500);
    }

    public static synchronized void StopFarming() throws InterruptedException {
        isFarming = false;
        sleep(500);
    }

    public static synchronized void StartBattle() throws InterruptedException {
        isBattling = true;
        sleep(500);
    }

    public static synchronized void StartPathfinding() throws InterruptedException {
        isPathfinding = true;
        isAttacking = false;
        sleep(500);
    }

    public static synchronized void StartAttacking() throws InterruptedException {
        isPathfinding = false;
        isAttacking = true;
        sleep(500);
    }

    public static synchronized void StopAll() throws InterruptedException {
        isPathfinding = false;
        isAttacking = false;
        sleep(500);
    }

    @Override
    public void run() {
        try {
            if (!initialized) {
                Initialize();
            }
            mainLoop:
            while (Assistant.getInstance().isRunning()) {
                if (isBattling) {
                    if (ScreenCheck.canDodge) {
                        MLog.info("BattleController: 可闪避");
                        Actions.DodgeLeft();
                        StartAttacking();
                        sleep(3000);
                        continue;
                    }
                    if (isPathfinding) {
                        MLog.info("BattleController: 寻路中");
                        if (ScreenCheck.isDamaging) {
                            StartAttacking();
                            continue;
                        }
                        if (isFarming) {
                            if (ScreenCheck.hasReward) {
                                MLog.info("BattleController: 可翻牌");
                                StopAll();
                                GetReward();
                                continue;
                            }
                            if (ScreenCheck.hasResult) {
                                MLog.info("BattleController: 副本结束");
                                StartPathfinding();
                                continue;
                            }
                            if (ScreenCheck.beforeLion && !ScreenCheck.hasMonster) {
                                MLog.info("BattleController: 狮子头前");
                                StopAll();
                                do {
                                    MLog.info("BattleController: 狮子头循环");
                                    if (!Assistant.getInstance().isRunning()) {
                                        return;
                                    }
                                    int random = Utility.RandomInt(2000, 2300);
                                    Robot.LongPress(Presets.moveRecs[0], random);
                                    if (!Actions.SleepCheckBeforeLion(random + 200)) {
                                        MLog.info("BattleController: 不在狮子头前");
                                        break;
                                    }
                                    random = Utility.RandomInt(1000, 1200);
                                    Robot.LongPress(Presets.moveRecs[2], random);
                                    if (!Actions.SleepCheckBeforeLion(random)) {
                                        MLog.info("BattleController: 不在狮子头前");
                                        break;
                                    }
                                    random = Utility.RandomInt(100, 150);
                                    PressJoystick(3, random);
                                    sleep(random);
                                    random = Utility.RandomInt(1700, 1800);
                                    PressLongAttack(random, random);
                                    if (!Actions.SleepCheckHasMonster(random)) {
                                        MLog.info("BattleController: 狮子头前继续攻击");
                                        StartAttacking();
                                        continue mainLoop;
                                    }
                                } while (beforeLion);
                                Actions.DodgeDown();
                                StartPathfinding();
                                MLog.info("BattleController: 不在狮子头前");
                            }
                        } else {
                            if (hasDailyContinue.isValid()) {
                                MLog.info("BattleController: 每日副本可继续");
                                StopAll();
                                MoveAround();
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random);
                                }
                                Robot.Press(hasDailyContinue);
                                sleep(1000);
                                Robot.Press(continueConfirmRec);
                                sleep(3000);
                                InitializeDailyQuestParameters();
                                continue;
                            }
                            if (hasDailySelect.isValid()) {
                                MLog.info("BattleController: 每日副本可选择");
                                StopAll();
                                MoveAround();
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random);
                                }
                                Robot.Press(hasDailySelect);
                                sleep(3000);
                                InitializeDailyQuestParameters();
                                isBattling = false;
                                continue;
                            }
                        }
                        if (ScreenCheck.screenFreezeTime >= 15) {
                            MLog.info("BattleController: 卡住次数大于15");
                            Actions.MoveAround();
                            if (ScreenCheck.inHell && !ScreenCheck.hasMonster && ScreenCheck.isEnergyEmpty) {
                                MLog.info("BattleController: ");
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random);
                                }
                                StopAll();
                                isBattling = false;
                                Actions.GoOutDungeon();
                                sleep(10000);
                                Actions.SwitchCharacter();
                                continue;
                            }
                        }
                    } else if (isAttacking) {
                        MLog.info("BattleController: 攻击中");
                        if (!ScreenCheck.hasMonster || !ScreenCheck.isDamaging) {
                            StartPathfinding();
                            continue;
                        }
                    }
                }
                sleep(CHECK_INTERVAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Initialize() {
        isBattling = false;
        isFarming = false;
        isAttacking = false;
        isPathfinding = false;
        initialized = true;
    }
}
