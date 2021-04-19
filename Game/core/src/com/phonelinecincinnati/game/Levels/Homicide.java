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
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.PlayerCar;
import com.phonelinecincinnati.game.GameObjects.Objects.Threshold;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.BaseballBat;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.GolfClub;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.M1911;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Homicide extends Level{
    private SoundSource music;

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

        if(!reloading) {
            music = SoundSource.buildSoundSource(0).setMusic("Jan Hammer - Miami Vice.mp3").playMusic();
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

        final InteractiveModel documents = new InteractiveModel(new Vector3(-2, 18.12f,-0.5f), -90, ModelName.PhoneTable1);
        documents.rendering = false;
        documents.unlocked = false;
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
                        documents.unlocked = true;
                        music.stopMusic();
                        music.setMusic("El Huervo - Crush.mp3");
                        music.playMusic();
                    }
                },
                "Check documents"
        );

        final PlayerCar car = new PlayerCar(new Vector3(0f, -0.1f, -14), 180, player);
        car.unlocked = false;
        documents.setAction(new Action() {
            @Override
            public void activate() {
                player.lookAt(documents.position.x, documents.position.z, -35f);
                player.textBox.text.clear();
                player.textBox.text.add("Hockett: Hmm...");
                player.textBox.text.add("Hockett: ...");
                player.textBox.text.add("Hockett: '18 Sept, Miami Dock, 1x Colt Commander, 2x Colt M1911, 1x SIG-Sauer P220'");
                player.textBox.text.add("Hockett: ...");
                player.textBox.text.add("*CLICK*");
                player.textBox.text.add("*DIAL TONE");
                player.textBox.text.add("Hudds: You okay Hockett?");
                player.textBox.text.add("Hockett: Yeah I'm fine.");
                player.textBox.text.add("Hockett: It seems like they're planning on taking in a shipment in two days.");
                player.textBox.text.add("Hockett: It's quite a big one, I reckon Rogerigo will be there to oversee it.");
                player.textBox.text.add("Hudds: Thanks man.");
                player.textBox.text.add("Hudds: Did you take care of everyone?");
                player.textBox.text.add("Hockett: ...");
                player.textBox.text.add("Hockett: Yes.");
                player.textBox.text.add("Hudds: We're going to look into staging a raid.");
                player.textBox.text.add("Hudds: You should go home and rest.");
                player.textBox.text.add("Hockett: See you Rico.");
                player.textBox.text.add("Hudds: Bye Hockett.");
                player.textBox.setExitAction(new Action() {
                    @Override
                    public void activate() {
                        car.unlocked = true;
                        activeObjects.remove(thresholdL);
                        activeObjects.remove(thresholdR);
                        activeObjects.add(iThresholdL);
                        activeObjects.add(iThresholdR);
                    }
                });
                player.textBox.open();
                player.hud.objectiveText = "Return to car";
            }
        }, "(RMB) Check");
        activeObjects.add(documents);

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
        }, "(RMB) Leave the scene");
        activeObjects.add(car);

        //Random thresholds to stop player from leaving play area
        //Front top window
        activeObjects.add(new Threshold(new Vector3(-14, 16, -2), new Vector3(28 , 9, 0.5f)));

        //Side top windows and sides of street
        activeObjects.add(new Threshold(new Vector3(-16.5f, 0, -12), new Vector3(1, 24, 22)));
        activeObjects.add(new Threshold(new Vector3(15.5f, 0, -12), new Vector3(1, 24, 22)));

        //Inline with car to stop playing crossing road
        activeObjects.add(new Threshold(new Vector3(-18, 0, -12), new Vector3(40, 10, 2)));

        activeObjects.add(stageController);
        final PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(false);
        activeObjects.add(player);

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
