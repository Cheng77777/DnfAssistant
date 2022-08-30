package com.noxen.FarmKing.GamePackage;

import com.noxen.FarmKing.Tools.MLog;
import com.noxen.FarmKing.Tools.Utility;

public class PathFind extends Thread {
    private int hellCount = 0;

    @Override
    public void run() {
        try {
            mainLoop:
            while (Assistant.RUN) {
                if (!BattleController.isBattling || !BattleController.isPathfinding) {
                    sleep(ScreenCheck.CHECK_INTERVAL);
                    continue;
                }
                if (Actions.UseBuff()) {
                    continue;
                }
                MLog.info("自动寻路中");
                int pressTime = Utility.RandomInt(15000, 16000);
                Actions.PressLongAttack(pressTime, ScreenCheck.CHECK_INTERVAL);

                if (!Actions.SleepCheckIsPathfinding(pressTime)) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
