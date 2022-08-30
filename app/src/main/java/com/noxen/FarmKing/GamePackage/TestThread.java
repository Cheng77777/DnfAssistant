package com.noxen.FarmKing.GamePackage;

public class TestThread extends Thread{
    @Override
    public void run() {
        try {
            Actions.GetDailyReward();
            Actions.SaveItems();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
