package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI.MafiaMobBrain;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.MovingText;


import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class Score {
    private int total = 0;
    private int comboNumber = 0;
    private long timeComboStarted = -1;
    private long timeLevelStarted = 0;
    private ArrayList<String> actions = new ArrayList<String>();

    private int displayedTotal = 0;
    private MovingText movingText = new MovingText(Renderer.hudTopFont, Renderer.hudBottomFont);
    private static GlyphLayout glyphLayout = new GlyphLayout();
    private CopyOnWriteArrayList<String> currentActions = new CopyOnWriteArrayList<String>();
    private float offset = 0;

    @SuppressWarnings("FieldCanBeLocal")
    private final int comboTimeSec = 2;


    public void reset() {
        total = 0;
        comboNumber = 0;
        timeComboStarted = -1;
        timeLevelStarted = System.currentTimeMillis();
        actions = new ArrayList<String>();
    }

    public void addScore(int score, int alertedBonus, String action) {
        comboNumber += 1;
        timeComboStarted = System.currentTimeMillis();

        int alertedEnemies = 0;
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(object.getClass().isAssignableFrom(MafiaMob.class)) {
                MafiaMob enemy = (MafiaMob)object;
                if(enemy.brain.currentState != MafiaMobBrain.State.Patrolling &&
                        enemy.brain.currentState != MafiaMobBrain.State.Returning &&
                        !enemy.dead && !enemy.knockedOver) {
                    alertedEnemies++;
                }
            }
        }

        ArrayList<String> currentActions = determineActions(alertedEnemies, action);
        actions.addAll(currentActions);
        this.currentActions.addAll(currentActions);

        total += score + (alertedBonus * alertedEnemies);
    }

    private ArrayList<String> determineActions(int alertedEnemies, String action) {
        ArrayList<String> currentActions = new ArrayList<String>();
        if(!action.equals("")) {
            currentActions.add(action);
        }

        if(action.equals("Execution")) return currentActions;

        if(alertedEnemies == 1) {
            currentActions.add("Exposure");
        }
        else if(alertedEnemies == 2) {
            currentActions.add("Double Exposure");
        }
        else if(alertedEnemies == 3) {
            currentActions.add("Triple Exposure");
        }
        else if (alertedEnemies >= 4) {
            currentActions.add("Severe Exposure");
        }

        return currentActions;
    }

    public void update() {
        movingText.update();

        // If the combo is active AND the time passed is longer than 'comboTimeSec'
        if(timeComboStarted > 0 && (System.currentTimeMillis()-timeComboStarted)/1000 > comboTimeSec) {
            if(comboNumber >= 2) {
                total += 800*(comboNumber-1);
                currentActions.add(comboNumber+"x Combo");
                actions.add(comboNumber+"x Combo");
            }
            comboNumber = 0;
        }
    }

    public int get() {
        return total;
    }

    public ArrayList<String> levelResults() {
        ArrayList<String> results = new ArrayList<String>();

        results.add("Level: "+Main.levelHandler.currentLevel.name);
        results.add("Time: "+ (System.currentTimeMillis()-timeLevelStarted)/1000 +" seconds");
        results.add("Score: "+total);

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

            results.add(action);
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
        results.add(playStyle);

        return results;
    }

    public void renderHudElement(Renderer renderer) {
        glyphLayout.setText(renderer.hudBottomFont, "G");
        float offsetBase = glyphLayout.height + 10;
        if(currentActions.isEmpty()) {
            offset = offsetBase;
        } else {
            for(String action : currentActions) {
                glyphLayout.setText(renderer.hudBottomFont, action);
                float x = (Gdx.graphics.getWidth()-glyphLayout.width)-10;
                float y = (Gdx.graphics.getHeight()-10)-offset;
                if(currentActions.indexOf(action) > 0) {
                    y -= (offsetBase+10)*currentActions.indexOf(action);
                }
                renderer.renderText(x, y, action, renderer.hudBottomFont);
            }
            offset -= 1;
            if(offset <= 0) {
                currentActions.remove(0);
                offset = offsetBase;
            }
        }

        if(displayedTotal < total) {
            displayedTotal += 5;
        } else {
            displayedTotal = total;
        }
        String totalStr = Integer.toString(displayedTotal)+" pts";

        glyphLayout.setText(renderer.hudBottomFont, totalStr);
        float width = glyphLayout.width;
        movingText.render(renderer, (Gdx.graphics.getWidth()-width)-10, Gdx.graphics.getHeight()-10, totalStr);
    }
}
