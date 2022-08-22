package com.slvrmn.DnfAssistant.GamePackage;

import static android.os.SystemClock.sleep;

import android.graphics.Bitmap;
import android.os.Environment;

import com.slvrmn.DnfAssistant.Model.Image;
import com.slvrmn.DnfAssistant.Model.Robot;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.ScreenCaptureUtil;
import com.slvrmn.DnfAssistant.Tools.Utility;

public class Main implements Runnable {
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final int checkInterval = 200;
    private static final int backJumpCD = 4;
    private static final int checkBattlingCD = 1;
    private static final int checkCoolDownCD = 1;
    private static final boolean[] skills = {true, true, true, true, true, true, true};
    private static boolean beforeLion = false;
    private static boolean inLion = false;
    private static boolean autoFarm = false;
    private static boolean autoBattle = false;
    private static boolean reward = false;
    private static int backJump = 9;
    private static int checkBattling = 9;
    private static int checkCoolDown = 9;
    private static boolean isBattling = true;
    public volatile boolean run = false;

    @Override
    public void run() {
        try {
            while (run) {
                MLog.setDebug(true);
                if (!Presets.initialized) {
                    Presets.initialize();
                }

                /** 游戏初始化 **/
                //TODO
                /** 公会签到 **/
                //TODO
                /** 好友 **/
                //TODO
                /** 接受邮件 **/
                //TODO
                /** 进图 **/
                //TODO
                /** 识别地图 **/
                //TODO
                /** 识别战斗 **/
                //TODO
                AutoFarm();
                /** 日常 **/
                //TODO
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

//                Bitmap screenshot = ScreenCaptureUtil.getScreenCap();
//                CheckLion(screenshot);


//                if(CheckBattle()){
//                    Utility.show("True");
//                }
//                else {
//                    Utility.show("False");
//                }
//                sleep(2000);

            }
            Utility.show("线程停止运行");
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }

    private void AutoFarm() throws InterruptedException {

        System.out.println("进入自动搬砖");

        autoFarm = true;
        autoBattle = true;

        while (autoFarm) {
            /** 检测图中 **/
            while (!CheckInDungeon()) {

                System.out.println("检测是否进入地图");

                sleep(100);

                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return;
                }

            }
            while (autoBattle) {
                /** 自动寻路循环 **/
                while (!PathFinding()) {

                    System.out.println("自动寻路");

                    /** 检测退出 **/
                    if (!run) {
                        Utility.show("线程停止运行");
                        break;
                    }
                }

                if (reward) {

                    System.out.println("翻牌");

                    GetReward();
                } else {
                    while (!AutoBattle()) {

                        System.out.println("自动战斗");

                        break;
                    }
                }


                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return;
                }
            }
        }

