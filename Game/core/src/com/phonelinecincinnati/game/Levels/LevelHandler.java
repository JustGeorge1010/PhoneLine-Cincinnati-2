package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;

import java.util.concurrent.CopyOnWriteArrayList;

public class LevelHandler {
    private CopyOnWriteArrayList<GameObject> activeObjects;
    private int progression = 1; //TODO: Change this to 0 to reset progress
    private Level currentLevel;

    public LevelHandler() {
        activeObjects = new CopyOnWriteArrayList<GameObject>();
    }

    public CopyOnWriteArrayList<GameObject> getActiveObjects() {
        return activeObjects;
    }

    private void clearActiveObjects() {
        for(GameObject object : activeObjects) {
            if(object.getClass() == Player.class)
                Main.controlHandler.resetPlayer();
            object.dispose();
            activeObjects.remove(object);
        }
    }

    public void addObjectToCurrentLevel(GameObject object) {
        GameObject player = activeObjects.get(activeObjects.size()-2);
        GameObject pauseMenu = activeObjects.get(activeObjects.size()-1);
        activeObjects.remove(player);
        activeObjects.remove(pauseMenu);
        activeObjects.add(object);
        activeObjects.add(player);
        activeObjects.add(pauseMenu);
    }

    public void loadCurrent() {
        if(progression == 0) {
            loadHouse();
        } else if(progression == 1) {
            loadLevel1();
        }
    }

    public void loadMenu() {
        clearActiveObjects();
        currentLevel = new Menu(activeObjects, progression);
        //currentLevel = new TestingGround(activeObjects);
        //currentLevel = new House(activeObjects, progression);
        //currentLevel = new Level1(activeObjects);
    }

    public void loadHouse() {
        clearActiveObjects();
        currentLevel = new House(activeObjects, progression);
    }

    public void loadLevel1() {
        clearActiveObjects();
        currentLevel = new Level1(activeObjects);
    }

    public void dispose() {
        for(GameObject object : activeObjects) {
            object.dispose();
        }
    }
}
