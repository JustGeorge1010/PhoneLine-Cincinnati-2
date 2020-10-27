package com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.Melee;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Animations.WeaponAnimations.OverheadSwingFinisher;
import com.phonelinecincinnati.game.Animations.WeaponAnimations.SwingWeaponAnimation;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.Weapon;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Weapons.WeaponType;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Utility.VectorMaths;

public abstract class Melee extends Weapon {
    float reach;

    @Override
    public void use(Vector3 locationOfUse, Vector3 direction) {
        super.use(locationOfUse, direction);

        locationOfUse = new Vector3(locationOfUse);
        direction = new Vector3(direction);

        //The hit angle either side of [direction]
        float hitAngle = 30;

        Vector3 leftMost = new Vector3(locationOfUse).add(
                new Vector3(direction.x*reach, direction.y*reach, direction.z*reach).rotate(-hitAngle, 0, 1, 0));
        Vector3 rightMost = new Vector3(locationOfUse).add(
                new Vector3(direction.x*reach, direction.y*reach, direction.z*reach).rotate(hitAngle, 0, 1, 0));

        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(object.getClass() == MafiaMob.class) {
                MafiaMob enemy = (MafiaMob)object;

                if(VectorMaths.vectorIsInsideTriangle(enemy.position, locationOfUse,  leftMost, rightMost)) {
                    enemy.hit(type, locationOfUse, 0.3f);
                }
            }
        }
    }
}
