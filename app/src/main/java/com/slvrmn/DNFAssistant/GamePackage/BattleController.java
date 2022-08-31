package com.slvrmn.DNFAssistant.GamePackage;


import static com.slvrmn.DNFAssistant.GamePackage.Actions.MoveAround;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PressJoystick;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PressLongAttack;
import static com.slvrmn.DNFAssistant.GamePackage.Assistant.RUN;
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
        MLog.info("翻牌并进入下次战斗");
        sleep(1000);
        int random = Utility.RandomInt(7, 9);
        Robot.Press(Presets.skillRecs[0], random);
        sleep(3000);

        while (!ScreenCheck.hasContinue.isValid()) {
            if (!Assistant.RUN) {
                return;
            }
            sleep(ScreenCheck.CHECK_INTERVAL);
        }

        Actions.PickDrops();

        if (ScreenCheck.hasRepair.isValid()) {
            Actions.RepairEquipments();
        }

        if (ScreenCheck.isInventoryFull.isValid()) {
            Actions.CleanInventory();
        }

        if (ScreenCheck.isEnergyEmpty) {
            Robot.Press(ScreenCheck.hasGoBack, Utility.RandomInt(2, 3));
            sleep(3000);
            isBattling = false;
            Actions.SwitchCharacter();
            return;
        } else {
            if (!Assistant.RUN) {
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

    public static synchronized void StartBattle() throws InterruptedException {
        isBattling = true;
//        isPathfinding = true;
//        isAttacking = false;
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
            while (Assistant.RUN) {
                if (isBattling) {
                    if (ScreenCheck.canDodge) {
                        Actions.Dodge();
                        StartAttacking();
                        sleep(3000);
                        continue;
                    }
                    if (isPathfinding) {
                        if (ScreenCheck.hasMonster && ScreenCheck.isDamaging) {
                            StartAttacking();
                            continue;
                        }
                        if (isFarming) {
                            if (ScreenCheck.hasReward) {
                                StopAll();
                                GetReward();
                                continue;
                            }
                            if (ScreenCheck.hasResult) {
                                StartPathfinding();
                                continue;
                            }
                            if (ScreenCheck.beforeLion && !ScreenCheck.hasMonster) {
                                StopAll();
                                do {
                                    if (!RUN) {
                                        return;
                                    }
                                    int random = Utility.RandomInt(2000, 2300);
                                    Robot.LongPress(Presets.moveRecs[0], random);
                                    if (!Actions.SleepCheckBeforeLion(random)) {
                                        continue mainLoop;
                                    }
                                    random = Utility.RandomInt(1000, 1200);
                                    Robot.LongPress(Presets.moveRecs[2], random);
                                    if (!Actions.SleepCheckBeforeLion(random)) {
                                        continue mainLoop;
                                    }
                                    random = Utility.RandomInt(100, 150);
                                    PressJoystick(3, random);
                                    sleep(random);
                                    random = Utility.RandomInt(1500, 1600);
                                    PressLongAttack(random, random);
                                    if (!Actions.SleepCheckIsDamaging(random)) {
                                        StartPathfinding();
                                        continue mainLoop;
                                    }
                                } while (beforeLion);
                            }
                        } else {
                            if (hasDailyContinue.isValid()) {
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
                                isBattling = false;
                                continue;
                            }
                            if (hasDailySelect.isValid()) {
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
                        if (ScreenCheck.screenFreezeTime >= 100) {
                            Actions.MoveAround();
                            if (ScreenCheck.inHell) {
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random);
                                }
                                isBattling = false;
                                Actions.GoOutDungeon();
                                Actions.SwitchCharacter();
                                StopAll();
                            }
                        }
                    } else {
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
