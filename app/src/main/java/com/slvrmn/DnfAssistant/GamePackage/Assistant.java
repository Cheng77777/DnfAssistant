package com.slvrmn.DnfAssistant.GamePackage;

import com.slvrmn.DnfAssistant.Tools.Utility;

public class Assistant {

    public static volatile boolean RUN = false;
    private static volatile Assistant instance;
    private ScreenCheck screenCheck;

    private Assistant() {
        if(screenCheck == null){
            screenCheck = new ScreenCheck();
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
            RUN=true;
            screenCheck.start();
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
