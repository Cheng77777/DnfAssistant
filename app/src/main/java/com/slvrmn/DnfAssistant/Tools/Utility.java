package com.slvrmn.DnfAssistant.Tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.slvrmn.DnfAssistant.MainApplication;

public class Utility {
    public static boolean debugging = true;
    private static Context context = MainApplication.getInstance();

    private static java.util.Random r = new java.util.Random();

    public static void show(final String msg) {
        if (debugging) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static int randomInt(int start, int end) {
        return r.nextInt(end - start) + start;
    }
}
