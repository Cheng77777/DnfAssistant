package com.slvrmn.DNFAssistant.GamePackage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.slvrmn.DNFAssistant.MainApplication;
import com.slvrmn.DNFAssistant.Model.CheckImageModel;
import com.slvrmn.DNFAssistant.Model.CheckRuleModel;
import com.slvrmn.DNFAssistant.Model.Color;
import com.slvrmn.DNFAssistant.Model.Rectangle;

import java.io.IOException;
import java.io.InputStream;

public class Presets {

    public static final Rectangle mapRec = new Rectangle(1145, 31, 1235, 103);
    public static final Rectangle resultRec = new Rectangle(845, 8, 1006, 63);
    public static final Rectangle rewardRec = new Rectangle(485, 24, 793, 97);

    public static final Rectangle homeRec = new Rectangle(1013, 639, 1121, 661);
    public static final Rectangle monsterLevelRec = new Rectangle(354, 53, 387, 88);
    public static final String inLionRule = "213D4A,10|1|7BBADE,-2|10|7BBAD6,9|10|7BBAD6,24|10|003DFF,10|21|29516B,32|10|73B6D6,49|10|EFEB7B,49|-38|3A75F7,48|-29|195DFF,48|-21|0010D6,40|-28|0010DE,57|-29|001CC5";
    public static final String beforeLionRules = "214D5A,-11|10|7BBAD6,1|10|7BBAD6,12|11|294D63,1|24|7BBADE,0|38|73B6D6,-9|49|294D5A,-1|49|73BADE,12|49|7BBAD6,1|61|7BBAD6,40|36|73B6D6,28|48|73B6D6,40|48|D69A31,53|48|7BBADE,39|61|295163,40|24|0039FF,66|62|081821,66|35|081419";
    public static final Rectangle stuckRec = new Rectangle(121, 623, 183, 654);
    public static final Rectangle attackRec = new Rectangle(1114, 617, 1169, 683);
    public static final Rectangle backJumpRec = new Rectangle(1008, 630, 1048, 660);

    public static final Rectangle[] moveRecs = {
            new Rectangle(230, 579, 276, 589),
            new Rectangle(132, 561, 170, 604),
            new Rectangle(252, 441, 287, 467),
            new Rectangle(132, 561, 170, 604)
    };
    public static final Rectangle[] dodgeRecs = {
            new Rectangle(1020, 630, 1040, 640),
            new Rectangle(940, 630, 960, 640),
            new Rectangle(1020, 710, 1040, 720)
    };
    public static final Rectangle[] joystickRecs = {
            new Rectangle(114, 432, 283, 613),
            new Rectangle(165, 484, 231, 558)
    };
    public static final Rectangle confirmGoOutRec = new Rectangle(696, 436, 772, 463);

    public static final CheckImageModel dailyMenuModel = new CheckImageModel(267, 10, 336, 76, "DailyMenuButton.png");

    public static final CheckImageModel backButtonModel = new CheckImageModel(0, 0, 47, 48, "BackButton.png");
    public static final CheckImageModel continueButtonModel = new CheckImageModel(999, 20, 1270, 600, "ContinueButton.png");
    public static final CheckImageModel continueConfirmButtonModel = new CheckImageModel(665, 429, 791, 470, "ConfirmButton_Bright.png");

    public static final CheckImageModel[] continueModels = {continueButtonModel, continueConfirmButtonModel};

    public static final CheckImageModel goOutDungeonButtonModel = new CheckImageModel(999, 20, 1270, 600, "GoOutDungeonButton.png");
    public static final CheckImageModel repairButtonModel = new CheckImageModel(12, 8, 97, 92, "RepairButton.png");

