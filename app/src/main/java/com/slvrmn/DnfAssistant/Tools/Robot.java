package com.slvrmn.DnfAssistant.Tools;


import android.os.Build;

import com.slvrmn.DnfAssistant.MainApplication;
import com.slvrmn.DnfAssistant.Tools.InputImp.AccessibilityInput;
import com.slvrmn.DnfAssistant.Tools.InputImp.Input;
import com.slvrmn.DnfAssistant.Tools.InputImp.NullInput;


/**
 * 模拟操作的实现类
 * <p>
 * 目前只使用了xposed提权实现
 * <p>
 */
public class Robot {


    private static int execType;

    public static final int ExecTypeAccessibillty = 18;

    private static Input getInput() {
        MainApplication mainApplication = MainApplication.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && mainApplication.checkAccessibilityService()) {
            execType = ExecTypeAccessibillty;
            return AccessibilityInput.getInstance();
        } else {
            return NullInput.getInstance();
        }
    }

    /**
     * 设置以什么方式执行模拟操作
     * <p>
     * 目前支持无障碍和xposed提权操作
     *
     * @param execType
     */
    public static void setExecType(int execType) {
        Robot.execType = execType;
    }


    /**
     * 点击操作
     *
     * @param x
     * @param y
     */
    public static void tap(final int x, final int y) {
        getInput().tap(x, y);
    }

    /**
     * 长按操作，可以自定义按下时间，单位为毫秒
     *
     * @param x
     * @param y
     * @param delay
     */
    public static void tap(final int x, final int y, final long delay) {
        getInput().tap(x, y, delay);
    }


    public static void tap(Point p) {
        getInput().tap(p);
    }


    public static void tap(Point p, long delay) {
        getInput().tap(p, delay);
    }


    /**
     * 拖拽操作
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param duration //单位为毫秒
     */
    public static void swipe(int x1, int y1, int x2, int y2, int duration) {
        getInput().swipe(x1, y1, x2, y2, duration);
    }

    public static void swipe(float x1, float y1, float x2, float y2, float duration) {
        getInput().swipe(x1, y1, x2, y2, duration);
    }


    /**
     * 往输入框输入文字
     *
     * @param str
     */
    public static void input(String str) {
        getInput().input(str);
    }


    /**
     * 放大屏幕（捏开）
     *
     * @param distance // 缩放距离，0到100
     */
    public static void pinchOpen(int distance) {
        getInput().pinchOpen(distance);
    }


    /**
     * 缩小屏幕（捏合）
     *
     * @param distance // 缩放距离，0到100
     */
    public static void pinchClose(int distance) {
        getInput().pinchClose(distance);
    }

}