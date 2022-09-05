package com.slvrmn.DNFAssistant.GamePackage;

import com.slvrmn.DNFAssistant.Model.Image;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

public class Assistant {

    public static volatile boolean RUN = false;
    public static volatile boolean PAUSE = false;
    private static volatile Assistant instance;
    private static boolean presetsInitialized = false;
    private static boolean initialized = false;
    private ScreenCheck screenCheck;
    private DailyQuest dailyQuest;
    private BattleController battleController;
    private Battle battle;

    private Assistant() {
        screenCheck = new ScreenCheck();
        dailyQuest = new DailyQuest();
        battleController = new BattleController();
        battle = new Battle();
    }

    public static Assistant getInstance() {
        if (instance == null) {
            instance = new Assistant();
        }
        return instance;
    }

    public void start() {
        Utility.show("开始运行");
        try {
            if (!presetsInitialized) {
                Presets.Initialize();
                presetsInitialized = true;
            }

            if (!isRunning()) {
                battleController.Initialize();
                ScreenCheck.InitializeDailyQuestParameters();
                ScreenCheck.InitializeFarmingParameters();
                ScreenCheck.InitializeCharacterParameters();
                MLog.setDebugToConsole(true);
                RUN = true;
                do {
                    if (Image.findPointByCheckImageModel(ScreenCheck.GetScreenshot(), Presets.inDungeonModel).isValid()) {
                        Utility.show("Assistant: 在地下城中,开始搬砖");
                        MLog.info("Assistant: 在地下城中,开始搬砖");
                        DailyQuest.StopDailyQuesting();
                        BattleController.StartFarming();
                        BattleController.StartBattle();
                        initialized = true;
                    } else if (Image.findPointByCheckImageModel(ScreenCheck.GetScreenshot(), Presets.dailyMenuModel).isValid()) {
                        Utility.show("Assistant: 不在地下城中,开始每日");
                        MLog.info("Assistant: 不在地下城中,开始每日");
                        BattleController.StopFarming();
                        DailyQuest.StartDailyQuesting();
                        initialized = true;
                    }
                }
                while (!initialized && RUN);
                battle.start();
                screenCheck.start();
                dailyQuest.start();
                battleController.start();
            } else {
                Utility.show("运行中,请勿重复运行");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Pause() {
        PAUSE = true;
        Utility.show("暂停");
    }


    public void Stop() {
        RUN = false;
        initialized = false;
        Utility.show("停止");
    }


    public boolean isRunning() {
        return RUN && !PAUSE;
    }

    public boolean isPausing() {
        return PAUSE;
    }

    public boolean isStopped() {
        return !RUN;
    }

    public void Restart() throws InterruptedException {
        initialized = false;
        battleController.Initialize();
        ScreenCheck.InitializeDailyQuestParameters();
        ScreenCheck.InitializeFarmingParameters();
        ScreenCheck.InitializeCharacterParameters();
        RUN = true;
        do {
            if (Image.findPointByCheckImageModel(ScreenCheck.GetScreenshot(), Presets.inDungeonModel).isValid()) {
                MLog.info("Assistant: 在地下城中,开始搬砖");
                DailyQuest.StopDailyQuesting();
                BattleController.StartFarming();
                BattleController.StartBattle();
                initialized = true;
            } else if (Image.findPointByCheckImageModel(ScreenCheck.GetScreenshot(), Presets.dailyMenuModel).isValid()) {
                MLog.info("Assistant: 不在地下城中,开始每日");
                BattleController.StopFarming();
                DailyQuest.StartDailyQuesting();
                initialized = true;
            }
        }
        while (!initialized && RUN);
        PAUSE = false;
    }
}