    public static final CheckImageModel inventoryFullButtonModel = new CheckImageModel(999, 20, 1270, 600, "InventoryFullButton.png");
    public static final CheckImageModel breakSelectButtonModel = new CheckImageModel(954, 591, 1185, 653, "BreakSelectButton.png");
    public static final CheckImageModel breakSelectConfirmButtonModel = new CheckImageModel(650, 500, 850, 600, "ConfirmButton_Bright.png");
    public static final CheckImageModel breakResultButtonModel = new CheckImageModel(532, 494, 752, 591, "ConfirmButton_Bright.png");
    public static final CheckImageModel breakCloseButtonModel = new CheckImageModel(1173, 51, 1226, 104, "CloseButton.png");
    public static final CheckImageModel sellButtonModel = new CheckImageModel(1029, 640, 1156, 709, "SellButton.png");
    public static final CheckImageModel sellSelectButtonModel = new CheckImageModel(963, 599, 1175, 660, "SellSelectButton.png");
    public static final CheckImageModel sellSelectConfirmButtonModel = new CheckImageModel(657, 451, 849, 513, "ConfirmButton_Dark.png");
    public static final CheckImageModel sellResultButtonModel = new CheckImageModel(540, 417, 744, 496, "ConfirmButton_Dark.png");
    public static final CheckImageModel sellCloseButtonModel = new CheckImageModel(1173, 51, 1226, 104, "CloseButton.png");

    public static final CheckImageModel dailyContinueButtonModel = new CheckImageModel(999, 20, 1270, 600, "DailyContinueButton.png");
    public static final CheckImageModel dailySelectButtonModel = new CheckImageModel(999, 20, 1270, 600, "DailySelectButton.png");
    public static final CheckImageModel dailyReturnButtonModel = new CheckImageModel(999, 20, 1270, 600, "DailyReturnButton.png",0.8f);

    public static final CheckImageModel switchCharacterButtonModel = new CheckImageModel(6, 46, 43, 82, "SwitchCharacterButton.png");

    public static final CheckImageModel[] cleanInventoryModels = {
            inventoryFullButtonModel,
            breakSelectButtonModel,
            breakSelectConfirmButtonModel,
            breakResultButtonModel,
            breakCloseButtonModel,
            sellButtonModel,
            sellSelectButtonModel,
            sellSelectConfirmButtonModel,
            sellResultButtonModel,
            sellCloseButtonModel,
            backButtonModel

    };

    public static final CheckImageModel[] recoverModels = {
            new CheckImageModel(547, 250, 619, 324, "RecoverCoinIcon.png"),
            new CheckImageModel(633, 536, 848, 625, "RecoverButton.png")
    };

    public static final CheckImageModel dodgeButtonModel = new CheckImageModel(975, 599, 1071, 680, "DodgeButton.png");

    public static final CheckImageModel inDungeonModel = new CheckImageModel(1006, 8, 1113, 48, "InDungeonOptionsButton.png");

    public static final String hellRules = "73B6D6,-13|12|7BBADE,0|12|73B6D6,15|11|003DFF,0|22|215163,30|9|EF24EF,51|8|EF24EF,40|12|FFBA42,39|-36|4A8AFF,39|-21|0014DE";

    public static final CheckImageModel inBoosModel = new CheckImageModel(1183, 69, 1216, 103, "BossIcon.png");

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

    public static final String blackScreenRule = "000000,-12|-24|000000,101|-33|000000,77|34|000000,-45|156|000000,77|160|000000,182|53|000000,168|-14|000000,258|-14|000000,247|63|000000,165|187|000000,-52|278|000000";

    public static final Rectangle[] skillRecs = {
            new Rectangle(1056, 530, 1115, 590),
            new Rectangle(1117, 440, 1173, 490),
            new Rectangle(998, 440, 1055, 490),
            new Rectangle(940, 530, 996, 590),
            new Rectangle(893, 630, 942, 680),
            new Rectangle(760, 630, 810, 680),
            new Rectangle(825, 530, 876, 590),
            new Rectangle(899, 355, 938, 400),
            new Rectangle(975, 355, 1019, 400),
            new Rectangle(1053, 355, 1098, 400),
            new Rectangle(1134, 352, 1178, 400),

    };
    public static final Rectangle[] skillCDRecs = {
            new Rectangle(1083, 517, 1088, 519),
            new Rectangle(1140, 427, 1145, 429),
            new Rectangle(1025, 427, 1030, 429),
            new Rectangle(968, 517, 973, 519),
            new Rectangle(910, 607, 915, 609),
            new Rectangle(790, 607, 795, 609),
            new Rectangle(850, 517, 855, 519),
            new Rectangle(917, 341, 922, 343),
            new Rectangle(996, 341, 1001, 343),
            new Rectangle(1076, 341, 1081, 343),
            new Rectangle(1155, 341, 1160, 343)
    };
    public static final Color[] skillColors = {
            new Color(255, 255, 8),
            new Color(255, 255, 181),
            new Color(255, 166, 99),
            new Color(33, 251, 255),
            new Color(230, 255, 197),
            new Color(123, 255, 255)};
    public static final Color[] skillCDColors = {new Color(8, 206, 255),
            new Color(33, 255, 255)};
    public static final Color skillFrameColor = new Color(197, 166, 99);

