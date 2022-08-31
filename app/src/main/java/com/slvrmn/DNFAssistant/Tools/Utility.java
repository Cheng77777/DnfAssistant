package com.slvrmn.DNFAssistant.Tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.slvrmn.DNFAssistant.MainApplication;
import com.slvrmn.DNFAssistant.Model.Point;
import com.slvrmn.DNFAssistant.Model.Rectangle;

public class Utility {
    public static boolean debugging = true;
    private static final Context context = MainApplication.getInstance();

    private static final java.util.Random r = new java.util.Random();

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

    public static int RandomInt(int start, int end) {
        return r.nextInt(end - start) + start;
    }

    public static Point RandomPoint(Rectangle rectangle){
        return new Point(RandomInt(rectangle.x1, rectangle.x2),RandomInt(rectangle.y1, rectangle.y2));
    }
    public static Point RandomPoint(Rectangle rectangle,Point p){
        return new Point(RandomInt((rectangle.x1+p.getX()*2)/3, (rectangle.x2+p.getX()*2)/3),
                RandomInt((rectangle.y1+p.getY()*2)/3, (rectangle.y2+p.getY()*2)/3));
    }
}
