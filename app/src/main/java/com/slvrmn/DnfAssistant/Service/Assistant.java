package com.slvrmn.DnfAssistant.Service;

import com.slvrmn.DnfAssistant.GamePackage.Main;
import com.slvrmn.DnfAssistant.Tools.Utility;

public class Assistant {

    private static volatile Assistant instance;
    private Thread thread;
    private Main main;

    private Assistant() {
        if(main == null){
            main = new Main();
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
        if(!main.run){
            main.run=true;
            thread.start();
        }
        else {
            Utility.show("运行中,请勿重复运行");
        }
    }


    public void stop() {
        main.run=false;
    }


    public boolean isRunning() {
        return main.run;
    }
}
