package com.slvrmn.DnfAssistant.GamePackage;

import static android.os.SystemClock.sleep;

import android.graphics.Bitmap;
import android.os.Environment;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Model.Point;
import com.slvrmn.DnfAssistant.Model.Rectangle;
import com.slvrmn.DnfAssistant.Model.Robot;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.ScreenCaptureUtil;
import com.slvrmn.DnfAssistant.Tools.Utility;

public class SecondMain implements Runnable {
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final boolean[] skills = {true, true, true, true, true, true, true};
    private static final int backJumpCD = 15;
    private static final int checkBattlingCD = 1;
    private static final int checkCoolDownCD = 1;
    private static final int checkInterval = 200;
    private static boolean autoBattle = false;
    private static boolean autoFarm = false;
    private static boolean beforeLion = false;
    private static boolean beforeLionMove = true;
    private static boolean inLion = false;
    private static boolean isBattling = true;
    private static boolean reward = false;
    private static int backJump = 99;
    private static int checkBattling = 99;
    private static int checkCoolDown = 99;
    private static boolean inHell;
    private static int stuckSteps;
    public volatile boolean run = false;

    @Override
    public void run() {
        try {
            while (run) {
                MLog.setDebug(true);
                if (!Presets.initialized) {
                    Presets.initialize();
                }

                /** 游戏初始化 **/
                //TODO
                /** 公会签到 **/
                //TODO
                /** 好友 **/
                //TODO
                /** 接受邮件 **/
                //TODO
                /** 进图 **/
                //TODO
                /** 识别地图 **/
                //TODO
                /** 识别战斗 **/
                //TODO
                AutoFarm();
                /** 日常 **/
                //TODO
                /** 换号 **/
                //TODO

//                Dodge();
//                sleep(2000);
            }
            Utility.show("线程停止运行");
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }

    private void InitializeAutoFarmParameters() {
        autoBattle = false;
        autoFarm = false;
        backJump = 99;
        beforeLion = false;
        beforeLionMove = true;
        checkBattling = 99;
        checkCoolDown = 99;
        inHell = false;
        inLion = false;
        isBattling = true;
        reward = false;
        MLog.info("初始化战斗参数");
    }

    private void AutoFarm() throws InterruptedException {

        MLog.info("自动搬砖");

        autoFarm = true;
        autoBattle = true;

        while (autoFarm) {
            /** 检测图中 **/
            while (!CheckInDungeon()) {
                sleep(200);
                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return;
                }
            }
            while (autoBattle) {
                /** 自动寻路循环 **/
                while (!PathFinding()) {
                    /** 检测退出 **/
                    if (!run) {
                        Utility.show("线程停止运行");
                        break;
                    }
                }
                if (reward) {
                    GetReward();
                } else if (inHell) {

                } else {
                    while (!AutoBattle()) {
                        /** 检测退出 **/
                        if (!run) {
                            Utility.show("线程停止运行");
                            return;
                        }
                    }
                }
                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return;
                }
            }
        }

