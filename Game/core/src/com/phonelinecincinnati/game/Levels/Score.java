package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI.MafiaMobBrain;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;

import java.util.ArrayList;


public class Score {
    private int total = 0;
    private int comboNumber = 0;
    private float timeComboStarted = -1;
    private float timeLevelStarted = 0;
    private ArrayList<String> actions = new ArrayList<String>();

    private final int comboTimeSec = 2;

    public void reset() {
        total = 0;
        comboNumber = 0;
        timeComboStarted = -1;
        timeLevelStarted = System.nanoTime();
        actions = new ArrayList<String>();
    }

    public void addScore(int score, int alertedBonus, String action) {
        comboNumber += 1;
        timeComboStarted = System.nanoTime();

        int alertedEnemies = 0;
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(object.getClass().isAssignableFrom(MafiaMob.class)) {
                MafiaMob enemy = (MafiaMob)object;
                if(enemy.brain.currentState != MafiaMobBrain.State.Patrolling &&
                        enemy.brain.currentState != MafiaMobBrain.State.Returning && !enemy.dead) {
                    alertedEnemies++;
                }
            }
        }

        determineActions(alertedEnemies, action);

        total += score + (alertedBonus * alertedEnemies);
    }

    private void determineActions(int alertedEnemies, String action) {
        if(!action.equals("")) {
            actions.add(action);
        }

        if(action.equals("Execution")) return;

        if(alertedEnemies == 1) {
            actions.add("Exposure");
        }
        else if(alertedEnemies == 2) {
            actions.add("Double Exposure");
        }
        else if(alertedEnemies == 3) {
            actions.add("Triple Exposure");
        }
        else if (alertedEnemies >= 4) {
            actions.add("Severe Exposure");
        }

        if(comboNumber > 1) {
            actions.add(comboNumber+"x Combo");
        }
    }

    public void update() {
        // If the combo is active AND the time passed is longer than 'comboTimeSec'
        if(timeComboStarted != -1 && (System.nanoTime() - timeComboStarted)*9 > comboTimeSec) {
            total += 800*comboNumber;
            comboNumber = 0;
        }
    }

    public int get() {
        return total;
    }

    public void levelReadout() {
        // TODO: change this to return object used in ending card
        System.out.println("Level: "+Main.levelHandler.currentLevel.name);
        System.out.println("Time: "+ (System.nanoTime()-timeLevelStarted)*9 +" seconds");
        System.out.println("Score: "+total);
        System.out.println("\nActions:");

        int numExposures = 0;
        int numCombos = 0;
        int numDoorSlams = 0;
        int numExecutions = 0;
        for(String action : actions) {
            if(action.contains("Exposure")) {
                numExposures += 1;
            }
            if(action.contains("Combo"))  {
                numCombos += 1;
            }
            if(action.equals("Door Slam")) {
                numDoorSlams += 1;
            }
            if(action.equals("Execution")) {
                numExecutions += 1;
            }

            System.out.println(action);
        }

        String playStyle = "Generic";
        if(numExposures > 5) {
            playStyle = "Exhibitionist";
        }
        if(numCombos > 4) {
            playStyle = "ComboBreaker";
        }
        if(numDoorSlams > 3) {
            playStyle = "Door Man";
        }
        if(numExecutions > 4) {
            playStyle = "Executioner";
        }
        if(numExecutions > 9) {
            playStyle = "Sadist";
        }
        if(numExposures == 0) {
            playStyle = "Invisible Man";
        }
        System.out.println("\nPlay style: "+playStyle);
    }
}
