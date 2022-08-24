package com.slvrmn.DnfAssistant.GamePackage;

import static java.lang.Thread.sleep;

import android.graphics.Bitmap;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Model.Point;
import com.slvrmn.DnfAssistant.Model.Rectangle;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.ScreenCaptureUtil;

public class ScreenCheck implements Runnable {
    public static final int CHECK_INTERVAL = 1000;
    public static volatile boolean RUN = false;
    public static volatile int stuckTime;
    public static volatile boolean inDungeon, inBoss, beforeLion, inLion, hasMonster, isDamaging,
            canDodge, hasReward, inHell, hasContinueConfirm, isInventoryFull, isEnergyEmpty,
            canBreak, canSell, canBreakSelect, canSellSelect, canBreakConfirm;
    public static volatile Rectangle hasContinue, hasRepair;
    public static volatile boolean[] skills = {true, true, true, true, true, true, true};

    private static volatile Bitmap lastScreenshot;
    private static volatile Bitmap screenshot;

    public static synchronized void InitializeParameters() {
        stuckTime = 0;
        beforeLion = false;
        canDodge = false;
        hasContinueConfirm = false;
        hasMonster = false;
        hasReward = false;
        inBoss = false;
        inDungeon = false;
        inHell = false;
        inLion = false;
        isDamaging = false;
        isEnergyEmpty = false;
        isInventoryFull = false;
        hasContinue = Rectangle.INVALID_RECTANGLE;
        hasRepair = Rectangle.INVALID_RECTANGLE;
    }

    @Override
    public void run() {
        MLog.setDebug(true);
        MLog.info("Start Debugging");
        InitializeParameters();
        Presets.initialize();
        try {
            while (RUN) {
                MLog.info("------------------------------------------");
                screenshot = GetScreenshot();
                CheckInDungeon(screenshot);
                CheckBoss(screenshot);
                CheckLion(screenshot);
                CheckInLion(screenshot);
                CheckInHell(screenshot);
                CheckMonster(screenshot);
                CheckDamaging(screenshot);
                CheckDodge(screenshot);
                CheckStuck(screenshot);
                CheckReward(screenshot);
                CheckInventoryFull(screenshot);
                CheckBreak(screenshot);
                CheckSell(screenshot);
                CheckBreakSelectButton(screenshot);
                CheckSellSelectButton(screenshot);
                CheckBreakConfirmButton(screenshot);
                CheckSellConfirmButton(screenshot);
                CheckRepair(screenshot);
                CheckContinueButton(screenshot);
                CheckContinueConfirmButton(screenshot);
                CheckEnergyEmpty(screenshot);
                CheckSkillsCoolDown(screenshot);

                sleep(CHECK_INTERVAL);
                lastScreenshot = screenshot;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }

    private Bitmap GetScreenshot() {
        return ScreenCaptureUtil.getScreenCap();
    }

    private void CheckInDungeon(Bitmap screenshot) {
        if (Image.matchTemplate(screenshot, Presets.inDungeonIcon, 0.5, Presets.mapRec).
                isValid()) {
            MLog.info("地下城中");
            inDungeon = true;
            return;
        }
        inDungeon = false;
    }

    private void CheckBoss(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.bossRules, Presets.mapRec).isValid()) {
            MLog.info("BOSS房中");
            inBoss = true;
            return;
        }
        inBoss = false;
    }

