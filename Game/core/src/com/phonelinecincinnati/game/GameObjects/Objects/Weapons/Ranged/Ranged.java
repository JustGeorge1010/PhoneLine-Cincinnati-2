package com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Bullet;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.Main;

public abstract class Ranged extends Weapon {
    public int rounds;
    public boolean triggerHeld;
    float rateOfFire;
    private long timeLastFired;

    @Override
    public void use(Vector3 locationOfUse, Vector3 direction) {
        long currentTime = System.currentTimeMillis();
        if(currentTime-timeLastFired > rateOfFire) {
            timeLastFired = currentTime;
            if(rounds > 0) {
                rounds--;
                super.use(locationOfUse, direction);
                Main.levelHandler.getActiveObjects().add(new Bullet(locationOfUse, direction));
            }
        }
    }
}
