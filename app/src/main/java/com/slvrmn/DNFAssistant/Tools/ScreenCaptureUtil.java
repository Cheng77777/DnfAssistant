package com.slvrmn.DNFAssistant.Tools;


import android.content.res.Configuration;
import android.graphics.Bitmap;

import com.slvrmn.DNFAssistant.MainApplication;
import com.slvrmn.DNFAssistant.Model.Image;


/**
 * 截图工具类
 */
public class ScreenCaptureUtil {

    private static int screenOrientation = 2;   // 0 未设置  ，1竖屏    2横屏


    /**
     * 强制设置为横屏模式
     */
    public static void setHorizontalScreen() {
        ScreenCaptureUtil.screenOrientation = 2;
    }


    /**
     * 强制设置为竖屏模式
     */
    public static void setVerticalScreen() {
        ScreenCaptureUtil.screenOrientation = 1;
    }


    /**
     * 获取屏幕图像
     *
     * @return
     */
    public static synchronized Bitmap getScreenCap() {
        Bitmap o_img;
        boolean retry = false;
        do {
            if (retry) {
                MLog.info("资源已被回收，尝试重试！");
            }
            if (ScreenCaptureUtil.screenOrientation == 0) {
                if (MainApplication.getInstance().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    o_img = ScreenCaptureUtilByMediaPro.getScreenCapVertical();
                } else {
                    //横屏
                    o_img = ScreenCaptureUtilByMediaPro.getScreenCapHorizontal();
                }
            } else if (ScreenCaptureUtil.screenOrientation == 1) {
                o_img = ScreenCaptureUtilByMediaPro.getScreenCapVertical();
            } else {
                o_img = ScreenCaptureUtilByMediaPro.getScreenCapHorizontal();
            }
            retry = true;
        } while (o_img == null || o_img.isRecycled());


        return Bitmap.createBitmap(o_img);

    }

    /**
     * 指定范围截图
     *
     * @param leftX
     * @param leftY
     * @param rightX
     * @param rightY
     * @return
     */
    public static Bitmap getScreenCap(int leftX, int leftY, int rightX, int rightY) {
        Bitmap bitmap = getScreenCap();
        return Image.cropBitmap(bitmap, leftX, leftY, rightX, rightY);
    }
}