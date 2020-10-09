package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.Melee;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.WeaponType;
import com.phonelinecincinnati.game.GameObjects.Objects.SolidWall;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar.getAStar;

public class TestingGround extends Level {
    public TestingGround(final CopyOnWriteArrayList<GameObject> list) {
        Main.backgroundColor.set(1, 0, 1, 1);
        Main.modelHandler.setModelsForLevel1();

        final Player player = new Player(25, 3.6f, 0);
        player.weapon = new Melee(WeaponType.Bat);

        Main.camera.lookAt(25, 3.6f, 1);

        list.add(new SolidWall(new Vector3(20f, -6f, 5f), new Vector3(10f, 12, 0.5f), TextureName.Concrete1));
        list.add(new SolidWall(new Vector3(20f, -6f, 5f), new Vector3(0.5f, 12, 5f), TextureName.Concrete1));

        MafiaMob mob = new MafiaMob(new Vector3(25, 0, 11), new Vector3(0, 25, 0), new Melee(WeaponType.Bat), player);
        AStar.generateAStar();

        list.add(mob);
        list.add(player);
    }
}
