package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.*;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;
import com.phonelinecincinnati.game.GameObjects.Objects.Gore.BloodSplat;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.SolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.Melee;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.WeaponType;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Fade;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.LevelTitleCard;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;
import com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Direction;
import com.phonelinecincinnati.game.GameObjects.Objects.Vertical.Stairs;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Models.TextureName;

import java.util.concurrent.CopyOnWriteArrayList;

public class Level1 extends Level {
    public Level1(final CopyOnWriteArrayList<GameObject> list) {
        Main.backgroundColor.set(1, 0, 1, 1);
        Main.modelHandler.setModelsForLevel1();

        list.add(SoundSource.buildSoundSource(0).setMusic("M.O.O.N. - Paris.mp3").playMusic());

        final Player player = new Player(25, 9.6f, 0);
        player.takeControl(null);

        list.add(new Plane(new Vector3(0, 6.05f, -10), new Vector3(51, 0.1f, 10), TextureName.FakeLimbo));
        //<editor-fold desc="Platform1">
        list.add(new SolidModel(new Vector3(15.4f, 0, 2.48f), 180, ModelName.SubwayBench3));
        list.add(new SolidModel(new Vector3(15.4f, 0, 6.5f), 180, ModelName.SubwayBench2));

        //Wall
        list.add(new Model(new Vector3(20.5f, 6, -0.1f), 0, ModelName.Pillar));
        list.add(new SolidWall(new Vector3(0, 0, 0), new Vector3(20.4f, 12, 0.5f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(0, 0, 0), new Vector3(0.5f, 12, 49f), TextureName.Brick));

        list.add(new Wall(new Vector3(16.6f, 0, 0), new Vector3(0.5f, 6, 9f), TextureName.Concrete1));

        //Floor
        list.add(new Plane(new Vector3(0, 0.05f, 0), new Vector3(3f, -0.1f, 49f), TextureName.DarkGreenDiamonds));
        float yPos = 6, yAddition = 12;
        for(int i = 0; i < 4; i++) {
            list.add(new Model(new Vector3(6.9f, -0.05f, yPos), 0, ModelName.SubwayTracks));
            yPos+=yAddition;
        }
        list.add(new Plane(new Vector3(10.7f, -0.05f, 0), new Vector3(6f, 0.1f, 9f), TextureName.DarkGreenDiamonds));
        list.add(new Plane(new Vector3(10.7f, -0.05f, 9f), new Vector3(4.54f, 0.1f, 40f), TextureName.DarkGreenDiamonds));
        list.add(new Threshold(new Vector3(3, 0f, 0), new Vector3(7.7f, 6f, 40f)));
        //</editor-fold>
        //<editor-fold desc="Entrance">
        float stairX = 20.4f;
        for(int i = 0; i < 3; i++) {
            list.add(new Stairs(false, Direction.North, new Vector3(stairX, 6.11f, -0.24f), 11, 0.612f));
            stairX += 3f;
        }

        list.add(new SolidModel(new Vector3(16.5f, 0, 12.08f), 0, ModelName.SubwayBench3));
        list.add(new SolidModel(new Vector3(16.5f, 0, 16.1f), 0, ModelName.SubwayBench3));
        list.add(new SolidModel(new Vector3(34f, 0, 11.48f), 180, ModelName.SubwayBench2));
        list.add(new SolidModel(new Vector3(34f, 0, 16.1f), 180, ModelName.SubwayBench1));

        list.add(new MafiaMob(new Vector3(25f, 0, 11), new Vector3(0, 0, 0), new Melee(WeaponType.Bat), player));

        //wall
        list.add(new SolidWall(new Vector3(15f, 0, 9), new Vector3(8.8f, 12, 0.5f), TextureName.Concrete1));
        list.add(new Door(new Vector3(23.9f, 0f, 9f), true, false, ModelName.Door));
        list.add(new SolidWall(new Vector3(23.8f, 6f, 9f), new Vector3(3.3f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(27.1f, 0f, 9f), new Vector3(8.15f, 12, 0.5f), TextureName.Concrete1));

        list.add(new SolidWall(new Vector3(20.4f, 0, 0), new Vector3(0.5f, 12, 9f), TextureName.Concrete1));
        list.add(new Model(new Vector3(15.15f, 0, 8.9f), 0, ModelName.Pillar));
        list.add(new SolidWall(new Vector3(15.25f, 0, 9), new Vector3(0.4f, 6, 9.4f), TextureName.Concrete1));
        list.add(new Model(new Vector3(15.25f, 0, 18.4f), 0, ModelName.Pillar));

        list.add(new SolidWall(new Vector3(30.42f, 0, 0), new Vector3(0.5f, 12, 9f), TextureName.Concrete1));
        list.add(new Model(new Vector3(35.26f, 0, 8.9f), 0, ModelName.Pillar));
        list.add(new SolidWall(new Vector3(35.25f, 0, 9), new Vector3(0.4f, 6, 9.5f), TextureName.Concrete1));
        list.add(new Model(new Vector3(35.25f, 0, 18.4f), 0, ModelName.Pillar));

        //Floor
        list.add(new Plane(new Vector3(20.4f, -0.05f, 0), new Vector3(14.2f, 0.1f, 9), TextureName.DarkGreenDiamonds));
        list.add(new Plane(new Vector3(15.24f, -0.05f, 9), new Vector3(20, 0.1f, 16.7f), TextureName.DarkBrownCheckeredTiles));
        //</editor-fold>
        //<editor-fold desc="Bathroom">
        list.add(new MafiaMob(new Vector3(28f, 0, 36), new Vector3(0, -90, 0), new Melee(WeaponType.Bat), player));

        //wall
        list.add(new SolidWall(new Vector3(15.25f, 0, 25.5f), new Vector3(0.4f, 6, 9.4f), TextureName.Concrete1));
        list.add(new Model(new Vector3(15.25f, 0, 25.5f), 0, ModelName.Pillar));

        list.add(new SolidWall(new Vector3(21.25f, 0, 25.7f), new Vector3(0.5f, 6, 25f), TextureName.Concrete1));
        list.add(new Model(new Vector3(21.15f, 0, 25.6f), 0, ModelName.Pillar));
        list.add(new SolidWall(new Vector3(35.25f, 0, 25.7f), new Vector3(0.5f, 6, 25f), TextureName.Concrete1));
        list.add(new Model(new Vector3(35.35f, 0, 25.6f), 0, ModelName.Pillar));

        list.add(new SolidWall(new Vector3(21.25f, 0, 25.7f), new Vector3(5f, 6, 0.5f), TextureName.Concrete1));
        list.add(new Door(new Vector3(26.35f, 0f, 25.7f), true, false, ModelName.Door));
        list.add(new SolidWall(new Vector3(29.6f, 0, 25.7f), new Vector3(5.65f, 6, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(21.25f, 0, 38f), new Vector3(14f, 6, 0.5f), TextureName.Concrete1));

        //Floor
        list.add(new Plane(new Vector3(21.24f, -0.05f, 27-1.3f), new Vector3(14, 0.1f, 16f), TextureName.BlackWhiteCheckeredTiles));
        //</editor-fold>
        //<editor-fold desc="Platform2">
        list.add(new SolidModel(new Vector3(35f, 0, 2.48f), 0, ModelName.SubwayBench3));
        list.add(new SolidModel(new Vector3(35f, 0, 6.5f), 0, ModelName.SubwayBench2));

        list.add(new MafiaMob(new Vector3(38f, 0, 20), new Vector3(0, 180, 0), new Melee(WeaponType.GolfClub), player));

        //Wall
        list.add(new Model(new Vector3(30.3f, 6, -0.1f), 0, ModelName.Pillar));
        list.add(new SolidWall(new Vector3(30.5f, 0, 0), new Vector3(20.4f, 12, 0.5f), TextureName.Brick));
        list.add(new SolidWall(new Vector3(50.9f, 0, 0), new Vector3(0.5f, 12, 49f), TextureName.Brick));

        list.add(new Wall(new Vector3(33.8f, 0, 0), new Vector3(0.5f, 6, 9f), TextureName.Concrete1));

        //Floor
        list.add(new Plane(new Vector3(33.77f, -0.05f, 0), new Vector3(6f, 0.1f, 9f), TextureName.DarkGreenDiamonds));
        list.add(new Plane(new Vector3(35.23f, -0.05f, 9f), new Vector3(4.54f, 0.1f, 40f), TextureName.DarkGreenDiamonds));
        list.add(new Threshold(new Vector3(38.23f, -0.05f, 0), new Vector3(7.7f, 6f, 40f)));
        yPos = 6; yAddition = 12;
        for(int i = 0; i < 4; i++) {
            list.add(new Model(new Vector3(43.5f, -0.05f, yPos), 0, ModelName.SubwayTracks));
            yPos+=yAddition;
        }
        list.add(new Plane(new Vector3(47.3f, -0.05f, 0), new Vector3(4f, 0.1f, 49f), TextureName.DarkGreenDiamonds));
        //</editor-fold>
        list.add(new SolidWall(new Vector3(0, 0, 48), new Vector3(52f, 12, 0.5f), TextureName.Brick));

        AStar.generateAStar();

        player.weapon = new Melee(WeaponType.Bat);
        list.add(player);
        final PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(true);
        final LevelTitleCard titleCard = new LevelTitleCard("PRELUDE", "THE METRO", 400);//todo set duration to 400
        titleCard.addAction( new Action() {
            @Override
            public void activate() {
                list.add(new Fade(false, new Action() {
                    @Override
                    public void activate() {
                        player.giveControl();
                        pauseMenuHandler.enablePausing();
                        titleCard.rendering = false;
                        list.add(new Fade(true, null, 0.04f));
                    }
                }, 0.02f));
                //list.add(new MaskSelect());
                list.add(pauseMenuHandler);
            }
        });
        list.add(titleCard);
    }
}
