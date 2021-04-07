package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.BuilderWidget;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.M16;
import com.phonelinecincinnati.game.Main;
import javafx.util.Pair;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class LevelHandler {
    private final CopyOnWriteArrayList<GameObject> activeObjects;
    private int progression = 1; //TODO: Change this to 0 to reset progress
    public Level currentLevel;
    public Player player = null;
    public Score score;

    public LevelHandler() {
        Main.modelHandler.setupAllModels();
        activeObjects = new CopyOnWriteArrayList<GameObject>();
        score = new Score();
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
            if(!values.isEmpty()) {
                level.json.add(new Pair<String, ArrayList<String>>(key, values));
            }
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

    public void loadLevelEditor(String levelName) {
        clearActiveObjects();

        Main.backgroundColor.set(0.043137256f, 0.043137256f, 0.19215687f, 1f);

        if(!levelName.equals("")) {
            loadFromJson(levelName);
        }

        final Player player =
                Level.createPlayer(new Vector3(0, 0f, -5), new Vector3(0, 0, 1), null,
                        false, false);
        player.giveControl();
        activeObjects.add(player);
        activeObjects.add(new BuilderWidget());
    }

    public void loadMenu() {
        clearActiveObjects();
        currentLevel = new Menu(activeObjects, progression);
        //currentLevel = new TestingGround(activeObjects);
        //currentLevel = new House(activeObjects, progression);
        //currentLevel = new Level1(activeObjects);
    }

    public void loadEndCard(ArrayList<String> results) {
        clearActiveObjects();
        currentLevel = new EndCard(activeObjects, results);
    }

    public void loadHouse() {
        clearActiveObjects();
        currentLevel = new House(activeObjects, progression);
    }

    public void loadLevel1() {
        clearActiveObjects();
        currentLevel = new Homicide(activeObjects);
        //currentLevel = new Level1(activeObjects);
    }

    public void dispose() {
        for(GameObject object : activeObjects) {
            object.dispose();
        }
    }
}
