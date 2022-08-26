package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.skillRecs;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.directionalBuff;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.directionalBuffs;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasContinue;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasGoBack;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasMonster;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasRepair;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inBoss;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inHell;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inLion;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isBattling;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isDamaging;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isEnergyEmpty;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isInventoryFull;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.skills;
import static java.lang.Thread.sleep;

import android.os.SystemClock;

import com.slvrmn.DnfAssistant.Model.Rectangle;
import com.slvrmn.DnfAssistant.Model.Robot;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.Utility;

public class Actions {

    static void PressJoystick(int direction, int duration) {
        if (direction == 1) {
            Robot.LongPress(new Rectangle(Presets.joystickRecs[1].x2,
                            Presets.joystickRecs[1].y1,
                            Presets.joystickRecs[0].x2,
                            Presets.joystickRecs[1].y2),
                    Utility.RandomInt(duration, duration + 10));
        } else if (direction == 3) {
            Robot.LongPress(new Rectangle(Presets.joystickRecs[0].x1,
                            Presets.joystickRecs[1].y1,
                            Presets.joystickRecs[1].x1,
                            Presets.joystickRecs[1].y2),
                    Utility.RandomInt(duration, duration + 10));
        }
    }

    static void PressMultipleAttacks(int multiple) throws InterruptedException {
        Robot.Press(Presets.attackRec, multiple);
    }

    static void PressLongAttack(int pressTime, int sleepTime) throws InterruptedException {
        Robot.LongPress(Presets.attackRec, pressTime);
        sleep(sleepTime);
    }

    static void GoOutDungeon() throws InterruptedException {
        Robot.Press(Presets.settingsRec);
        sleep(2000);
        Robot.Press(Presets.homeRec);
        sleep(2000);
        ScreenCheck.InitializeParameters();
        Robot.Press(Presets.confirmGoOutRec);
        sleep(2000);
    }

    static void MoveAround() throws InterruptedException {
        MLog.info("稍微移动");
        int random;
        for (int i = 0; i < 4; i++) {
            if (!RUN) {
                return;
            }
            random = Utility.RandomInt(100, 200);
            Robot.LongPress(Presets.joystickRecs[0], Presets.joystickRecs[1], random);
            sleep(random);
        }
    }

    static void GetReward() throws InterruptedException {
        MLog.info("翻牌并进入下次战斗");
        sleep(1000);
        int random = Utility.RandomInt(7, 8);
        Robot.Press(skillRecs[0], random);
        sleep(3000);

        while (!hasContinue.isValid()) {
            if (!RUN) {
                return;
            }
            sleep(CHECK_INTERVAL);
        }

        PickDrops();

        /** 检测修理 **/
        if (hasRepair.isValid()) {
            RepairEquipments();
        }

        /** 检测背包 **/
        if (isInventoryFull.isValid()) {
            CleanInventory();
        }

        if (isEnergyEmpty) {
            /** 回城 **/
            Robot.Press(hasGoBack);
            sleep(1000);
            ScreenCheck.InitializeParameters();
            return;
        } else {
            /** 进入下一次副本 **/
            Robot.Press(hasContinue);
            sleep(1000);
            Robot.Press(Presets.continueConfirmRec);
            sleep(1000);
            ScreenCheck.InitializeParameters();
            sleep(1000);
        }
    }

    static void PickDrops() throws InterruptedException {
        /** 拾取物品 **/
        MLog.info("拾取物品");
        MoveAround();
        int random = Utility.RandomInt(3000, 3200);
        PressLongAttack(random, random);
    }

    static void RepairEquipments() throws InterruptedException {
        Robot.Press(hasRepair);
        sleep(1000);
        Robot.Press(Presets.confirmRepairRecs);
        sleep(1000);
        PressBack();
        sleep(1000);
    }

    static void CleanInventory() throws InterruptedException {
        Robot.Press(ScreenCheck.isInventoryFull);
        sleep(1500);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(2000);
        Robot.Press(Presets.breakConfirmRec);
        sleep(4000);
        PressBack();
        PressBack();
        Robot.Press(Presets.sellRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(2000);
        Robot.Press(Presets.sellConfirmRec);
        sleep(4000);
        PressBack();
        PressBack();
        PressBack();
        PressBack();
        sleep(1000);
    }

    static void PressBack() throws InterruptedException {
        MLog.info("点击返回按钮");
        Robot.Press(Presets.backRec);
        sleep(1000);
    }

    static void BackJump() throws InterruptedException {
        MLog.info("后跳");
        Robot.Press(Presets.backJumpRec);
        sleep(300);
    }

    static void Dodge() throws InterruptedException {
        MLog.info("闪避");
        Robot.swipe(Presets.dodgeRecs[0], Presets.dodgeRecs[1], Utility.RandomInt(120, 130));
        SystemClock.sleep(800);
        PressMultipleAttacks(3);
    }

    static boolean UseBuff() throws InterruptedException {
        for (int i = skills.length - 1; i >= skills.length - 3; i--) {
            if (!RUN) {
                Utility.show("线程停止运行");
                return false;
            }
            if (skills[i]) {
                if (i == skills.length - 1 && directionalBuff) {
                    for (int j = 0; j < directionalBuffs.length; j++) {
                        if (!RUN) {
                            Utility.show("线程停止运行");
                            return false;
                        }
                        boolean b = directionalBuffs[j];
                        if (b) {
                            UseDirectionalBuff(j);
                            return true;
                        }
                    }
                    return false;
                }
                sleep(500);
                Robot.Press(skillRecs[i]);
                sleep(500);
                return true;
            }
        }
        return false;
    }

    static void UseDirectionalBuff(int direction) throws InterruptedException {
        Rectangle destination = skillRecs[skillRecs.length - 1].Copy();
        switch (direction) {
            case 0:
                destination.Move(0, destination.YLen() * 2);
                break;
            case 1:
                destination.Move(destination.XLen() * 2, 0);
                break;
            case 2:
                destination.Move(0, -destination.YLen() * 2);
                break;
            case 3:
                destination.Move(-destination.XLen() * 2, 0);
                break;
        }
        Robot.swipe(skillRecs[skillRecs.length - 1], destination, 150);
        sleep(150);
    }

    static void UseSkills() throws InterruptedException {
        if (skills[skills.length - 4] && (inBoss || inLion || inHell)) {
            Robot.Press(Presets.skillRecs[skills.length - 4]);
            return;
        }
        for (int i = 0; i < skills.length - 4; i++) {
            if (!RUN) {
                return;
            }
            if (skills[i]) {
                Robot.Press(Presets.skillRecs[i]);
                sleep(300);
                if (!hasMonster||!isDamaging) {
                    isBattling = false;
                    isPathFinding = true;
                    return;
                }
            }
        }
    }
}
