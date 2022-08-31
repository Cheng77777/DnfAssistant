package com.slvrmn.DNFAssistant.GamePackage;

import static com.slvrmn.DNFAssistant.GamePackage.Actions.EnterDailyDungeonSelectScene;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.EnterFarming;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.FindAndTap;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.FriendDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.GoBackToMainScene;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.GuildDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.MailDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PetDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.StoreDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.SwitchCharacter;
import static com.slvrmn.DNFAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DNFAssistant.GamePackage.BattleController.isBattling;
import static com.slvrmn.DNFAssistant.GamePackage.BattleController.isFarming;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.GetScreenshot;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.dailyDungeons;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.isEnergyEmpty;

import com.slvrmn.DNFAssistant.Model.Image;
import com.slvrmn.DNFAssistant.Model.Robot;
import com.slvrmn.DNFAssistant.Tools.MLog;

public class DailyQuest extends Thread {
    public static void StartUIDaily() throws InterruptedException {
        StoreDaily();
        FriendDaily();
        GuildDaily();
        MailDaily();
        PetDaily();
    }

    public static void StartDailyDungeon() throws InterruptedException {
        sleep(1000);
        GoBackToMainScene();
        EnterDailyDungeonSelectScene();
        sleep(3000);
        mainLoop:
        while (RUN) {
            //如果正在战斗,continue
            if (BattleController.isBattling) {
                sleep(3000);
                continue;
            }
            if (!RUN) {
                return;
            }
            ScreenCheck.RefreshDailyDungeons(GetScreenshot());
            //进入副本
            for (int i = 0; i < dailyDungeons.length; i++) {
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
                    BattleController.StartBattle();
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
                    continue ;
                }else {
                    if(SwitchCharacter()){
                        continue ;
                    }
                }
            }
        }

    }

    @Override
    public void run() {
        try {
            while (RUN) {
                if (isFarming) {
                    return;
                }
                GoBackToMainScene();
                StartUIDaily();
                StartDailyDungeon();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
