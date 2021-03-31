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
        Main.backgroundColor.set(0.043137256f, 0.043137256f, 0.19215687f, 1f);

        activeObjects.add(SoundSource.buildSoundSource(0).setMusic("Blondie - Call me.mp3").playMusic());

        final Player player =
                createPlayer(new Vector3(0, 0, -10), new Vector3(0, 0, 1), new M16(),
                        reloading, retainPlayer);

        String levelFileName = "Homicide";
        Main.levelHandler.loadFromJson(levelFileName);
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
                        activeObjects.add(new MafiaMob(new Vector3(-8, 9.1f, 16.5f), new Vector3(0, 0, 0), path, new BaseballBat()));
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
                        activeObjects.add(new MafiaMob(new Vector3(-12, 9.1f, 5f), new Vector3(0, 0, 0), path, new BaseballBat()));
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
                        activeObjects.add(new MafiaMob(new Vector3(12, 18.1f, 32), new Vector3(0, -90, 0), path, new BaseballBat()));
                        Collections.reverse(path);
                        activeObjects.add(new MafiaMob(new Vector3(-12, 18.1f, 32), new Vector3(0, -90, 0), path, new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(8, 18.1f, 14), new Vector3(0, 110, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(-8, 18.1f, 14), new Vector3(0, 70, 0), new BaseballBat()));
                        activeObjects.add(new MafiaMob(new Vector3(0, 18.1f, 4), new Vector3(0, 90, 0), new BaseballBat()));
                    }
                },
                "Kill everyone"
        );

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
                    }
                },
                "Return to car"
        );

        activeObjects.add(player);
        activeObjects.add(stageController);
        final PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(true);

        if(!reloading) {
            final LevelTitleCard titleCard = new LevelTitleCard("LEVEL 1", "HOMICIDE", 400);//todo set duration to 400
            titleCard.addAction(new Action() {
                @Override
                public void activate() {
                    activeObjects.add(new Fade(false, new Action() {
                        @Override
                        public void activate() {
                            player.giveControl();
                            pauseMenuHandler.enablePausing();
                            titleCard.rendering = false;
                            activeObjects.add(new Fade(true, null, 0.04f));
                        }
                    }, 0.02f));
                    //activeObjects.add(new MaskSelect());
                    activeObjects.add(pauseMenuHandler);
                }
            });
            activeObjects.add(titleCard);
        }
    }

    @Override
    public void reload() {
        Main.levelHandler.clearActiveObjects();
        load(true, false);
    }
}
