package com.noxen.FarmKing.GamePackage;

import static com.noxen.FarmKing.GamePackage.Actions.EnterDailyDungeonSelectScene;
import static com.noxen.FarmKing.GamePackage.Actions.EnterFarming;
import static com.noxen.FarmKing.GamePackage.Actions.FindAndTap;
import static com.noxen.FarmKing.GamePackage.Actions.FriendDaily;
import static com.noxen.FarmKing.GamePackage.Actions.GoBackToMainScene;
import static com.noxen.FarmKing.GamePackage.Actions.GuildDaily;
import static com.noxen.FarmKing.GamePackage.Actions.MailDaily;
import static com.noxen.FarmKing.GamePackage.Actions.PetDaily;
import static com.noxen.FarmKing.GamePackage.Actions.StoreDaily;
import static com.noxen.FarmKing.GamePackage.Actions.SwitchCharacter;
import static com.noxen.FarmKing.GamePackage.Assistant.RUN;
import static com.noxen.FarmKing.GamePackage.BattleController.isBattling;
import static com.noxen.FarmKing.GamePackage.BattleController.isFarming;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.GetScreenshot;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.dailyDungeons;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isEnergyEmpty;

import com.noxen.FarmKing.Model.Image;
import com.noxen.FarmKing.Model.Robot;
import com.noxen.FarmKing.Tools.MLog;

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
            for (int i = 0; i < dailyDungeons.length-2; i++) {
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
                    isBattling = true;
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
