package com.slvrmn.DnfAssistant.GamePackage;

import com.slvrmn.DnfAssistant.Tools.*;

public class Assistant {

    public static volatile boolean RUN = false;
    private static volatile Assistant instance;
    private ScreenCheck screenCheck;
    private AutoPathFinding autoPathFinding;
    private AutoBattle autoBattle;

    private Assistant() {
        if(screenCheck == null){
            screenCheck = new ScreenCheck();
            autoPathFinding = new AutoPathFinding();
            autoBattle = new AutoBattle();
        }
    }

    public static Assistant getInstance() {
        if (instance == null) {
            instance = new Assistant();
        }
        return instance;
    }


    public void start() {
        if(!RUN){
            MLog.setDebug(true);
            RUN=true;
            screenCheck.start();
            autoPathFinding.start();
            autoBattle.start();
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