    public static final Rectangle[] directionalBuffRecs = {
            new Rectangle(1153, 412, 1160, 413),
            new Rectangle(1118, 371, 1119, 378),
            new Rectangle(1193, 371, 1194, 378),
            new Rectangle(1153, 337, 1160, 338),
    };
    public static final Color directionalBuffCDColor = new Color(107, 255, 255);

    public static final Color[] ammoColors = {
            new Color(206, 170, 107),
            new Color(181, 162, 107)

    };
    public static final Rectangle ammoRec = new Rectangle(1133, 349, 1135, 352);

    public static final Rectangle backRec = new Rectangle(5, 8, 36, 38);

    public static final CheckRuleModel[] storeModels = {
            new CheckRuleModel(915, 637, 945, 677, "BDF3F7,10|27|4269F7,15|-2|B5EBFF,21|28|5A7DE6,22|-1|4265DE")
            , new CheckRuleModel(894, 549, 926, 574, "CEEFF7,1|11|081029,1|21|4A6DC5,15|0|4A65F7,15|11|212442,15|20|19208C,26|1|4261E6,26|12|294DB5,26|19|314194")
            , new CheckRuleModel(8, 226, 84, 257, "101C21,0|5|7392AD,30|-4|738EA4,30|3|7392AD,29|12|32353A,50|-2|738EA4,50|5|7391AC,50|11|7392AD")
            , new CheckRuleModel(530, 73, 619, 104, "638194,0|4|425563,0|12|738EA4,37|1|738EA4,37|5|63798C,37|14|4A596B,65|0|7392AD,65|6|4A5D73,65|14|7392AD")
            , new CheckRuleModel(1012, 650, 1099, 682, "F7FBF7,-1|9|F7FBFF,-1|18|007DBD,45|1|FFFFFF,44|10|0075B5,44|18|0079BD,75|0|FFFFFF,75|9|FFFFFF,75|18|FFFFFF")
            , new CheckRuleModel(607, 462, 671, 501, "DEF7FF,0|10|E5F6FF,0|17|007DBD,23|4|0075AD,23|8|DEF3FF,22|17|DEF3FF,33|0|DEF3FF,33|9|DEF3FF,33|17|DEF3FF")

    };

    public static final CheckRuleModel[] friendModels = {
            new CheckRuleModel(338, 643, 393, 688, "94DBFF,0|9|A57984,-1|16|94697B,9|1|213963,10|8|193563,9|18|215D94,21|3|73B2E6,20|8|428AC6,21|17|1969D6")
            , new CheckRuleModel(851, 648, 893, 691, "DECE94,1|15|00618C,12|-7|105D8C,12|1|101010,14|10|DECE94,25|-10|085983,25|1|DECE94,25|18|085983")
            , new CheckRuleModel(588, 652, 629, 693, "08417B,-1|7|215594,0|22|004584,13|-1|084184,14|10|29395A,15|29|084584,25|-3|083D7B,25|8|8CAEFF,26|23|083D7B")
            , new CheckRuleModel(697, 429, 769, 469, "FFFFFF,0|9|FFFFFF,12|-5|FFFFFF,12|5|FFFFFF,12|9|FFFFFF,25|-3|0075B5,23|8|FFFFFF,34|2|FFFFFF")
    };

