package com.slvrmn.DNFAssistant.GamePackage;

import static com.slvrmn.DNFAssistant.GamePackage.BattleController.isFarming;
import static com.slvrmn.DNFAssistant.GamePackage.ScreenCheck.GetScreenshot;

import com.slvrmn.DNFAssistant.Model.Image;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

public class Assistant {

    public static volatile boolean RUN = false;
    private static volatile Assistant instance;
    private static boolean initialized = false;
    private ScreenCheck screenCheck;
    private DailyQuest dailyQuest;
    private BattleController battleController;
    private Battle pathFind;

    private Assistant() {
        screenCheck = new ScreenCheck();
        dailyQuest = new DailyQuest();
        battleController = new BattleController();
        pathFind = new Battle();
    }

    public static Assistant getInstance() {
        if (instance == null) {
            instance = new Assistant();
        }
        return instance;
    }


    public void start() {
        try {
            if (!initialized) {
                Presets.Initialize();
                initialized = true;
            }
            if (!isRunning()) {
                battleController.Initialize();
                ScreenCheck.InitializeDailyQuestParameters();
                ScreenCheck.InitializeFarmingParameters();
                ScreenCheck.InitializeCharacterParameters();
                MLog.setDebug(true);
                RUN = true;
                if (Image.findPointByCheckRuleModel(GetScreenshot(), Presets.inDungeonModel).isValid()) {
                    MLog.info("Assistant: 在地下城中,开始搬砖");
                    DailyQuest.StopDailyQuesting();
                    BattleController.StartFarming();
                    BattleController.StartBattle();
                } else {
                    MLog.info("Assistant: 不在地下城中,开始每日");
                    BattleController.StopFarming();
                    DailyQuest.StartDailyQuesting();
                }
//                attack.start();
                pathFind.start();
                screenCheck.start();
                dailyQuest.start();
                battleController.start();
                Utility.show("开始运行");
            } else {
                Utility.show("运行中,请勿重复运行");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        RUN = false;
        Utility.show("停止");
    }


    public boolean isRunning() {
        return RUN;
    }
}
