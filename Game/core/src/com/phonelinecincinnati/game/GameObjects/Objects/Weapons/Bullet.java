package com.phonelinecincinnati.game.GameObjects.Objects.Weapons;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class Bullet extends GameObject {
    private Vector3 velocity;
    private ModelInstance model;

    public Bullet(Vector3 position, Vector3 velocity) {
        this.position = position;
        this.velocity = velocity;

        model = Main.modelHandler.getModel(ModelName.Bullet);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void update() {
        position.add(velocity);
        model.transform.setToTranslation(position);
        model.transform.rotate(0, 1, 0, MathUtils.atan2(velocity.x, velocity.z)*MathUtils.radiansToDegrees);

        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(object instanceof MafiaMob) {
                MafiaMob mob = (MafiaMob)object;
                float hitDist = 0.8f;
                if(Math.abs(mob.position.x - position.x) < hitDist && Math.abs(mob.position.z - position.z) < hitDist) {
                    mob.hit(WeaponType.Automatic, position.cpy().sub(velocity.scl(4)), 0.3f);
                }
            } else if(object instanceof Collidable) {
                if(((Collidable)object).inBounds(position, getClass())) {
                    final GameObject This = this;
                    Main.postUpdateSchedule.add(new Action() {
                        @Override
                        public void activate() {
                            Main.levelHandler.getActiveObjects().remove(This);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderModel(model);
    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {
    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
