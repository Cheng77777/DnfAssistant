package com.slvrmn.DnfAssistant.GamePackage;

import android.graphics.Bitmap;
import android.os.Environment;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.ScreenCaptureUtil;
import com.slvrmn.DnfAssistant.Tools.Utility;

import static android.os.SystemClock.sleep;

public class Main implements Runnable{
    public volatile boolean run = false;
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    @Override
    public void run() {
        while (run) {
            MLog.setDebug(true);
            if(!ColorRules.initialized) {
                ColorRules.initialize();
            }

            /** 游戏初始化 **/
            //TODO
            /** 公会签到 **/
            //TODO
            /** 好友 **/
            //TODO
            /** 接受邮件 **/
            //TODO
            /** 日常 **/
            //TODO
            /** 进图 **/
            //TODO
            /** 识别地图 **/
            //TODO
            /** 识别战斗 **/
            //TODO
            AutoFarm();
            /** 换号 **/
            //TODO

            /****************************  模板匹配demo  *******************************/
//        InputStream is = null;
//        try {
//            is = MainApplication.getInstance().getAssets().open("ImgMatch.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(is);
//        //在当前屏幕中查找模板图片

//        // 点击找到的这个图
//        Robot.tap(point);
//
//
//        /**************************** 文字识别demo  **********************************/
//        try {
//            //识别素材文件中的ocrTest.png图片中的文字
//            is = MainApplication.getInstance().getAssets().open("ocrTest.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bitmap = BitmapFactory.decodeStream(is);
//
//        String res = TessactOcr.img2string(ScreenCaptureUtil.getScreenCap(0, 0, 200, 30), "chi_sim", "", "");
//        MLog.info("文字识别结果：" + res);
//
//
//        /*****************************  特征点找图  ************************************/
//        //当前屏幕中查找chrome图标（特征点是3120X1440分辨率手机制作）
//            Point point = Image.findPointByMulColor(ScreenCaptureUtil.getScreenCap(), "434FD7,65|0|414DDB,90|55|46CDFF,5|86|5FA119");


//        //点击chrome图标
//            Robot.tap(new Point(1, 1));

//            Point point = Image.matchTemplate(ScreenCaptureUtil.getScreenCap(), ColorRules.reward, 0.1);
//            if(point.isValid()){
//                Toast.show("找到模板");
//            }else{
//                Toast.show("未找到");
//            }
            sleep(1000);

        }
        Utility.show("线程停止运行");
    }

    private void AutoFarm() {
        /** 检测图中 **/
        while (!CheckFirstRoom()){
            sleep(100);

            /** 检测退出 **/
            if(!run){
                Utility.show("线程停止运行");
                return;
            }

        }
        Utility.show("在图中,开始战斗");
    }

    private boolean CheckFirstRoom() {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap(1152,45,1236,116);
        return Image.matchTemplate(screenshot,ColorRules.inDungeon,0.5).isValid();
    }
}
