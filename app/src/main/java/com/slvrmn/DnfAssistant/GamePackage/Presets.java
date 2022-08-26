package com.slvrmn.DnfAssistant.GamePackage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.slvrmn.DnfAssistant.MainApplication;
import com.slvrmn.DnfAssistant.Model.Color;
import com.slvrmn.DnfAssistant.Model.Rectangle;

import java.io.IOException;
import java.io.InputStream;

public class Presets {

    public static final Rectangle mapRec = new Rectangle(1143, 29, 1225, 105);

    public static final Rectangle settingsRec = new Rectangle(1065, 8, 1104, 46);
    public static final Rectangle homeRec = new Rectangle(1013, 639, 1121, 661);
    public static final Rectangle monsterLevelRec = new Rectangle(354, 53, 387, 88);
    public static final String damageNumberRule = "006DFF,-1|3|198EFF,-2|5|089AF7";
    public static final String inLionRule = "29495A,13|1|7BBAD6,22|1|294D63,0|11|7BBAD6,13|11|7BBAD6,24|11|0841F7,29|9|083DFF,29|15|0039FF,3|21|29495A,11|21|29556B,22|21|295563,39|-38|0824CE,52|-36|428AF7,67|-37|00008C,38|-27|293D42,52|-27|1051F7,65|-27|081819,38|-18|081819,51|-20|0010CE,67|-16|101821,47|-30|000000,57|-29|000010,53|5|E6A631,53|11|EFDB63,52|17|E67500";
    public static final String beforeLionRules = "294D5A,13|-1|314D63,22|1|294D5A,1|10|7BBAD6,13|11|7BBAD6,26|11|214552,2|21|214D5A,13|22|7BBAD6,25|21|294D63,2|36|21394A,12|38|7BBAD6,23|38|295163,1|49|31556B,14|49|73B6D6,24|49|73B6D6,2|59|214152,13|61|7BBADE,23|60|295163,41|38|294152,51|37|7BBAD6,64|39|295163,39|50|7BBAD6,49|50|FFBE52,55|50|E6D25A,52|43|DED763,52|55|DE5500,66|50|7BBAD6,41|63|295163,51|62|295163,64|62|214152,49|27|1041F7,54|27|0039FF,52|23|083DF7,75|61|101821,76|37|081419";
    public static final Rectangle damageNumberRec = new Rectangle(894, 4, 1161, 210);
    public static final Rectangle stuckRec = new Rectangle(121, 623, 183, 654);
    public static final Rectangle attackRec = new Rectangle(1107, 623, 1168, 672);
    public static final Rectangle backJumpRec = new Rectangle(1008, 630, 1048, 660);

    public static final Rectangle[] moveRecs = {
            new Rectangle(230, 579, 276, 589),
            new Rectangle(132, 561, 170, 604),
            new Rectangle(252, 441, 287, 467),
            new Rectangle(132, 561, 170, 604)
    };
    public static final Rectangle[] dodgeRecs = {
            new Rectangle(1020, 630, 1040, 640),
            new Rectangle(1020, 700, 1040, 710)
    };
    public static final Rectangle[] joystickRecs = {
            new Rectangle(114, 432, 283, 613),
            new Rectangle(165, 484, 231, 558)
    };
    public static final Color dodgeColor = new Color(197, 255, 123);
    public static final Rectangle completeDungeonMenuRec = new Rectangle(999, 20, 1270, 600);
    public static final Rectangle continueConfirmRec = new Rectangle(665, 429, 791, 470);
    public static final Rectangle inventoryRec = new Rectangle(813, 0, 880, 81);
    public static final Rectangle breakAndSellCloseRec = new Rectangle(1185, 64, 1212, 92);
    public static final Rectangle confirmGoOutRec = new Rectangle(696, 436, 772, 463);
    public static final String inventoryFullRules = "A4EBFF,10|1|6BAEDE,15|-12|212021,-1|-12|DEEBFF,-11|-13|002063,-14|-25|4A5D84,-5|-25|080C08,3|-23|080808,7|-21|080C10,5|-29|0000FF,7|-27|000084,9|-26|101410,18|-22|0000FF,21|-22|00003A,25|-26|0000EF,25|-30|0000EF,25|-32|0000B5,25|-23|0000EF,25|-20|0000EF,26|-26|0000FF,20|-22|08044A,16|-22|100C19,7|-21|080C10,4|-21|081010,2|-21|101010,1|-21|080808,-1|-21|191C21,1|-21|080808,2|-21|101010,4|-21|081010,6|-21|0000EF,8|-21|000810,10|-21|080C10,5|-29|0000FF,5|-31|0000FF,6|-32|00009C";
    public static final Rectangle energyRec = new Rectangle(101, 49, 153, 83);
    public static final String energyEmptyRules = "9CC2C5,-3|6|3186CE,-7|11|BD5D6B,-10|16|A4869C,-15|20|3181C5,13|7|000000,14|6|000021,16|5|0000FF,17|3|000010,19|3|000010,21|7|000073,23|11|0000FF,22|14|0000FF,17|16|0000F7,14|12|0000F7";

