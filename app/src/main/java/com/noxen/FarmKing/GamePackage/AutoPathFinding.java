package com.noxen.FarmKing.GamePackage;

import static com.noxen.FarmKing.GamePackage.Actions.Dodge;
import static com.noxen.FarmKing.GamePackage.Actions.GetReward;
import static com.noxen.FarmKing.GamePackage.Actions.GoOutDungeon;
import static com.noxen.FarmKing.GamePackage.Actions.MoveAround;
import static com.noxen.FarmKing.GamePackage.Actions.PressJoystick;
import static com.noxen.FarmKing.GamePackage.Actions.PressLongAttack;
import static com.noxen.FarmKing.GamePackage.Actions.PressMultipleAttacks;
import static com.noxen.FarmKing.GamePackage.Actions.UseBuff;
import static com.noxen.FarmKing.GamePackage.Actions.UseSkills;
import static com.noxen.FarmKing.GamePackage.Assistant.RUN;
import static com.noxen.FarmKing.GamePackage.Presets.continueConfirmRec;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.InitializeDailyQuestParameters;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.beforeLion;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.canDodge;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasDailyContinue;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasDailySelect;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasMonster;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasResult;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.hasReward;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.inHell;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.inLion;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isBattling;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isDamaging;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isEnergyEmpty;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isFarming;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.isPathFinding;
import static com.noxen.FarmKing.GamePackage.ScreenCheck.screenFreezeTime;

import com.noxen.FarmKing.Model.Robot;
import com.noxen.FarmKing.Tools.MLog;
import com.noxen.FarmKing.Tools.Utility;

public class AutoPathFinding extends Thread {
    private int hellCount = 0;

    @Override
    public void run() {
        try {
            mainLoop:
            while (RUN) {
                if (!isPathFinding) {
                    sleep(CHECK_INTERVAL);
                    continue;
                }

                if (canDodge) {
                    Dodge();
                    UseSkills();
                    isPathFinding = false;
                    isBattling = true;
                    continue mainLoop;
                }
                if (UseBuff()) {
                    continue mainLoop;
                }
                MLog.info("自动寻路中");
                if (isFarming) {
                    if (hasReward) {
                        GetReward();
                        sleep(3000);
                        continue;
                    }
                    if (beforeLion) {
                        do {
                            if (!RUN) {
                                return;
                            }
                            int random = Utility.RandomInt(2000, 2300);
                            Robot.LongPress(Presets.moveRecs[0], random);
                            sleep(random + 100);
                            if (!beforeLion) {
                                continue mainLoop;
                            }
                            random = Utility.RandomInt(1000, 1200);
                            Robot.LongPress(Presets.moveRecs[2], random);
                            sleep(1200);

                            if (!beforeLion) {
                                continue mainLoop;
                            }
                            random = Utility.RandomInt(100, 150);
                            PressJoystick(3, random);
                            sleep(random);
                            int pressTime = Utility.RandomInt(1500, 1600);
                            PressLongAttack(pressTime, pressTime);

                            int steps = pressTime / CHECK_INTERVAL;
                            for (int i = steps; i > 0; i--) {
                                if (inLion) {
                                    continue mainLoop;
                                }
                                if (canDodge) {
                                    Dodge();
                                    continue mainLoop;
                                }
                                if (isDamaging) {
                                    isPathFinding = false;
                                    isBattling = true;
                                    continue mainLoop;
                                }
                                sleep(CHECK_INTERVAL);

                            }
                        } while (beforeLion);
                    }
                } else {
                    if (hasDailyContinue.isValid()) {
                        MoveAround();
                        int random = Utility.RandomInt(5000, 5500);
                        PressLongAttack(random, random);
                        PressMultipleAttacks(3);
                        random = Utility.RandomInt(5000, 5500);
                        PressLongAttack(random, random);
                        Robot.Press(hasDailyContinue);
                        sleep(1000);
                        Robot.Press(continueConfirmRec);
                        sleep(3000);
                        InitializeDailyQuestParameters();
                        continue;
                    }
                    if (hasDailySelect.isValid()) {
                        MoveAround();
                        int random = Utility.RandomInt(5000, 5500);
                        PressLongAttack(random, random);
                        PressMultipleAttacks(3);
                        random = Utility.RandomInt(5000, 5500);
                        PressLongAttack(random, random);
                        Robot.Press(hasDailySelect);
                        sleep(3000);
                        InitializeDailyQuestParameters();
                        continue;
                    }

                }
                int pressTime = Utility.RandomInt(5000, 5500);
                PressLongAttack(pressTime, 500);

                int steps = (pressTime - 500) / CHECK_INTERVAL;
                for (int i = steps + 1; i >= 0; i--) {

                    if (isFarming) {
                        if (hasReward) {
                            continue mainLoop;
                        }

                        if (hasResult) {
                            continue;
                        }
                    } else {
                        if (hasDailyContinue.isValid()) {
                            continue;
                        }
                    }
                    if (isDamaging && hasMonster) {
                        hellCount = 0;
                        isPathFinding = false;
                        isBattling = true;
                        continue mainLoop;
                    }
                    MLog.info("" + hellCount);
                    if (inHell) {
                        if (isEnergyEmpty && hellCount > 100) {
                            MoveAround();
                            int random = Utility.RandomInt(1500, 2000);
                            PressLongAttack(random, random);
                            PressMultipleAttacks(3);
                            random = Utility.RandomInt(1500, 2000);
                            PressLongAttack(random, random);
                            GoOutDungeon();
                            hellCount = 0;
                            Actions.SwitchCharacter();
                            continue mainLoop;
                        }
                        hellCount++;
                    }
                    if (screenFreezeTime >= 30) {
                        MoveAround();
                        continue mainLoop;
                    }
                    sleep(CHECK_INTERVAL);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
