package com.phonelinecincinnati.game.Levels.Old;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.GameObjects.Objects.*;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveSolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.PlayerCar;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Fade;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.ForcedController;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.Levels.Level;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Models.TextureName;

import java.util.concurrent.CopyOnWriteArrayList;

class House extends Level {

    House(final CopyOnWriteArrayList<GameObject> list, int currentProgression) {
        /*
        final SoundSource soundSource = SoundSource.buildSoundSource(1).setMusic("DeepCover.mp3").playMusic();
        list.add(soundSource);
        Main.backgroundColor.set(1, 0, 1, 1);

        final Player player = new Player(4, 3.6f, 7f);

        //<editor-fold desc="Floors">
        //Floor
        list.add(new Plane(new Vector3(0, -0.05f, 0), new Vector3(18.5f, 0.1f, 14.2f), TextureName.Wood));
        list.add(new Plane(new Vector3(0, -0.05f, 14.2f), new Vector3(16f, 0.1f, 14.2f), TextureName.BrownGrayKitchenTile));
        list.add(new Plane(new Vector3(16f, -0.05f, 14.2f), new Vector3(12.4f, 0.1f, 6.5f), TextureName.GrayCarpet));
        //<editor-fold desc="Bathroom Floor">
        list.add(new Plane(new Vector3(18.5f, -0.05f, 0f), new Vector3(9.9f, 0.1f, 14.2f), TextureName.BrownSmallTiles));
        list.add(new Plane(new Vector3(20.65f, -0.049f, 6f), new Vector3(6.45f, 0.1f, 5f), TextureName.BrownTinyTiles));
        list.add(new Plane(new Vector3(22.1f, -0.049f, 11f), new Vector3(3.5f, 0.1f, 3.2f), TextureName.BrownTinyTiles));
        //</editor-fold>
        list.add(new Plane(new Vector3(28.4f, -0.05f, 14.2f), new Vector3(12.4f, 0.1f, 19.5f), TextureName.Wood));
        //<editor-fold desc="Landing Floor">
        list.add(new Plane(new Vector3(17.3f, -0.05f, 20.7f), new Vector3(11f, 0.1f, 25.5f), TextureName.DarkGreenCarpet));
        list.add(new Plane(new Vector3(16f, -0.05f, 20.7f), new Vector3(1.3f, 0.1f, 15f), TextureName.DarkGreenCarpet));
        list.add(new Plane(new Vector3(0, -0.05f, 28.2f), new Vector3(16f, 0.1f, 7.5f), TextureName.DarkGreenCarpet));
        list.add(new Plane(new Vector3(0, -4.5f, 34.85f), new Vector3(6.4f, 0.1f, 9.8f), TextureName.DarkGreenCarpet));
        list.add(new Plane(new Vector3(0, -9f, 30.85f), new Vector3(20f, 0.1f, 14.8f), TextureName.DarkGreenCarpet));
        //</editor-fold>
        //</editor-fold>
        //Roof
        list.add(new Plane(new Vector3(0, 6.05f, 0), new Vector3(45, 0.1f, 50), TextureName.Concrete));

        //<editor-fold desc="Vertical Walls">
        //Bedroom
        list.add(new SolidWall(new Vector3(0, 0, 3.4f), new Vector3(0.5f, 6f, 3.8f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(0, 0, 10.4f), new Vector3(0.5f, 6, 7f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(18.5f, 0, 0f), new Vector3(0.5f, 6, 14.2f), TextureName.Concrete1));
        //Kitchen
        list.add(new SolidWall(new Vector3(0, -9f, 20.6f), new Vector3(0.5f, 15f, 24f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(15.75f, 0, 20.7f), new Vector3(0.5f, 6, 7.85f), TextureName.Concrete1));
        //Bathroom
        list.add(new SolidWall(new Vector3(28.4f, 0, 0f), new Vector3(0.5f, 6, 14.2f), TextureName.Concrete1));
        //Living room
        list.add(new SolidWall(new Vector3(28.4f, 0, 20.7f), new Vector3(0.5f, 6, 24f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(40.8f, 0, 14.2f), new Vector3(0.5f, 6, 19.5f), TextureName.Concrete1));
        //</editor-fold>
        //<editor-fold desc="Horizontal Walls">
        //Bedroom
        list.add(new SolidWall(new Vector3(0f, 0, 0), new Vector3(28.4f, 6, 0.5f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(0f, 0, 14.2f), new Vector3(9.9f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(13.3f, 0, 14.2f), new Vector3(8.8f, 6, 0.5f), TextureName.Concrete1));
        //kitchen
        list.add(new SolidWall(new Vector3(0f, 0, 28.4f), new Vector3(15.6f, 6, 0.5f), TextureName.Concrete1));
        //hallway
        list.add(new SolidWall(new Vector3(15.75f, 0, 20.7f), new Vector3(6.35f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(25.5f, 0, 20.7f), new Vector3(2.9f, 6, 0.5f), TextureName.Concrete1));
        //Living room
        list.add(new SolidWall(new Vector3(25.5f, 0, 14.2f), new Vector3(15.3f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(28.4f, 0, 33.7f), new Vector3(12.4f, 6, 0.5f), TextureName.Concrete1));
        //Landing
        list.add(new SolidWall(new Vector3(0f, -4.5f, 35.5f), new Vector3(17.5f, 4.5f, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(5.8f, -4.5f, 40.2f), new Vector3(11.5f, 4.5f, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(0f, -9f, 44.7f), new Vector3(22.1f, 15, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(25.5f, 0, 44.7f), new Vector3(3f, 6, 0.5f), TextureName.Concrete1));
        //</editor-fold>

        list.add(new Model(new Vector3(15.65f, 0, 20.6f), 0, ModelName.Pillar));
        list.add(new Model(new Vector3(28.5f, 0, 20.6f), 0, ModelName.Pillar));
        list.add(new Model(new Vector3(15.85f, 0, 28.5f), 0, ModelName.Pillar));

        //<editor-fold desc="Bedroom Objects">
        SolidModel bed2 = new SolidModel(new Vector3(3.5f, 0, 3.5f), 90, ModelName.Bed2);
        BoundingBox boundingBox = bed2.getBoundingBox();
        boundingBox.set(new Vector3(0, 0, 0), new Vector3(boundingBox.getWidth(), (boundingBox.getHeight()-(boundingBox.getHeight()/1.8f)), boundingBox.getDepth()));
        list.add(bed2);
        list.add(new SolidModel(new Vector3(1.8f, 0, 6f), 180, ModelName.Table));
        list.add(new SolidModel(new Vector3(1.6f, 0, 8.4f), 180, ModelName.TableLamp));
        SolidModel bed = new SolidModel(new Vector3(3.5f, 0, 10.8f), 90, ModelName.Bed);
        boundingBox = bed.getBoundingBox();
        boundingBox.set(new Vector3(0, 0, 0), new Vector3(boundingBox.getWidth(), (boundingBox.getHeight()-(boundingBox.getHeight()/3f)), boundingBox.getDepth()));
        list.add(bed);
        list.add(new SolidModel(new Vector3(17f, 0, 7.2f), 180, ModelName.TV));
        list.add(new SolidModel(new Vector3(15f, 0, 7.2f), 180, ModelName.NES));

        //list.add(new WeaponPickUp(new Vector3(4, 1.1f, 3.72f), new Melee(WeaponType.Bat)));
        //list.add(new WeaponPickUp(new Vector3(14.23f,0,3.72f), new Melee(WeaponType.GolfClub)));

        list.add(new SolidWall(new Vector3(0, 0, 0f), new Vector3(0.5f, 2.1f, 3.4f), TextureName.Brick));
        list.add(new SolidModel(new Vector3(0f, 2, 1.8f), 0, ModelName.LargeWindow));
        list.add(new Wall(new Vector3(0, 4.5f, 0f), new Vector3(0.5f, 1.5f, 3.4f), TextureName.Brick));

        list.add(new SolidWall(new Vector3(0, 0, 7.2f), new Vector3(0.5f, 2.1f, 3.2f), TextureName.Brick));
        list.add(new SolidModel(new Vector3(0f, 2, 8.8f), 0, ModelName.LargeWindow));
        list.add(new Wall(new Vector3(0, 4.5f, 7.2f), new Vector3(0.5f, 1.5f, 3.2f), TextureName.Brick));

        list.add(new Door(new Vector3(10.05f, -0.04f, 14.2f), true, false, ModelName.Door));

        list.add(new GameDecal(new Vector3(8.4f, 0.01f, 5.9f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt2));
        list.add(new GameDecal(new Vector3(14.7f, 0.01f, 11.6f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt3));
        list.add(new GameDecal(new Vector3(18.1f, 3, 10.6f), 3, 4, new Vector3(0, -90, -2), TextureName.MidnightAnimal));
        //</editor-fold>
        //<editor-fold desc="Kitchen Objects">
        list.add(new SolidModel(new Vector3(5f, 0, 19f), 90, ModelName.KitchenTable));
        list.add(new SolidModel(new Vector3(4f, 0, 16f), -85, ModelName.KitchenChair));
        list.add(new SolidModel(new Vector3(3.6f, 0, 21.5f), 110, ModelName.KitchenChair));
        list.add(new SolidModel(new Vector3(14.3f, 0, 27f), 90, ModelName.KitchenFridge));
        list.add(new SolidModel(new Vector3(11.5f, 0, 27f), 90, ModelName.KitchenSink));
        list.add(new SolidModel(new Vector3(8.4f, 0, 27f), 90, ModelName.KitchenCabinet));
        list.add(new SolidModel(new Vector3(5.3f, 0, 27f), 90, ModelName.KitchenCabinetOverhead));
        list.add(new SolidModel(new Vector3(2.2f, 0, 27f), 90, ModelName.KitchenCabinetOverhead));

        list.add(new SolidWall(new Vector3(0, 0, 17.4f), new Vector3(0.5f, 2.1f, 3.2f), TextureName.Brick));
        list.add(new SolidModel(new Vector3(0f, 2, 19.0f), 0, ModelName.LargeWindow));
        list.add(new Wall(new Vector3(0, 4.5f, 17.4f), new Vector3(0.5f, 1.5f, 3.2f), TextureName.Brick));
        //</editor-fold>
        final Door lockedDoor = new Door(new Vector3(22.25f, -0.04f, 20.7f), true, true, ModelName.Door);
        //<editor-fold desc="Hallway Objects">
        list.add(new Model(new Vector3(18f, 0, 19f), 120, ModelName.BloodyJacket));
        list.add(new Model(new Vector3(20.93f,0,18.64f), 180, ModelName.GravelPieces));
        list.add(new Model(new Vector3(23.8f, -0.04f, 19.7f), 180, ModelName.DoorMat));

        list.add(lockedDoor);

        list.add(new GameDecal(new Vector3(24.1f, 0.01f, 16.9f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt3));
        //</editor-fold>
        //<editor-fold desc="Bathroom Objects">
        list.add(new Model(new Vector3(20.5f, 0, 13f), -70, ModelName.MiamiShirt));
        list.add(new SolidModel(new Vector3(24.8f, 0f, 1.5f), -90, ModelName.BathTub));
        list.add(new Model(new Vector3(20f, 2.5f, 0.7f), -90, ModelName.WallShower));
        list.add(new SolidModel(new Vector3(19.5f, 0f, 10f), 90, ModelName.Sink));

        list.add(new Door(new Vector3(22.24f, -0.04f, 14.2f), true, false, ModelName.Door));

        list.add(new GameDecal(new Vector3(27f, 0.01f, 12f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt1));
        list.add(new GameDecal(new Vector3(21.4f, 0.02f, 6.8f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt2));
        list.add(new GameDecal(new Vector3(20.1f,0.01f,1.64f), 2, 2, new Vector3(90, 0, 0), TextureName.ShowerDrain));
        //</editor-fold>
        //<editor-fold desc="Living room Objects">
        list.add(new SolidModel(new Vector3(36, 0, 32), 180, ModelName.GreenSofa));
        final InteractiveSolidModel phoneTable = new InteractiveSolidModel(new Vector3(31.5f, 0, 32.4f), 90, ModelName.PhoneTable1);
        phoneTable.setAction(new Action() {
            @Override
            public void activate() {
                final SoundSource soundSource = SoundSource.buildSoundSource(0).setSound("Misc/Phone.wav");
                soundSource.playSound();
                phoneTable.updateModel(ModelName.PhoneTable2);
                player.lookAt(phoneTable.position.x, phoneTable.position.z, -47f);
                player.textBox.text.clear();
                player.textBox.text.add("You have one new message! *BEEP*");
                player.textBox.text.add("Hi this is 'Tim' at the bakery.");
                player.textBox.text.add("Those cookies that you ordered should be delivered by now...");
                player.textBox.text.add("A list of ingredients are included... make sure that you read them carefully!");
                player.textBox.text.add("...");
                player.textBox.text.add("*CLICK*");
                player.textBox.setExitAction(new Action() {
                    @Override
                    public void activate() {
                        soundSource.playSound();
                        phoneTable.updateModel(ModelName.PhoneTable3);
                        lockedDoor.locked = false;
                        phoneTable.removeAction();
                    }
                });
                player.textBox.open();
            }
        }, "(RMB) to check recording");
        list.add(phoneTable);
        list.add(new SolidModel(new Vector3(36, 0, 28), 0, ModelName.LivingRoomTable));
        list.add(new Model(new Vector3(35, 0, 21), 0, ModelName.MaroonCarpet));

        list.add(new GameDecal(new Vector3(37f, 0.15f, 18.3f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt2));
        list.add(new GameDecal(new Vector3(35f, 0.15f, 23f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt1));
        list.add(new GameDecal(new Vector3(29.6f, 0.01f, 30.6f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt3));
        list.add(new GameDecal(new Vector3(39.4f, 0.01f, 29.7f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt1));
        //</editor-fold>
        final InteractionThreshold interactionThreshold = new InteractionThreshold(player, new Vector3(0f, 0f, 35.5f), new Vector3(17.5f, 6f, 5f), new Action() {
            @Override
            public void activate() {
                player.lookAt(0, 38f, -25);
                player.takeControl(new ForcedController() {
                    @Override
                    public void update() {
                        player.position.mulAdd(Main.camera.direction, 0.05f);
                        Main.camera.position.set(player.position);
                        list.add(new Fade(false, new Action() {
                            @Override
                            public void activate() {
                                player.giveControl();
                                list.clear();
                                list.add(soundSource);
                                next(list, player);
                            }
                        }, 0.005f));
                    }
                });
            }
        });
        //<editor-fold desc="Landing Objects">
        final InteractiveModel box = new InteractiveModel(new Vector3(23.9f, 0, 27.5f), 0, ModelName.Box);
        box.setAction(new Action() {
            @Override
            public void activate() {
                final SoundSource soundSource = SoundSource.buildSoundSource(0).setSound("Misc/BoxTear.wav");
                soundSource.playSound();
                box.updateModel(ModelName.BoxOpen);
                player.lookAt(box.position.x, box.position.z, -60f);
                player.textBox.image = Main.modelHandler.textures.get(TextureName.MaskLetter);
                player.textBox.text.clear();
                player.textBox.text.add("The target is a briefcase. Discretion is of essence.");
                player.textBox.text.add("Leave target at point F32, inside the dumpster.");
                player.textBox.text.add("Failure is not an option. We'll be watching you.");
                player.textBox.setExitAction(new Action() {
                    @Override
                    public void activate() {
                        player.textBox.image = null;
                        box.removeAction();
                        interactionThreshold.unlock();
                    }
                });
                player.textBox.open();
            }
        }, "(RMB) to take contents");
        list.add(box);

        list.add(new Door(new Vector3(22.25f, -0.04f, 44.7f), true, true, ModelName.Door));

        list.add(new SolidModel(new Vector3(11.35f, 0, 35.5f), 180, ModelName.PlayerStairsP1));
        list.add(new SolidModel(new Vector3(2.65f, 0, 35.45f), 180, ModelName.PlayerStairsP2));
        list.add(new SolidModel(new Vector3(17.3f, 0, 42.65f), 180, ModelName.PlayerStairsP3));
        list.add(new Model(new Vector3(11.35f, -9.1f, 40.75f), 180, ModelName.PlayerStairsP4));

        list.add(interactionThreshold);

        list.add(new Model(new Vector3(26.54f,0f,23.53f), 80, ModelName.TrashPiece));
        list.add(new Model(new Vector3(23.14f,0f,43.58f), 80, ModelName.TrashPiece));
        list.add(new Model(new Vector3(19.33f,0f,41.37f), 180, ModelName.GravelPieces));
        list.add(new Model(new Vector3(2.98f,-4.5f,37.5f), 180, ModelName.GravelPieces));
        list.add(new GameDecal(new Vector3(16.69f,0.01f,31.24f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt2));
        list.add(new GameDecal(new Vector3(22.33f,0.01f,34.11f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt1));
        list.add(new GameDecal(new Vector3(26.36f,0.01f,42.7f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt3));
        list.add(new GameDecal(new Vector3(2.4f,-4.4f,42.33f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt3));
        //</editor-fold>

        AStar.generateAStar();

        list.add(player);
        list.add(new Fade(true, new Action() {
            @Override
            public void activate() {
                player.giveControl();
            }
        }, 0.005f));
        list.add(new PauseMenuHandler(true));
    }

    private void next(final CopyOnWriteArrayList<GameObject> list, final Player player) {
        player.position.set(18.23f,3.6f,42.45f);
        Main.camera.position.set(player.position);
        player.lookAt(19, 42, 0);

        //<editor-fold desc="Facade">
        list.add(new Model(new Vector3(11.35f, 9.1f, 35.5f), 180, ModelName.PlayerStairsP1));
        list.add(new Model(new Vector3(2.65f, 9.1f, 35.45f), 180, ModelName.PlayerStairsP2));
        list.add(new Model(new Vector3(17.3f, 9.1f, 42.65f), 180, ModelName.PlayerStairsP3));
        list.add(new SolidModel(new Vector3(11.35f, 0, 40.75f), 180, ModelName.PlayerStairsP4));

        list.add(new SolidWall(new Vector3(0f, 0, 0), new Vector3(28.4f, 15f, 0.5f), TextureName.Brick));
        list.add(new Wall(new Vector3(0f, 9, 14.2f), new Vector3(9.9f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(0, 0, 0f), new Vector3(0.5f, 11.1f, 3.4f), TextureName.Brick));
        list.add(new Model(new Vector3(0f, 11, 1.8f), 0, ModelName.LargeWindow));
        list.add(new SolidWall(new Vector3(0, 13.5f, 0f), new Vector3(0.5f, 1.5f, 3.4f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(0, 0, 7.2f), new Vector3(0.5f, 11.1f, 3.2f), TextureName.Brick));
        list.add(new Model(new Vector3(0f, 11, 8.8f), 0, ModelName.LargeWindow));
        list.add(new SolidWall(new Vector3(0, 13.5f, 7.2f), new Vector3(0.5f, 1.5f, 3.2f), TextureName.Brick));

        list.add(new Wall(new Vector3(0f, 9, 28.4f), new Vector3(15.6f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(0, 0, 17.4f), new Vector3(0.5f, 11.1f, 3.2f), TextureName.Brick));
        list.add(new Model(new Vector3(0f, 11, 19.0f), 0, ModelName.LargeWindow));
        list.add(new SolidWall(new Vector3(0, 13.5f, 17.4f), new Vector3(0.5f, 1.5f, 3.2f), TextureName.Brick));

        list.add(new SolidWall(new Vector3(0, 0, 3.4f), new Vector3(0.5f, 15f, 3.8f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(0, 0, 10.4f), new Vector3(0.5f, 15f, 7f), TextureName.Brick));
        list.add(new Wall(new Vector3(0, 6f, 20.6f), new Vector3(0.5f, 9f, 25f), TextureName.Brick));

        list.add(new Plane(new Vector3(0, 15.05f, 0), new Vector3(30, 0.1f, 45), TextureName.Concrete));

        for(int i = 1; i < 50; i++) {
            float yAdjust = 6*i;
            list.add(new Wall(new Vector3(0f, 9+yAdjust, 0), new Vector3(28.4f, 6, 0.5f), TextureName.Brick));
            list.add(new Wall(new Vector3(0f, 9+yAdjust, 14.2f), new Vector3(9.9f, 6, 0.5f), TextureName.Concrete1));
            list.add(new Wall(new Vector3(0f, 9+yAdjust, 0f), new Vector3(0.5f, 2.1f, 3.4f), TextureName.Brick));
            list.add(new Model(new Vector3(0f, 11+yAdjust, 1.8f), 0, ModelName.LargeWindow));
            list.add(new Wall(new Vector3(0, 13.5f+yAdjust, 0f), new Vector3(0.5f, 1.5f, 3.4f), TextureName.Brick));
            list.add(new Wall(new Vector3(0, 9+yAdjust, 7.2f), new Vector3(0.5f, 2.1f, 3.2f), TextureName.Brick));
            list.add(new Model(new Vector3(0f, 11+yAdjust, 8.8f), 0, ModelName.LargeWindow));
            list.add(new Wall(new Vector3(0, 13.5f+yAdjust, 7.2f), new Vector3(0.5f, 1.5f, 3.2f), TextureName.Brick));

            list.add(new Wall(new Vector3(0f, 9+yAdjust, 28.4f), new Vector3(15.6f, 6, 0.5f), TextureName.Concrete1));
            list.add(new Wall(new Vector3(0, 9+yAdjust, 17.4f), new Vector3(0.5f, 2.1f, 3.2f), TextureName.Brick));
            list.add(new Model(new Vector3(0f, 11+yAdjust, 19.0f), 0, ModelName.LargeWindow));
            list.add(new Wall(new Vector3(0, 13.5f+yAdjust, 17.4f), new Vector3(0.5f, 1.5f, 3.2f), TextureName.Brick));

            list.add(new Wall(new Vector3(0, 9+yAdjust, 3.4f), new Vector3(0.5f, 6f, 3.8f), TextureName.Brick));
            list.add(new Wall(new Vector3(0, 9+yAdjust, 10.4f), new Vector3(0.5f, 6f, 7f), TextureName.Brick));
            list.add(new Wall(new Vector3(0, 9+yAdjust, 20.6f), new Vector3(0.5f, 6f, 25f), TextureName.Brick));

            list.add(new Plane(new Vector3(0, 12.0f+yAdjust, 0), new Vector3(30, 0.1f, 45), TextureName.Concrete));
        }
        //</editor-fold>

        //<editor-fold desc="Vertical Walls">
        list.add(new SolidWall(new Vector3(0, 0f, 20.6f), new Vector3(0.5f, 9f, 6f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(0, 0f, 30f), new Vector3(0.5f, 9f, 15.6f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(17.5f, 0f, 35.5f), new Vector3(0.5f, 9f, 4.7f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(30, 0f, 20.65f), new Vector3(0.5f, 6f, 10f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(30, 0f, 34f), new Vector3(0.5f, 6f, 11f), TextureName.Concrete1));
        //</editor-fold>
        //<editor-fold desc="Horizontal Walls">
        list.add(new SolidWall(new Vector3(0f, 0f, 35.5f), new Vector3(17.5f, 9f, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(5.8f, 0f, 40.2f), new Vector3(11.5f, 9f, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(0f, 0f, 44.7f), new Vector3(30f, 15f, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(0f, 0f, 20.61f), new Vector3(14.9f, 6f, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(18.2f, 0f, 20.61f), new Vector3(14.9f, 6f, 0.5f), TextureName.Concrete1));
        //</editor-fold>

        PlayerCar car = new PlayerCar(new Vector3(-15f, 0f, 28.32f), -90, player);
        car.setAction(new Action() {
            @Override
            public void activate() {
                player.position.set(player.position.x, player.position.y, 28.32f);
                list.add(new Fade(false, new Action() {
                    @Override
                    public void activate() {
                        for(GameObject object : list) {
                            object.dispose();
                        }
                        Main.controlHandler.resetPlayer();
                        Main.levelHandler.loadLevel1();
                    }
                }, 0.008f));
                player.takeControl(new ForcedController() {
                    @Override
                    public void update() {
                        if(player.position.y != 0) {
                            if(player.position.x > -14) {
                                player.position.add(-0.05f, 0, 0);
                            } else {
                                player.position.set(-14, player.position.y, player.position.z);
                            }
                            if(player.position.y > 2.7f) {
                                player.position.add(0, -0.02f, 0);
                            } else {
                                player.position.set( player.position.x, 2.7f, player.position.z);
                            }

                            if(player.position.x == -14 && player.position.y == 2.7f) {
                                player.position.set(0, 0, 0);
                                SoundSource.buildSoundSource(1).setSound("Misc/CarEngine.wav").playSound();
                            } else {
                                Main.camera.position.set(player.position);
                                player.lookAt(-14, 30,0);
                            }
                        }
                    }
                });
            }
        }, "Get the briefcase");
        list.add(car);

        list.add(new Door(new Vector3(30f, -0.04f, 33.9f), false, true, ModelName.Door));
        list.add(new Door(new Vector3(15f, -0.04f, 20.61f), true, true, ModelName.Door));
        list.add(new Door(new Vector3(0f, -0.04f, 29.86f), false, false, ModelName.Door));

        list.add(new GameDecal(new Vector3(26.9f,0.01f,22.15f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt1));
        list.add(new GameDecal(new Vector3(10.36f,0.01f,30f), 2, 2, new Vector3(90, 0, 0), TextureName.Dirt2));

        list.add(new Model(new Vector3(25.67f,0f,31.82f), 180, ModelName.GravelPieces));
        list.add(new Model(new Vector3(4.23f,0f,25.23f), 180, ModelName.TrashPiece));

        list.add(new Plane(new Vector3(0, -0.05f, 20.6f), new Vector3(30f, 0.1f, 24f), TextureName.DarkGreenCarpet));

        list.add(new Model(new Vector3(17.6f,0,35.4f), 0, ModelName.Pillar));
        list.add(new Model(new Vector3(17.6f,0f,40.3f), 0, ModelName.Pillar));
        //roof
        list.add(new Plane(new Vector3(0, 6.05f, 20.6f), new Vector3(17.75f, 0.1f, 15f), TextureName.Concrete));
        list.add(new Plane(new Vector3(17.75f, 6.05f, 20.6f), new Vector3(12.25f, 0.1f, 24f), TextureName.Concrete));

        list.add(player);
        list.add(new Fade(true, null, 0.005f));
        list.add(new PauseMenuHandler(true));
         */
    }

    @Override
    public void load(boolean reloading, boolean retainPlayer) {

    }
    @Override
    public void reload() {

    }
}
