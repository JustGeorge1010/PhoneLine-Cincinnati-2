package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Door;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.InteractionThreshold;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.*;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.PlayerCar;
import com.phonelinecincinnati.game.GameObjects.Objects.Threshold;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.BaseballBat;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.GolfClub;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Alteration extends Level{
    private SoundSource music;

    Alteration(final CopyOnWriteArrayList<GameObject> activeObjects) {
        this.activeObjects = activeObjects;
        load(false, false);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        name = "Alteration";
        Main.levelHandler.levelName = name;

        Main.backgroundColor.set(0.043137256f, 0.043137256f, 0.19215687f, 1f);

        if(!reloading) {
            music = SoundSource.buildSoundSource(0).setMusic("Phil Collins - In The Air Tonight.mp3").playMusic();
            activeObjects.add(music);
        }

        final Player player =
                createPlayer(new Vector3(0, 0, -10), new Vector3(0, 0, 1), new BaseballBat(),
                        reloading, retainPlayer);

        Main.levelHandler.loadFromJson(name);
        for(GameObject gameObject : Main.levelHandler.getActiveObjects()) {
            if(gameObject.getClass() == Model.class) {
                Model model = (Model)gameObject;
                if(model.name == ModelName.DoorFrame) {
                    activeObjects.add(new Door(model.position.cpy(), true, false, ModelName.Door));
                }
            }
        }

        AStar.generateAStar(0);
        activeObjects.add(new MafiaMob(new Vector3(0, 0.2f, 25), new Vector3(0, -90, 0), new BaseballBat()));
        activeObjects.add(new MafiaMob(new Vector3(0, 0.2f, 32), new Vector3(0, 0, 0), new GolfClub()));

        stageController = new StageController("Find Hudds");

        final PlayerCar car = new PlayerCar(new Vector3(0f, -0.1f, -14), 180, player);
        car.unlocked = false;

        car.setAction(new Action() {
            @Override
            public void activate() {
                player.position.set(player.position.x, player.position.y+3.5f, car.position.z+4);
                activeObjects.add(new Fade(false, new Action() {
                    @Override
                    public void activate() {
                        for(GameObject object : activeObjects) {
                            object.dispose();
                        }
                        Main.levelHandler.loadEndCard(Main.levelHandler.score.levelResults());
                    }
                }, 0.008f));
                player.takeControl(new ForcedController() {
                    @Override
                    public void update() {
                        if(player.position.z > car.position.z+1) {
                            player.position.add(0, 0, -0.05f);
                        } else {
                            player.position.set(player.position.x, player.position.y, car.position.z + 1);
                        }
                        if(player.position.y > car.position.y+2.7f) {
                            player.position.add(0, -0.02f, 0);
                        } else {
                            player.position.set( player.position.x, car.position.y+2.7f, player.position.z);
                        }

                        if(player.position.z == car.position.z+1 && car.unlocked) {
                            player.position.set(0,  player.position.y, car.position.z);
                            car.unlocked = false;
                            SoundSource.buildSoundSource(1).setSound("Misc/CarEngine.wav").playSound();
                        } else {
                            Main.camera.position.set(player.position);
                            player.lookAt(car.position.x-10, car.position.z ,car.position.y);
                        }
                    }
                });
            }
        }, "(RMB) Flee the scene");
        activeObjects.add(car);

        activeObjects.add(stageController);
        final PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(false);
        activeObjects.add(player);

        if(!reloading) {
            final LevelTitleCard titleCard = new LevelTitleCard("LEVEL 2", name, 400);
            titleCard.addAction(new Action() {
                @Override
                public void activate() {
                    Main.levelHandler.score.reset();
                    activeObjects.add(new Fade(false, new Action() {
                        @Override
                        public void activate() {
                            player.giveControl();
                            pauseMenuHandler.enablePausing();
                            titleCard.rendering = false;
                            activeObjects.add(new Fade(true, null, 0.04f));
                        }
                    }, 0.02f));
                }
            });
            activeObjects.add(titleCard);
        } else {
            Main.levelHandler.score.reset();
            pauseMenuHandler.enablePausing();
        }
        activeObjects.add(pauseMenuHandler);
    }

    @Override
    public void reload() {
        for(GameObject object : activeObjects) {
            if(object != music) {
                object.dispose();
                activeObjects.remove(object);
            }
        }
        load(true, false);
    }
}
