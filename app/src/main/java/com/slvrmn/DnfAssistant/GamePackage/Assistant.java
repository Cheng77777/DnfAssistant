package com.slvrmn.DnfAssistant.GamePackage;

import com.slvrmn.DnfAssistant.Tools.Utility;

public class Assistant {

    private static volatile Assistant instance;
    private final Thread thread;
    private ScreenCheck main;

    private Assistant() {
        if(main == null){
            main = new ScreenCheck();
        }
        thread = new Thread(main);
    }

    public static Assistant getInstance() {
        if (instance == null) {
            instance = new Assistant();
        }
        return instance;
    }


    public void start() {
        if(!main.RUN){
            main.RUN=true;
            thread.start();
            Utility.show("开始运行");
        }
        else {
            Utility.show("运行中,请勿重复运行");
        }
    }


    public void stop() {
        main.RUN=false;
        Utility.show("停止");
    }


    public boolean isRunning() {
        return main.RUN;
    }
}
