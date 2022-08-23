package com.slvrmn.DnfAssistant.GamePackage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.slvrmn.DnfAssistant.MainApplication;
import com.slvrmn.DnfAssistant.Model.Color;
import com.slvrmn.DnfAssistant.Model.Rectangle;
import com.slvrmn.DnfAssistant.R;
import com.slvrmn.DnfAssistant.Tools.Utility;

import java.io.IOException;
import java.io.InputStream;

public class Presets {

    private static InputStream input;
    private static final AssetManager assetManager = MainApplication.getInstance().getAssets();

    public static boolean initialized;

    public static Bitmap inDungeon,beforeLion,monsterLevel,reward,nextButton,confirmButton;
    public static final Rectangle mapRec = new Rectangle(1136,27,1270,140);

    public static final Rectangle buffRec = new Rectangle(1132,355,1174,393);
    public static final Color buffColor = new Color(230,255,206);

    public static final Rectangle monsterLevelRec = new Rectangle(354,53,387,88);

    public static final String damageNumberRule ="006DFF,-1|3|198EFF,-2|5|089AF7";
    public static final String inLionRule ="003DFF,-2|0|003DFF,22|2|EF6908,23|-1|D69231,23|-6|BDA24A,23|-34|0028EF,23|-41|084DF7,23|-48|4279FF,16|-41|00005A,27|-40|000010";
    public static final Rectangle damageNumberRec = new Rectangle(894,4,1161,210);

    public static final Rectangle leftBottomRec = new Rectangle(20,648,105,690);

    public static final Rectangle attackRec = new Rectangle(1107,623,1168,672);
    public static final Rectangle uniqueSkillRec = new Rectangle(901,355,934,393);
    public static final Rectangle backJumpRec = new Rectangle(1008,630,1048,660);

    public static final Color[] skillColors = {
            new Color(255,255,181),
            new Color(255,255,115)};
    public static final Rectangle[] skillRecs = {
            new Rectangle(768,627,822,677),
            new Rectangle(893,630,942,670),
            new Rectangle(831,533,876,584),
            new Rectangle(950,536,996,585),
            new Rectangle(1057,537,1115,583),
            new Rectangle(998,448,1055,493),
            new Rectangle(1117,445,1173,492)
    };

    public static final Rectangle[] moveRecs = {
            new Rectangle(230,579,276,589),
            new Rectangle(132,561,170  ,604),
            new Rectangle(252,441,287,467),
            new Rectangle(132,561,170  ,604)
    };
    public static final Rectangle[] dodgeRecs = {
            new Rectangle(1018,623,1049,651),
            new Rectangle(1015,692,1046  ,706)
    };
    public static final Color dodgeColor = new Color(197,255,123);

    public static final Rectangle nextMenuRec = new Rectangle(999,20,1261,374);
    public static final Rectangle confirmMenuRec = new Rectangle(638,400,830,489);

    public static final Rectangle backRec = new Rectangle(5,8,36,38);
    public static final Rectangle inventoryRec = new Rectangle(813,0,880,81);
    public static final Rectangle breakRec = new Rectangle(1168,668,1250,687);
    public static final Rectangle sellRec = new Rectangle(1056,668,1137,687);
    public static final Rectangle breakAndSellConfirmRec = new Rectangle(1008,608,1137,642);
    public static final Rectangle breakAndSellSecondConfirmRec = new Rectangle(693,517,805,551);
    public static final Rectangle breakAndSellCloseRec = new Rectangle(1185,64,1212,92);
    public static final String inventoryFullRules = "A4EBFF,10|1|6BAEDE,15|-12|212021,-1|-12|DEEBFF,-11|-13|002063,-14|-25|4A5D84,-5|-25|080C08,3|-23|080808,7|-21|080C10,5|-29|0000FF,7|-27|000084,9|-26|101410,18|-22|0000FF,21|-22|00003A,25|-26|0000EF,25|-30|0000EF,25|-32|0000B5,25|-23|0000EF,25|-20|0000EF,26|-26|0000FF,20|-22|08044A,16|-22|100C19,7|-21|080C10,4|-21|081010,2|-21|101010,1|-21|080808,-1|-21|191C21,1|-21|080808,2|-21|101010,4|-21|081010,6|-21|0000EF,8|-21|000810,10|-21|080C10,5|-29|0000FF,5|-31|0000FF,6|-32|00009C";


    public static final Rectangle energyRec = new Rectangle(101,49,153,83);
    public static final String energyEmptyRules ="9CC2C5,-3|6|3186CE,-7|11|BD5D6B,-10|16|A4869C,-15|20|3181C5,13|7|000000,14|6|000021,16|5|0000FF,17|3|000010,19|3|000010,21|7|000073,23|11|0000FF,22|14|0000FF,17|16|0000F7,14|12|0000F7";

    public static void initialize(){
        inDungeon = readImage("InDungeon.jpg");
        beforeLion = readImage("BeforeLion.jpg");
        monsterLevel = readImage("number 5.jpg");
        reward = readImage("Reward.jpg");
        nextButton = readImage("NextButton.png");
        confirmButton = readImage("ConfirmButton.png");
        initialized = true;
    }

    public static Bitmap readImage(String name){
        try {
            input = assetManager.open(name);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