    public static final CheckRuleModel[] guildModels = {
            new CheckRuleModel(1099, 628, 1152, 684, "214563,0|9|194D8C,0|19|08183A,11|0|005DFF,11|11|00D6EF,12|23|004DEE,25|1|63EBFF,24|13|0072F7,24|23|1175FF")
            , new CheckRuleModel(688, 538, 729, 581, "7BDFEF,2|9|3A598C,2|16|214173,13|-1|B5FBFF,13|8|63CAE6,13|16|192D4A,22|2|5AB6DE,22|9|6392B5,22|17|080819")
            , new CheckRuleModel(1025, 114, 1075, 148, "29283A,-1|8|101C21,0|22|212829,13|4|101C21,13|14|101C21,15|24|D6AE73,29|2|101C21,28|8|635152,30|22|292431")
            , new CheckRuleModel(223, 616, 359, 649, "0069AC,2|8|103D6B,2|19|007DBD,25|2|0071AD,25|9|0079BD,25|16|0079BD,61|3|097ABD,63|10|2186BD,63|18|007DBD")
            , new CheckRuleModel(629, 551, 771, 585, "0071AD,-1|8|E6F7FF,-1|21|007DBD,13|5|DEF3FF,12|12|DEF3FF,12|19|DEF3FF,35|4|E6F7FF,34|15|DEF3FF,34|18|0079B5")
            , new CheckRuleModel(592, 431, 684, 480, "DEF3FF,0|11|007DBD,9|-8|0071AD,9|4|DEF3FF,10|12|007DBD,26|-6|BDDFF7,24|10|DEF3FF,35|-6|DEF3FF,34|9|007DBD")

    };
    public static final Rectangle GuildRewardRec = new Rectangle(1022, 241, 1111, 330);

    public static final CheckRuleModel[] mailModels = {
            new CheckRuleModel(1144, 19, 1189, 57, "ADA68C,-1|12|DEE3DE,1|20|EFEBEF,11|-2|ADAA94,12|8|B5AA94,12|18|E6EBEF,25|0|A49E84,25|8|E6EBE6,24|21|E6E7E6")
            , new CheckRuleModel(14, 66, 126, 91, "001021,0|5|081021,0|10|001021,17|-4|319ECE,16|6|195D84,16|12|29A6DE,36|-1|298ABD,35|7|08203A,36|12|198ABD")
            , new CheckRuleModel(614, 648, 715, 690, "3A6D8C,2|12|4A8194,1|27|4A819C,37|0|3A6D8C,38|12|8CC2D6,39|27|4A8194,77|1|3A6D84,76|13|4A7A94,79|28|52869C")
    };

    public static final CheckRuleModel[] petModels = {
            new CheckRuleModel(1216, 24, 1256, 57, "DEEBF7,0|11|BDDBEF,1|22|73B2C5,21|-6|0010FF")
            , new CheckRuleModel(990, 318, 1013, 337, "8CCEF7,1|3|9CDEFF,0|10|DEF3EF,7|0|9CDBFF,6|5|7BBEFF,7|10|EFFFFF,16|1|6BBEF7,15|6|B5E7FF,15|10|8CD7FF")
            , new CheckRuleModel(11, 155, 97, 179, "192021,-1|5|192021,-1|13|192021,39|3|314552,40|9|7392A4,38|15|192429,68|1|7392A4,68|8|212429,68|15|7392AD")
            , new CheckRuleModel(1064, 666, 1141, 696, "D6EBF7,1|9|9CCEE6,2|17|6BB6DE,38|3|94C2DE,39|7|097AB5,38|17|007DBD,64|2|D6EBEF,65|13|FFFFFF,65|18|FFFFFF")
            , new CheckRuleModel(986, 629, 1062, 664, "0075B5,7|3|DEF3FF,8|11|0079B5,8|18|007DBD,27|1|0075B5,29|9|DEF3FF,29|19|007DBD,41|1|DEF3FF,41|17|DEF3FF")
            , new CheckRuleModel(691, 409, 777, 456, "FFFFFF,-1|5|0075B5,1|17|0079BD,9|1|FFFFFF,14|16|0079BD,20|4|0075B5,18|16|0079BD,29|3|0075AD,32|18|FFFFFF")
    };

    public static final CheckRuleModel[] dailyDungeonModels = {
            new CheckRuleModel(1099, 628, 1152, 684, "214563,0|9|194D8C,0|19|08183A,11|0|005DFF,11|11|00D6EF,12|23|004DEE,25|1|63EBFF,24|13|0072F7,24|23|1175FF")
            , new CheckRuleModel(830, 539, 878, 576, "192D4A,-1|8|294573,-1|13|5A8EAD,12|-3|BDCEDE,12|3|BDC2D6,12|13|73B6D6,23|-3|94A2B5,24|3|BD9E5A,24|14|73AAD6")
    };

