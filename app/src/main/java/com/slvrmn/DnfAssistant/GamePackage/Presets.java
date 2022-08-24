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

    public static final Rectangle mapRec = new Rectangle(1143, 29, 1217, 103);
    public static final Rectangle settingsRec = new Rectangle(1065, 8, 1104, 46);
    public static final Rectangle homeRec = new Rectangle(1013, 639, 1121, 661);
    public static final Rectangle buffRec = new Rectangle(1132, 355, 1174, 393);
    public static final Color buffColor = new Color(230, 255, 206);
    public static final Rectangle monsterLevelRec = new Rectangle(354, 53, 387, 88);
    public static final String damageNumberRule = "006DFF,-1|3|198EFF,-2|5|089AF7";
    public static final String inLionRule = "73B6D6,13|-1|295563,-12|11|7BBAD6,-1|10|7BBAD6,14|10|0039FF,17|10|083DFF,-11|20|294D5A,2|20|214D5A,12|20|214D63,28|-35|4265FF,39|-35|5292FF,53|-35|3A5DF7,26|-27|293D42,38|-28|000829,48|-28|001CC5,53|-28|101821,28|-16|081819,40|-17|080C42,50|-16|081419,41|13|DE4D10,41|6|E65D08,26|-4|213542,53|-5|21354A,25|24|29455A,54|25|214552";
    public static final String beforeLionRules = "314D5A,11|1|295163,25|-1|295563,-2|10|7BBADE,11|10|73B6D6,23|11|294D63,0|21|295563,12|21|7BBAD6,24|21|21495A,0|36|294152,12|37|7BBAD6,21|36|31515A,-2|48|294963,12|48|7BBADE,22|49|7BBAD6,-1|58|294963,13|58|73B6D6,23|59|214D5A,38|40|315563,51|36|73B6D6,63|38|295163,35|48|7BB6D6,49|47|CE9231,53|47|EF6519,63|48|73B6D6,35|57|29415A,42|57|19394A,50|57|000000,59|57|29556B,64|58|295163,52|23|003DFF,50|23|003DFF,50|26|083DFF,52|26|083DFF";
    public static final Rectangle damageNumberRec = new Rectangle(894, 4, 1161, 210);
    public static final Rectangle rightBottomRec = new Rectangle(1240, 640, 1270, 680);
    public static final Rectangle attackRec = new Rectangle(1107, 623, 1168, 672);
    public static final Rectangle uniqueSkillRec = new Rectangle(901, 355, 934, 393);
    public static final Rectangle backJumpRec = new Rectangle(1008, 630, 1048, 660);
    public static final Color[] skillColors = {
            new Color(255, 255, 181),
            new Color(255, 255, 173),
            new Color(255, 255, 115)};
    public static final Rectangle[] skillRecs = {
            new Rectangle(1056, 530, 1115, 590),
            new Rectangle(1117, 440, 1173, 490),
            new Rectangle(998, 440, 1055, 490),
            new Rectangle(940, 530, 996, 590),
            new Rectangle(893, 630, 942, 680),
            new Rectangle(825, 530, 876, 590),
            new Rectangle(760, 630, 810, 680),
    };
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
            new Rectangle(165, 484, 231,558)
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

    public static final String hellRules = "D66508,0|0|D66508,-1|1|E66508,-1|4|FFBE52,-1|6|DE5900,0|7|DE5900,3|10|100010,-3|10|BD359C,-7|4|000408,-8|0|E671E6,-10|0|FF61FF,7|0|FF14DE,10|0|E681D6,14|-2|F718DE,9|6|E628D6";
    public static final String bossRules = "2145EF,2|0|0849E6,4|0|2971FF,8|0|4A8EFF,11|0|3175F7,15|0|2155FF,21|0|2949FF,-1|4|0020EF,2|4|000000,5|4|103194,9|5|1051FF,12|5|000010,16|5|101429,0|8|0010EF,3|8|001CD6,7|8|083DF7,14|9|2949FF,17|9|0024EF,2|14|0010CE,4|14|0000A4,7|14|0010C5,11|14|0010CE,14|14|0010CE";

    public static final Rectangle breakRec = new Rectangle(1159, 653, 1260, 701);
    public static final String breakRule = "8BBACE,4|0|DEEFF7,9|0|0871B5,18|-1|EEF7FF,28|-1|0871AD,38|-2|006DAD,51|-1|0071AD,63|-2|0871B5,75|-2|0871B5,-1|8|0079B5,6|8|087AB5,18|7|107DBD,29|7|0075B5,47|7|0075B5,55|8|218AC5,65|8|007ABD,71|8|0079B5,72|8|DEF3FF,0|12|0075B5,4|12|94C6E6,10|12|84BAD6,15|12|097EBD,21|12|1075B5,27|12|0079BD,35|12|0079BD,39|12|DEF7FF,41|12|2186C6,44|11|0079B5,48|11|0079B5,49|11|DEF3FF,53|12|E6F7FF,57|12|0879BD,61|12|E6F7FF,63|12|DEF7FF,68|12|DEF3FF,72|12|DEF3FF,2|18|6BB2DE,6|18|6BB2DE,11|18|73B6DE,17|18|6BB2DE,21|17|73B6DE,32|17|007DBD,36|17|007DBD,41|17|0079BD,45|17|0079BD,49|17|DEF3FF,53|17|DEF3FF,58|17|007DBD,63|18|007DBD,65|18|007DBD,68|18|DEF3FF,72|18|DEF3FF";

    public static final Rectangle sellRec = new Rectangle(1043, 652, 1144, 701);
    public static final String sellRule ="DEEFF7,6|-1|DEEFF7,12|-1|0071AD,66|-4|0871AD,-4|7|53A2CE,5|7|73B6DE,26|6|0075B5,34|6|0075B5,40|6|DEF3FF,46|6|DEF3FF,52|7|DEF3FF,58|7|DEF3FF,62|7|DEF3FF,66|7|DEF3FF,74|6|0075B5,-5|10|63AED6,6|11|399AC5,24|12|0879BD,37|12|0079BD,61|11|0079BD,75|12|0079B5,-3|17|6BB6DE,11|18|63B2DE,20|18|007DBD,31|18|007DBD,39|17|3296CE,53|17|007DBD,62|17|DEF3FF,65|17|007DBD";

    public static final Rectangle breakAndSellSelectRec = new Rectangle(961, 600, 1170, 653);
    public static final String breakSelectRule = "0071AD,5|0|0075B5,9|0|298AC5,17|0|E6F7FF,14|0|0071AD,19|1|298ABD,22|1|DEF3FF,25|0|0075B5,31|1|DEF3FF,34|0|0071AD,38|0|DEF3FF,43|1|DEF3FF,46|1|0075B5,2|2|0071AD,6|2|DEF3FF,10|2|DEF3FF,13|2|DEF7FF,16|2|1179AD,17|2|DEF7FF,19|2|298EC5,21|2|84C2E6,25|3|0075B5,29|3|DEF3FF,32|3|DEF3FF,38|3|DEF3FF,42|3|73BADE,48|3|0075B5,1|8|0079BD,6|8|53A2CE,10|9|0079B5,13|9|DEF3FF,15|9|7CBEDE,17|9|DEF3FF,20|8|DEF3FF,22|8|DEF3FF,26|8|0075B5,30|9|DEF3FF,35|9|0079B5,38|9|DEF3FF,41|9|0079B5,43|9|DEF3FF,48|9|0079B5,4|15|007DBD,10|13|DEF3FF,9|16|007DBD,13|15|0079BD,17|15|DEF3FF,19|15|218EC5,20|15|007DBD,22|15|DEF3FF,25|14|007DBD,27|14|E6F7FF,31|15|0079BD,34|13|DEF3FF,36|15|0881BD,38|15|DEF3FF,42|16|73BAD6,46|15|007DBD";
    public static final String sellSelectRule = "0071AD,7|-1|0071AD,20|-3|0871AD,34|-3|0871AD,43|-1|0071AD,45|4|0075B5,36|6|0075B5,28|9|DEF3FF,5|12|93C6E6,5|7|0075B5,13|7|DEF3FF,24|5|0075B5,33|5|DEF3FF,42|18|DEF3FF,38|18|DEF3FF,32|18|0079BD,17|19|DEF3FF,11|19|DEF3FF,4|16|0079B5,-2|13|0079BD,20|7|DEF3FF,26|2|53A2C6,31|-2|0875AD,43|2|DEF7FF,42|14|DEF3FF,27|19|007DBD";

    public static final Rectangle breakConfirmRec = new Rectangle(655, 514, 842, 564);
    public static final String breakConfirmRule = "0071B5,8|1|E6F7FF,17|1|DEF3FF,27|1|0071AD,37|1|4B9EC6,47|2|0075B5,-1|6|0075B5,5|6|0075B5,9|6|DEF3FF,13|6|0075B5,17|6|DEF3FF,21|6|DEF3FF,25|6|DEF3FF,29|6|DEF3FF,34|6|0075B5,39|7|0075B5,44|7|DEF7FF,2|11|0879BD,8|11|DEF7FF,16|11|DDF7FF,25|11|0079B5,31|11|0079B5,43|11|DEF3FF,48|11|0879BD,-2|15|007DBD,10|14|DEF3FF,16|14|DEF3FF,26|14|0079BD,39|14|0079BD,3|17|0079BD,14|18|007DBD,19|18|007DBD,21|18|DEF3FF,26|17|007DBD,33|17|DEF3FF,43|17|429ECE,50|18|007DBD,54|18|007DBD";

    public static final Rectangle sellConfirmRec = new Rectangle(654, 452, 844, 504);
    public static final String sellConfirmRule = "006DA4,14|0|006DA4,33|1|006DA4,44|3|0871AD,46|12|0071AD,36|16|DEF3FF,33|16|0079B5,17|15|0075B5,14|15|DEF3FF,3|16|DEF7FF,-4|21|0079B5,0|26|0079B5,11|26|E6F7FF,15|26|E6F7FF,27|26|007DBD,32|26|0079B5,43|27|0079BD,41|30|007DBD,29|30|D5EEFF,27|29|DEF3FF,8|29|007DBD,2|32|007DBD,-10|29|007DBD";

    public static final Rectangle backRec = new Rectangle(5, 8, 36, 38);

    public static final Rectangle[] storeRecs = {
            new Rectangle(913,642 ,941,667),
            new Rectangle(889,545 ,919,567),
            new Rectangle(15,224 ,553,68),
            new Rectangle(536,72 ,616,102),
            new Rectangle(997,648,1122,681),
            new Rectangle(586,463,696,496),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] friendRecs = {
            new Rectangle(350,649,371,684),
            new Rectangle(569,653,824,685),
            new Rectangle(693,431,781,462),
            new Rectangle(861,653,687,685),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] guildRecs = {
            new Rectangle(1106,641,1133,672),
            new Rectangle(700,544,722,574),
            new Rectangle(1025,118,1078,154),
            new Rectangle(1035,266,1102,317),
            new Rectangle(244, 622, 345, 644),
            new Rectangle(689, 555, 770, 584),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] mailRecs = {
            new Rectangle(1157,35,1178,56),
            new Rectangle(627,654,736,677),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    public static final Rectangle[] petRecs = {
            new Rectangle(1219,30,1249,51),
            new Rectangle(990,314,1011,341),
            new Rectangle(1076,677,1135,699),
            new Rectangle(989,625,1078,666),
            new Rectangle(683,442,775,453),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38),
            new Rectangle(5, 8, 36, 38)
    };

    private static InputStream input;
    private static final AssetManager assetManager = MainApplication.getInstance().getAssets();
    public static boolean initialized;
    public static Bitmap inDungeonIcon, monsterLevelNumberFive, rewardIcon, nextButton, continueConfirmButton,repairButton;

    public static void initialize() {
        inDungeonIcon = readImage("InDungeonIcon.jpg");
        monsterLevelNumberFive = readImage("MonsterLevelNumberFive.jpg");
        rewardIcon = readImage("RewardIcon.jpg");
        nextButton = readImage("NextButton.png");
        continueConfirmButton = readImage("ContinueConfirmButton.png");
        repairButton = readImage("RepairButton.png");
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
