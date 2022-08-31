package com.slvrmn.DNFAssistant.InputImp;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.slvrmn.DNFAssistant.GamePackage.Assistant;
import com.slvrmn.DNFAssistant.Model.Point;
import com.slvrmn.DNFAssistant.Service.Accessibility;
import com.slvrmn.DNFAssistant.Tools.MLog;
import com.slvrmn.DNFAssistant.Tools.Utility;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccessibilityInput implements Input {


    private static volatile AccessibilityInput instance;

    public static AccessibilityInput getInstance() {
        if (instance == null) {
            synchronized (AccessibilityInput.class) {
                if (instance == null) {
                    instance = new AccessibilityInput();
                }
            }
        }

        return instance;
    }


    @Override
    public void tap(int x, int y) {
        if (x < 0 || y < 0) {
            return;
        }
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo((float) x, (float) y);
        path.lineTo((float) x, (float) y);
        /**
         * 参数path：笔画路径
         * 参数startTime：时间 (以毫秒为单位)，从手势开始到开始笔划的时间，非负数
         * 参数duration：笔划经过路径的持续时间(以毫秒为单位)，非负数
         */
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 50));
        final GestureDescription build = builder.build();

        Accessibility.getInstance().dispatchGesture(build, null, null);
    }

    @Override
    public void tap(int x, int y, long delay) {
        if(!Assistant.RUN){
            return;
        }
        if (x < 0 || y < 0) {
            return;
        }
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo((float) x, (float) y);
        path.lineTo((float) x, (float) y);
        /**
         * 参数path：笔画路径
         * 参数startTime：时间 (以毫秒为单位)，从手势开始到开始笔划的时间，非负数
         * 参数duration：笔划经过路径的持续时间(以毫秒为单位)，非负数
         */
        builder.addStroke(new GestureDescription.StrokeDescription(path, 1, delay));
        final GestureDescription build = builder.build();
        Accessibility.getInstance().dispatchGesture(build, null, null);
    }

    @Override
    public void tap(Point p) {
        tap(p.getX(), p.getY());
    }

    @Override
    public void tap(Point p, long delay) {
        tap(p.getX(), p.getY(), delay);
    }

    @Override
    public void swipe(int x1, int y1, int x2, int y2, int duration) {
        swipe((float) x1, (float) y1, (float) x2, (float) y2, duration);
    }

    @Override
    public void swipe(float x1, float y1, float x2, float y2, float duration) {
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        float xLen, yLen, scale;
        xLen = x2 - x1;
        yLen = y2 - y1;
        path.moveTo(x1, y1);
        path.lineTo(x1 + 0.2f * xLen, y1 + 0.2f * yLen);
        path.lineTo(x2, y2);
        /**
         * 参数path：笔画路径
         * 参数startTime：时间 (以毫秒为单位)，从手势开始到开始笔划的时间，非负数
         * 参数duration：笔划经过路径的持续时间(以毫秒为单位)，非负数
         */
        builder.addStroke(new GestureDescription.StrokeDescription(path, 1, (long) duration));
        final GestureDescription build = builder.build();
        Accessibility.getInstance().dispatchGesture(build, null, null);
    }

    @Override
    public void input(String str) {

    }

    @Override
    public void pinchOpen(int distance) {
        Utility.show("目前仅在xposed方式中实现了该方法");
        MLog.error("目前仅在xposed方式中实现了该方法");
    }

    @Override
    public void pinchClose(int distance) {
        Utility.show("目前仅在xposed方式中实现了该方法");
        MLog.error("目前仅在xposed方式中实现了该方法");
    }
}
