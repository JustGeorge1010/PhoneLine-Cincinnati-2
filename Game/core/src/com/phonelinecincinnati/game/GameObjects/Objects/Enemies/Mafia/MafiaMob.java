package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Animations.HumanoidAnimation.HumanoidAnimation;
import com.phonelinecincinnati.game.Animations.HumanoidAnimation.Move;
import com.phonelinecincinnati.game.Animations.HumanoidAnimation.Stand;
import com.phonelinecincinnati.game.Animations.HumanoidAnimation.SwingingWeapon;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI.MafiaMobBrain;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Gore.BloodPool;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Pickups.WeaponPickUp;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee.Melee;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.WeaponType;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.CollisionMaths;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI.MafiaMobBrain.State.Following;
import static com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI.MafiaMobBrain.State.Patrolling;
import static com.phonelinecincinnati.game.Models.ModelName.EnemyBack1;
import static com.phonelinecincinnati.game.Models.ModelName.EnemyWall;

public class MafiaMob extends GameObject{
    public Vector3 rotation;
    public MafiaMobBody body;
    private Vector3 baseRotation;
    private HumanoidAnimation stand, walk, run, swingingWeapon;
    private Weapon weapon;

    public ArrayList<Vector3> currentPath;
    private ArrayList<Vector3> patrolRoute;
    public int index = 0;
    private int wait;
    private float walkSpeed = 0.05f;
    private float runSpeed = 0.2f;

    public boolean knockedOver = false, againstWall = false;
    private boolean mounted = false, dead = false, bloodPooled = false;
    private ModelInstance knockedOverModel;
    private float fallVelocity;
    private SoundSource impactSound, deathSound;
    private int knockedOverTimer = 0, standUpTimer = 50;
    private int finisherStage = 0;

    private Player playerRef;
    private boolean isSwinging = false;

    private MafiaMobBrain brain;

    public MafiaMob(Vector3 position, Vector3 rotation, Weapon weapon) {
        this.position = position;
        this.baseRotation = new Vector3(rotation);
        this.rotation = new Vector3(rotation);
        this.weapon = weapon;
        playerRef = Main.levelHandler.player;

        setup();
    }

    public MafiaMob(Vector3 position, Vector3 rotation, ArrayList<Vector3> patrolRoute, Weapon weapon) {
        this.position = position;
        this.baseRotation = new Vector3(rotation);
        this.rotation = new Vector3(rotation);
        this.weapon = weapon;
        playerRef = Main.levelHandler.player;

        setup(patrolRoute);
    }

