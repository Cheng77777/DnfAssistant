package com.slvrmn.DnfAssistant.GamePackage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public static Bitmap inDungeon,beforeLion,lion,monsterLevel;
    public static final Rectangle mapRec = new Rectangle(1136,27,1270,140);

    public static final Rectangle buffRec = new Rectangle(1132,355,1174,393);
    public static final Color buffColor = new Color(230,255,206);

    public static final Rectangle monsterLevelRec = new Rectangle(354,53,387,88);

    public static final String damageNumberRule ="006DFF,-1|3|198EFF,-2|5|089AF7";
    public static final Rectangle damageNumberRec = new Rectangle(894,4,1161,210);

    public static final Rectangle attackRec = new Rectangle(1107,623,1168,672);
    public static final Rectangle uniqueSkillRec = new Rectangle(901,355,934,393);
    public static final Rectangle backJumpRec = new Rectangle(1008,630,1048,660);

    public static final Color skillColor = new Color(255,255,181);
    public static final Rectangle[] skillRecs = {
            new Rectangle(768,627,822,677),
            new Rectangle(893,630,942,670),
            new Rectangle(831,533,876,584),
            new Rectangle(950,536,996,585),
            new Rectangle(1057,537,1115,583),
            new Rectangle(998,448,1055,493),
            new Rectangle(1117,445,1173,492)
    };

    public static final Rectangle[] lionRecs = {
            new Rectangle(230,579,276,589),
            new Rectangle(132,561,170  ,604),
            new Rectangle(252,441,287,467)
    };
    public static final Rectangle[] dodgeRecs = {
            new Rectangle(1006,623,1049,651),
            new Rectangle(1015,692,1046  ,706)
    };

    public static Bitmap reward;
    public static final String rewardRule = "EFEBFF,-9|-1|524994,16|-1|4A41AD,39|-1|4231CE,41|10|E6EBFF,53|15|EFEBFF,66|12|EFEFFF,77|7|4A2DEF,95|7|EFEBFF,116|11|EFEFFF,110|16|EFEBFF,142|12|EFEBFF,135|9|9C61F7,121|18|5239E6,175|-3|5231E6,213|0|3120CE,197|15|633DBD,238|25|4239A4,245|5|EFEFFF,227|-6|6351BD,-1|-15|423DB5";
    public static final String hellRule = "083DFF,15|-1|EF75EF,22|2|EFEB7B,33|-1|EF86DE,23|-35|1041FF,19|-38|000000,28|-38|000010,-1|2|003DFF,1|1|083DFF,-4|3|083DF7,1|5|083DFF,0|-1|0839E6";

    public static void initialize(){
        inDungeon = readImage("InDungeon.jpg");
        beforeLion = readImage("BeforeLion.jpg");
        lion = readImage("Lion.jpg");
        monsterLevel = readImage("number 5.jpg");
        reward = readImage("Reward.jpg");
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
