package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inDungeon;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isDailyQuesting;

import com.slvrmn.DnfAssistant.Tools.MLog;

public class DailyQuest extends Thread {
    @Override
    public void run() {
        try {
            while (RUN) {
                if (!isDailyQuesting) {
                    sleep(CHECK_INTERVAL);
                    continue;
                }
                if (inDungeon) {
                    continue;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
