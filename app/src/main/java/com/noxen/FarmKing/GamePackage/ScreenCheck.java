package com.noxen.FarmKing.GamePackage;

import static com.noxen.FarmKing.GamePackage.BattleController.isFarming;

import android.graphics.Bitmap;

import com.noxen.FarmKing.Model.Image;
import com.noxen.FarmKing.Model.Point;
import com.noxen.FarmKing.Model.Rectangle;
import com.noxen.FarmKing.Tools.MLog;
import com.noxen.FarmKing.Tools.ScreenCaptureUtil;

import org.opencv.android.BaseLoaderCallback;

import java.util.Arrays;

public class ScreenCheck extends Thread {
    public static final int CHECK_INTERVAL = 100;
    public static volatile int screenFreezeTime;
    public static volatile boolean
            inDungeon,
            inBoss, beforeLion, inLion, hasMonster, isDamaging,
            canDodge, hasResult, hasReward, inHell, hasContinueConfirm, isEnergyEmpty,
            canBreak, canSell, canBreakSelect, canSellSelect, canBreakConfirm,
            ammoBuff, hasAmmo, dailyNonMapDungeon;
    public static volatile Rectangle hasContinue, hasRepair, isInventoryFull, hasGoBack,
            hasDailyContinue, hasDailySelect, hasDailyGoBack;
    public static volatile boolean[] skills = {false, false, false, false, false, false, false, false, false, false, false};
    public static volatile boolean[] availableSkills = {false, false, false, false, false, false, false, false, false, false, false};
    public static volatile boolean[] directionalBuffs = {false, false, false, false};

    public static volatile Rectangle[] dailyDungeons = new Rectangle[5];

    private static volatile Bitmap lastScreenshot;
    private static volatile Bitmap screenshot;

    public static synchronized void InitializeBattleParameters() {
        screenFreezeTime = 0;
        inDungeon = false;
        inBoss = false;
        hasMonster = false;
        isDamaging = false;
        canDodge = false;
        ammoBuff = false;
        Arrays.fill(skills, true);
        Arrays.fill(directionalBuffs, true);
    }

    public static synchronized void InitializeFarmingParameters() {
        InitializeBattleParameters();
        beforeLion = false;
        inLion = false;
        hasResult = false;
        hasReward = false;
        inHell = false;
        hasContinueConfirm = false;
        canBreak = false;
        canSell = false;
        canBreakSelect = false;
        canSellSelect = false;
        canBreakConfirm = false;
        hasContinue = Rectangle.INVALID_RECTANGLE;
        hasRepair = Rectangle.INVALID_RECTANGLE;
        isInventoryFull = Rectangle.INVALID_RECTANGLE;
        hasGoBack = Rectangle.INVALID_RECTANGLE;
    }

    public static synchronized void InitializeDailyQuestParameters() {
        InitializeBattleParameters();
        for (int i = 0; i < dailyDungeons.length; i++) {
            dailyDungeons[i] = Rectangle.INVALID_RECTANGLE;
        }
        dailyNonMapDungeon = false;
        hasDailyContinue = Rectangle.INVALID_RECTANGLE;
        hasDailySelect = Rectangle.INVALID_RECTANGLE;
        hasDailyGoBack = Rectangle.INVALID_RECTANGLE;
    }

    public static synchronized void InitializeCharacterParameters() {
        Arrays.fill(availableSkills, false);
        isEnergyEmpty = false;
    }

    public static Bitmap GetScreenshot() {
        return ScreenCaptureUtil.getScreenCap();
    }

    public static void RefreshDailyDungeons(Bitmap screenshot) {
        for (int i = 0; i < dailyDungeons.length; i++) {
            dailyDungeons[i] = Image.matchTemplate(screenshot, Presets.dailyDungeonIcons[i], 0.9).MakeRectangle(50, 50);
        }
    }