    public static final String inDungeonRule = "B5EFFF,23|0|BDF3FF,-1|12|BDF3FF,23|12|BDF3FF,11|17|B5EFFF,67|-1|E6FBFF,64|7|E6FBF7,67|15|E6FFFF,75|18|EFFFFF,83|14|DEFFFF,86|6|E6F7FF,82|-1|DEFBFF,75|-4|EFFBFF,75|7|D6FBF7";
    public static final Rectangle inDungeonRec = new Rectangle(999, 4, 1114, 49);

    public static final String hellRules = "D66508,0|0|D66508,-1|1|E66508,-1|4|FFBE52,-1|6|DE5900,0|7|DE5900,3|10|100010,-3|10|BD359C,-7|4|000408,-8|0|E671E6,-10|0|FF61FF,7|0|FF14DE,10|0|E681D6,14|-2|F718DE,9|6|E628D6";

    public static final Rectangle mapCentreRec = new Rectangle(1183, 69, 1218, 103);
    public static final String bossRules = "101073,2|3|4265FF,3|6|1935DE,8|3|2965F7,15|1|2961E6,21|4|2155FF,25|6|1035EF,28|0|000063,19|10|000019,9|10|000000,7|8|000029,21|8|000042,18|11|000010,10|12|000000,11|16|001CD6,8|19|0010CE,11|21|000008,17|22|000010,21|17|0010CE,16|15|0035EF,12|16|0020E6";

    public static final Rectangle breakRec = new Rectangle(1159, 653, 1260, 701);
    public static final String breakRule = "8BBACE,4|0|DEEFF7,9|0|0871B5,18|-1|EEF7FF,28|-1|0871AD,38|-2|006DAD,51|-1|0071AD,63|-2|0871B5,75|-2|0871B5,-1|8|0079B5,6|8|087AB5,18|7|107DBD,29|7|0075B5,47|7|0075B5,55|8|218AC5,65|8|007ABD,71|8|0079B5,72|8|DEF3FF,0|12|0075B5,4|12|94C6E6,10|12|84BAD6,15|12|097EBD,21|12|1075B5,27|12|0079BD,35|12|0079BD,39|12|DEF7FF,41|12|2186C6,44|11|0079B5,48|11|0079B5,49|11|DEF3FF,53|12|E6F7FF,57|12|0879BD,61|12|E6F7FF,63|12|DEF7FF,68|12|DEF3FF,72|12|DEF3FF,2|18|6BB2DE,6|18|6BB2DE,11|18|73B6DE,17|18|6BB2DE,21|17|73B6DE,32|17|007DBD,36|17|007DBD,41|17|0079BD,45|17|0079BD,49|17|DEF3FF,53|17|DEF3FF,58|17|007DBD,63|18|007DBD,65|18|007DBD,68|18|DEF3FF,72|18|DEF3FF";

    public static final Rectangle sellRec = new Rectangle(1043, 652, 1144, 701);
    public static final String sellRule = "DEEFF7,6|-1|DEEFF7,12|-1|0071AD,66|-4|0871AD,-4|7|53A2CE,5|7|73B6DE,26|6|0075B5,34|6|0075B5,40|6|DEF3FF,46|6|DEF3FF,52|7|DEF3FF,58|7|DEF3FF,62|7|DEF3FF,66|7|DEF3FF,74|6|0075B5,-5|10|63AED6,6|11|399AC5,24|12|0879BD,37|12|0079BD,61|11|0079BD,75|12|0079B5,-3|17|6BB6DE,11|18|63B2DE,20|18|007DBD,31|18|007DBD,39|17|3296CE,53|17|007DBD,62|17|DEF3FF,65|17|007DBD";

