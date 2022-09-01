package com.slvrmn.DNFAssistant.GamePackage;

import static java.lang.Thread.sleep;

import android.graphics.Bitmap;
import android.os.SystemClock;

import com.slvrmn.DNFAssistant.Model.CheckImageModel;
import com.slvrmn.DNFAssistant.Model.CheckRuleModel;
import com.slvrmn.DNFAssistant.Model.Color;
import com.slvrmn.DNFAssistant.Model.Image;
import com.slvrmn.DNFAssistant.Model.Point;
import com.slvrmn.DNFAssistant.Model.Rectangle;
import com.slvrmn.DNFAssistant.Model.Robot;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

public class Actions {

    static void PressJoystick(int direction, int duration) {
        MLog.info("Actions: ==========摇杆移动==========");
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
        MLog.info("Actions: ==========多次攻击==========");
        for (int i = 0; i < multiple; i++) {
            if (BattleController.isPathfinding) {
                return;
            }
            Robot.Press(Presets.attackRec);
        }
    }

    static void PressLongAttack(int pressTime, int sleepTime) throws InterruptedException {
        MLog.info("Actions: ==========长按攻击==========");
        Robot.LongPress(Presets.attackRec, pressTime);
        sleep(sleepTime);
    }

    static void GoOutDungeon() throws InterruptedException {
        MLog.info("Actions: ==========退出地下城==========");
        Robot.Press(Presets.settingsRec);
        sleep(2000);
        Robot.Press(Presets.homeRec);
        sleep(2000);
        Robot.Press(Presets.confirmGoOutRec);
        sleep(2000);
    }