    @Override
    public void run() {
        //MLog.info("Start Debugging");
        InitializeFarmingParameters();
        InitializeDailyQuestParameters();
        try {
            while (Assistant.RUN) {
                screenshot = GetScreenshot();
                if (CheckBlackScreen()) {
                    sleep(CHECK_INTERVAL);
                    continue;
                }
                //MLog.info("------------------------------------------");
                if (BattleController.isBattling) {
                    CheckBattlingParameters();
                }
                if (isFarming) {
                    CheckFarmingParameters();
                } else {
                    CheckDailyMenu();
                }
                CheckEnergyEmpty();
                sleep(CHECK_INTERVAL / 2);
                lastScreenshot = screenshot;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //MLog.error(e.toString());
        }
    }

    private void CheckBattlingParameters() throws InterruptedException {
        if (!dailyNonMapDungeon) {
            CheckBoss();
            CheckStuck();
        }
        CheckInDungeon();
        CheckMonster();
        CheckDamaging();
        CheckDodge();
        CheckSkillsCoolDown();
        CheckDirectionBuffsCoolDown();
        CheckAmmo();
    }

    private void CheckFarmingParameters() {

        CheckInventoryFull();
        CheckBreak();
        CheckSell();
        CheckBreakSelectButton();
        CheckSellSelectButton();
        CheckBreakConfirmButton();
        CheckSellConfirmButton();
        CheckRepair();
        CheckLion();
        CheckInLion();
        CheckInHell();
        CheckResult();
        CheckReward();
        CheckContinueButton();
        CheckGoBackButton();
        CheckContinueConfirmButton();
    }

    private boolean CheckBlackScreen() {
        return Image.findPointByMulColor(screenshot, Presets.blackScreenRule).isValid();
    }

    private void CheckInDungeon() throws InterruptedException {
        if (inDungeon) {
            return;
        }
        if (Image.findPointByCheckRuleModel(screenshot, Presets.inDungeonModel).
                isValid()) {
            //MLog.info("地下城中");
            inDungeon = true;
            BattleController.StartPathfinding();
            CheckAvailableSkills();
            return;
        }
        inDungeon = false;
    }

    private void CheckBoss() {
        if (Image.findPointByMulColor(screenshot, Presets.bossRules, Presets.mapCentreRec).isValid()) {
            //MLog.info("BOSS房中");
            inBoss = true;
            return;
        }
        inBoss = false;
    }

    private void CheckLion() {
        if (Image.findPointByMulColor(screenshot, Presets.beforeLionRules, Presets.mapRec).
                isValid()) {
            //MLog.info("狮子头前");
            beforeLion = true;
            return;
        }
        beforeLion = false;
    }

    private void CheckInLion() {
        if (Image.findPointByMulColor(screenshot, Presets.inLionRule, Presets.mapRec).isValid()) {
            //MLog.info("狮子头中");
            inLion = true;
            return;
        }
        inLion = false;
    }

    private void CheckInHell() {
        if (Image.findPointByMulColor(screenshot, Presets.hellRules, Presets.mapRec).isValid()) {
            //MLog.info("深渊房中");
            inHell = true;
            return;
        }
        inHell = false;
    }

    private void CheckMonster() {
        if (Image.matchTemplate(screenshot, Presets.numbers, 0.5,
                Presets.monsterLevelRec).isValid()) {
            hasMonster = true;
            //MLog.info("有怪物");
            return;
        }
        hasMonster = false;
    }

    private void CheckDamaging() {
        if (Image.findPointByMulColor(screenshot, Presets.damageNumberRule,
                Presets.damageNumberRec).isValid()) {
            isDamaging = true;
            //MLog.info("造成伤害中");
            return;
        }
        isDamaging = false;
    }

    private void CheckDodge() {
        if (Image.matchTemplate(screenshot, Presets.crouchingIcon, 0.8, Presets.attackRec).isValid()) {
            canDodge = true;
            //MLog.info("可闪避");
            return;
        }
        canDodge = false;
    }

    private void CheckStuck() {
        if (!BattleController.isPathfinding) {
            screenFreezeTime = 0;
            return;
        }
        if (lastScreenshot != null) {
            if (Image.matchTemplate(
                    Image.cropBitmap(lastScreenshot, Presets.stuckRec),
                    Image.cropBitmap(screenshot, Presets.stuckRec),
                    0.95).isValid()) {
                AddStuck();
                //MLog.info("屏幕静止" + stuckTime + "次");
                return;
            }
            screenFreezeTime = 0;
        }
    }

    private synchronized void AddStuck() {
        screenFreezeTime++;
    }

    private void CheckResult() {
        if (hasResult) {
            return;
        }
        if (Image.matchTemplate(screenshot, Presets.resultIcon, 0.8, Presets.resultRec).isValid()) {
            hasResult = true;
//            MLog.info("副本结束");
            Arrays.fill(skills, false);
            Arrays.fill(directionalBuffs, false);
            return;
        }
        hasResult = false;
    }

    private void CheckReward() {
        if (hasReward) {
            return;
        }
        if (Image.matchTemplate(screenshot, Presets.rewardIcon, 0.8, Presets.rewardRec).isValid()) {
            hasReward = true;
//            MLog.info("可翻牌");
            return;
        }
        hasReward = false;
    }

    private void CheckInventoryFull() {
        if (isInventoryFull.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, Presets.inventoryFullButton,
                0.85, Presets.completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
            p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
            //MLog.info("背包满");
            isInventoryFull = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            return;
        }
        isInventoryFull = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckBreak() {
        if (Image.findPointByMulColor(screenshot, Presets.breakRule, Presets.breakRec).isValid()) {
            canBreak = true;
            //MLog.info("可按分解");
            return;
        }
        canBreak = false;
    }

    private void CheckSell() {
        if (Image.findPointByMulColor(screenshot, Presets.sellRule, Presets.sellRec).isValid()) {
            canSell = true;
            //MLog.info("可按出售");
            return;
        }
        canSell = false;
    }

    private void CheckBreakSelectButton() {
        if (Image.findPointByMulColor(screenshot, Presets.breakSelectRule,
                Presets.breakAndSellSelectRec).isValid()) {
            canBreakSelect = true;
            //MLog.info("可分解确认选择");
            return;
        }
        canBreakSelect = false;
    }

    private void CheckSellSelectButton() {
        if (Image.findPointByMulColor(screenshot, Presets.sellSelectRule,
                Presets.breakAndSellSelectRec).isValid()) {
            canSellSelect = true;
            //MLog.info("可出售确认选择");
            return;
        }
        canSellSelect = false;
    }

    private void CheckBreakConfirmButton() {
        if (Image.findPointByMulColor(screenshot, Presets.breakConfirmRule,
                Presets.breakConfirmRec).isValid()) {
            canBreakConfirm = true;
            //MLog.info("可确认分解");
            return;
        }
        canBreakConfirm = false;
    }

    private void CheckSellConfirmButton() {
        if (Image.findPointByMulColor(screenshot, Presets.sellConfirmRule,
                Presets.sellConfirmRec).isValid()) {
            canBreakConfirm = true;
            //MLog.info("可确认出售");
            return;
        }
        canBreakConfirm = false;
    }

    private void CheckRepair() {
        if (hasRepair.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, Presets.repairButton, 0.85,
                Presets.completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
            p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
            //MLog.info("需要修理");
            hasRepair = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            return;
        }
        hasRepair = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckContinueButton() {
        if (hasContinue.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, Presets.nextButton, 0.9,
                Presets.completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
            p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
            //MLog.info("可点击继续");
            hasContinue = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            return;
        }
        hasContinue = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckGoBackButton() {
        if (hasGoBack.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, Presets.goBackButton, 0.9,
                Presets.completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
            p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
            //MLog.info("可点击回城");
            hasGoBack = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            return;
        }
        hasGoBack = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckContinueConfirmButton() {
        if (Image.matchTemplate(screenshot, Presets.continueConfirmButton, 0.9,
                Presets.continueConfirmRec).isValid()) {
            hasContinueConfirm = true;
            //MLog.info("可点击确认继续");
            return;
        }
        hasContinueConfirm = false;
    }

    private void CheckEnergyEmpty() {
        if (isEnergyEmpty) {
            return;
        }
        if (Image.findPointByCheckRuleModels(screenshot, Presets.energyModels).isValid()) {
            isEnergyEmpty = true;
            MLog.info("疲劳为0");
            return;
        }
        isEnergyEmpty = false;
    }

    private void CheckAvailableSkills() {
        for (int i = 0; i < availableSkills.length; i++) {
            if (Image.findPoint(screenshot, Presets.skillFrameColor, Presets.skillCDRecs[i]).isValid()) {
                availableSkills[i] = true;
            }
        }
    }

    private void CheckSkillsCoolDown() {
        if (!BattleController.isBattling || hasResult || hasReward) {
            return;
        }
        for (int i = 0; i < skills.length; i++) {
            if (availableSkills[i]) {
                skills[i] = !Image.findPoint(screenshot, Presets.skillCDColors, Presets.skillCDRecs[i]).isValid();
            } else {
                skills[i] = false;
            }
        }
    }

    private void CheckDirectionBuffsCoolDown() {
        if (!BattleController.isBattling || hasResult || hasReward) {
            return;
        }
        if (!ammoBuff && Image.matchTemplate(screenshot, Presets.ammoBuffIcon, 0.85, Presets.skillRecs[10]).isValid()) {
            ammoBuff = true;
        }
        if (ammoBuff) {
            for (int i = 0; i < directionalBuffs.length; i++) {
                //MLog.info("方向性BUFF " + i + " 可用");
                directionalBuffs[i] = Image.findPoint(screenshot, Presets.directionalBuffCDColor, Presets.directionalBuffRecs[i]).isValid();
            }
        }
    }

    private void CheckAmmo() {
        if (!ammoBuff) {
            return;
        }

        if (Image.findPoint(screenshot, Presets.ammoColors, Presets.ammoRec).isValid()) {
            hasAmmo = false;
            return;
        }
        hasAmmo = true;
    }

    private void CheckDailyMenu() {
        Point p;
        if (!hasDailyContinue.isValid()) {
            p = Image.matchTemplate(screenshot, Presets.dailyContinueButton, 0.8, Presets.completeDungeonMenuRec);
            if (p.isValid()) {
                p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
                p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
                hasDailyContinue = p.MakeRectangle(Presets.dailyContinueButton.getWidth(), Presets.dailyContinueButton.getHeight());
            } else {
                hasDailyContinue = Rectangle.INVALID_RECTANGLE;
            }
        }
        if (!hasDailySelect.isValid()) {
            p = Image.matchTemplate(screenshot, Presets.dailySelectButton, 0.8, Presets.completeDungeonMenuRec);
            if (p.isValid()) {
                p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
                p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
                hasDailySelect = p.MakeRectangle(Presets.dailySelectButton.getWidth(), Presets.dailySelectButton.getHeight());
            } else {
                hasDailySelect = Rectangle.INVALID_RECTANGLE;
            }
        }
        if (!hasDailyGoBack.isValid()) {
            p = Image.matchTemplate(screenshot, Presets.dailyGoBackButton, 0.8, Presets.completeDungeonMenuRec);
            if (p.isValid()) {
                p.setX(p.getX() + Presets.completeDungeonMenuRec.x1);
                p.setY(p.getY() + Presets.completeDungeonMenuRec.y1);
                hasDailyGoBack = p.MakeRectangle(Presets.dailyGoBackButton.getWidth(), Presets.dailyGoBackButton.getHeight());
            } else {
                hasDailyGoBack = Rectangle.INVALID_RECTANGLE;
            }
        }
    }
}
