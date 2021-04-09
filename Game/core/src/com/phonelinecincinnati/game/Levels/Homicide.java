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
import com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Stairs;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.BaseballBat;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.GolfClub;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.M16;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Utility.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Homicide extends Level{

    Homicide(final CopyOnWriteArrayList<GameObject> activeObjects) {
        this.activeObjects = activeObjects;
        load(false, false);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        name = "Homicide";
        Main.levelHandler.levelName = name;

        Main.backgroundColor.set(0.043137256f, 0.043137256f, 0.19215687f, 1f);

        final SoundSource soundSource = SoundSource.buildSoundSource(0).setMusic("Miami Vice.mp3").playMusic();
        activeObjects.add(soundSource);

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

        stageController = new StageController("Kill everyone");

        final Threshold thresholdL = new Threshold(new Vector3(22, 0, 6), new Vector3(1, 50, 10));
        final Threshold thresholdR = new Threshold(new Vector3(-22, 0, 6), new Vector3(1, 50, 10));

        final InteractionThreshold iThresholdL = new InteractionThreshold(thresholdL,
                new Vector3(20, 0, 6), new Vector3(2, 50, 10),
                new Action() {
                    @Override
                    public void activate() {
                        stageController.currentKills -= 1;
                    }
                });
        final InteractionThreshold iThresholdR = new InteractionThreshold(thresholdL,
                new Vector3(-20, 0, 6), new Vector3(2, 50, 10),
                new Action() {
                    @Override
                    public void activate() {
                        stageController.currentKills -= 1;
                    }
                });
        iThresholdL.unlock();
        iThresholdR.unlock();

        activeObjects.add(thresholdL);
        activeObjects.add(thresholdR);

        stageController.addStage(
                new Condition() {
                    @Override
                    public boolean check() {
                        return stageController.currentKills == 2;
                    }
                },
                new Action() {
                    @Override
                    public void activate() {
                        activeObjects.remove(thresholdL);
                        activeObjects.remove(thresholdR);
                        activeObjects.add(iThresholdL);
                        activeObjects.add(iThresholdR);
                    }
                },
                "Go upstairs"
        );


        stageController.addStage(
                new Condition() {
                    @Override
                    public boolean check() {
                        return stageController.currentKills == 1;
                    }
                },
                new Action() {
                    @Override
                    public void activate() {
                        stageController.currentKills += 1;
                        activeObjects.remove(iThresholdL);
                        activeObjects.remove(iThresholdR);
                        activeObjects.add(thresholdL);
                        activeObjects.add(thresholdR);

                        ArrayList<Vector3> path = new ArrayList<Vector3>();
                        AStar.generateAStar(9);
                        activeObjects.add(new MafiaMob(new Vector3(0, 9.1f, 32), new Vector3(0, 0, 0), new BaseballBat()));
                        path.add(new Vector3(8, 9.1f, 16.5f));
                        path.add(new Vector3(-8, 9.1f, 16.5f));
                        activeObjects.add(new MafiaMob(new Vector3(-8, 9.1f, 16.5f), new Vector3(0, 0, 0), path, new GolfClub()));
                        path = new ArrayList<Vector3>();
                        path.add(new Vector3(-12, 9.1f, 25));
                        path.add(new Vector3(-12, 9.1f, 5));
                        path.add(new Vector3(12, 9.1f, 5));
                        path.add(new Vector3(12, 9.1f, 25));
                        activeObjects.add(new MafiaMob(new Vector3(12, 9.1f, 25f), new Vector3(0, 0, 0), path, new BaseballBat()));
                        path = new ArrayList<Vector3>();
                        path.add(new Vector3(12, 9.1f, 5));
                        path.add(new Vector3(12, 9.1f, 25));
                        path.add(new Vector3(-12, 9.1f, 25));
                        path.add(new Vector3(-12, 9.1f, 5));
                        activeObjects.add(new MafiaMob(new Vector3(-12, 9.1f, 5f), new Vector3(0, 0, 0), path, new GolfClub()));
                    }
                },
                "Kill everyone"
        );


        stageController.addStage(
                new Condition() {
                    @Override
                    public boolean check() {
                        return stageController.currentKills == 6;
                    }
                },
                new Action() {
                    @Override
                    public void activate() {
                        activeObjects.remove(thresholdL);
                        activeObjects.remove(thresholdR);
                        activeObjects.add(iThresholdL);
                        activeObjects.add(iThresholdR);
                    }
                },
                "Go upstairs"
        );

        stageController.addStage(
                new Condition() {
                    @Override
                    public boolean check() {
                        return stageController.currentKills == 5;
                    }
                },
                new Action() {
                    @Override
                    public void activate() {
                        stageController.currentKills += 1;
                        activeObjects.remove(iThresholdL);
                        activeObjects.remove(iThresholdR);
                        activeObjects.add(thresholdL);
                        activeObjects.add(thresholdR);

                        ArrayList<Vector3> path = new ArrayList<Vector3>();
                        AStar.generateAStar(18);
                        path.add(new Vector3(12, 18, 32));
                        path.add(new Vector3(-12, 18, 32));
                        activeObjects.add(new MafiaMob(new Vector3(12, 18.1f, 32), new Vector3(0, -90, 0), path, new GolfClub()));
                        Collections.reverse(path);
                        activeObjects.add(new MafiaMob(new Vector3(-12, 18.1f, 32), new Vector3(0, -90, 0), path, new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(8, 18.1f, 14), new Vector3(0, 110, 0), new GolfClub()));
                        activeObjects.add(new MafiaMob(new Vector3(-8, 18.1f, 14), new Vector3(0, 70, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(0, 18.1f, 4), new Vector3(0, 90, 0), new BaseballBat()));
                    }
                },
                "Kill everyone"
        );

        final PlayerCar car = new PlayerCar(new Vector3(0f, -0.1f, -14), 180, player);
        car.unlocked = false;
        stageController.addStage(
                new Condition() {
                    @Override
                    public boolean check() {
                        return stageController.currentKills == 11;
                    }
                },
                new Action() {
                    @Override
                    public void activate() {
                        activeObjects.remove(thresholdL);
                        activeObjects.remove(thresholdR);
                        activeObjects.add(iThresholdL);
                        activeObjects.add(iThresholdR);

                        car.unlocked = true;
                        soundSource.stopMusic();
                        soundSource.setMusic("El Huervo - Crush.mp3");
                        soundSource.playMusic();
                    }
                },
                "Return to car"
        );

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
        }, "Leave the scene");
        activeObjects.add(car);

        activeObjects.add(player);
        activeObjects.add(stageController);
        final PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(true);

        if(!reloading) {
            final LevelTitleCard titleCard = new LevelTitleCard("LEVEL 1", name, 400);
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
                    activeObjects.add(pauseMenuHandler);
                }
            });
            activeObjects.add(titleCard);
        } else {
            Main.levelHandler.score.reset();
            pauseMenuHandler.enablePausing();
        }
    }

    @Override
    public void reload() {
        Main.levelHandler.clearActiveObjects();
        load(true, false);
    }
}
