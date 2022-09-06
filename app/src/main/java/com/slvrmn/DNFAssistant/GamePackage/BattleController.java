package com.slvrmn.DNFAssistant.GamePackage;


import static com.slvrmn.DNFAssistant.GamePackage.Actions.MoveAround;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PressJoystick;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PressLongAttack;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.GetScreenshot;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.InitializeDailyQuestParameters;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.beforeLion;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.canRecover;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.hasDailyContinue;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.hasDailySelect;

import com.slvrmn.DNFAssistant.Model.Image;
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


        while (!Image.findPointByCheckImageModel(GetScreenshot(), Presets.continueButtonModel).isValid()) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            sleep(ScreenCheck.CHECK_INTERVAL);
        }

        Actions.PickDrops();

        if (Image.findPointByCheckImageModel(GetScreenshot(), Presets.inventoryFullButtonModel).isValid()) {
            MLog.info("BattleController: 背包满");
            Actions.CleanInventory();
        }

        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        if (ScreenCheck.isEnergyEmpty) {
            isBattling = false;
            MLog.info("BattleController: 疲劳为0,切换角色");
            Actions.FindAndTapTilDisappear(Presets.goOutDungeonButtonModel);
            do {
                sleep(3000);
            }
            while (!Image.findPointByCheckImageModel(GetScreenshot(), Presets.dailyMenuModel).isValid());
            Actions.SwitchCharacter();
            return;
        } else {
            MLog.info("BattleController: 疲劳不为0,继续副本");
            Actions.FindAndTapTilDisappear(Presets.continueModels);
            sleep(3000);
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

    public static synchronized void StopAll(String info) throws InterruptedException {
        MLog.info(info);
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
            while (!Assistant.getInstance().isStopped()) {
                if (Assistant.getInstance().isPausing()) {
                    sleep(3000);
                    continue;
                }
                if (isBattling) {
                    if (isPathfinding) {
//                        MLog.in fo("BattleController: 寻路中");
                        if (ScreenCheck.isDamaging && ScreenCheck.hasMonster) {
                            StartAttacking();
                            continue;
                        }
                        if (isFarming) {
                            if (ScreenCheck.hasReward) {
                                MLog.info("BattleController: 可翻牌");
                                StopAll("BattleController.133");
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
                                StopAll("BattleController144");
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
                                    random = Utility.RandomInt(200, 250);
                                    PressJoystick(3, random);
                                    sleep(random);
                                    random = Utility.RandomInt(1400, 1500);
                                    PressLongAttack(random, random, "BattleController.171");
                                    if (!Actions.SleepCheckHasMonster(random)) {
                                        MLog.info("BattleController: 狮子头前继续攻击");
                                        StartAttacking();
                                        continue mainLoop;
                                    }
                                } while (beforeLion);
                                sleep(500);
                                StartPathfinding();
                                MLog.info("BattleController: 不在狮子头前");
                            }
                            if(ScreenCheck.inHell && !ScreenCheck.hasMonster){
                                if (ScreenCheck.isEnergyEmpty) {
                                    StopAll("BattleController.173");
                                    int random;
                                    for (int i = 0; i < 3; i++) {
                                        random = Utility.RandomInt(2000, 3000);
                                        PressLongAttack(random, 0, "BattleController.176");
                                        if (!Actions.SleepCheckHasMonster(random)) {
                                            MLog.info("BattleController: ----------进入战斗----------");
                                            sleep(100);
                                            StartAttacking();
                                            continue mainLoop;
                                        }
                                    }
                                    isBattling = false;
                                    Actions.GoOutDungeonFromMenu();
                                    sleep(10000);
                                    Actions.SwitchCharacter();
                                } else {
                                    StartPathfinding();
                                }
                                continue mainLoop;
                            }
                        } else {
                            if (hasDailyContinue) {
                                MLog.info("BattleController: 每日副本可继续");
                                StopAll("BattleController.197");
                                MoveAround();
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random, "BattleController.190");
                                }
                                Actions.FindAndTapTilDisappear(Presets.dailyContinueButtonModel);
                                Actions.FindAndTapTilDisappear(Presets.continueConfirmButtonModel);
                                sleep(1000);
                                InitializeDailyQuestParameters();
                                sleep(1000);
                                continue;
                            }
                            if (hasDailySelect) {
                                MLog.info("BattleController: 每日副本可选择");
                                StopAll("BattleController196");
                                MoveAround();
                                int random;
                                for (int i = 0; i < 3; i++) {
                                    random = Utility.RandomInt(2000, 3000);
                                    PressLongAttack(random, random, "BattleController.206");
                                }
                                Actions.FindAndTapTilDisappear(Presets.dailySelectButtonModel);
                                sleep(3000);
                                InitializeDailyQuestParameters();
                                isBattling = false;
                                continue;
                            }
                        }
                    } else if (isAttacking) {
//                        MLog.info("BattleController: 攻击中");
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
