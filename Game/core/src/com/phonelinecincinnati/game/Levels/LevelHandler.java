package com.phonelinecincinnati.game.Levels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;
import javafx.util.Pair;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

public class LevelHandler {
    private CopyOnWriteArrayList<GameObject> activeObjects;
    private int progression = 1; //TODO: Change this to 0 to reset progress
    public Level currentLevel;
    public Player player = null;

    public LevelHandler() {
        activeObjects = new CopyOnWriteArrayList<GameObject>();
    }

    public CopyOnWriteArrayList<GameObject> getActiveObjects() {
        return activeObjects;
    }

    public void clearActiveObjects() {
        for(GameObject object : activeObjects) {
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void loadFromJson(String levelFileName) {
        Gson gson = new Gson();
        LevelJson level = null;
        try {
            FileReader fileReader = new FileReader("Config/Levels/"+levelFileName);

            level = gson.fromJson(fileReader, LevelJson.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert level != null;
        for(Pair<String, ArrayList<String>> object : level.json) {
            if(object.getValue().isEmpty())
                continue;
            try {
                //Class objectClass = Class.forName(object.getKey());
                //Constructor<?> constructor = objectClass.getConstructor(ArrayList.class);
                //GameObject gameObject = (GameObject)constructor.newInstance(object.getValue());
                activeObjects.add(GameObject.constructFromClassName(object.getKey(), object.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SaveToJson(String levelFileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        LevelJson level = new LevelJson();

        for(GameObject object : activeObjects) {
            String key = object.getClass().getName();
            ArrayList<String> values = object.getConstructParams();
            level.json.add(new Pair<String, ArrayList<String>>(key, values));
        }

        //Can take a Writable(file) in the toJson method to write directly to file instead of to string
        try {
            FileWriter writer = new FileWriter("Config/Levels/"+levelFileName);
            gson.toJson(level, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
