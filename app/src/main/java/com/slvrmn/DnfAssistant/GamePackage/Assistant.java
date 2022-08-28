package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.GetScreenshot;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isFarming;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Tools.*;

public class Assistant {

    public static volatile boolean RUN = false;
    private static volatile Assistant instance;
    private ScreenCheck screenCheck;
    private AutoPathFinding autoPathFinding;
    private AutoBattle autoBattle;
    private DailyQuest dailyQuest;
    private static boolean initialized = false;

    private Assistant() {
        if(screenCheck == null){
            screenCheck = new ScreenCheck();
            autoPathFinding = new AutoPathFinding();
            autoBattle = new AutoBattle();
            dailyQuest = new DailyQuest();
        }
    }

    public static Assistant getInstance() {
        if (instance == null) {
            instance = new Assistant();
        }
        return instance;
    }


    public void start() {
        if(!initialized){
            Presets.Initialize();
            initialized = true;
        }
        if(!RUN){
            ScreenCheck.InitializeDailyQuestParameters();
            ScreenCheck.InitializeFarmingParameters();
            ScreenCheck.InitializeCharacterParameters();
            MLog.setDebug(true);
            RUN=true;
            if(Image.findPointByCheckRuleModel(GetScreenshot(),Presets.mainMenuModel).isValid()){
                isFarming = false;
            }
            else {
                isFarming = true;
            }
            screenCheck.start();
            autoPathFinding.start();
            autoBattle.start();
            dailyQuest.start();
            Utility.show("开始运行");
        }
        else {
            Utility.show("运行中,请勿重复运行");
        }
    }


    public void stop() {
        RUN=false;
        Utility.show("停止");
    }


    public boolean isRunning() {
        return RUN;
    }
}
