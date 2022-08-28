package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.GuildRewardRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.skillRecs;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.switchCharacterModels;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.GetScreenshot;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.ammoBuff;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.dailyNonMapDungeon;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.directionalBuffs;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasAmmo;
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
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isFarming;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isInventoryFull;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.skills;
import static java.lang.Thread.sleep;

import android.graphics.Bitmap;
import android.os.SystemClock;

import com.slvrmn.DnfAssistant.Model.CheckRuleModel;
import com.slvrmn.DnfAssistant.Model.Color;
import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Model.Point;
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
//        MLog.info("稍微移动");
        int random;
        for (int i = 0; i < 4; i++) {
            if (!RUN) {
                return;
            }
            random = Utility.RandomInt(100, 150);
            Robot.LongPress(Presets.joystickRecs[0], Presets.joystickRecs[1], random);
            sleep(random);
        }
    }

    static void GetReward() throws InterruptedException {
//        MLog.info("翻牌并进入下次战斗");
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

        if (hasRepair.isValid()) {
            RepairEquipments();
        }

        if (isInventoryFull.isValid()) {
            CleanInventory();
        }

        if (isEnergyEmpty) {
            Robot.Press(hasGoBack);
            sleep(1000);
            ScreenCheck.InitializeFarmingParameters();
            return;
        } else {
            if (!RUN) {
                return;
            }
            Robot.Press(hasContinue);
            sleep(1000);
            Robot.Press(Presets.continueConfirmRec);
            sleep(1000);
            ScreenCheck.InitializeFarmingParameters();
            sleep(1000);
            SwitchCharacter();
        }
    }

    static void PickDrops() throws InterruptedException {
//        MLog.info("拾取物品");
        MoveAround();
        int random = Utility.RandomInt(5500, 5600);
        PressLongAttack(random, random);
    }

    static void RepairEquipments() throws InterruptedException {
        Robot.Press(hasRepair);
        sleep(2000);
        Robot.Press(Presets.confirmRepairRecs);
        sleep(2000);
        PressBack();
        sleep(2000);
    }

    static void CleanInventory() throws InterruptedException {
        Robot.Press(ScreenCheck.isInventoryFull);
        sleep(4000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(4000);
        Robot.Press(Presets.breakConfirmRec);
        sleep(4000);
        PressBack();
        PressBack();
        sleep(2000);
        Robot.Press(Presets.sellRec);
        sleep(4000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(4000);
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
        sleep(1500);
    }

    static void BackJump() throws InterruptedException {
//        MLog.info("后跳");
        Robot.Press(Presets.backJumpRec);
        sleep(500);
    }

    static void Dodge() throws InterruptedException {
//        MLog.info("闪避");
        Robot.swipe(Presets.dodgeRecs[0], Presets.dodgeRecs[1], Utility.RandomInt(120, 130));
        SystemClock.sleep(800);
        PressMultipleAttacks(3);
    }

    static boolean UseBuff() throws InterruptedException {
        for (int i = skills.length - 1; i >= skills.length - 3; i--) {
            if (!RUN) {
                return false;
            }
            if (skills[i]) {
                if (i == skills.length - 1) {
                    if (ammoBuff) {
                        if (!hasAmmo) {
                            for (int j = 0; j < directionalBuffs.length; j++) {
                                if (!RUN) {
                                    return false;
                                }
                                boolean b = directionalBuffs[j];
                                if (b) {
                                    UseDirectionalBuff(j);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                }
                Robot.Press(skillRecs[i], Utility.RandomInt(2, 3));
                sleep(1000);
                return true;
            }
        }
        return false;
    }

    static void UseDirectionalBuff(int direction) throws InterruptedException {
        Rectangle destination = skillRecs[skillRecs.length - 1].Copy();
        switch (direction) {
            case 0:
                destination.Move(0, destination.Height() * 2);
                break;
            case 1:
                destination.Move(-destination.Width() * 2, 0);
                break;
            case 2:
                destination.Move(destination.Width() * 2, 0);
                break;
            case 3:
                destination.Move(0, -destination.Height() * 2);
                break;
        }


        Robot.swipe(skillRecs[skillRecs.length - 1], destination, 150);
        sleep(500);
    }

    static void UseSkills() throws InterruptedException {
        if (skills[skills.length - 4] && (inBoss || inLion || inHell || dailyNonMapDungeon)) {
            Robot.Press(Presets.skillRecs[skills.length - 4], Utility.RandomInt(2, 3));
            return;
        }
        for (int i = 0; i < skills.length - 4; i++) {
            if (!RUN) {
                return;
            }
            if (skills[i]) {
                if (!hasMonster || !isDamaging) {
                    isBattling = false;
                    isPathFinding = true;
                    return;
                }
                Robot.Press(Presets.skillRecs[i], Utility.RandomInt(2, 3));
                sleep(300);
            }
        }
    }

    public static void GoBackToMainScene() throws InterruptedException {
        while (!Image.findPointByCheckRuleModel(GetScreenshot(), Presets.mainMenuModel).isValid()) {
            if (!RUN) {
                return;
            }
            PressBack();
        }
    }

    public static void StoreDaily() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        for (CheckRuleModel m : Presets.storeModels) {
            if (!RUN) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene();
    }

    public static void FriendDaily() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        for (CheckRuleModel m : Presets.friendModels) {
            if (!RUN) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene();
    }

    public static void GuildDaily() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        for (CheckRuleModel m : Presets.guildModels) {
            if (!RUN) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        Robot.Press(GuildRewardRec);
        sleep(2000);
        GoBackToMainScene();
    }

    public static void MailDaily() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        for (int i = 0; i < Presets.mailModels.length; i++) {
            if (!RUN) {
                return;
            }
            if (!FindAndTap(Presets.mailModels[i])) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene();
    }

    public static void PetDaily() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        for (CheckRuleModel m : Presets.petModels) {
            if (!RUN) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene();
    }

    public static boolean FindAndTap(Color color, Rectangle rectangle) throws InterruptedException {
        Bitmap screenshot = GetScreenshot();
        Point p = Image.findPoint(screenshot, color, rectangle);
        if (p.isValid()) {
            MLog.info(p.toString());
            p.setX(p.getX() + rectangle.x1);
            p.setY(p.getY() + rectangle.y1);
            MLog.info(p.toString());
            Rectangle r = p.MakeRectangle(5, 5);
            Robot.Press(r);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(Bitmap image) throws InterruptedException {
        Bitmap screenshot = GetScreenshot();
        Point p = Image.matchTemplate(screenshot, image, 0.7);
        if (p.isValid()) {
            Rectangle rectangle = p.MakeRectangle(image.getWidth(), image.getHeight());
            Robot.Press(rectangle);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(Bitmap image, Rectangle rectangle) throws InterruptedException {
        Bitmap screenshot = GetScreenshot();
        Point p = Image.matchTemplate(Image.cropBitmap(screenshot, rectangle), image, 0.7);
        if (p.isValid()) {
            p.setX(p.getX() + rectangle.x1);
            p.setY(p.getY() + rectangle.y1);
            Rectangle r = p.MakeRectangle(image.getWidth(), image.getHeight());
            Robot.Press(r);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(String imageRule, Rectangle rectangle) throws InterruptedException {
        Bitmap screenshot = GetScreenshot();
        Point p = Image.findPointByMulColor(screenshot, imageRule, rectangle);
        if (p.isValid()) {
            Robot.Press(rectangle);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(CheckRuleModel checkRuleModel) throws InterruptedException {
        Bitmap screenshot = GetScreenshot();
        Point p = Image.findPointByCheckRuleModel(screenshot, checkRuleModel);
        if (p.isValid()) {
            p.setX(checkRuleModel.rectangle.x1 + p.getX());
            p.setY(checkRuleModel.rectangle.y1 + p.getY());
            Robot.Press(p.MakeRectangle(5, 5));
            sleep(500);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(CheckRuleModel[] checkRuleModels) throws InterruptedException {
        Bitmap screenshot = GetScreenshot();
        Point p;
        for (CheckRuleModel checkRuleModel :
                checkRuleModels) {
            p = Image.findPointByCheckRuleModel(screenshot, checkRuleModel);
            if (p.isValid()) {
                p.setX(checkRuleModel.rectangle.x1 + p.getX());
                p.setY(checkRuleModel.rectangle.y1 + p.getY());
                Robot.Press(p.MakeRectangle(5, 5));
                return true;
            }
        }
        return false;
    }

    public static void EnterDailyDungeonSelectScene() throws InterruptedException {
        for (CheckRuleModel m : Presets.dailyDungeonModels) {
            while (!FindAndTap(m)) {
                if (!RUN) {
                    return;
                }
            }
        }
    }

    public static void EnterFarming() throws InterruptedException {
        sleep(1000);
        for (int i = 0; i < Presets.enterFarmingModels.length; i++) {
            if (!RUN) {
                return;
            }
            while (!FindAndTap(Presets.enterFarmingModels[i])) {
                if (!RUN) {
                    return;
                }
                MLog.info(i + "");
                sleep(2000);
            }
        }
        isFarming = true;
    }

    public static boolean SwitchCharacter() throws InterruptedException {
        GoBackToMainScene();
        while (!FindAndTap(switchCharacterModels[0])) {
            if (!RUN) {
                return false;
            }
            sleep(200);
        }
        for (int i = 0; i < 10; i++) {
            sleep(1000);
            if (FindAndTap(Presets.characterRemainColor, Presets.characterRemainRec)) {
                sleep(3000);
                while (FindAndTap(switchCharacterModels[1])) {
                    if (!RUN) {
                        return false;
                    }
                }
                sleep(10000);
                ScreenCheck.InitializeCharacterParameters();
                ScreenCheck.InitializeFarmingParameters();
                ScreenCheck.InitializeBattleParameters();
                ScreenCheck.InitializeDailyQuestParameters();
                isFarming = false;
                return true;
            }
            Robot.swipe(new Rectangle(1078, 551, 1083, 556), new Rectangle(1078, 154, 1083, 159), 1000);
        }
        RUN = false;
        return false;
    }
}