    public static final CheckRuleModel[] dailyDungeonConfirmModels = {
            new CheckRuleModel(799, 594, 1122, 652, "0875AD,0|2|1982BD,0|5|CEE7F7,1|7|8CC6E6,2|12|A4CEEF,2|16|9CD2EF,9|2|0071AD,9|13|94CAE6,9|17|D5F3FF,12|1|A5D2E6,11|10|318EC5,11|16|63B2D6,20|2|8CC2E6,20|6|BDDFF7,20|9|298ABD,21|12|84C2DE,21|14|1081B5,21|17|D5EEFF,28|1|94C6E6,27|9|3A9AC5,28|11|6BB6D6,28|14|BDE2F7")
            , new CheckRuleModel(799, 594, 1122, 652, "0071AD,0|2|ADD7E6,1|5|0075B5,1|10|1182C5,0|14|94CAE6,10|1|A5D2E6,9|5|5AA6CE,10|11|7CBADE,10|14|D6EFF7,19|2|8CC2E6,21|8|CDEBFF,21|12|C5E3F7,21|14|007DBD,21|16|1086C5,26|1|94C6DE,25|7|3A96C5,25|15|2992C6")
            , new CheckRuleModel(799, 594, 1122, 652, "006DAD,-1|7|0075B5,-1|15|DEF3FF,8|4|9CCEE6,8|16|CDEBF7,16|4|84BEDE,15|16|CEEBFF,24|5|73B6DE,24|17|5AAED6")
    };

    public static final CheckRuleModel dailyDungeonTitleModel = new CheckRuleModel(19, 7, 158, 42, "101819,0|4|526D73,1|12|BDF3FF,0|18|B5EFFF,17|1|101819,16|18|101821,26|0|101C21,26|4|B5EFFF,26|14|101821,26|22|B5EFFF,55|1|101819,55|4|A4D2DE,55|12|BDF3FF,54|21|211C29,85|5|B5EFFF,85|14|BDF3FF,85|19|B5EFFF");

    public static final CheckRuleModel[] enterFarmingModels = {
            new CheckRuleModel(1099, 628, 1152, 684, "214563,0|9|194D8C,0|19|08183A,11|0|005DFF,11|11|00D6EF,12|23|004DEE,25|1|63EBFF,24|13|0072F7,24|23|1175FF")
            , new CheckRuleModel(1138, 544, 1175, 576, "7BEFFF,0|8|73EBF7,0|15|3A4D6B,10|0|6BA6DE,10|8|315984,11|15|7392C5,22|0|213D63,22|8|21244A,22|16|84EBFF")
            , new CheckRuleModel(322, 140, 403, 181, "191C21,0|7|312D31,0|13|7392AD,-1|20|7392AD,20|0|191C21,21|5|738EA4,20|17|7392AD,20|22|738EA4,31|13|738EA4")
            , new CheckRuleModel(1061, 446, 1094, 464, "315573,7|-8|31516B,13|4|3175A4,22|4|3175A4")
            , new CheckRuleModel(91, 176, 157, 210, "19354A,3|8|A4EBFF,8|20|9CDFEF,18|6|213D5A,25|20|293D63,29|11|19395A,34|9|A4EBFF,41|7|9CEBF7,41|24|9CDFEF")
            , new CheckRuleModel(321, 458, 380, 478, "636D6B,3|6|000000,6|16|111111,15|4|292829,21|0|B5C6C5,29|6|C5C6C5,33|15|737173,39|5|D6D2D6,47|2|848284")
            , new CheckRuleModel(1013, 581, 1140, 621, "006DA4,10|16|B5D7E6,27|23|DEEFF7,40|11|CEE3EF,53|18|FFFFFF,62|25|9CCAE6,76|30|007DBD,82|15|94C2DE,83|29|F7FBFF")
    };
    public static final CheckImageModel[] energyEmptyModels = {
            new CheckImageModel(126, 52, 156, 81, "EnergyEmptyIcon_1.png"),
            new CheckImageModel(126, 52, 156, 81, "EnergyEmptyIcon_2.png")
    };
    public static final CheckImageModel[] saveItemsModels = {
            new CheckImageModel(1192, 621, 1261, 689, "InventoryButton.png"),
            new CheckImageModel(11, 306, 58, 335, "SaveButton.png"),
            new CheckImageModel(313, 61, 432, 115, "AccountSaveButton.png"),
            new CheckImageModel(648, 330, 815, 388, "TransferAllButton.png"),
            new CheckImageModel(546, 597, 730, 647, "ConfirmButton_Dark.png")
    };
    public static final CheckImageModel[] getDailyRewardModels = {
            new CheckImageModel(1087, 621, 1162, 693, "AdventureButton.png"),
            new CheckImageModel(1053, 534, 1105, 577, "AchievementButton.png"),
            new CheckImageModel(1092, 652, 1224, 690, "AchievementGetAllButton.png"),
    };
    public static final CheckImageModel[] FriendAndGuildModels = {
            dailyMenuModel,
            new CheckImageModel(384, 140, 423, 170, "DailyDungeonCollapseButton.png"),
            new CheckImageModel(385, 228, 417, 249, "DailyDungeonCollapseButton.png"),
            new CheckImageModel(561, 428, 661, 449, "DailyGuildCheckInButton.png"),
            new CheckImageModel(696, 549, 760, 588, "ConfirmButton_Dark.png"),
            new CheckImageModel(604, 437, 678, 475, "ConfirmButton_Dark.png"),
            new CheckImageModel(674, 424, 776, 455, "DailyGuildRewardButton.png", 0.7f),
            new CheckImageModel(614, 527, 671, 565, "ConfirmButton_Dark.png"),
            new CheckImageModel(938, 629, 1054, 663, "FriendSendButton.png"),
            new CheckImageModel(683, 427, 802, 472, "ConfirmButton_Dark.png"),
            new CheckImageModel(1085, 630, 1198, 656, "FriendReceiveButton.png"),
    };
    public static final CheckRuleModel[] switchCharacterModels = {
            new CheckRuleModel(15, 54, 34, 73, "6BAABD,3|10|63AACE,6|1|314152,8|12|102021,11|4|84CADE,14|13|102021")
            , new CheckRuleModel(1057, 603, 1180, 641, "107DBD,5|11|0881BD,17|-8|0071AD,20|-2|0075B5,20|8|DEF3FF,41|-8|0071AD,48|1|DEF3FF,53|12|007DBD,58|-6|DEF3FF,71|1|DEF7FF,79|11|E6F7FF")
    };

