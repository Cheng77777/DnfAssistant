package com.noxen.FarmKing.GamePackage;


import static com.noxen.FarmKing.GamePackage.Actions.MoveAround;
import static com.noxen.FarmKing.GamePackage.Actions.PressJoystick;
import static com.noxen.FarmKing.GamePackage.Actions.PressLongAttack;
import static com.noxen.FarmKing.GamePackage.Actions.SwitchCharacter;
import static com.noxen.FarmKing.GamePackage.Assistant.RUN;
import static com.noxen.FarmKing.GamePackage.Presets.continueConfirmRec;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.InitializeDailyQuestParameters;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.beforeLion;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasDailyContinue;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasDailySelect;

import com.noxen.FarmKing.Model.Robot;
import com.noxen.FarmKing.Tools.MLog;
import com.noxen.FarmKing.Tools.Utility;

public class BattleController extends Thread {
    public static volatile boolean isBattling = false;
    public static volatile boolean isFarming = false;
    public static volatile boolean isAttacking = false;
    public static volatile boolean isPathfinding = false;
    private boolean initialized;
    private Attack attack;
    private PathFind pathFind;

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
                            if (ScreenCheck.beforeLion) {
                                StopAll();

                                do {
                                    if (!RUN) {
                                        return;
                                    }
                                    int random = Utility.RandomInt(2000, 2300);
                                    Robot.LongPress(Presets.moveRecs[0], 0);
                                    if (!Actions.SleepCheckBeforeLion(random)) {
                                        continue mainLoop;
                                    }
                                    random = Utility.RandomInt(1000, 1200);
                                    Robot.LongPress(Presets.moveRecs[2], 0);
                                    if (!Actions.SleepCheckBeforeLion(random)) {
                                        continue mainLoop;
                                    }
                                    random = Utility.RandomInt(100, 150);
                                    PressJoystick(3, random);
                                    sleep(random);
                                    random = Utility.RandomInt(1500, 1600);
                                    PressLongAttack(random, 0);
                                    if (!Actions.SleepCheckIsDamaging(random)) {
                                        StartAttacking();
                                        continue mainLoop;
                                    }
                                } while (beforeLion);
                            }
                        } else {
                            if (hasDailyContinue.isValid()) {
                                StopAll();
                                isBattling = false;
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
                                StopAll();
                                isBattling = false;
                                MoveAround();
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random);
                                }
                                Robot.Press(hasDailySelect);
                                sleep(3000);
                                InitializeDailyQuestParameters();
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

    private void Initialize() {
        attack = new Attack();
        attack.start();
        pathFind = new PathFind();
        pathFind.start();
        initialized = true;
    }
}