    private void CheckLion(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.beforeLionRules, Presets.mapRec).
                isValid()) {
            MLog.info("狮子头前");
            beforeLion = true;
            return;
        }
        beforeLion = false;
    }

    private void CheckInLion(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.inLionRule, Presets.mapRec).isValid()) {
            MLog.info("狮子头中");
            inLion = true;
            return;
        }
        inLion = false;
    }

    private void CheckInHell(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.hellRules, Presets.mapRec).isValid()) {
            MLog.info("深渊房中");
            inHell = true;
            return;
        }
        inHell = false;
    }

    private void CheckMonster(Bitmap screenshot) {
        if (Image.matchTemplate(screenshot, Presets.monsterLevelNumberFive, 0.5,
                Presets.monsterLevelRec).isValid()) {
            hasMonster = true;
            MLog.info("有怪物");
            return;
        }
        hasMonster = false;
    }

    private void CheckDamaging(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.damageNumberRule,
                Presets.damageNumberRec).isValid()) {
            isDamaging = true;
            MLog.info("造成伤害中");
            return;
        }
        isDamaging = false;
    }

    private void CheckDodge(Bitmap screenshot) {
        if (Image.findPoint(screenshot, Presets.dodgeColor, Presets.dodgeRecs[0]).isValid()) {
            canDodge = true;
            MLog.info("可闪避");
            return;
        }
        canDodge = false;
    }

    private void CheckStuck(Bitmap screenshot) {
        if (lastScreenshot != null) {
            if (Image.matchTemplate(
                    Image.cropBitmap(lastScreenshot, Presets.rightBottomRec),
                    Image.cropBitmap(screenshot, Presets.rightBottomRec),
                    0.99).isValid()) {
                stuckTime++;
                MLog.info("屏幕静止" + stuckTime + "次");
                return;
            }
            stuckTime = 0;
        }
    }

    private void CheckReward(Bitmap screenshot) {
        if (Image.matchTemplate(screenshot, Presets.rewardIcon, 0.9).isValid()) {
            hasReward = true;
            MLog.info("可翻牌");
            return;
        }
        hasReward = false;
    }

    private void CheckInventoryFull(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.inventoryFullRules,
                Presets.inventoryRec).isValid()) {
            isInventoryFull = true;
            MLog.info("背包满");
            return;
        }
        isInventoryFull = false;
    }

    private void CheckBreak(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.breakRule, Presets.breakRec).isValid()) {
            canBreak = true;
            MLog.info("可按分解");
            return;
        }
        canBreak = false;
    }

    private void CheckSell(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.sellRule, Presets.sellRec).isValid()) {
            canSell = true;
            MLog.info("可按出售");
            return;
        }
        canSell = false;
    }

    private void CheckBreakSelectButton(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.breakSelectRule,
                Presets.breakAndSellSelectRec).isValid()) {
            canBreakSelect = true;
            MLog.info("可分解确认选择");
            return;
        }
        canBreakSelect = false;
    }

    private void CheckSellSelectButton(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.sellSelectRule,
                Presets.breakAndSellSelectRec).isValid()) {
            canSellSelect = true;
            MLog.info("可出售确认选择");
            return;
        }
        canSellSelect = false;
    }

    private void CheckBreakConfirmButton(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.breakConfirmRule,
                Presets.breakConfirmRec).isValid()) {
            canBreakConfirm = true;
            MLog.info("可确认分解");
            return;
        }
        canBreakConfirm = false;
    }

    private void CheckSellConfirmButton(Bitmap screenshot) {
        if (Image.findPointByMulColor(screenshot, Presets.sellConfirmRule,
                Presets.sellConfirmRec).isValid()) {
            canBreakConfirm = true;
            MLog.info("可确认出售");
            return;
        }
        canBreakConfirm = false;
    }

    private void CheckRepair(Bitmap screenshot) {
        if (hasRepair.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, Presets.repairButton, 0.9,
                Presets.completeDungeonMenuRec);
        if (p.isValid()) {
            Rectangle rectangle = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            MLog.info("需要修理");
            hasRepair = rectangle;
        }
        hasRepair = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckContinueButton(Bitmap screenshot) {
        if (hasContinue.isValid()) {
            return;
        }
        Point p = Image.matchTemplate(screenshot, Presets.nextButton, 0.9,
                Presets.completeDungeonMenuRec);
        if (p.isValid()) {
            Rectangle rectangle = new Rectangle(p.getX(), p.getY(),
                    p.getX() + 50, p.getY() + 10);
            MLog.info("可点击继续");
            hasContinue = rectangle;
        }
        hasContinue = Rectangle.INVALID_RECTANGLE;
    }

    private void CheckContinueConfirmButton(Bitmap screenshot) {
        if (Image.matchTemplate(screenshot, Presets.continueConfirmButton, 0.9,
                Presets.continueConfirmRec).isValid()) {
            hasContinueConfirm = true;
            MLog.info("可点击确认继续");
        }
        hasContinueConfirm = false;
    }

    private void CheckEnergyEmpty(Bitmap screenshot) {
        if (isEnergyEmpty) {
            return;
        }
        if (Image.findPointByMulColor(screenshot, Presets.energyEmptyRules,
                Presets.energyRec).isValid()) {
            isEnergyEmpty = true;
            MLog.info("疲劳为0");
            return;
        }
        isEnergyEmpty = false;
    }

    private void CheckSkillsCoolDown(Bitmap screenshot) {
        for (int i = 0; i < skills.length; i++) {
            if (!skills[i]) {
                skills[i] = Image.findPoint(screenshot, Presets.skillColors,
                        Presets.skillRecs[i]).isValid();
            }
        }
    }
}