    static void MoveAround() throws InterruptedException {
        MLog.info("Actions: ==========稍微移动==========");
        int random;
        for (int i = 0; i < 4; i++) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            random = Utility.RandomInt(100, 150);
            Robot.LongPress(Presets.joystickRecs[0], Presets.joystickRecs[1], random);
            sleep(random);
        }
    }

    static void PickDrops() throws InterruptedException {
        MLog.info("Actions: ==========拾取物品==========");
        MoveAround();
        int random = Utility.RandomInt(5500, 5600);
        PressLongAttack(random, random);
    }

    static void RepairEquipments() throws InterruptedException {
        MLog.info("Actions: ==========修理装备==========");
        Robot.Press(ScreenCheck.hasRepair);
        sleep(500);
        Robot.Press(Presets.confirmRepairRecs);
        sleep(500);
    }

    static void CleanInventory() throws InterruptedException {
        MLog.info("Actions: ==========清理背包==========");
        Robot.Press(ScreenCheck.isInventoryFull);
        sleep(4000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(4000);
        Robot.Press(Presets.breakConfirmRec);
        sleep(4000);
        PressBack("Actions.CleanInventory");
        PressBack("Actions.CleanInventory");
        sleep(2000);
        Robot.Press(Presets.sellRec);
        sleep(4000);
        Robot.Press(Presets.breakAndSellSelectRec);
        sleep(4000);
        Robot.Press(Presets.sellConfirmRec);
        sleep(4000);
        PressBack("Actions.CleanInventory");
        PressBack("Actions.CleanInventory");
        PressBack("Actions.CleanInventory");
        PressBack("Actions.CleanInventory");
        sleep(1000);
    }

    static void PressBack(String caller) throws InterruptedException {
        MLog.info("Actions: " + caller + " ==========点击返回按钮==========");
        Robot.Press(Presets.backRec);
        sleep(1500);
    }

    static void BackJump() throws InterruptedException {
        MLog.info("Actions: ==========后跳==========");
        Robot.Press(Presets.backJumpRec);
        sleep(500);
    }

    static void DodgeLeft() throws InterruptedException {
        MLog.info("Actions: ==========向后闪避==========");
        Robot.swipe(Presets.dodgeRecs[0], Presets.dodgeRecs[1], Utility.RandomInt(120, 130));
        SystemClock.sleep(800);
        PressMultipleAttacks(3);
    }

    static void DodgeDown() throws InterruptedException {
        MLog.info("Actions: ==========向下闪避==========");
        Robot.swipe(Presets.dodgeRecs[0], Presets.dodgeRecs[2], Utility.RandomInt(120, 130));
        SystemClock.sleep(800);
        PressMultipleAttacks(3);
    }

    static boolean UseBuff() throws InterruptedException {
        for (int i = ScreenCheck.skills.length - 1; i >= ScreenCheck.skills.length - 3; i--) {
            if (!Assistant.getInstance().isRunning()) {
                return false;
            }
            if (ScreenCheck.skills[i]) {
                if (i == ScreenCheck.skills.length - 1) {
                    if (ScreenCheck.ammoBuff) {
                        if (!ScreenCheck.hasAmmo) {
                            for (int j = 0; j < ScreenCheck.directionalBuffs.length; j++) {
                                if (!Assistant.getInstance().isRunning()) {
                                    return false;
                                }
                                boolean b = ScreenCheck.directionalBuffs[j];
                                if (b) {
                                    UseDirectionalBuff(j);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                }
                MLog.info("Actions: ==========使用BUFF==========");
                Robot.Press(Presets.skillRecs[i], Utility.RandomInt(1, 2));
                sleep(500);
                return true;
            }
        }
        return false;
    }

    static void UseDirectionalBuff(int direction) throws InterruptedException {
        MLog.info("Actions: ==========使用方向性BUFF==========");
        Rectangle destination = Presets.skillRecs[Presets.skillRecs.length - 1].Copy();
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


        Robot.swipe(Presets.skillRecs[Presets.skillRecs.length - 1], destination, 150);
        sleep(500);
    }

    public static void GoBackToMainScene(String caller) throws InterruptedException {
        MLog.info("Actions: " + caller + " ==========回到主界面==========");
        while (!Image.findPointByCheckImageModel(ScreenCheck.GetScreenshot(),Presets.DailyMenuModel).isValid()){
            sleep(1000);
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            PressBack("Actions.GoBackToMainScene");
        }
    }

    public static void StoreDaily() throws InterruptedException {
        MLog.info("Actions: ==========日常商城==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.StoreDaily1");
        for (CheckRuleModel m : Presets.storeModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.StoreDaily2");
    }

    public static void FriendDaily() throws InterruptedException {
        MLog.info("Actions: ==========日常友情点==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.FriendDaily1");
        for (CheckRuleModel m : Presets.friendModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.FriendDaily2");
    }

    public static void GuildDaily() throws InterruptedException {
        MLog.info("Actions: ==========日常公会==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.GuildDaily1");
        for (CheckRuleModel m : Presets.guildModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        Robot.Press(Presets.GuildRewardRec);
        sleep(2000);
        GoBackToMainScene("Actions.GuildDaily2");
    }

    public static void FriendAndGuildDaily() throws InterruptedException {
        MLog.info("Actions: ==========日常友情点与公会==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.FriendAndGuildDaily1");
        for (CheckImageModel m : Presets.FriendAndGuildModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.FriendAndGuildDaily2");
    }

    public static void MailDaily() throws InterruptedException {
        MLog.info("Actions: ==========日常邮件==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.MailDaily1");
        for (int i = 0; i < Presets.mailModels.length; i++) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(Presets.mailModels[i])) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.MailDaily2");
    }

    public static void PetDaily() throws InterruptedException {
        MLog.info("Actions: ==========日常宠物==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.PetDaily1");
        for (CheckRuleModel m : Presets.petModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.PetDaily2");
    }

    public static void SaveItems() throws InterruptedException {
        MLog.info("Actions: ==========保存物品至金库==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.SaveItems1");
        for (CheckImageModel m : Presets.saveItemsModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.SaveItems2");
    }

    public static void GetDailyReward() throws InterruptedException {
        MLog.info("Actions: ==========领取成就奖励==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("Actions.GetDailyReward1");
        for (CheckImageModel m : Presets.getDailyRewardModels) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            if (!FindAndTap(m)) {
                break;
            }
            sleep(2000);
        }
        GoBackToMainScene("Actions.GetDailyReward2");
    }

    public static boolean FindAndTap(Color color, Rectangle rectangle) throws InterruptedException {
        Bitmap screenshot = ScreenCheck.GetScreenshot();
        Point p = Image.findPoint(screenshot, color, rectangle);
        if (p.isValid()) {
            p.setX(p.getX() + rectangle.x1);
            p.setY(p.getY() + rectangle.y1);
            Rectangle r = p.MakeRectangle(10, 10);
            Robot.Press(r);
            sleep(300);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(Bitmap image) throws InterruptedException {
        Bitmap screenshot = ScreenCheck.GetScreenshot();
        Point p = Image.matchTemplate(screenshot, image, 0.7);
        if (p.isValid()) {
            Rectangle rectangle = p.MakeRectangle(image.getWidth(), image.getHeight());
            Robot.Press(rectangle);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(Bitmap image, Rectangle rectangle) throws InterruptedException {
        Bitmap screenshot = ScreenCheck.GetScreenshot();
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
        if (!Assistant.getInstance().isRunning()) {
            return false;
        }
        Bitmap screenshot = ScreenCheck.GetScreenshot();
        Point p = Image.findPointByMulColor(screenshot, imageRule, rectangle);
        if (p.isValid()) {
            Robot.Press(rectangle);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(CheckRuleModel checkRuleModel) throws InterruptedException {
        if (!Assistant.getInstance().isRunning()) {
            return false;
        }
        Bitmap screenshot;
        Point p;
        int count = 0;
        do {
            screenshot = ScreenCheck.GetScreenshot();
            p = Image.findPointByCheckRuleModel(screenshot, checkRuleModel);
            sleep(ScreenCheck.CHECK_INTERVAL);
            count++;
        } while (!p.isValid() && count < 20);

        if (p.isValid()) {
            p.setX(checkRuleModel.rectangle.x1 + p.getX());
            p.setY(checkRuleModel.rectangle.y1 + p.getY());
            Robot.Press(p.MakeRectangle(10, 10));
            sleep(1000);
            return true;
        }
        return false;
    }

    public static boolean FindAndTap(CheckRuleModel[] checkRuleModels) throws InterruptedException {
        if (!Assistant.getInstance().isRunning()) {
            return false;
        }
        Bitmap screenshot = ScreenCheck.GetScreenshot();
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

    public static boolean FindAndTap(CheckImageModel checkImageModel) throws InterruptedException {
        if (!Assistant.getInstance().isRunning()) {
            return false;
        }
        Bitmap screenshot;
        Point p;
        int count = 0;
        do {
            screenshot = ScreenCheck.GetScreenshot();
            p = Image.findPointByCheckImageModel(screenshot, checkImageModel);
            sleep(ScreenCheck.CHECK_INTERVAL);
            count++;
        } while (!p.isValid() && count < 20);

        if (p.isValid()) {
            p.setX(checkImageModel.rectangle.x1 + p.getX());
            p.setY(checkImageModel.rectangle.y1 + p.getY());
            Robot.Press(p.MakeRectangle(checkImageModel.image.getWidth(), checkImageModel.image.getHeight()));
            return true;
        }
        return false;
    }

    public static void EnterDailyDungeonSelectScene() throws InterruptedException {
        MLog.info("Actions: ==========进入日常副本选择界面==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        for (CheckRuleModel m : Presets.dailyDungeonModels) {
            while (!FindAndTap(m)) {
                if (!Assistant.getInstance().isRunning()) {
                    return;
                }
                sleep(2000);
            }
        }
        sleep(5000);
    }

    public static void EnterFarming() throws InterruptedException {
        MLog.info("Actions: ==========移动进入搬砖==========");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        for (int i = 0; i < Presets.enterFarmingModels.length; i++) {
            if (!Assistant.getInstance().isRunning()) {
                return;
            }
            while (!FindAndTap(Presets.enterFarmingModels[i])) {
                if (!Assistant.getInstance().isRunning()) {
                    return;
                }
                sleep(2000);
            }
        }
        BattleController.StartFarming();
        DailyQuest.StopDailyQuesting();
        BattleController.StartBattle();
    }

    public static boolean SwitchCharacter() throws InterruptedException {
        MLog.info("Actions: ==========切换角色==========");
        sleep(3000);
        GoBackToMainScene("Actions.SwitchCharacter");
        SaveItems();
        sleep(3000);
        GetDailyReward();

        while (!FindAndTap(Presets.switchCharacterButton, Presets.switchCharacterButtonRec)) {
            if (!Assistant.getInstance().isRunning()) {
                return false;
            }
            sleep(200);
        }
        for (int i = 0; i < 10; i++) {
            sleep(1000);
            if (FindAndTap(Presets.characterRemainColor, Presets.characterRemainRec)) {
                sleep(3000);
                while (FindAndTap(Presets.switchCharacterModels[1])) {
                    if (!Assistant.getInstance().isRunning()) {
                        return false;
                    }
                }
                Assistant.getInstance().stop();
                BattleController.StopFarming();
                sleep(10000);
                Assistant.getInstance().start();
                return true;
            }
            Robot.swipe(new Rectangle(1078, 551, 1083, 556), new Rectangle(1078, 154, 1083, 159), 1000);
        }
        return false;
    }

    public static boolean SleepCheckBeforeLion(int random) throws InterruptedException {
        MLog.info("Actions: ==========检查是否在狮子头前==========");
        for (int i = 0; i < random / ScreenCheck.CHECK_INTERVAL - 1; i++) {
            sleep(ScreenCheck.CHECK_INTERVAL);
            if (!ScreenCheck.beforeLion) {
                return false;
            }
        }
        return true;
    }

    public static boolean SleepCheckIsPathfinding(int random) throws InterruptedException {
        MLog.info("Actions: ==========检查是否在寻路==========");
        for (int i = 0; i < random / ScreenCheck.CHECK_INTERVAL - 1; i++) {
            sleep(ScreenCheck.CHECK_INTERVAL);
            if (!BattleController.isPathfinding) {
                return false;
            }
        }
        return true;
    }

    public static boolean SleepCheckHasMonster(int random) throws InterruptedException {
        MLog.info("Actions: ==========检查是否有怪物==========");
        for (int i = 0; i < random / ScreenCheck.CHECK_INTERVAL - 1; i++) {
            sleep(ScreenCheck.CHECK_INTERVAL);
            if (ScreenCheck.hasMonster) {
                return false;
            }
        }
        return true;
    }
}