        InitializeAutoFarmParameters();
        /** 检测退出 **/
        if (!run) {
            Utility.show("线程停止运行");
            return;
        }
    }

    private boolean CheckInDungeon() {
        MLog.info("检测是否进入地下城");
        Bitmap screenshot = GetScreenshot();
//        return Image.matchTemplate(screenshot, Presets.inDungeonIcon, 0.5, Presets.mapRec).isValid();
        return false;
    }

    private boolean PathFinding() throws InterruptedException {
        MLog.info("寻路中");

        Bitmap screenshot = GetScreenshot();

        CheckBoss(screenshot);

        /** 检测狮子头 **/
        CheckLion(screenshot);
        /** 检测BUFF **/
        CheckBuff(screenshot);

        MLog.info(beforeLion + " " + inLion);
        if (beforeLion && !inLion) {
            /** 进入狮子头房间 **/
            while (!inLion) {
                int random = Utility.RandomInt(2000, 2300);
                Robot.LongPress(Presets.moveRecs[0], random);
                sleep(random + 100);
                random = Utility.RandomInt(1000, 1200);
                Robot.LongPress(Presets.moveRecs[2], random);
                sleep(random);
                //TODO
                PressJoystick(3);
                sleep(40);
                PressLongAttack(Utility.RandomInt(50,100),50);
                if (isBattling(GetScreenshot())) {
                    isBattling = true;
                    return true;
                }
                if (Image.findPointByMulColor(screenshot, Presets.inLionRule, Presets.mapRec).isValid()) {
                    inLion = true;
                    PressJoystick(1);
                    sleep(200);
                    Dodge();
                    sleep(200);
//                    Robot.Press(Presets.uniqueSkillRec, Utility.RandomInt(3, 4));
                    break;
                }
                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return false;
                }

                if (inLion) {
                    break;
                }
                BackJump();
                random = Utility.RandomInt(2000, 2500);
                PressLongAttack(random, random);
                sleep(1000);
                for (int i = 0; i < 6; i++) {
                    screenshot = GetScreenshot();
                    if (isBattling(screenshot)) {
                        return true;
                    }
                    sleep(300);
                    /** 检测退出 **/
                    if (!run) {
                        Utility.show("线程停止运行");
                        return false;
                    }
                }

                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return false;
                }
            }
        } else {
            if (beforeLion) {
                return false;
            }
            /** 长按攻击 **/
            int pressTime = Utility.RandomInt(8000, 9000);
            PressLongAttack(pressTime, 1000);
            Bitmap oldScreenshot;
            int steps = (pressTime - 2000) / checkInterval;
            for (int i = steps; i > 0; i--) {
                oldScreenshot = screenshot;
                screenshot = GetScreenshot();

                CheckLion(screenshot);

                if (beforeLion) {
                    return false;
                }

                /** 检测战斗 **/
                if (isBattling(screenshot)) {
                    isBattling = true;
                    return true;
                }
                /** 检测闪避 **/
                if (CheckDodge(screenshot)) {
                    return false;
                }
                /** 检测卡住 **/
                if (Image.matchTemplate(
                        Image.cropBitmap(oldScreenshot, Presets.stuckRec),
                        Image.cropBitmap(screenshot, Presets.stuckRec),
                        0.99).isValid()) {
                    stuckSteps++;
                } else {
                    stuckSteps = 0;
                }
                if (stuckSteps >= 2000 / checkInterval) {
                    if (CheckHell(screenshot) && CheckEnergyEmpty(screenshot)) {
                        GoOutDungeon();
                        return true;
                    } else {
                        MoveAround();
                        return false;
                    }
                }

                /** 检测翻牌 **/
                if (CheckReward(screenshot)) {
                    reward = true;
                    return true;
                }
                sleep(checkInterval);
            }
        }
        return false;
    }

    private void PressJoystick(int direction) {
        if (direction==1){
            Robot.LongPress(new Rectangle(Presets.joystickRecs[1].x2,
                            Presets.joystickRecs[1].y1,
                            Presets.joystickRecs[0].x2,
                            Presets.joystickRecs[1].y2),
                    Utility.RandomInt(40,50));
        }
        else if (direction==3){
            Robot.LongPress(new Rectangle(Presets.joystickRecs[0].x1,
                            Presets.joystickRecs[1].y1,
                            Presets.joystickRecs[1].x1,
                            Presets.joystickRecs[1].y2),
                    Utility.RandomInt(40,50));
        }
    }

    private void GoOutDungeon() {
        Robot.Press(Presets.settingsRec);
        sleep(2000);
        Robot.Press(Presets.homeRec);
        sleep(2000);
        Robot.Press(Presets.confirmGoOutRec);
        sleep(2000);
        autoFarm = false;
    }

    private boolean AutoBattle() throws InterruptedException {
        MLog.info("自动战斗中");
        Bitmap screenshot = GetScreenshot();

        CheckBoss(screenshot);

        /** 检测狮子头 **/
        if (!beforeLion) {
            CheckLion(screenshot);
        } else {
            if (Image.findPointByMulColor(screenshot, Presets.inLionRule, Presets.mapRec).isValid()) {
                inLion = true;
                Dodge();
//                Robot.Press(Presets.uniqueSkillRec, Utility.RandomInt(3, 4));
            }
        }
        /** 连按攻击 **/
        int pressTime = Utility.RandomInt(200, 300);
        PressMultipleAttacks(pressTime);
        /** 检测战斗 **/
        for (int i = 0; i < pressTime / checkInterval; i++) {
            screenshot = GetScreenshot();

            /** 检测狮子头 **/
            CheckLion(screenshot);

            /** 检测使用技能 **/
            CheckSkill(screenshot);

            if (!isBattling) {
                return true;
            }

            sleep(checkInterval);
        }
        return false;
    }

    private void GetReward() throws InterruptedException {
        MLog.info("翻牌并进入下次战斗");
        autoBattle = false;
        Point p;

        sleep(1500);
        int random = Utility.RandomInt(3, 5);
        Robot.Press(Presets.skillRecs[4], random);
        random = Utility.RandomInt(3, 5);
        Robot.Press(Presets.skillRecs[4], random);
        sleep(3000);

        do {
            p = Image.matchTemplate(GetScreenshot(), Presets.nextButton, 0.9, Presets.completeDungeonMenuRec);
            sleep(1000);

            /** 检测退出 **/
            if (!run) {
                Utility.show("线程停止运行");
                return;
            }
        } while (!p.isValid());
        p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
        p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);

        Rectangle next = new Rectangle(p.getX(), p.getY(), p.getX() + 50, p.getY() + 10);
        /** 拾取物品 **/
        MoveAround();
        random = Utility.RandomInt(3500, 4000);
        PressLongAttack(random, random);

        /** 检测背包 **/
        CheckInventory(GetScreenshot());

        /** 进入下一次副本 **/
        CheckNextDungeon(next);

        InitializeAutoFarmParameters();
    }

    private boolean CheckReward(Bitmap screenshot) {
        MLog.info("检测翻牌");
        return Image.matchTemplate(screenshot, Presets.rewardIcon, 0.9).isValid();
    }

    private void CheckInventory(Bitmap screenshot) {
        MLog.info("检测背包是否满");
        if (Image.findPointByMulColor(screenshot, Presets.inventoryFullRules, Presets.inventoryRec).isValid()) {
            Robot.Press(Presets.inventoryRec);
            sleep(1500);
            BreakItems();
            SellItems();
            PressBack();
        } else {
        }
    }

    private void CheckNextDungeon(Rectangle next) {
        MLog.info("检测重新进入地下城");
        if (CheckEnergyEmpty(GetScreenshot())) {
            autoFarm = false;
            return;
        } else {
            Robot.Press(next);
            sleep(1000);
            Robot.Press(Presets.continueConfirmRec);
        }
    }

    private boolean CheckEnergyEmpty(Bitmap screenshot) {
        MLog.info("检测疲劳是否为0");
        if (Image.findPointByMulColor(screenshot, Presets.energyEmptyRules, Presets.energyRec).isValid()) {
            return true;
        }
        return false;
    }

    private void CheckSkill(Bitmap screenshot) throws InterruptedException {
        MLog.info("检测技能");
        /** 后跳 **/
        if (backJump >= backJumpCD) {
            BackJump();
            backJump = 0;
            sleep(300);
        }
        /** 刷新技能冷却 **/
        if (checkCoolDown >= checkCoolDownCD) {
            RefreshCoolDownList(screenshot);
        }
        /** 使用技能 **/
        UseSkills();
        backJump++;
        checkCoolDown++;
        checkBattling++;
    }

    private void UseSkills() throws InterruptedException {
        MLog.info("使用技能");
        int count = 0;
        for (int i = 0; i < skills.length; i++) {
            /** 检测结束战斗 **/
            if (!isBattling(GetScreenshot())) {
                isBattling = false;
                backJump = 99;
                return;
            }
            if (skills[i]) {
                count++;
                Robot.Press(Presets.skillRecs[i]);
                sleep(500);
                skills[i] = false;
                CheckBuff(GetScreenshot());
                PressLongAttack(1000, 1000);
            }
            if (count >= 2) {
                return;
            }
        }
    }

    private void RefreshCoolDownList(Bitmap screenshot) {
        MLog.info("刷新技能冷却表");
        for (int i = 0; i < skills.length; i++) {
            skills[i] = CheckSkillCoolDown(screenshot, i);
        }
    }

    private boolean CheckSkillCoolDown(Bitmap screenshot, int i) {
//        return Image.findPoint(screenshot, Presets.skillColors, Presets.skillRecs[i]).isValid();
        return false;
    }

    private boolean isBattling(Bitmap screenshot) {
        MLog.info("检测是否战斗中");
        boolean isBattling = true;
        isBattling = (isBattling && Image.matchTemplate(screenshot, Presets.monsterLevelNumberFive, 0.5, Presets.monsterLevelRec).isValid());
        isBattling = (isBattling && Image.findPointByMulColor(screenshot, Presets.damageNumberRule, Presets.damageNumberRec).isValid());
        return isBattling;
    }

    private void CheckLion(Bitmap screenshot) {
        MLog.info("检测狮子头");
        if (Image.findPointByMulColor(screenshot, Presets.beforeLionRules, Presets.mapRec).isValid()) {
            beforeLion = true;
            if (beforeLionMove) {
                int random = Utility.RandomInt(2000, 2300);
                Robot.LongPress(Presets.moveRecs[0], random);
                sleep(random + 100);
                beforeLionMove = false;
            }
            MLog.info("狮子头前");
        } else {
            beforeLion = false;
        }
    }

    private void CheckBuff(Bitmap screenshot) throws InterruptedException {
        MLog.info("检测BUFF");
//        if (Image.findPoint(screenshot, Presets.buffColor, Presets.buffRec).isValid()) {
//            sleep(0);
//            Robot.Press(Presets.buffRec, 2);
//            sleep(1000);
//        }
    }

    private boolean CheckDodge(Bitmap screenshot) {
        MLog.info("检测闪避");
        if (Image.findPoint(screenshot, Presets.dodgeColor, Presets.dodgeRecs[0]).isValid()) {
            Dodge();
            return true;
        }
        return false;
    }

    private boolean CheckHell(Bitmap screenshot) {
        return Image.findPointByMulColor(screenshot, Presets.hellRules, Presets.mapRec).isValid();
    }

    private boolean CheckBoss(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.bossRules, Presets.mapRec).isValid()) {
            MLog.info("在BOSS房中");
            return true;
        }
        return false;
    }

    private void Dodge() {
        MLog.info("闪避");
        Robot.swipe(Presets.dodgeRecs[0], Presets.dodgeRecs[1], Utility.RandomInt(120, 130));
        sleep(800);
    }

    private void BackJump() {
        MLog.info("后跳");
        Robot.Press(Presets.backJumpRec);
    }

    private void MoveAround() {
        MLog.info("稍微移动");
        int random;
        for (int i = 0; i < 4; i++) {
            random = Utility.RandomInt(100, 200);
            Robot.LongPress(Presets.joystickRecs[0], Presets.joystickRecs[1], random);
            sleep(random);
        }
    }

    private void SellItems() {
        MLog.info("出售物品");
        Robot.Press(Presets.sellRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(2000);
        Robot.Press(Presets.sellConfirmRec);
        sleep(4000);
        Robot.Press(Presets.breakAndSellCloseRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellCloseRec);
        sleep(2000);
    }

    private void BreakItems() {
        MLog.info("分解物品");
        Robot.Press(Presets.breakRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(2000);
        Robot.Press(Presets.breakConfirmRec);
        sleep(4000);
        Robot.Press(Presets.breakAndSellCloseRec);
        sleep(2000);
        Robot.Press(Presets.breakAndSellCloseRec);
        sleep(2000);
    }

    private void PressBack() {
        MLog.info("点击返回按钮");
        Robot.Press(Presets.backRec);
        sleep(1000);
    }

    private void PressLongAttack(int pressTime, int sleepTime) {
        MLog.info("长按攻击");
        Robot.LongPress(Presets.attackRec, pressTime);
        sleep(sleepTime);
    }

    private void PressMultipleAttacks(long pressTime) throws InterruptedException {
        MLog.info("多次点击攻击");
        Robot.Press(Presets.attackRec, (int) pressTime / 100);
    }

    private Bitmap GetScreenshot() {
        return ScreenCaptureUtil.getScreenCap();
    }
}
