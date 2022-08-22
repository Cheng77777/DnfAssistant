package com.slvrmn.DnfAssistant;

import android.app.Application;

import com.slvrmn.DnfAssistant.Service.Accessibility;


public class MainApplication extends Application {
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static int dpi;
    private static MainApplication instance;

    public static MainApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public boolean checkAccessibilityService() {
        return Accessibility.DOM != null;
    }
}
