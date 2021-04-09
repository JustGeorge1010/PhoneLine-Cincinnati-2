package com.phonelinecincinnati.game.Levels;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.Main;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Level {
    public String name = "";
    public StageController stageController;
    protected CopyOnWriteArrayList<GameObject> activeObjects;

    public static Player createPlayer(Vector3 position, Vector3 lookDirection, Weapon weapon, boolean reloading, boolean retainPlayer) {
        Main.controlHandler.resetPlayer();
        Player player;
        if(!retainPlayer) {
            player = new Player(position.x, position.y+0.1f, position.z);
            Main.levelHandler.player = player;
        } else {
            player = Main.levelHandler.player;
        }
        player.weapon = weapon;
        if(reloading) {
            player.giveControl();
        } else {
            player.takeControl(null);
        }
        Main.camera.lookAt(player.position.cpy().add(lookDirection));

        return player;
    }
    public abstract void load(boolean reloading, boolean retainPlayer);
    public abstract void reload();
}
