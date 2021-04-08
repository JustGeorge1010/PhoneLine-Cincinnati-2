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

import java.lang.reflect.Array;
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

        rotation = baseRotation;
        targetRotation = rotation;
        minRotation = rotation + 103.3f;
        maxRotation = rotation - 103.3f;

        modelInstance = Main.modelHandler.getModel(modelName);
        modelInstance.calculateBoundingBox(bounds);
        center = new Vector3(position);
        center.add(-bounds.getCenterX(), 0, -bounds.getCenterZ());
        modelInstance.transform.translate(position.x, position.y, position.z);
        modelInstance.transform.rotate(0, 1, 0, rotation);

        openSound = SoundSource.buildSoundSource(1).setSound("Misc/DoorOpen.wav");
        knockOverSound = SoundSource.buildSoundSource(0).setSound("Combat/EnemyDoorHit.wav");

        createBoundLines();
    }

    private void createBoundLines() {
        boundLines = new ArrayList<ModelInstance>();

        Vector3 p1 = new Vector3(minX(), minY(), minZ());
        Vector3 p2 = new Vector3(minX(), minY(), maxZ());
        Vector3 p3 = new Vector3(minX(), maxY(), minZ());
        Vector3 p4 = new Vector3(maxX(), minY(), minZ());

        Vector3 p5 = new Vector3(maxX(), maxY(), minZ());
        Vector3 p6 = new Vector3(maxX(), minY(), maxZ());
        Vector3 p7 = new Vector3(minX(), maxY(), maxZ());
        Vector3 p8 = new Vector3(maxX(), maxY(), maxZ());

        //corner 1 lower
        boundLines.add(Main.modelHandler.getLine(p1, p2));
        boundLines.add(Main.modelHandler.getLine(p1, p3));
        boundLines.add(Main.modelHandler.getLine(p1, p4));

        //corner 2 upper
        boundLines.add(Main.modelHandler.getLine(p5, p8));
        boundLines.add(Main.modelHandler.getLine(p6, p8));
        boundLines.add(Main.modelHandler.getLine(p7, p8));

        //corner 3 lower xz upper y
        boundLines.add(Main.modelHandler.getLine(p3, p5));
        boundLines.add(Main.modelHandler.getLine(p3, p7));

        //corner 4 upper xz lower y
        boundLines.add(Main.modelHandler.getLine(p6, p2));
        boundLines.add(Main.modelHandler.getLine(p6, p4));

        //Remaining lines
        boundLines.add(Main.modelHandler.getLine(p2, p7));
        boundLines.add(Main.modelHandler.getLine(p4, p5));
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
                    if(player.getPosition().x > minX() && player.getPosition().x < maxX()) {
                        if(player.getPosition().z > minZ() && player.getPosition().z < position.z) {
                            targetRotation = maxRotation;
                        } else if(player.getPosition().z < maxZ() && player.getPosition().z > position.z) {
                            targetRotation = minRotation;
                        }
                    }
                } else {
                    if(player.getPosition().z > minZ() && player.getPosition().z < maxZ()) {
                        if(player.getPosition().x > minX() && player.getPosition().x < position.x) {
                            targetRotation = maxRotation;
                        }
                        if(player.getPosition().x < maxX() && player.getPosition().x > position.x) {
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
        if(Main.debugDrawBounds) {
            drawBoundingBox(renderer);
        }
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

        float upperX = maxX();
        float lowerX = minX();
        float upperZ = maxZ();
        float lowerZ = minZ();

        if(position.x < upperX && position.x > lowerX) {
            if(position.z < upperZ && position.z > lowerZ) {
                return c == WeaponPickUp.class || locked;
            }
        }
        return false;
    }

    @Override
    public float minX() {
        if(horizontal) return this.position.x-bounds.getDepth()/2;
        return (this.position.x-bounds.getWidth()/2)-0.5f;
    }

    @Override
    public float maxX() {
        if(horizontal) return this.position.x+bounds.getDepth()/2;
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