        /** 检测退出 **/
        if (!run) {
            Utility.show("线程停止运行");
            return;
        }
    }

    private boolean CheckInDungeon() {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap(Presets.mapRec);
        return Image.matchTemplate(screenshot, Presets.inDungeon, 0.5).isValid();
    }

    private boolean PathFinding() throws InterruptedException {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap();
        /** 检测狮子头 **/
        CheckLion(screenshot);
        /** 检测BUFF **/
        CheckBuff(screenshot);

        if(beforeLion){
            /** 进入狮子头房间 **/
            while (!inLion){
                int random = Utility.RandomInt(2000,2300);
                Robot.LongPress(Presets.lionRecs[0],random);
                sleep(random);
                random = Utility.RandomInt(1000,1500);
                Robot.LongPress(Presets.lionRecs[1],random);
                sleep(random);
                random = Utility.RandomInt(1000,1500);
                Robot.LongPress(Presets.lionRecs[0],random);
                sleep(random);
                random = Utility.RandomInt(1000,1500);
                Robot.LongPress(Presets.lionRecs[2],random);
                sleep(3000);

                for (int i = 0; i < 10; i++) {
                    screenshot = ScreenCaptureUtil.getScreenCap();
                    if(Image.matchTemplate(screenshot,Presets.lion,0.5,Presets.mapRec).isValid()){
                        inLion = true;
                        beforeLion = false;
                        Robot.swipe(Presets.dodgeRecs[0],Presets.dodgeRecs[1],Utility.RandomInt(300,400));
                        Robot.Press(Presets.uniqueSkillRec,Utility.RandomInt(3,4));
                        break;
                    }
                }
                if(inLion){
                    break;
                }
                random = Utility.RandomInt(2000,2500);
                Robot.LongPress(Presets.attackRec,random);
                sleep(random);
                for (int i = 0; i < 6; i++) {
                    screenshot = ScreenCaptureUtil.getScreenCap();
                    if(isBattling(screenshot)){
                        Robot.Press(Presets.backJumpRec);
                        return true;
                    }
                    sleep(300);
                }
                Robot.Press(Presets.backJumpRec);

                /** 检测退出 **/
                if (!run) {
                    Utility.show("线程停止运行");
                    return false;
                }
            }
        }
        else {
            /** 长按攻击 **/
            long pressTime = Utility.RandomInt(2500, 3000);
            Robot.Press(Presets.attackRec, 5);
            Robot.LongPress(Presets.attackRec, pressTime);
            /** 检测战斗 **/
            for (int i = 1; i < pressTime / checkInterval; i++) {
                screenshot = ScreenCaptureUtil.getScreenCap();

                /** 检测狮子头 **/
                CheckLion(screenshot);

                /** 检测战斗 **/
                if (isBattling(screenshot)) {
                    isBattling = true;
                    return true;
                }

                /** 检测翻牌 **/
                if (CheckReward(screenshot)) {
                    reward = true;
                    return true;
                }
                sleep(checkInterval);
            }
        }
        return false;
    }

    private boolean CheckReward(Bitmap screenshot) {
        if(Image.matchTemplate(screenshot,Presets.reward,0.9).isValid()){
            return true;
        }
        return false;
    }

    private void GetReward() throws InterruptedException {
        autoBattle = false;
        int random = Utility.RandomInt(5,10);
        Robot.Press(Presets.attackRec,random);
        sleep(random*400);
        random = Utility.RandomInt(500,1000);
        Robot.LongPress(Presets.lionRecs[2], random);
        sleep(random);
        random = Utility.RandomInt(500,1000);
        Robot.LongPress(Presets.lionRecs[1], random);
        sleep(random);
        random = Utility.RandomInt(500,1000);
        Robot.LongPress(Presets.lionRecs[0], random);
        sleep(random);
        random = Utility.RandomInt(2500,3000);
        Robot.LongPress(Presets.attackRec, random);
        sleep(random);

//        boolean next = false;
//        while (!next){
//
//        }
    }

    private boolean AutoBattle() throws InterruptedException {
        Bitmap screenshot = ScreenCaptureUtil.getScreenCap();
        /** 检测狮子头 **/
        CheckLion(screenshot);
        /** 长按攻击 **/
        long pressTime = Utility.RandomInt(300,500);
        Robot.Press(Presets.attackRec, (int)pressTime/50);
        /** 检测战斗 **/
        for (int i = 0; i < pressTime / checkInterval; i++) {
            screenshot = ScreenCaptureUtil.getScreenCap();

            /** 检测狮子头 **/
            CheckLion(screenshot);

            /** 检测使用技能 **/
            CheckSkill(screenshot);

            /** 检测结束战斗 **/
            if (isEndBattling(screenshot)) {
                backJump = 9;
                return true;
            }
            sleep(checkInterval);
        }

        return false;
    }

    private void CheckSkill(Bitmap screenshot) throws InterruptedException {
        /** 后跳 **/
        if (backJump >= backJumpCD) {
            Robot.Press(Presets.backJumpRec,2);
            backJump = 0;
        }
        /** 刷新技能冷却 **/
        if (checkCoolDown >= checkCoolDownCD) {
            RefreshCoolDownList(screenshot);
        }
        /** 使用技能 **/
        if (isBattling) {
            UseSkills();
        }
        /** 检测战斗中 **/
        if (checkBattling >= checkBattlingCD) {
            isBattling = isBattling(screenshot);
        }
        backJump++;
        checkCoolDown++;
        checkBattling++;
    }

    private void UseSkills() {
        int count = 0;
        for (int i = 0; i < skills.length; i++) {
            if (skills[i]) {
                count++;
                Robot.Press(Presets.skillRecs[i]);
                sleep(500);
                skills[i] = false;
            }
            if (count >= 2) {
                return;
            }
        }
    }

    private void RefreshCoolDownList(Bitmap screenshot) {
        for (int i = 0; i < skills.length; i++) {
            skills[i] = CheckSkillCoolDown(screenshot, i);
        }
    }

    private boolean CheckSkillCoolDown(Bitmap screenshot, int i) {
        return Image.findPoint(screenshot, Presets.skillColor, Presets.skillRecs[i]).isValid();
    }

    private boolean isBattling(Bitmap screenshot) {

        System.out.println("检测是否战斗中");

        boolean isBattling = true;
        isBattling = (isBattling && Image.matchTemplate(screenshot, Presets.monsterLevel, 0.5, Presets.monsterLevelRec).isValid());
        isBattling = (isBattling && Image.findPointByMulColor(screenshot, Presets.damageNumberRule, Presets.damageNumberRec).isValid());
        return isBattling;
    }

    private boolean isEndBattling(Bitmap screenshot) {

        System.out.println("检测是否结束战斗");

        return !Image.matchTemplate(screenshot, Presets.monsterLevel, 0.5, Presets.monsterLevelRec).isValid();
    }

    private void CheckLion(Bitmap screenshot) {
        if (beforeLion || !inLion) {
            return;
        }

        System.out.println("检测狮子头");
        if (Image.matchTemplate(screenshot, Presets.beforeLion, 0.9,Presets.mapRec).isValid()) {
            beforeLion = true;
            Utility.show("狮子头前");
        }
    }

    private void CheckBuff(Bitmap screenshot) throws InterruptedException {

        System.out.println("检测BUFF");

        if (Image.findPoint(screenshot, Presets.buffColor, Presets.buffRec).isValid()) {
            Robot.Press(Presets.buffRec, 2);
        }
    }
}