    public static final Rectangle breakAndSellSelectRec = new Rectangle(961, 600, 1170, 653);
    public static final String breakSelectRule = "0071AD,5|0|0075B5,9|0|298AC5,17|0|E6F7FF,14|0|0071AD,19|1|298ABD,22|1|DEF3FF,25|0|0075B5,31|1|DEF3FF,34|0|0071AD,38|0|DEF3FF,43|1|DEF3FF,46|1|0075B5,2|2|0071AD,6|2|DEF3FF,10|2|DEF3FF,13|2|DEF7FF,16|2|1179AD,17|2|DEF7FF,19|2|298EC5,21|2|84C2E6,25|3|0075B5,29|3|DEF3FF,32|3|DEF3FF,38|3|DEF3FF,42|3|73BADE,48|3|0075B5,1|8|0079BD,6|8|53A2CE,10|9|0079B5,13|9|DEF3FF,15|9|7CBEDE,17|9|DEF3FF,20|8|DEF3FF,22|8|DEF3FF,26|8|0075B5,30|9|DEF3FF,35|9|0079B5,38|9|DEF3FF,41|9|0079B5,43|9|DEF3FF,48|9|0079B5,4|15|007DBD,10|13|DEF3FF,9|16|007DBD,13|15|0079BD,17|15|DEF3FF,19|15|218EC5,20|15|007DBD,22|15|DEF3FF,25|14|007DBD,27|14|E6F7FF,31|15|0079BD,34|13|DEF3FF,36|15|0881BD,38|15|DEF3FF,42|16|73BAD6,46|15|007DBD";
    public static final String sellSelectRule = "0071AD,7|-1|0071AD,20|-3|0871AD,34|-3|0871AD,43|-1|0071AD,45|4|0075B5,36|6|0075B5,28|9|DEF3FF,5|12|93C6E6,5|7|0075B5,13|7|DEF3FF,24|5|0075B5,33|5|DEF3FF,42|18|DEF3FF,38|18|DEF3FF,32|18|0079BD,17|19|DEF3FF,11|19|DEF3FF,4|16|0079B5,-2|13|0079BD,20|7|DEF3FF,26|2|53A2C6,31|-2|0875AD,43|2|DEF7FF,42|14|DEF3FF,27|19|007DBD";

    public static final Rectangle breakConfirmRec = new Rectangle(655, 514, 842, 564);
    public static final String breakConfirmRule = "0071B5,8|1|E6F7FF,17|1|DEF3FF,27|1|0071AD,37|1|4B9EC6,47|2|0075B5,-1|6|0075B5,5|6|0075B5,9|6|DEF3FF,13|6|0075B5,17|6|DEF3FF,21|6|DEF3FF,25|6|DEF3FF,29|6|DEF3FF,34|6|0075B5,39|7|0075B5,44|7|DEF7FF,2|11|0879BD,8|11|DEF7FF,16|11|DDF7FF,25|11|0079B5,31|11|0079B5,43|11|DEF3FF,48|11|0879BD,-2|15|007DBD,10|14|DEF3FF,16|14|DEF3FF,26|14|0079BD,39|14|0079BD,3|17|0079BD,14|18|007DBD,19|18|007DBD,21|18|DEF3FF,26|17|007DBD,33|17|DEF3FF,43|17|429ECE,50|18|007DBD,54|18|007DBD";

    public static final Rectangle sellConfirmRec = new Rectangle(654, 452, 844, 504);
    public static final String sellConfirmRule = "006DA4,14|0|006DA4,33|1|006DA4,44|3|0871AD,46|12|0071AD,36|16|DEF3FF,33|16|0079B5,17|15|0075B5,14|15|DEF3FF,3|16|DEF7FF,-4|21|0079B5,0|26|0079B5,11|26|E6F7FF,15|26|E6F7FF,27|26|007DBD,32|26|0079B5,43|27|0079BD,41|30|007DBD,29|30|D5EEFF,27|29|DEF3FF,8|29|007DBD,2|32|007DBD,-10|29|007DBD";

