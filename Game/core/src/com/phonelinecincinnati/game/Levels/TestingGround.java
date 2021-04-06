package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Plane;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.BaseballBat;
import com.phonelinecincinnati.game.GameObjects.Objects.SolidWall;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.M16;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;

import java.util.concurrent.CopyOnWriteArrayList;

public class TestingGround extends Level {
    public TestingGround(final CopyOnWriteArrayList<GameObject> activeObjects) {
        this.activeObjects = activeObjects;
        load(false, false);
    }

    private void load() {
        Main.backgroundColor.set(1, 0, 1, 1);

        Player player = createPlayer(new Vector3(25, 3.6f, 0), new Vector3(0, 0, 1), new M16(),
                false, false);
        player.giveControl();

        Main.camera.lookAt(25, 3.6f, 1);

        activeObjects.add(new Plane(new Vector3(15f, -0.05f, 0), new Vector3(25f, 0.1f, 14.2f), TextureName.Wood));
        activeObjects.add(new SolidWall(new Vector3(20f, -6f, 5f), new Vector3(10f, 12, 0.5f), TextureName.Concrete1));
        activeObjects.add(new SolidWall(new Vector3(20f, -6f, 5f), new Vector3(0.5f, 12, 5f), TextureName.Concrete1));

        MafiaMob mob = new MafiaMob(new Vector3(25, 0, 11), new Vector3(0, 25, 0), new BaseballBat());
        AStar.generateAStar(0);

        activeObjects.add(mob);
        activeObjects.add(player);

        PauseMenuHandler pauseMenuHandler = new PauseMenuHandler(true);
        pauseMenuHandler.enablePausing();
        activeObjects.add(pauseMenuHandler);
    }

    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        load();
    }

    @Override
    public void reload() {
        load();
    }
}
