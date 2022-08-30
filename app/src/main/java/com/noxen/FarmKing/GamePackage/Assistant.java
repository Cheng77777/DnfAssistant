package com.noxen.FarmKing.GamePackage;

import static com.noxen.FarmKing.GamePackage.ScreenCheck.GetScreenshot;
import static com.noxen.FarmKing.GamePackage.BattleController.*;

import com.noxen.FarmKing.Model.Image;
import com.noxen.FarmKing.Tools.*;

public class Assistant {

    public static volatile boolean RUN = false;
    private static volatile Assistant instance;
    private ScreenCheck screenCheck;
    private DailyQuest dailyQuest;
    private BattleController battleController;
    private static boolean initialized = false;

    private Assistant() {
        if(screenCheck == null){
            screenCheck = new ScreenCheck();
            dailyQuest = new DailyQuest();
            battleController = new BattleController();
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
            if(Image.findPointByCheckRuleModel(GetScreenshot(),Presets.mainMenuModel).isValid()){
                isFarming = false;
            }
            else {
                isFarming = true;
                isBattling = true;
            }
        }
        if(!RUN){
            ScreenCheck.InitializeDailyQuestParameters();
            ScreenCheck.InitializeFarmingParameters();
            ScreenCheck.InitializeCharacterParameters();
            MLog.setDebug(true);
            RUN=true;
            screenCheck.start();
            dailyQuest.start();
            battleController.start();
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