    public static final Rectangle confirmRepairRecs = new Rectangle(997, 604, 1126, 644);

    public static final Rectangle[] skillRecs = {
            new Rectangle(1056, 530, 1115, 590),
            new Rectangle(1117, 440, 1173, 490),
            new Rectangle(998, 440, 1055, 490),
            new Rectangle(940, 530, 996, 590),
            new Rectangle(893, 630, 942, 680),
            new Rectangle(825, 530, 876, 590),
            new Rectangle(760, 630, 810, 680),
            new Rectangle(899, 354, 938, 399)
    };
    public static final Rectangle[] skillCDRecs = {
            new Rectangle(1082, 517, 1084, 520),
            new Rectangle(1140, 427, 1142, 430),
            new Rectangle(1024, 427, 1027, 429),
            new Rectangle(967, 517, 969, 520),
            new Rectangle(910, 607, 912, 610),
            new Rectangle(790, 607, 792, 610),
            new Rectangle(850, 517, 852, 520),
            new Rectangle(916, 340, 919, 344)
    };
    public static final Color skillCDColor = new Color(8, 206, 255);
    public static final Color[] skillColors = {
            new Color(255, 255, 8),
            new Color(255, 255, 181),
            new Color(255, 255, 173),
            new Color(255, 255, 115)};


    public static final Rectangle[] buffRecs = {
            new Rectangle(1135, 355, 1178, 400),
            new Rectangle(1053, 355, 1098, 400),
            new Rectangle(975, 355, 1019, 400)
    };
    public static final Color buffColor = new Color(230, 255, 206);
    public static final Rectangle[] directionalBuffRecs = {
            new Rectangle(1151, 411, 1160, 414),
            new Rectangle(1118, 369, 1121, 381),
            new Rectangle(1191, 367, 1195, 381),
            new Rectangle(1151, 336, 1164, 340),
    };
    public static final Color directionalBuffCDColor = new Color(107, 255, 255);

    public static final Rectangle backRec = new Rectangle(5, 8, 36, 38);

    public static final Rectangle[] storeRecs = {
            new Rectangle(913, 642, 941, 667),
            new Rectangle(889, 545, 919, 567),
            new Rectangle(15, 224, 553, 68),
            new Rectangle(536, 72, 616, 102),
            new Rectangle(997, 648, 1122, 681),
            new Rectangle(586, 463, 696, 496),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] friendRecs = {
            new Rectangle(350, 649, 371, 684),
            new Rectangle(569, 653, 824, 685),
            new Rectangle(693, 431, 781, 462),
            new Rectangle(861, 653, 687, 685),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] guildRecs = {
            new Rectangle(1106, 641, 1133, 672),
            new Rectangle(700, 544, 722, 574),
            new Rectangle(1025, 118, 1078, 154),
            new Rectangle(1035, 266, 1102, 317),
            new Rectangle(244, 622, 345, 644),
            new Rectangle(689, 555, 770, 584),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] mailRecs = {
            new Rectangle(1157, 35, 1178, 56),
            new Rectangle(627, 654, 736, 677),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] petRecs = {
            new Rectangle(1219, 30, 1249, 51),
            new Rectangle(990, 314, 1011, 341),
            new Rectangle(1076, 677, 1135, 699),
            new Rectangle(989, 625, 1078, 666),
            new Rectangle(683, 442, 775, 453),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };
    private static final AssetManager assetManager = MainApplication.getInstance().getAssets();
    public static boolean initialized;
    public static Bitmap monsterLevelNumberFive, rewardIcon, nextButton, continueConfirmButton,
            repairButton, inventoryFullButton, goBackButton, directionalBuffIcon;
    private static InputStream input;

    public static void initialize() {
        monsterLevelNumberFive = readImage("MonsterLevelNumberFive.jpg");
        rewardIcon = readImage("RewardIcon.jpg");
        nextButton = readImage("NextButton.png");
        continueConfirmButton = readImage("ContinueConfirmButton.png");
        repairButton = readImage("RepairButton.png");
        inventoryFullButton = readImage("InventoryFullButton.png");
        goBackButton = readImage("GoBackButton.png");
        directionalBuffIcon = readImage("DirectionalBuffIcon.png");
        initialized = true;
    }

    public static Bitmap readImage(String name) {
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
