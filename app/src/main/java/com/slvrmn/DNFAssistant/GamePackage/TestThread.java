package com.slvrmn.DNFAssistant.GamePackage;

import com.slvrmn.DNFAssistant.Tools.MLog;

public class TestThread implements Runnable{
    @Override
    public void run() {
        try {
            int i = 0;
            while (true){
                MLog.info("Test Thread " + i);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.info(e.toString());
        }
    }
}
