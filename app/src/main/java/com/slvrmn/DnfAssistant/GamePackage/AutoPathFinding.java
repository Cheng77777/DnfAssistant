package com.slvrmn.DnfAssistant.GamePackage;

import static com.slvrmn.DnfAssistant.GamePackage.Actions.Dodge;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.GetReward;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.GoOutDungeon;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.MoveAround;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressJoystick;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressLongAttack;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.PressMultipleAttacks;
import static com.slvrmn.DnfAssistant.GamePackage.Actions.UseBuff;
import static com.slvrmn.DnfAssistant.GamePackage.Assistant.RUN;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.CHECK_INTERVAL;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.beforeLion;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.canDodge;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasMonster;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.hasReward;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inDungeon;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inHell;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.inLion;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isBattling;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isDamaging;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isEnergyEmpty;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.isPathFinding;
import static com.slvrmn.DnfAssistant.GamePackage.ScreenCheck.screenFreezeTime;

import com.slvrmn.DnfAssistant.Model.Robot;
import com.slvrmn.DnfAssistant.Tools.MLog;
import com.slvrmn.DnfAssistant.Tools.Utility;

public class AutoPathFinding extends Thread {
    private int stuckTime = 0;

    @Override
    public void run() {
        try {
            mainLoop:
            while (RUN) {
                if (inDungeon && isPathFinding) {
                    if (hasReward) {
                        GetReward();
                    } else {
                        if (beforeLion) {
                            do {
                                if (!RUN) {
                                    return;
                                }
                                int random = Utility.RandomInt(2000, 2300);
                                Robot.LongPress(Presets.moveRecs[0], random);
                                sleep(random + 100);
                                random = Utility.RandomInt(1000, 1200);
                                Robot.LongPress(Presets.moveRecs[2], random);
                                sleep(3000);

                                if (!inLion) {
                                    PressJoystick(3, 100);
                                    sleep(100);
                                    int pressTime = Utility.RandomInt(1500, 1600);
                                    PressLongAttack(pressTime, pressTime);

                                    int steps = pressTime / CHECK_INTERVAL;
                                    for (int i = steps; i > 0; i--) {
                                        if (inLion) {
                                            continue mainLoop;
                                        }
                                        /** 检测闪避 **/
                                        if (canDodge) {
                                            Dodge();
                                            continue mainLoop;
                                        }
                                        /** 检测战斗 **/
                                        if (isDamaging) {
                                            isPathFinding = false;
                                            isBattling = true;
                                            continue mainLoop;
                                        }
                                        sleep(CHECK_INTERVAL);
                                    }
                                }
                            } while (!inLion);
                        }
                        /** 长按攻击 **/
                        int pressTime = Utility.RandomInt(4500, 5000);
                        PressLongAttack(pressTime, 500);

                        int steps = (pressTime - 500) / CHECK_INTERVAL;
                        for (int i = steps; i > 0; i--) {

                            if (hasReward) {
                                continue mainLoop;
                            }

                            if (UseBuff()) {
                                continue mainLoop;
                            }
                            /** 检测战斗 **/
                            if (isDamaging && hasMonster) {
                                stuckTime = 0;
                                isPathFinding = false;
                                isBattling = true;
                                continue mainLoop;
                            }
                            /** 检测卡住 **/

                            if (stuckTime >= 1) {
                                MoveAround();
                                stuckTime = 0;
                                if (!inHell) {
                                    continue mainLoop;
                                } else {
                                    int random = Utility.RandomInt(1500, 2000);
                                    PressLongAttack(random, random);
                                    PressMultipleAttacks(3);
                                    random = Utility.RandomInt(1500, 2000);
                                    PressLongAttack(random, random);
                                    if (isEnergyEmpty) {
                                        GoOutDungeon();
                                    }
                                }
                            } else {
                                if (screenFreezeTime >= 2500 / CHECK_INTERVAL) {
                                    stuckTime++;
                                    continue mainLoop;
                                }
                            }
                            /** 检测闪避 **/
                            if (canDodge) {
                                Dodge();
                                continue mainLoop;
                            }
                            sleep(CHECK_INTERVAL);
                        }
                    }
                }
                sleep(CHECK_INTERVAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MLog.error(e.toString());
        }
    }
}
