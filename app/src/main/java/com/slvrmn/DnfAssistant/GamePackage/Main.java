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

public class Main implements Runnable {
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final int checkInterval = 200;
    private static final int backJumpCD = 15;
    private static final int checkBattlingCD = 1;
    private static final int checkCoolDownCD = 1;
    private static final boolean[] skills = {true, true, true, true, true, true, true};
    private static boolean beforeLion = false;
    private static boolean inLion = false;
    private static boolean autoFarm = false;
    private static boolean autoBattle = false;
    private static boolean reward = false;
    private static int backJump = 99;
    private static int checkBattling = 99;
    private static int checkCoolDown = 99;
    private static boolean isBattling = true;
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
//                GetReward();
//                sleep(2000);
            }
            Utility.show("线程停止运行");
            InitializeParameters();
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }

    private void InitializeParameters() {
        beforeLion = false;
        inLion = false;
        autoFarm = false;
        autoBattle = false;
        reward = false;
        backJump = 99;
        checkBattling = 99;
        checkCoolDown = 99;
        isBattling = true;
    }

    private void AutoFarm() throws InterruptedException {

        Utility.show("进入自动搬砖");

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
                } else {
                    while (!AutoBattle()) {
                    }
                }
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

    private boolean CheckInDungeon() {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap();
        return Image.matchTemplate(screenshot, Presets.inDungeon, 0.5, Presets.mapRec).isValid();
    }

    private boolean PathFinding() throws InterruptedException {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap();
        /** 检测BUFF **/
        CheckBuff(screenshot);

        if (beforeLion) {
            /** 进入狮子头房间 **/
            while (!inLion) {
                int random = Utility.RandomInt(2000, 2300);
                Robot.LongPress(Presets.lionRecs[0], random);
                sleep(random+100);
                random = Utility.RandomInt(1000, 1200);
                Robot.LongPress(Presets.lionRecs[2], random);
                sleep(3000);

                for (int i = 0; i < 10; i++) {
                    screenshot = ScreenCaptureUtil.getScreenCap();
                    if (Image.findPointByMulColor(screenshot, Presets.inLionRule, Presets.mapRec).isValid()) {
//                        Image.matchTemplate(screenshot, Presets.lion, 0.9, Presets.mapRec).isValid()
                        inLion = true;
                        beforeLion = false;
                        Dodge();
                        Robot.Press(Presets.uniqueSkillRec, Utility.RandomInt(3, 4));
                        break;
                    }
                }
                if (inLion) {
                    break;
                }
                random = Utility.RandomInt(2000, 2500);
                PressLongAttack(random,random);
                sleep(random);
                for (int i = 0; i < 6; i++) {
                    screenshot = ScreenCaptureUtil.getScreenCap();
                    if (isBattling(screenshot)) {
                        BackJump();
                        return true;
                    }
                    sleep(300);
                }

                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return false;
                }
            }
        } else {
            /** 长按攻击 **/
            int pressTime = Utility.RandomInt(5000, 6000);
            PressMultipleAttacks(pressTime / 1500);
            PressLongAttack(pressTime,0);
            Bitmap oldScreenshot;
            /** 检测战斗 **/
            for (int i = pressTime / checkInterval; i > 0; i--) {
                oldScreenshot = screenshot;
                screenshot = ScreenCaptureUtil.getScreenCap();
                /** 检测闪避 **/
                if (CheckDodge(screenshot)) {
                    return false;
                }

                /** 检测战斗 **/
                if (isBattling(screenshot)) {
                    isBattling = true;
                    return true;
                }

                if (i <= (int) pressTime / checkInterval / 2) {
                    if (Image.matchTemplate(
                            Image.cropBitmap(oldScreenshot, Presets.leftBottomRec),
                            Image.cropBitmap(screenshot, Presets.leftBottomRec),
                            0.99).isValid()) {
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

    private boolean AutoBattle() throws InterruptedException {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap();
        /** 检测狮子头 **/
        CheckLion(screenshot);
        /** 长按攻击 **/
        long pressTime = Utility.RandomInt(300, 500);
        PressMultipleAttacks(pressTime);
        /** 检测战斗 **/
        for (int i = 0; i < pressTime / checkInterval; i++) {
            screenshot = ScreenCaptureUtil.getScreenCap();

            /** 检测狮子头 **/
            CheckLion(screenshot);

            /** 检测使用技能 **/
            CheckSkill(screenshot);

            /** 检测结束战斗 **/
            if (!isBattling(screenshot)) {
                backJump = 9;
                return true;
            }
            sleep(checkInterval);
        }
        return false;
    }

    private void GetReward() throws InterruptedException {
        autoBattle = false;
        Bitmap screenshot;
        Point p;

        sleep(1000);
        int random = Utility.RandomInt(5, 10);
        Robot.Press(Presets.skillRecs[4], random);
        sleep(3000);

        do{
            screenshot = ScreenCaptureUtil.getScreenCap();
            p = Image.matchTemplate(screenshot,Presets.nextButton,0.9,Presets.nextMenuRec);
            sleep(1000);

            /** 检测退出 **/
            if (!run) {
                Utility.show("线程停止运行");
                return;
            }
        }while (!p.isValid());
        p.setX(p.getX()+Presets.nextMenuRec.x1);
        p.setY(p.getY()+Presets.nextMenuRec.y1);

        Rectangle next = new Rectangle(p.getX(),p.getY(), p.getX()+50, p.getY()+10);
        /** 拾取物品 **/
        MoveAround();
        random = Utility.RandomInt(3500, 4000);
        PressLongAttack(random,random);

        /** 检测背包 **/
        screenshot = ScreenCaptureUtil.getScreenCap();
        CheckInventory(screenshot);

        /** 进入下一次副本 **/
        CheckNextDungeon(next);

        InitializeParameters();
    }

    private boolean CheckReward(Bitmap screenshot) {
        if (Image.matchTemplate(screenshot, Presets.reward, 0.9).isValid()) {
            return true;
        }
        return false;
    }

    private void CheckInventory(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot,Presets.inventoryFullRules,Presets.inventoryRec).isValid()){
            //TODO
        }
    }

    private void CheckNextDungeon(Rectangle next) {
        //TODO 检测疲劳值

        Point p;
        Bitmap screenshot;
        do{
            Robot.Press(next);
            sleep(1000);
            screenshot = ScreenCaptureUtil.getScreenCap();
            p = Image.matchTemplate(screenshot,Presets.confirmButton,0.9,Presets.confirmMenuRec);

            if(p.isValid()){
                p.setX(p.getX()+Presets.confirmMenuRec.x1);
                p.setY(p.getY()+Presets.confirmMenuRec.y1);
                Robot.Press(new Rectangle(p.getX(),p.getY(), p.getX()+40, p.getY()+20));
            }
            /** 检测退出 **/
            if (!run) {
                Utility.show("线程停止运行");
                return;
            }
        }while (!p.isValid());
    }

    private void CheckSkill(Bitmap screenshot) {
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
        if (isBattling) {
            UseSkills();
        }
        /** 检测战斗中 **/
        if (checkBattling >= checkBattlingCD) {
            isBattling = isBattling(screenshot);
        }
        backJump++;
        checkCoolDown++;
        checkBattling++;
    }

    private void UseSkills() {
        int count = 0;
        for (int i = 0; i < skills.length; i++) {
            if (skills[i]) {
                count++;
                Robot.Press(Presets.skillRecs[i]);
                sleep(500);
                skills[i] = false;
            }
            if (count >= 1) {
                return;
            }
        }
    }

    private void RefreshCoolDownList(Bitmap screenshot) {
        for (int i = 0; i < skills.length; i++) {
            skills[i] = CheckSkillCoolDown(screenshot, i);
        }
    }

    private boolean CheckSkillCoolDown(Bitmap screenshot, int i) {
        return Image.findPoint(screenshot, Presets.skillColor, Presets.skillRecs[i]).isValid();
    }

    private boolean isBattling(Bitmap screenshot) {
        boolean isBattling = true;
        isBattling = (isBattling && Image.matchTemplate(screenshot, Presets.monsterLevel, 0.5, Presets.monsterLevelRec).isValid());
        isBattling = (isBattling && Image.findPointByMulColor(screenshot, Presets.damageNumberRule, Presets.damageNumberRec).isValid());
        return isBattling;
    }

    private void CheckLion(Bitmap screenshot) {
        if (beforeLion || inLion) {
            return;
        }
        if (Image.matchTemplate(screenshot, Presets.beforeLion, 0.8, Presets.mapRec).isValid()) {
            beforeLion = true;
            System.out.println("狮子头前");
        }
    }

    private void CheckBuff(Bitmap screenshot) throws InterruptedException {
        if (Image.findPoint(screenshot, Presets.buffColor, Presets.buffRec).isValid()) {
            Robot.Press(Presets.buffRec, 2);
        }
    }

    private boolean CheckDodge(Bitmap screenshot) {
        if (Image.findPoint(screenshot, Presets.dodgeColor, Presets.dodgeRecs[0]).isValid()) {
            Dodge();
            return true;
        }
        return false;
    }

    private void Dodge() {
        Robot.swipe(Presets.dodgeRecs[0], Presets.dodgeRecs[1], Utility.RandomInt(100, 150));
        sleep(800);
    }

    private void BackJump() {
        Robot.Press(Presets.backJumpRec);
    }

    private void MoveAround() {
        int random;
        random = Utility.RandomInt(100, 200);
        Robot.LongPress(Presets.lionRecs[2], random);
        sleep(random);
        random = Utility.RandomInt(100, 200);
        Robot.LongPress(Presets.lionRecs[1], random);
        sleep(random);
        random = Utility.RandomInt(100, 200);
        Robot.LongPress(Presets.lionRecs[0], random);
        sleep(random);

    }

    private void PressLongAttack(int pressTime, int sleepTime) {
        Robot.LongPress(Presets.attackRec, pressTime);
        sleep(sleepTime);
    }

    private void PressMultipleAttacks(long pressTime) throws InterruptedException {
        Robot.Press(Presets.attackRec, (int) pressTime / 100);
    }
}