    public MafiaMob(ArrayList<String> params) {
        try {
            this.position = VectorMaths.constructFromString(params.get(0));
            this.rotation = VectorMaths.constructFromString(params.get(1));
            this.baseRotation = new Vector3(rotation);
            this.weapon = Weapon.constructFromClassName(params.get(2));
            playerRef = Main.levelHandler.player;

            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        setup(new ArrayList<Vector3>());
    }

    private void setup(ArrayList<Vector3> patrolRoute) {
        this.patrolRoute = patrolRoute;
        currentPath = patrolRoute;

        body = new MafiaMobBody(position, rotation, true, weapon.name);
        stand = new Stand(body);
        walk = new Move(body, 3f);
        run = new Move(body, 6f);
        swingingWeapon = new SwingingWeapon(body);

        impactSound = SoundSource.buildSoundSource(1).setSound("Combat/EnemyBluntHit.wav");
        deathSound = SoundSource.buildSoundSource(2).setSound("Combat/EnemyDeath.wav");

        brain = new MafiaMobBrain(this, playerRef);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void update() {
        if(dead && fallVelocity == 0f && !mounted) {
            if(!bloodPooled) poolBlood();
            return;
        }

        if(!PauseMenuHandler.isPaused && !playerRef.dead) {
            if(!knockedOver && !dead) {
                calculateNextMovements();
                handleWeapon();
                swingingWeapon.update();
                body.resetPosition();
            } else {
                knockOverUpdate();
                //<editor-fold desc="Conditional">
                if(!dead && !mounted) {
                    if(knockedOverTimer > 0) {
                        knockedOverTimer--;
                    } else {
                        knockedOver = false;
                    }
                } else if(dead && mounted) {
                    if(standUpTimer <= 0) {
                        mounted = false;
                        for(GameObject object : Main.levelHandler.getActiveObjects()) {
                            if(object.getClass() == Player.class) {
                                ((Player)object).dismount();
                            }
                        }
                    } else {
                        standUpTimer--;
                    }
                }
                //</editor-fold>
            }
        } else {
            stand.update();
            swingingWeapon.update();
            body.resetPosition();
        }
    }

    private void calculateNextMovements() {
        brain.update();
        if(brain.currentState == Following) {
            wait = 0;
            handleMovement(playerRef.position.cpy(), runSpeed);
            walk.update();
            return;
        }

        if(brain.currentState == Patrolling) currentPath = patrolRoute;

        float speed = runSpeed;
        if(brain.currentState == Patrolling) speed = walkSpeed;
        if(!currentPath.isEmpty() && handleMovement(currentPath.get(index).cpy(), speed)) {
            walk.update();
        } else stand.update();
    }

    private boolean handleMovement(Vector3 newPosition, float speed) {
        boolean isMoving = false;

        if(wait == 0) {
            if(position.x != newPosition.x || position.z != newPosition.z) {
                isMoving = true;

                Vector3 unitVector = newPosition.cpy().sub(position).nor();
                unitVector.y = 0;

                if(position.dst(newPosition) < speed) {
                    position.set(newPosition);
                }
                else {
                    position = position.add(unitVector.scl(speed));
                }
                float angle = MathUtils.atan2(newPosition.z-body.position.z, newPosition.x-body.position.x);
                rotation.y = (angle*(360/MathUtils.PI2));
            }
            else {
                index++;
                if(index == currentPath.size()) {
                    index = 0;
                }
                if(brain.currentState == Patrolling) wait = 100;
            }
        } else {
            wait--;
        }

        float angle = MathUtils.atan2(newPosition.z-body.position.z, newPosition.x-body.position.x);
        rotation.y = (angle*(360/MathUtils.PI2));
        body.rotation = new Vector3(0, rotation.y, 0);
        return isMoving;
    }

    private void handleWeapon() {
        if(Main.debugBlindEnemies) {
            return;
        }
        if(weapon.getClass() == Melee.class) {
            float distance = 17;

            if(playerRef.position.dst2(this.position) <= distance && !playerRef.dead) {
                float angle = MathUtils.atan2(playerRef.position.z-body.position.z, playerRef.position.x-body.position.x);
                rotation.y = (angle*(360/MathUtils.PI2));
                body.rotation = new Vector3(0, rotation.y, 0);

                swingingWeapon.playAnim();
                deathSound.playSound();
                playerRef.kill();
            }
        }
    }

    public void hit(WeaponType type, Vector3 locationHitFrom, float fallVelocity) {
        if(!dead && (!knockedOver || againstWall)) {
            knockOver(locationHitFrom, fallVelocity);

            dead = true;
            deathSound.playSound();
            Main.levelHandler.currentLevel.stageController.currentKills++;

            if(againstWall) {
                knockedOverModel = Main.modelHandler.getModel(ModelName.EnemyWallDead);
            } else {
                knockedOverModel = Main.modelHandler.getModel(ModelName.EnemyBluntKill3);
            }
            knockedOverModel.transform.setToTranslation(position);
            knockedOverModel.transform.rotate(0, 1, 0, rotation.y);
        }
    }

    public void knockOver(Vector3 locationHitFrom, float fallVelocity) {
        if(!knockedOver) {
            impactSound.playSound();
            float angle = MathUtils.atan2(locationHitFrom.x-position.x, locationHitFrom.z-position.z);
            float correctedAngle = angle*(360/MathUtils.PI2);
            rotation.y = (correctedAngle)+180;

            knockedOver = true;
            knockedOverTimer = 400;
            knockedOverModel = Main.modelHandler.getModel(EnemyBack1);
            knockedOverModel.transform.setToTranslation(position);
            knockedOverModel.transform.rotate(0, 1, 0, rotation.y);
            Main.levelHandler.addObjectToCurrentLevel(new WeaponPickUp(position.cpy(), weapon));
            this.fallVelocity = fallVelocity;
        }
    }

    private void knockOverUpdate() {
        Vector3 unitVector = new Vector3(0, 0, fallVelocity);
        unitVector.rotate(rotation.y, 0, 1, 0);
        Vector3 newPos = position.cpy().add(unitVector);

        handleSlideCollisions(newPos);

        if(fallVelocity != 0) {
            position.set(newPos);
        }

        if(fallVelocity > 0) {
            fallVelocity -= 0.01f;
        }
        else if(fallVelocity < 0) {
            fallVelocity = 0;
        }

        knockedOverModel.transform.setToTranslation(position);
        knockedOverModel.transform.rotate(0, 1, 0, rotation.y);

    }
    private void handleSlideCollisions(Vector3 newPos) {
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            Collidable collidable;
            try {
                collidable = (Collidable)object;
                if(CollisionMaths.lineCollision(CollisionMaths.lineTo2DLines(position.cpy(), newPos.cpy()), object, 0.5f)) {
                    if(!dead) {
                        againstWall = true;
                        knockedOverModel = Main.modelHandler.getModel(EnemyWall);

                        HashMap<Float, String> depths = new HashMap<Float, String>();
                        depths.put(collidable.maxX()-position.x, "depthMaxX");
                        depths.put(position.x-collidable.minX(), "depthMinX");
                        depths.put(collidable.maxZ()-position.z, "depthMaxZ");
                        depths.put(position.z-collidable.minZ(), "depthMinZ");

                        TreeMap<Float, String> sortedDepths = new TreeMap<Float, String>(depths);
                        String depth = sortedDepths.firstEntry().getValue();

                        if(Main.debug) {
                            for(Map.Entry<Float, String> entry: sortedDepths.entrySet()) {
                                System.out.println(entry.getValue()+": "+entry.getKey());
                            }
                        }

                        if(depth.equals("depthMaxX")) {
                            position.x = collidable.maxX()-0.3f;
                            rotation.y = -90;
                        } else if(depth.equals("depthMinX")) {
                            position.x = collidable.minX()+0.3f;
                            rotation.y = 90;
                        } else if(depth.equals("depthMaxZ")) {
                            position.z = collidable.maxZ()-0.3f;
                            rotation.y = 180;
                        } else if(depth.equals("depthMinZ")) {
                            position.z = collidable.minZ()+0.3f;
                            rotation.y = 0;
                        }
                    }
                    fallVelocity = 0;
                }
            } catch(Exception ignored) {}
        }
    }

    public boolean canMount(Vector3 position) {
        return (position.dst(this.position) < 4) && knockedOver && !dead;
    }

    public void mount(Player player) {
        mounted = true;
        Vector3 sitPos = position.cpy().sub(0, 3, 0);
        Vector3 directionVector = new Vector3(0, 0, 1f);
        directionVector.rotate(rotation.y, 0, 1, 0);
        sitPos.add(directionVector);
        player.position.set(sitPos);
        player.position.y += 2;

        Vector3 lookPos = new Vector3(position);
        lookPos.z += 1000;
        lookPos.rotate(rotation.y, 0, 1, 0);

        player.lookAt(lookPos.x, lookPos.z, -50);
    }

    public void mountedHit() {
        finisherStage += 1;
        if(finisherStage < 4) {
            knockedOverModel = Main.modelHandler.getModel(ModelName.valueOf("EnemyBluntKill"+finisherStage));
            knockedOverModel.transform.setToTranslation(position);
            knockedOverModel.transform.rotate(0, 1, 0, rotation.y);
            if(finisherStage < 3) {
                impactSound.playSound();
                if(finisherStage == 2) {
                    poolBlood();
                }
            } else {
                deathSound.playSound();
                dead = true;
                Main.levelHandler.currentLevel.stageController.currentKills++;
            }
        }
    }

    private void poolBlood() {
        bloodPooled = true;
        Vector3 bloodPos = position.cpy();
        Vector3 directionVector;
        if(!againstWall) {
            directionVector = new Vector3(0, 0, 2.3f);
        } else {
            directionVector = new Vector3(0, 0, -1.35f);
        }
        directionVector.rotate(rotation.y, 0, 1, 0);
        bloodPos.add(directionVector);
        bloodPos.y += 0.01f;
        Main.levelHandler.addObjectToCurrentLevel(new BloodPool(bloodPos, rotation));
    }

    @Override
    public void render(Renderer renderer) {
        if(!knockedOver && !dead) {
            body.render(renderer);
        } else {
            renderer.renderModel(knockedOverModel);
        }
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
        params.add(rotation.toString());
        params.add(weapon.getClass().getName());
        return params;
    }
}
