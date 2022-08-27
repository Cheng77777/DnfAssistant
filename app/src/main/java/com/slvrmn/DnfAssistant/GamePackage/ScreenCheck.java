package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Presets.ammoBuffIcon;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.ammoColor;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.ammoRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.beforeLionRules;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.blackScreenRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.bossRules;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.breakAndSellSelectRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.breakConfirmRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.breakConfirmRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.breakRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.breakRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.breakSelectRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.completeDungeonMenuRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.continueConfirmButton;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.continueConfirmRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.damageNumberRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.damageNumberRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.directionalBuffCDColor;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.directionalBuffRecs;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.dodgeColor;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.dodgeRecs;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.energyEmptyRules;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.energyRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.goBackButton;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.hellRules;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.inDungeonRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.inDungeonRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.inLionRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.initialize;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.inventoryFullButton;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.mapCentreRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.mapRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.monsterLevelNumberFive;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.monsterLevelRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.nextButton;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.repairButton;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.resultIcon;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.resultRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.rewardIcon;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.rewardRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.sellConfirmRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.sellConfirmRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.sellRec;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.sellRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.sellSelectRule;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.skillCDColor;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.skillCDRecs;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.skillFrameColor;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.skillRecs;
import static com.slvrmn.DnfAssistant.GamePackage.Presets.stuckRec;

import android.graphics.Bitmap;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Model.Point;
import com.slvrmn.DnfAssistant.Model.Rectangle;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.ScreenCaptureUtil;

import java.util.Arrays;

public class ScreenCheck extends Thread {
    public static final int CHECK_INTERVAL = 150;

    public static volatile boolean isFarming = true;
    public static volatile int screenFreezeTime;
    public static volatile boolean
            inDungeon, inBoss, beforeLion, inLion, hasMonster, isDamaging,
            canDodge, hasResult, hasReward, inHell, hasContinueConfirm, isEnergyEmpty,
            canBreak, canSell, canBreakSelect, canSellSelect, canBreakConfirm,
            isPathFinding, isBattling, ammoBuff, hasAmmo;
    public static volatile Rectangle hasContinue, hasRepair, isInventoryFull, hasGoBack;
    public static volatile boolean[] skills = {false, false, false, false, false, false, false, false, false, false, false};
    public static volatile boolean[] availableSkills = {false, false, false, false, false, false, false, false, false, false, false};
    public static volatile boolean[] directionalBuffs = {false, false, false, false};

    public static volatile boolean isDailyQuesting = false;



    private static volatile Bitmap lastScreenshot;
    private static volatile Bitmap screenshot;

    public static synchronized void InitializeFarmingParameters() {
        screenFreezeTime = 0;
        inDungeon = false;
        inBoss = false;
        beforeLion = false;
        inLion = false;
        hasMonster = false;
        isDamaging = false;
        canDodge = false;
        hasResult = false;
        hasReward = false;
        inHell = false;
        hasContinueConfirm = false;
        isEnergyEmpty = false;
        canBreak = false;
        canSell = false;
        canBreakSelect = false;
        canSellSelect = false;
        canBreakConfirm = false;
        isPathFinding = false;
        isBattling = false;
        ammoBuff = false;
        hasContinue = Rectangle.INVALID_RECTANGLE;
        hasRepair = Rectangle.INVALID_RECTANGLE;
        isInventoryFull = Rectangle.INVALID_RECTANGLE;
        hasGoBack = Rectangle.INVALID_RECTANGLE;
        Arrays.fill(skills, false);
        Arrays.fill(availableSkills, false);
        Arrays.fill(directionalBuffs, false);
    }

    public static synchronized void StartDailyQuests() {
        isDailyQuesting = true;
    }

    @Override
    public void run() {
        //MLog.info("Start Debugging");
        InitializeFarmingParameters();
        initialize();
        try {
            while (Assistant.RUN) {
                //MLog.info("------------------------------------------");
                if (isFarming) {
                    CheckFarmingParameters();
                }
                sleep(CHECK_INTERVAL / 2);

            }
        } catch (Exception e) {
            e.printStackTrace();
            //MLog.error(e.toString());
        }
    }

