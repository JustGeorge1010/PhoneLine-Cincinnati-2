package com.phonelinecincinnati.game.GameObjects.Objects.Pickups;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Plane;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class WeaponPickUp extends GameObject{
    private Vector3 position;
    private Weapon item;
    private ModelInstance model;
    private BoundingBox box;
    private String name;

    private float random;
    private Vector3 direction;
    private float rotation = 5f;
    private SoundSource hitWall;

    public WeaponPickUp(Vector3 position, Weapon item) {
        this.position = position;
        this.item = item;
        direction = null;

        setup();
    }

    public WeaponPickUp(ArrayList<String> params) {
        try {
            this.position = VectorMaths.constructFromString(params.get(0));
            this.item = Weapon.constructFromClassName(params.get(1));
            direction = null;

            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeaponPickUp(Vector3 position, Weapon item, Vector3 direction) {
        this.position = position;
        this.item = item;
        this.direction = direction.nor();
        this.direction = new Vector3(direction.x, -0.1f, direction.z);

        setup();
        hitWall = SoundSource.buildSoundSource(1).setSound("Combat/HitWall.wav");
    }

    private void setup() {
        name = item.name.toString();
        model = Main.modelHandler.getModel(item.name);
        random = MathUtils.random(360);
        updateModelPosition();
        box = new BoundingBox();
        model.calculateBoundingBox(box);
        model.transform.translate(0, -box.getHeight()/2, 0);
    }

    public boolean withinRange(Vector3 position) {
        if (position.y-1 < this.position.y  && this.position.y < position.y+3.5f) {
            if (position.x < this.position.x + 2 && this.position.x - 2 < position.x) {
                return position.z < this.position.z + 2 && this.position.z - 2 < position.z;
            }
        }
        return false;
    }

    public String getName() {
        if (direction == null) {
            return name;
        } else {
            return "";
        }
    }

    public Weapon getItem() {
        return item;
    }

    @Override
    public void update() {
        if(!PauseMenuHandler.isPaused) {
            if(direction != null) {
                Vector3 tempPosition = new Vector3(position);
                tempPosition.add(direction);

                position = collisionDetection(tempPosition);

                rotation += 5f;

                updateModelPosition();
                model.transform.rotate(0, 0, 1, rotation);
                model.transform.translate(0, -box.getHeight()/2, 0);
            }
        }
    }

    private Vector3 collisionDetection(Vector3 tempPosition) {
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            Collidable collidable;
            if(object.getClass() == Plane.class) {
                Plane plane = (Plane)object;
                if(tempPosition.x > plane.position.x && tempPosition.x < plane.position.x+plane.size.x) {
                    if(tempPosition.z > plane.position.z && tempPosition.z < plane.position.z+plane.size.z) {
                        if(tempPosition.y < plane.position.y+(plane.size.y/2) && position.y > plane.position.y-(plane.size.y/2)) {
                            tempPosition.y = plane.position.y+(plane.size.y/2);
                            direction = null;
                            return tempPosition;
                        }
                    }
                }
            } else if (object.getClass() == MafiaMob.class && direction != null) {
                MafiaMob mob = (MafiaMob)object;
                if(tempPosition.dst(new Vector3(mob.position.x, position.y, mob.position.z)) < 3) {
                    if(!mob.knockedOver) {
                        mob.knockOver(position, 0.4f, false, true);
                        direction = new Vector3(-(direction.x/2), direction.y, -(direction.z/2));
                        tempPosition.add(direction);
                    }
                }
            } else {
                try {
                    collidable = (Collidable)object;
                    if(collidable.inBounds(tempPosition, this.getClass())) {
                        tempPosition = bounceOff(collidable, tempPosition);
                    }
                } catch(Exception ignored) {}
            }
        }
        return tempPosition;
    }

    private Vector3 bounceOff(Collidable collidable, Vector3 tempPosition) {
        if(position.y > collidable.minY() && position.y < collidable.maxY()) {
            if(position.x > collidable.minX() && position.x < collidable.maxX() &&
                    !(position.z > collidable.minZ() && position.z < collidable.maxZ())) {
                hitWall.playSound();
                if(position.z < collidable.minZ()) {
                    tempPosition = position;
                    direction.set(0, direction.y*2, -0.2f);
                } else if(position.z > collidable.maxZ()) {
                    tempPosition = position;
                    direction.set(0, direction.y*2, 0.2f);
                }
            } else if(position.z > collidable.minZ() && position.z < collidable.maxZ() &&
                    !(position.x > collidable.minX() && position.x < collidable.maxX())) {
                hitWall.playSound();
                if(position.x < collidable.minX()) {
                    tempPosition = position;
                    direction.set(-0.2f, direction.y*2, 0);
                } else if(position.x > collidable.maxX()) {
                    tempPosition = position;
                    direction.set(0.2f, direction.y*2, 0);
                }
            } else {
                tempPosition.set(position.x, collidable.maxY(), position.z);
                direction.set(direction.x, 0, direction.z);
                tempPosition.add(direction);
                direction = null;
            }
        }
        if(direction != null) tempPosition.add(direction);

        return tempPosition;
    }

    private void updateModelPosition() {
        model.transform.setToTranslation(position);
        model.transform.scale(0.5f, 0.5f, 0.5f);
        model.transform.translate(0, 0.2f, 0);
        model.transform.rotate(0, 1, 0, random);
        model.transform.rotate(1, 0, 0, 90);
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
        ArrayList<String> params = new ArrayList<String>();
        params.add(position.toString());
        params.add(item.getClass().getName());
        return params;
    }
}
