package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Actions.EnterDailyDungeonSelectScene;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.EnterFarming;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.FindAndTap;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.FriendDaily;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.GoBackToMainScene;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.GuildDaily;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.MailDaily;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PetDaily;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.StoreDaily;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.SwitchCharacter;
import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.GetScreenshot;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.dailyDungeons;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isBattling;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isEnergyEmpty;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isFarming;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Model.Robot;
import com.slvrmn.DnfAssistant.Tools.MLog;

public class DailyQuest extends Thread {
    public static void StartUIDaily() throws InterruptedException {
        sleep(1000);
        StoreDaily();
        sleep(1000);
        FriendDaily();
        sleep(1000);
        GuildDaily();
        sleep(1000);
        MailDaily();
        sleep(1000);
        PetDaily();
    }

    public static void StartDailyDungeon() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        EnterDailyDungeonSelectScene();
        mainLoop:
        while (RUN) {
            //如果正在战斗,continue
            if (isPathFinding || isBattling) {
                sleep(3000);
                continue;
            }
            if (!RUN) {
                return;
            }
            ScreenCheck.RefreshDailyDungeons(GetScreenshot());
            //进入副本
            for (int i = 0; i < 1; i++) {
                if (dailyDungeons[i].isValid()) {
                    Robot.Press(dailyDungeons[i]);
                    while (!Actions.FindAndTap(Presets.dailyDungeonConfirmModels)) {
                        if (i >= 2) {
                            ScreenCheck.dailyNonMapDungeon = true;
                        }
                        if (!RUN) {
                            return;
                        }
                        sleep(1000);
                    }
                    continue mainLoop;
                }
            }
            if (Image.findPointByCheckRuleModel(GetScreenshot(), Presets.dailyDungeonTitleModel).isValid()) {
                Actions.PressBack();
                sleep(3000);
                FindAndTap(Presets.dailyGoBackButton,Presets.completeDungeonMenuRec);
                GoBackToMainScene();
                if (!isEnergyEmpty) {
                    EnterFarming();
                    return;
                }else {
                    if(SwitchCharacter()){
                        return;
                    }
                }
            }
        }

    }

    @Override
    public void run() {
        try {
            while (RUN) {
                MLog.info(""+isFarming);
                if (isFarming) {
                    return;
                }
                while (!Image.findPointByCheckRuleModel(GetScreenshot(), Presets.mainMenuModel).isValid()) {
                    if (!RUN) {
                        return;
                    }
                    sleep(CHECK_INTERVAL);
                }
                StartUIDaily();
                StartDailyDungeon();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
