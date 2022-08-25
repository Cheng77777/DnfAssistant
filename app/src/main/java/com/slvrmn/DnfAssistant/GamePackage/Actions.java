package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.buffs;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasContinue;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasRepair;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isEnergyEmpty;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isInventoryFull;
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
        Robot.Press(Presets.confirmGoOutRec);
        sleep(2000);
    }

    static void MoveAround() throws InterruptedException {
        MLog.info("稍微移动");
        int random;
        for (int i = 0; i < 4; i++) {
            random = Utility.RandomInt(100, 200);
            Robot.LongPress(Presets.joystickRecs[0], Presets.joystickRecs[1], random);
            sleep(random);
        }
    }

    static void GetReward() throws InterruptedException {
        MLog.info("翻牌并进入下次战斗");
        sleep(1000);
        int random = Utility.RandomInt(7, 8);
        Robot.Press(Presets.skillRecs[0], random);
        sleep(3000);

        while (!hasContinue.isValid()) {
            if (!RUN) {
                Utility.show("线程停止运行");
                return;
            }
            sleep(CHECK_INTERVAL);
        }

        /** 拾取物品 **/
        MLog.info("拾取物品");
        MoveAround();
        random = Utility.RandomInt(3500, 4000);
        PressLongAttack(random, random);

        /** 检测背包 **/
        if (isInventoryFull) {
            CleanInventory();
        }

        if (hasRepair.isValid()){

        }

        /** 进入下一次副本 **/
        if (isEnergyEmpty) {
            //TODO
            return;
        } else {
            Robot.Press(hasContinue);
            sleep(1000);
            Robot.Press(Presets.continueConfirmRec);
        }
    }

    static void CleanInventory() throws InterruptedException {
        while (!ScreenCheck.isInventoryFull) {
            if (RUN) {
                return;
            }
            Robot.Press(Presets.inventoryRec);
            sleep(1500);
        }
        BreakItems();
        SellItems();
        ScreenCheck.hasContinue = Rectangle.INVALID_RECTANGLE;
        while (!ScreenCheck.hasContinue.isValid()) {
            if (RUN) {
                return;
            }
            PressBack();
            sleep(1000);
        }
    }

    static void BreakItems() throws InterruptedException {
        MLog.info("分解物品");
        Robot.Press(Presets.breakRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(2000);
        Robot.Press(Presets.breakConfirmRec);
        sleep(4000);
        PressBack();
        PressBack();
    }

    static void SellItems() throws InterruptedException {
        MLog.info("出售物品");
        Robot.Press(Presets.sellRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(2000);
        Robot.Press(Presets.sellConfirmRec);
        sleep(4000);
        PressBack();
        PressBack();
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

    static void UseBuff() throws InterruptedException {
        for (int i = 0; i < buffs.length; i++) {
            if (buffs[i]) {
                Robot.Press(Presets.buffRecs[i]);
                sleep(500);
                break;
            }
        }
    }
}