    private void CheckFarmingParameters() throws InterruptedException {

        GetScreenshot();
        if (CheckBlackScreen()) {
            sleep(CHECK_INTERVAL);
            return;
        }
        CheckInDungeon();
        CheckBoss();
        CheckLion();
        CheckInLion();
        CheckInHell();
        CheckMonster();
        CheckDamaging();
        CheckDodge();
        CheckStuck();
        CheckResult();
        CheckReward();
        CheckInventoryFull();
        CheckBreak();
        CheckSell();
        CheckBreakSelectButton();
        CheckSellSelectButton();
        CheckBreakConfirmButton();
        CheckSellConfirmButton();
        CheckRepair();
        CheckContinueButton();
        CheckGoBackButton();
        CheckContinueConfirmButton();
        CheckEnergyEmpty();
        CheckSkillsCoolDown();
        CheckDirectionBuffsCoolDown();
        CheckAmmo();
        lastScreenshot = screenshot;
    }

    private void GetScreenshot() {
        screenshot = ScreenCaptureUtil.getScreenCap();
    }

    private boolean CheckBlackScreen() {
        return Image.findPointByMulColor(screenshot, blackScreenRule).isValid();
    }

    private void CheckInDungeon() {
        if (inDungeon) {
            return;
        }
        if (Image.findPointByMulColor(screenshot, inDungeonRule, inDungeonRec).
                isValid()) {
            //MLog.info("地下城中");
            inDungeon = true;
            isPathFinding = true;
            CheckAvailableSkills();
            return;
        }
        inDungeon = false;
    }

    private void CheckBoss() {
        if (Image.findPointByMulColor(screenshot, bossRules, mapCentreRec).isValid()) {
            //MLog.info("BOSS房中");
            inBoss = true;
            return;
        }
        inBoss = false;
    }

    private void CheckLion() {
        if (Image.findPointByMulColor(screenshot, beforeLionRules, mapRec).
                isValid()) {
            //MLog.info("狮子头前");
            beforeLion = true;
            return;
        }
        beforeLion = false;
    }

    private void CheckInLion() {
        if (Image.findPointByMulColor(screenshot, inLionRule, mapRec).isValid()) {
            //MLog.info("狮子头中");
            inLion = true;
            return;
        }
        inLion = false;
    }

    private void CheckInHell() {
        if (Image.findPointByMulColor(screenshot, hellRules, mapRec).isValid()) {
            //MLog.info("深渊房中");
            inHell = true;
            return;
        }
        inHell = false;
    }

    private void CheckMonster() {
        if (Image.matchTemplate(screenshot, monsterLevelNumberFive, 0.5,
                monsterLevelRec).isValid()) {
            hasMonster = true;
            //MLog.info("有怪物");
            return;
        }
        hasMonster = false;
    }

    private void CheckDamaging() {
        if (Image.findPointByMulColor(screenshot, damageNumberRule,
                damageNumberRec).isValid()) {
            isDamaging = true;
            //MLog.info("造成伤害中");
            return;
        }
        isDamaging = false;
    }

    private void CheckDodge() {
        if (Image.findPoint(screenshot, dodgeColor, dodgeRecs[0]).isValid()) {
            canDodge = true;
            //MLog.info("可闪避");
            return;
        }
        canDodge = false;
    }

