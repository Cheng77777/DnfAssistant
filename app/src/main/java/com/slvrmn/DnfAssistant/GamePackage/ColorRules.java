package com.slvrmn.DnfAssistant.GamePackage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.slvrmn.DnfAssistant.MainApplication;
import com.slvrmn.DnfAssistant.Model.Color;
import com.slvrmn.DnfAssistant.Tools.Utility;

import java.io.IOException;
import java.io.InputStream;

public class ColorRules {

    public static boolean initialized;

    public static final Color skillColor = new Color(255,255,173);
    public static final Color buffColor = new Color(230,255,206);
    public static Bitmap inDungeon;
    public static Bitmap reward;
    public static final String rewardRule = "EFEBFF,-9|-1|524994,16|-1|4A41AD,39|-1|4231CE,41|10|E6EBFF,53|15|EFEBFF,66|12|EFEFFF,77|7|4A2DEF,95|7|EFEBFF,116|11|EFEFFF,110|16|EFEBFF,142|12|EFEBFF,135|9|9C61F7,121|18|5239E6,175|-3|5231E6,213|0|3120CE,197|15|633DBD,238|25|4239A4,245|5|EFEFFF,227|-6|6351BD,-1|-15|423DB5";
    public static final String hellRule = "083DFF,15|-1|EF75EF,22|2|EFEB7B,33|-1|EF86DE,23|-35|1041FF,19|-38|000000,28|-38|000010,-1|2|003DFF,1|1|083DFF,-4|3|083DF7,1|5|083DFF,0|-1|0839E6";

    public static void initialize(){
                InputStream input = null;
        try {
            AssetManager assetManager = MainApplication.getInstance().getAssets();
            input = assetManager.open("InDungeon.jpg");
            inDungeon = BitmapFactory.decodeStream(input);
            initialized = true;
            Utility.show("模版图片读取成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
