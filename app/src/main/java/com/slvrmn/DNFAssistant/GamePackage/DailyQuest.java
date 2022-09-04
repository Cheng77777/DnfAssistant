package com.slvrmn.DNFAssistant.GamePackage;

import static com.slvrmn.DNFAssistant.GamePackage.Actions.EnterDailyDungeonSelectScene;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.EnterFarming;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.FindAndTapTilDisappear;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.FriendAndGuildDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.GoBackToMainScene;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.MailDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.PetDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.StoreDaily;
import static com.slvrmn.DNFAssistant.GamePackage.Actions.SwitchCharacter;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.GetScreenshot;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.dailyDungeons;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.isEnergyEmpty;

import com.slvrmn.DNFAssistant.Model.Image;
import com.slvrmn.DNFAssistant.Model.Robot;
import com.slvrmn.DNFAssistant.Tools.MLog;

public class DailyQuest extends Thread {
    public static volatile boolean isDailyQuesting;
    public static volatile boolean inDailyDungeon;

    public static synchronized void StopDailyQuesting() {
        isDailyQuesting = false;
    }

    public static synchronized void StartDailyQuesting() {
        isDailyQuesting = true;
    }

    public void StartUIDaily() throws InterruptedException {
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        MailDaily();
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        PetDaily();
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        StoreDaily();
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        FriendAndGuildDaily();
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
    }

    public void StartDailyDungeon() throws InterruptedException {
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        sleep(1000);
        GoBackToMainScene("DailyQuest.StartDailyDungeon1");
        if (!Assistant.getInstance().isRunning()) {
            return;
        }
        EnterDailyDungeonSelectScene();
        mainLoop:
        while (Assistant.getInstance().isRunning()) {
            //如果正在战斗,continue
            if (BattleController.isBattling) {
                sleep(3000);
                continue;
            }
            if (!Assistant.getInstance().isRunning()) {
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
                        if (!Assistant.getInstance().isRunning()) {
                            return;
                        }
                        sleep(1000);
                    }
                    inDailyDungeon = true;
                    BattleController.StartBattle();
                    continue mainLoop;
                }
            }
            if (Image.findPointByCheckRuleModel(GetScreenshot(), Presets.dailyDungeonTitleModel).isValid()) {
                if (!Assistant.getInstance().isRunning()) {
                    return;
                }
                if (inDailyDungeon) {
                    Actions.PressBack("DailyQuest.StartDailyDungeon");
                    sleep(5000);
                    FindAndTapTilDisappear(Presets.dailyReturnButtonModel);
                    inDailyDungeon = false;
                }
                GoBackToMainScene("DailyQuest.StartDailyDungeon2");
                if (!isEnergyEmpty) {
                    EnterFarming();
                    break;
                } else {
                    if (SwitchCharacter()) {
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void run() {
        try {
            while (!Assistant.getInstance().isStopped()) {
                if (Assistant.getInstance().isPausing()) {
                    sleep(3000);
                    continue;
                }
                if (!isDailyQuesting) {
                    sleep(CHECK_INTERVAL * 100);
                    continue;
                }
                sleep(2000);
                GoBackToMainScene("DailyQuest.run");
                StartUIDaily();
                StartDailyDungeon();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
