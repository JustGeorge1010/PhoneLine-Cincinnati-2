package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.Model;
import com.phonelinecincinnati.game.GameObjects.Objects.Pickups.WeaponPickUp;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class Door extends GameObject implements Collidable{
    private Vector3 center;
    private ModelInstance modelInstance;
    private BoundingBox bounds;
    private boolean horizontal;
    private ModelName modelName;
    private float baseRotation, rotation, minRotation, maxRotation, targetRotation;
    private Player player;
    private SoundSource openSound, knockOverSound;

    public boolean open = false;
    public boolean locked;

    public Door(Vector3 position, boolean horizontal, boolean locked, ModelName modelName) {
        this.position = position;
        this.horizontal = horizontal;
        this.locked = locked;
        this.modelName = modelName;

        setup();
    }

    public Door(ArrayList<String> params) {
        position = VectorMaths.constructFromString(params.get(0));
        horizontal = Boolean.parseBoolean(params.get(1));
        locked = Boolean.parseBoolean(params.get(2));
        modelName = ModelName.valueOf(params.get(3));

        setup();
    }

    private void setup() {
        bounds = new BoundingBox();
        if(horizontal) {
            baseRotation = -90;
        } else {
            baseRotation = 0;
        }
        //Main.levelHandler.getActiveObjects().add(new Model(new Vector3(position.x, position.y, position.z), baseRotation, ModelName.DoorFrame));

        rotation = baseRotation;
        targetRotation = rotation;
        minRotation = rotation + 103.3f;
        maxRotation = rotation - 103.3f;

        modelInstance = Main.modelHandler.getModel(modelName);
        modelInstance.calculateBoundingBox(bounds);
        center = new Vector3(position);
        center.add(-bounds.getCenterX(), 0, -bounds.getCenterY());
        modelInstance.transform.translate(position.x, position.y, position.z);
        modelInstance.transform.rotate(0, 1, 0, rotation);

        openSound = SoundSource.buildSoundSource(1).setSound("Misc/DoorOpen.wav");
        knockOverSound = SoundSource.buildSoundSource(0).setSound("Combat/EnemyDoorHit.wav");
    }

    @Override
    public void update() {
        if(!locked) {
            if(player == null) {
                for(GameObject object : Main.levelHandler.getActiveObjects()) {
                    if(object.getClass() == Player.class) player = (Player)object;
                }
            }

            float oldTarget = targetRotation;
            if(targetRotation == baseRotation) {
                open = false;
                if(horizontal) {
                    if(player.getPosition().x > position.x && player.getPosition().x < position.x+bounds.getDepth()) {
                        if(player.getPosition().z > position.z-(bounds.getWidth()*2) && player.getPosition().z < position.z) {
                            targetRotation = maxRotation;
                        } else if(player.getPosition().z < position.z+(bounds.getWidth()*2) && player.getPosition().z > position.z) {
                            targetRotation = minRotation;
                        }
                    }
                } else {
                    if(player.getPosition().z > position.z-bounds.getDepth() && player.getPosition().z < position.z) {
                        if(player.getPosition().x > position.x-(bounds.getWidth()*2) && player.getPosition().x < position.x) {
                            targetRotation = maxRotation;
                        }
                        if(player.getPosition().x < position.x+bounds.getWidth()*2 && player.getPosition().x > position.x) {
                            targetRotation = minRotation;
                        }
                    }
                }
            }
            if(targetRotation != oldTarget && targetRotation != baseRotation) {
                openSound.playSound();
            }

            if(rotation != targetRotation) {
                open = true;
                float rotationSpeed = 5;
                float hitBoxDepth;
                if(rotation < targetRotation) {
                    if(targetRotation-rotation > rotationSpeed) {
                        rotation += rotationSpeed;
                    } else {
                        rotation = targetRotation;
                    }
                    hitBoxDepth = -4;
                } else {
                    if(rotation-targetRotation > rotationSpeed) {
                        rotation -= rotationSpeed;
                    } else {
                        rotation = targetRotation;
                    }
                    hitBoxDepth = +4;
                }

                for(GameObject object : Main.levelHandler.getActiveObjects()) {
                    if(object.getClass() == MafiaMob.class) {
                        MafiaMob mob = (MafiaMob)object;
                        if(horizontal) {
                            if(mob.getPosition().x > position.x && mob.position.x < position.x+bounds.getDepth()) {
                                if(mob.getPosition().z < position.z+hitBoxDepth) {
                                    knockOverSound.playSound();
                                    mob.knockOver(center, hitBoxDepth/12, true, false);
                                }
                            } //TODO: change for different door rotations
                        }
                    }
                }

            }

            if(horizontal) {
                modelInstance.transform.setToTranslation(position.x - 1.56f, position.y, position.z);
            }
            else {
                modelInstance.transform.setToTranslation(position.x, position.y, position.z - 1.56f);
            }

            modelInstance.transform.rotate(0, 1, 0, rotation);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderModel(modelInstance);
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
        params.add(String.valueOf(horizontal));
        params.add(String.valueOf(locked));
        params.add(modelName.toString());
        return params;
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.getPosition(), c);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean inBounds(Vector3 position, Class c) {
        if(open) return false;

        float upperX;
        float lowerX;
        float upperZ;
        float lowerZ;
        if(horizontal) {
            upperX = this.position.x+bounds.getDepth()/2;
            lowerX = this.position.x-bounds.getDepth()/2;
            upperZ = (this.position.z+bounds.getWidth()/2)+0.5f;
            lowerZ = (this.position.z-bounds.getWidth()/2)-0.5f;
        } else {
            upperX = (this.position.x+bounds.getWidth()/2)+0.5f;
            lowerX = (this.position.x-bounds.getWidth()/2)-0.5f;
            upperZ = this.position.z+bounds.getDepth()/2;
            lowerZ = this.position.z-bounds.getDepth()/2;
        }

        if(position.x < upperX && position.x > lowerX) {
            if(position.z < upperZ && position.z > lowerZ) {
                return c == WeaponPickUp.class || locked;
            }
        }
        return false;
    }

    @Override
    public float minX() {
        if(horizontal) return this.position.x;
        return (this.position.x-bounds.getWidth()/2)-0.5f;
    }

    @Override
    public float maxX() {
        if(horizontal) return this.position.x+bounds.getDepth();
        return (this.position.x+bounds.getWidth()/2)+0.5f;
    }

    @Override
    public float minY() {
        return this.position.y;
    }

    @Override
    public float maxY() {
        return this.position.y+bounds.getHeight();
    }

    @Override
    public float minZ() {
        if(horizontal) return this.position.z-(bounds.getWidth()/2)-0.5f;
        return this.position.z-bounds.getDepth();
    }

    @Override
    public float maxZ() {
        if(horizontal) return this.position.z+(bounds.getWidth()/2)+0.5f;
        return this.position.z;
    }
}