    private void CheckStuck() {
        if (!isPathFinding) {
            screenFreezeTime = 0;
            return;
        }
        if (lastScreenshot != null) {
            if (Image.matchTemplate(
                    Image.cropBitmap(lastScreenshot, stuckRec),
                    Image.cropBitmap(screenshot, stuckRec),
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
        if (Image.matchTemplate(screenshot, resultIcon, 0.8, resultRec).isValid()) {
            hasResult = true;
            MLog.info("副本结束");
            Arrays.fill(skills, false);
            Arrays.fill(availableSkills, false);
            Arrays.fill(directionalBuffs, false);
            return;
        }
        hasResult = false;
    }

    private void CheckReward() {
        if (hasReward) {
            return;
        }
        if (Image.matchTemplate(screenshot, rewardIcon, 0.8, rewardRec).isValid()) {
            hasReward = true;
            MLog.info("可翻牌");
            return;
        }
        hasReward = false;
    }

    private void CheckInventoryFull() {
        if (isInventoryFull.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, inventoryFullButton,
                0.85, completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + completeDungeonMenuRec.x1);
            p.setY(p.getY() + completeDungeonMenuRec.y1);
            //MLog.info("背包满");
            isInventoryFull = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            return;
        }
        isInventoryFull = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckBreak() {
        if (Image.findPointByMulColor(screenshot, breakRule, breakRec).isValid()) {
            canBreak = true;
            //MLog.info("可按分解");
            return;
        }
        canBreak = false;
    }

    private void CheckSell() {
        if (Image.findPointByMulColor(screenshot, sellRule, sellRec).isValid()) {
            canSell = true;
            //MLog.info("可按出售");
            return;
        }
        canSell = false;
    }

    private void CheckBreakSelectButton() {
        if (Image.findPointByMulColor(screenshot, breakSelectRule,
                breakAndSellSelectRec).isValid()) {
            canBreakSelect = true;
            //MLog.info("可分解确认选择");
            return;
        }
        canBreakSelect = false;
    }

    private void CheckSellSelectButton() {
        if (Image.findPointByMulColor(screenshot, sellSelectRule,
                breakAndSellSelectRec).isValid()) {
            canSellSelect = true;
            //MLog.info("可出售确认选择");
            return;
        }
        canSellSelect = false;
    }

    private void CheckBreakConfirmButton() {
        if (Image.findPointByMulColor(screenshot, breakConfirmRule,
                breakConfirmRec).isValid()) {
            canBreakConfirm = true;
            //MLog.info("可确认分解");
            return;
        }
        canBreakConfirm = false;
    }

    private void CheckSellConfirmButton() {
        if (Image.findPointByMulColor(screenshot, sellConfirmRule,
                sellConfirmRec).isValid()) {
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
        Point p = Image.matchTemplate(screenshot, repairButton, 0.85,
                completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + completeDungeonMenuRec.x1);
            p.setY(p.getY() + completeDungeonMenuRec.y1);
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
        Point p = Image.matchTemplate(screenshot, nextButton, 0.9,
                completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + completeDungeonMenuRec.x1);
            p.setY(p.getY() + completeDungeonMenuRec.y1);
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
        Point p = Image.matchTemplate(screenshot, goBackButton, 0.9,
                completeDungeonMenuRec);
        if (p.isValid()) {
            p.setX(p.getX() + completeDungeonMenuRec.x1);
            p.setY(p.getY() + completeDungeonMenuRec.y1);
            //MLog.info("可点击回城");
            hasGoBack = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            return;
        }
        hasGoBack = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckContinueConfirmButton() {
        if (Image.matchTemplate(screenshot, continueConfirmButton, 0.9,
                continueConfirmRec).isValid()) {
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
        if (Image.findPointByMulColor(screenshot, energyEmptyRules,
                energyRec).isValid()) {
            isEnergyEmpty = true;
            //MLog.info("疲劳为0");
            return;
        }
        isEnergyEmpty = false;
    }

    private void CheckAvailableSkills() {
        for (int i = 0; i < availableSkills.length; i++) {
            if (Image.findPoint(screenshot, skillFrameColor, skillCDRecs[i]).isValid()) {
                availableSkills[i] = true;
                skills[i] = false;
            }
        }
    }

    private void CheckSkillsCoolDown() {
        if (!inDungeon || hasResult || hasReward) {
            return;
        }
        for (int i = 0; i < skills.length; i++) {
            if (availableSkills[i]) {
                skills[i] = !Image.findPoint(screenshot, skillCDColor, skillCDRecs[i]).isValid();
            } else {
                skills[i] = false;
            }
        }
    }

    private void CheckDirectionBuffsCoolDown() {
        if (!inDungeon || hasResult || hasReward) {
            return;
        }
        if (!ammoBuff && Image.matchTemplate(screenshot, ammoBuffIcon, 0.95, skillRecs[10]).isValid()) {
            ammoBuff = true;
        }
        if (ammoBuff) {
            for (int i = 0; i < directionalBuffs.length; i++) {
                //MLog.info("方向性BUFF " + i + " 可用");
                directionalBuffs[i] = Image.findPoint(screenshot, directionalBuffCDColor, directionalBuffRecs[i]).isValid();
            }
        }
    }

    private void CheckAmmo() {
        if (!ammoBuff) {
            return;
        }
        if (Image.findPoint(screenshot, ammoColor, ammoRec).isValid()) {
            hasAmmo = false;
            return;
        }
        hasAmmo = true;
    }

}
