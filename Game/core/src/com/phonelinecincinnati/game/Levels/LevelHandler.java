package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void loadFromJson(String levelFileName) {
        Gson gson = new Gson();
        LevelJson level = null;
        try {
            //new FileReader("Config/Levels/"+levelFileName)
            level = gson.fromJson(
                    "{\"json\":[{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource\",\"value\":[]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(0.0,6.05,-10.0)\",\"(51.0,0.1,10.0)\",\"FakeLimbo\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(15.4,0.0,2.48)\",\"180.0\",\"SubwayBench3\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(15.4,0.0,6.5)\",\"180.0\",\"SubwayBench2\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(20.5,6.0,-0.1)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(0.0,0.0,0.0)\",\"(20.4,12.0,0.5)\",\"Brick\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(0.0,0.0,0.0)\",\"(0.5,12.0,49.0)\",\"Brick\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Wall\",\"value\":[\"(16.6,0.0,0.0)\",\"(0.5,6.0,9.0)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(0.0,0.05,0.0)\",\"(3.0,-0.1,49.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(6.9,-0.05,6.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(6.9,-0.05,18.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(6.9,-0.05,30.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(6.9,-0.05,42.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(10.7,-0.05,0.0)\",\"(6.0,0.1,9.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(10.7,-0.05,9.0)\",\"(4.54,0.1,40.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Threshold\",\"value\":[\"(3.0,0.0,0.0)\",\"(7.7,6.0,40.0)\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Stairs\",\"value\":[\"false\",\"North\",\"(20.4,6.11,-0.24)\",\"11\",\"0.612\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Stairs\",\"value\":[\"false\",\"North\",\"(23.4,6.11,-0.24)\",\"11\",\"0.612\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Stairs\",\"value\":[\"false\",\"North\",\"(26.4,6.11,-0.24)\",\"11\",\"0.612\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(16.5,0.0,12.08)\",\"0.0\",\"SubwayBench3\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(16.5,0.0,16.1)\",\"0.0\",\"SubwayBench3\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(34.0,0.0,11.48)\",\"180.0\",\"SubwayBench2\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(34.0,0.0,16.1)\",\"180.0\",\"SubwayBench1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob\",\"value\":[\"(25.0,0.0,11.0)\",\"(0.0,0.0,0.0)\",\"Bat\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(15.0,0.0,9.0)\",\"(8.8,12.0,0.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(25.46,0.0,9.0)\",\"-90.0\",\"DoorFrame\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Door\",\"value\":[\"(23.9,0.0,9.0)\",\"true\",\"false\",\"Door\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(23.8,6.0,9.0)\",\"(3.3,6.0,0.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(27.1,0.0,9.0)\",\"(8.15,12.0,0.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(20.4,0.0,0.0)\",\"(0.5,12.0,9.0)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(15.15,0.0,8.9)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(15.25,0.0,9.0)\",\"(0.4,6.0,9.4)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(15.25,0.0,18.4)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(30.42,0.0,0.0)\",\"(0.5,12.0,9.0)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(35.26,0.0,8.9)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(35.25,0.0,9.0)\",\"(0.4,6.0,9.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(35.25,0.0,18.4)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(20.4,-0.05,0.0)\",\"(14.2,0.1,9.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(15.24,-0.05,9.0)\",\"(20.0,0.1,16.7)\",\"DarkBrownCheckeredTiles\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob\",\"value\":[\"(28.0,0.0,36.0)\",\"(0.0,-90.0,0.0)\",\"Bat\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(15.25,0.0,25.5)\",\"(0.4,6.0,9.4)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(15.25,0.0,25.5)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(21.25,0.0,25.7)\",\"(0.5,6.0,25.0)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(21.15,0.0,25.6)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(35.25,0.0,25.7)\",\"(0.5,6.0,25.0)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(35.35,0.0,25.6)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(21.25,0.0,25.7)\",\"(5.0,6.0,0.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(27.91,0.0,25.7)\",\"-90.0\",\"DoorFrame\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Door\",\"value\":[\"(26.35,0.0,25.7)\",\"true\",\"false\",\"Door\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(29.6,0.0,25.7)\",\"(5.65,6.0,0.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(21.25,0.0,38.0)\",\"(14.0,6.0,0.5)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(21.24,-0.05,25.7)\",\"(14.0,0.1,16.0)\",\"BlackWhiteCheckeredTiles\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(35.0,0.0,2.48)\",\"0.0\",\"SubwayBench3\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel\",\"value\":[\"(35.0,0.0,6.5)\",\"0.0\",\"SubwayBench2\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob\",\"value\":[\"(38.0,0.0,20.0)\",\"(0.0,180.0,0.0)\",\"GolfClub\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(30.3,6.0,-0.1)\",\"0.0\",\"Pillar\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(30.5,0.0,0.0)\",\"(20.4,12.0,0.5)\",\"Brick\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(50.9,0.0,0.0)\",\"(0.5,12.0,49.0)\",\"Brick\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Wall\",\"value\":[\"(33.8,0.0,0.0)\",\"(0.5,6.0,9.0)\",\"Concrete1\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(33.77,-0.05,0.0)\",\"(6.0,0.1,9.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(35.23,-0.05,9.0)\",\"(4.54,0.1,40.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Threshold\",\"value\":[\"(38.23,-0.05,0.0)\",\"(7.7,6.0,40.0)\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(43.5,-0.05,6.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(43.5,-0.05,18.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(43.5,-0.05,30.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Model.Model\",\"value\":[\"(43.5,-0.05,42.0)\",\"0.0\",\"SubwayTracks\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Plane\",\"value\":[\"(47.3,-0.05,0.0)\",\"(4.0,0.1,49.0)\",\"DarkGreenDiamonds\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.SolidWall\",\"value\":[\"(0.0,0.0,48.0)\",\"(52.0,12.0,0.5)\",\"Brick\"]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Player.Player\",\"value\":[]},{\"key\":\"com.phonelinecincinnati.game.GameObjects.Objects.Utility.LevelTitleCard\",\"value\":[]}]}\n",
                    LevelJson.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert level != null;
        for(Pair<String, ArrayList<String>> object : level.json) {
            if(object.getValue().isEmpty())
                continue;
            try {
                //Class.forName(object.getKey()).getConstructor(object.getValue());
                Class objectClass = Class.forName(object.getKey());
                Constructor<?> constructor = objectClass.getConstructor(ArrayList.class);
                GameObject gameObject = (GameObject)constructor.newInstance(object.getValue());
                activeObjects.add(gameObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SaveToJson(String levelFileName) {
        Gson gson = new Gson();
        LevelJson level = new LevelJson();

        for(GameObject object : activeObjects) {
            String key = object.getClass().getName();
            ArrayList<String> values = object.getConstructParams();
            level.json.add(new Pair<String, ArrayList<String>>(key, values));
        }

        //Can take a Writable(file) in the toJson method to write directly to file instead of to string
        String json = gson.toJson(level);
        System.out.println(json);
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
