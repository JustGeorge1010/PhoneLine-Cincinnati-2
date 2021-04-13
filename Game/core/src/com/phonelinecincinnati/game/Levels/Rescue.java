package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Door;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.InteractionThreshold;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.*;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveSolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Pickups.WeaponPickUp;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.PlayerCar;
import com.phonelinecincinnati.game.GameObjects.Objects.Threshold;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.BaseballBat;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.GolfClub;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.M16;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.M1911;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Rescue extends Level{
    private SoundSource music;

    Rescue(final CopyOnWriteArrayList<GameObject> activeObjects) {
        this.activeObjects = activeObjects;
        load(false, false);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        name = "Rescue";
        Main.levelHandler.levelName = name;
        isDark = true;

        Main.backgroundColor.set(0.043137256f, 0.043137256f, 0.19215687f, 1f);

        if(!reloading) {
            music = SoundSource.buildSoundSource(0).setMusic("Phil Collins - In The Air Tonight.mp3").playMusic();
            activeObjects.add(music);
        }

        final Player player =
                createPlayer(new Vector3(0, 0, -9), new Vector3(0, 0, 1), new BaseballBat(),
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
        activeObjects.add(new WeaponPickUp(new Vector3(8, 0, 32), new M16()));

        //<editor-fold desc="Enemies">
        AStar.generateAStar(0);
        activeObjects.add(new MafiaMob(new Vector3(23, 0.2f, 19.5f), new Vector3(0, 180, 0), new BaseballBat()));
        activeObjects.add(new MafiaMob(new Vector3(28, 0.2f, 13), new Vector3(0, 90, 0), new GolfClub()));
        activeObjects.add(new MafiaMob(new Vector3(48, 0.2f, 12.5f), new Vector3(0, 120, 0), new GolfClub()));
        activeObjects.add(new MafiaMob(new Vector3(35.5f, 0.2f, 29f), new Vector3(0, -90, 0), new BaseballBat()));

        activeObjects.add(new MafiaMob(new Vector3(10, 0.2f, 52), new Vector3(0, 0, 0), new GolfClub()));
        ArrayList<Vector3> path = new ArrayList<Vector3>();
        path.add(new Vector3(40, 0.2f, 46));
        path.add(new Vector3(40, 0.2f, 68));
        path.add(new Vector3(23, 0.2f, 68));
        path.add(new Vector3(23, 0.2f, 46));
        activeObjects.add(new MafiaMob(new Vector3(23, 0.2f, 46), new Vector3(0, 0, 0), path, new BaseballBat()));
        path = new ArrayList<Vector3>();
        path.add(new Vector3(23, 0.2f, 68));
        path.add(new Vector3(23, 0.2f, 46));
        path.add(new Vector3(40, 0.2f, 46));
        path.add(new Vector3(40, 0.2f, 68));
        activeObjects.add(new MafiaMob(new Vector3(40, 0.2f, 68), new Vector3(0, 0, 0), path, new BaseballBat()));

        activeObjects.add(new MafiaMob(new Vector3(58, 0.2f, 62), new Vector3(0, 180, 0), new BaseballBat()));
        activeObjects.add(new MafiaMob(new Vector3(75, 0.2f, 55.5f), new Vector3(0, -90, 0), new GolfClub()));

        activeObjects.add(new MafiaMob(new Vector3(75, 0.2f, 48), new Vector3(0, 180, 0), new GolfClub()));
        activeObjects.add(new MafiaMob(new Vector3(55, 0.2f, 49), new Vector3(0, -90, 0), new BaseballBat()));
        activeObjects.add(new MafiaMob(new Vector3(64, 0.2f, 28), new Vector3(0, 0, 0), new BaseballBat()));
        activeObjects.add(new MafiaMob(new Vector3(74, 0.2f, 34), new Vector3(0, 90, 0), new GolfClub()));

        activeObjects.add(new MafiaMob(new Vector3(48, 0.2f, -8), new Vector3(0, 90, 0), new GolfClub()));
        path = new ArrayList<Vector3>();
        path.add(new Vector3(86, 0.2f, 1));
        path.add(new Vector3(58, 0.2f, 1));
        activeObjects.add(new MafiaMob(new Vector3(58, 0.2f, 1), new Vector3(0, 0, 0), path, new BaseballBat()));
        path = new ArrayList<Vector3>();
        path.add(new Vector3(65, 0.2f, 10));
        path.add(new Vector3(85, 0.2f, 10));
        activeObjects.add(new MafiaMob(new Vector3(85, 0.2f, 10), new Vector3(0, 0, 0), path, new GolfClub()));
        activeObjects.add(new MafiaMob(new Vector3(84, 0.2f, 17), new Vector3(0, -90, 0), new BaseballBat()));

        activeObjects.add(new MafiaMob(new Vector3(100, 0.2f, 5), new Vector3(0, 180, 0), new GolfClub()));
        path = new ArrayList<Vector3>();
        path.add(new Vector3(100, 0.2f, 17));
        path.add(new Vector3(100, 0.2f, 30));
        activeObjects.add(new MafiaMob(new Vector3(100, 0.2f, 30), new Vector3(0, 0, 0), path, new BaseballBat()));
        path = new ArrayList<Vector3>();
        path.add(new Vector3(110, 0.2f, 36));
        path.add(new Vector3(110, 0.2f, 15));
        activeObjects.add(new MafiaMob(new Vector3(110, 0.2f, 15), new Vector3(0, 0, 0), path, new GolfClub()));
        activeObjects.add(new MafiaMob(new Vector3(120, 0.2f, 34), new Vector3(0, 180, 0), new GolfClub()));
        //</editor-fold>

        final PlayerCar car = new PlayerCar(new Vector3(0f, -0.1f, -13), 180, player);
        car.unlocked = false;

        stageController = new StageController("Find Hudds");
        final InteractiveSolidModel hudds = new InteractiveSolidModel(new Vector3(139.5f, 0, 22), -90,
                ModelName.HuddsWall);
        hudds.setAction(new Action() {
            @Override
            public void activate() {
                player.lookAt(hudds.position.x, hudds.position.z, -35f);
                player.textBox.text.clear();
                player.textBox.text.add("Hockett: Rico!");
                player.textBox.text.add("Hockett: Hey!");
                player.textBox.text.add("Hockett: Are you awake buddy?");
                player.textBox.text.add("Hudds: *Gasp*");
                player.textBox.text.add("Hudds: ...");
                player.textBox.text.add("Hudds: Where am I?");
                player.textBox.text.add("Hockett: At the docks.");
                player.textBox.text.add("Hockett: You've been shot.");
                player.textBox.text.add("Hockett: You'll be okay.");
                player.textBox.text.add("Hudds: I don't feel so good Sonny..");
                player.textBox.text.add("Hockett: I'll get you out of this Hudds.");
                player.textBox.text.add("Hudds: Take my gun...");
                player.textBox.setExitAction(new Action() {
                    @Override
                    public void activate() {
                        hudds.rendering = false;
                        hudds.removeAction();
                        player.hud.objectiveText = "Escape to car";
                        car.unlocked = true;
                        player.giveWeapon(new M1911());
                        player.speedMod = 0.1f;

                        //<editor-fold desc="More enemies">
                        activeObjects.add(new MafiaMob(new Vector3(84, 0.2f, 15), new Vector3(0, -90, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(68, 0.2f, 5), new Vector3(0, 0, 0), new GolfClub()));
                        activeObjects.add(new MafiaMob(new Vector3(48, 0.2f, -8), new Vector3(0, 90, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(65, 0.2f, 40), new Vector3(0, -90, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(38, 0.2f, 70), new Vector3(0, 0, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(39, 0.2f, 46), new Vector3(0, 90, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(15, 0.2f, 58), new Vector3(0, 0, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(40, 0.2f, 18), new Vector3(0, -90, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(15, 0.2f, 19), new Vector3(0, 0, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(9, 0.2f, 1), new Vector3(0, 90, 0), new BaseballBat()));
                        //</editor-fold>
                    }
                });
                player.textBox.open();
            }
        }, "(RMB) to help Rico");
        activeObjects.add(hudds);

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
        }, "(RMB) Get away");
        activeObjects.add(car);

        activeObjects.add(new Threshold(new Vector3(-2.5f, 0, -16), new Vector3(2, 5, 28)));

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