    public static final CheckImageModel availableCharacterModel = new CheckImageModel(390, 260, 474, 553, "CharacterAvailableIcon.png");


    private static final AssetManager assetManager = MainApplication.getInstance().getAssets();
    public static CheckImageModel[] damagingModels = {
            new CheckImageModel(908, 48, 1113, 300, "ComboIcon_1.png"),
            new CheckImageModel(908, 48, 1113, 300, "ComboIcon_2.png"),
            new CheckImageModel(908, 48, 1113, 300, "ComboIcon_3.png")
    };
    public static boolean initialized;
    public static Bitmap[] numbers = new Bitmap[5];
    public static Bitmap resultIcon, rewardIcon, continueConfirmButton, ammoBuffIcon, epicIcon,
            coalIcon, crystalIcon, towerIcon, ticketsIcon, goldIcon, crouchingIcon;
    public static Bitmap[] dailyDungeonIcons;
    private static InputStream input;

    public static void Initialize() {
        numbers[0] = readImage("Number_1.png");
        numbers[1] = readImage("Number_2.png");
        numbers[2] = readImage("Number_3.png");
        numbers[3] = readImage("Number_4.png");
        numbers[4] = readImage("Number_5.png");
        resultIcon = readImage("ResultIcon.jpeg");
        rewardIcon = readImage("RewardIcon.jpg");
        continueConfirmButton = readImage("ConfirmButton_Bright.png");
        ammoBuffIcon = readImage("AmmoBuffIcon.png");
        crouchingIcon = readImage("CrouchingIcon.jpeg");

        epicIcon = readImage("EpicIcon.png");
        ticketsIcon = readImage("TicketsIcon.png");
        crystalIcon = readImage("CrystalIcon.png");
        coalIcon = readImage("CoalIcon.png");
        goldIcon = readImage("GoldIcon.png");
        towerIcon = readImage("TowerIcon.png");
        dailyDungeonIcons = new Bitmap[]{epicIcon, goldIcon, ticketsIcon, crystalIcon, coalIcon};
        initialized = true;
    }

    public static synchronized Bitmap readImage(String name) {
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
