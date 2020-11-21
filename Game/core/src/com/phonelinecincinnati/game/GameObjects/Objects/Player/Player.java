package com.phonelinecincinnati.game.GameObjects.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Pickups.WeaponPickUp;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.Ranged;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.ForcedController;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.WeaponType;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;
import java.util.Map;

public class Player extends GameObject {
    private Vector3 tempForCam;

    private PerspectiveCamera mainCam;

    public Weapon weapon;
    private WeaponPickUp currentPickUp;
    private SoundSource pickupSound, throwSound;
    private int weaponResetTime = 0;

    private boolean mounted = false;
    private MafiaMob mountedMob;

    private boolean inControl = true;
    private ForcedController forcedController;

    public TextBox textBox;
    private Hud hud;

    private float floorY;
    private float ySpeed;

    public boolean dead = false;

    public Player(float x, float y, float z) {
        mainCam = Main.camera;
        mainCam.position.set(x, y, z);
        position = new Vector3(x, y, z);
        tempForCam = new Vector3();

        pickupSound = SoundSource.buildSoundSource(1).setSound("Combat/PickupWeapon.wav");
        throwSound = SoundSource.buildSoundSource(2).setSound("Combat/Throw.wav");

        textBox = new TextBox();
        hud = new Hud();

        floorY = y;
        ySpeed = 0.08f;
    }

    public void setFloorY(float y, float ySpeed) {
        floorY = y+3.6f;
        this.ySpeed = ySpeed;
    }

    public void giveControl() {
        inControl = true;
    }

    public void takeControl(ForcedController forcedController) {
        inControl = false;
        this.forcedController = forcedController;
    }

    public void LMB() {
        if(weaponResetTime <= 0 && weapon != null) {
            if(!(weapon.type == WeaponType.Automatic || weapon.type == WeaponType.SemiAutomatic)) {
                weaponResetTime = 50;
            }

            if(inControl && !mounted) {
                if(weapon.type == WeaponType.Automatic) {
                    ((Ranged)weapon).triggerHeld = true;
                }
                useWeapon();
            } else if(mounted) {
                useWeapon();
                mountedMob.mountedHit();
            }
        }
    }

    public void LMBReleased() {
        if(weapon != null && weapon.type == WeaponType.Automatic) {
            ((Ranged)weapon).triggerHeld = false;
        }
    }

    public void RMB() {
        if(inControl) {
            if(!textBox.isOpen()) {
                if(!mounted) {
                    for(GameObject object : Main.levelHandler.getActiveObjects()) {
                        if(InteractiveModel.class.isAssignableFrom(object.getClass())) {
                            InteractiveModel model = (InteractiveModel)object;
                            if(model.canInteract(this)) {
                                hud.interactionText = "";
                                model.interact();
                            }
                        }
                    }
                    switchWeapon();
                }
            } else {
                textBox.next();
            }
        }
    }

    public void spb() {
        if(weapon == null || Ranged.class.isAssignableFrom(weapon.getClass())) {
            return;
        }
        if(!mounted) {
            for(GameObject object : Main.levelHandler.getActiveObjects()) {
                if(object.getClass() == MafiaMob.class) {
                    MafiaMob mob = (MafiaMob)object;
                    if(mob.canMount(position)) {
                        mounted = true;
                        mob.mount(this);
                        mountedMob = mob;
                        weapon.switchType();
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        if(dead) {
            if(Main.controlHandler.keys.get(Input.Keys.R))
                Main.levelHandler.currentLevel.reload();
            return;
        }

        hud.update(weapon);

        if(!PauseMenuHandler.isPaused) {
            if(inControl) {
                if(!textBox.isOpen()) {
                    if(!mounted) {
                        move();
                        updateYSmoothly();
                        detectPickup();
                        detectInteraction();
                    }
                }
                textBox.update();
                mainCam.position.set(position);
            } else {
                if(forcedController != null) forcedController.update();
            }
            if(weapon != null) {
                if(weapon.type == WeaponType.Automatic) {
                    Ranged w = (Ranged)weapon;
                    if(w.triggerHeld) {
                        useWeapon();
                    }
                }
                weapon.update();
            }
            if(weaponResetTime > 0) weaponResetTime--;
        }
    }

    private void move() {
        Map<Integer, Boolean> keys = Main.controlHandler.keys;

        Vector2 tempVelocity = new Vector2(mainCam.direction.x, mainCam.direction.z);
        Vector3 tempPosition = new Vector3(position);

        tempVelocity = tempVelocity.nor();

        tempVelocity.x = tempVelocity.x * 0.2f;
        tempVelocity.y = tempVelocity.y * 0.2f;

        if(Main.debug) {
            if(keys.get(Input.Keys.SPACE)) {
                tempPosition.add(0, 0.4f, 0);
            }
            if(keys.get(Input.Keys.SHIFT_LEFT)) {
                tempPosition.add(0, -0.4f ,0);
            }
        }

        if(keys.get(Input.Keys.W) && !keys.get(Input.Keys.S)) {
            tempPosition.add(tempVelocity.x, 0, tempVelocity.y);
        }
        if(keys.get(Input.Keys.S) && !keys.get(Input.Keys.W)) {
            tempPosition.add(-tempVelocity.x, 0, -tempVelocity.y);
        }

        if(keys.get(Input.Keys.A) || keys.get(Input.Keys.D)) {
            tempVelocity.rotate(90);
            tempVelocity = tempVelocity.nor();
            tempVelocity.x = tempVelocity.x * 0.2f;
            tempVelocity.y = tempVelocity.y * 0.2f;

            if(keys.get(Input.Keys.A) && !keys.get(Input.Keys.D)) {
                tempPosition.add(-tempVelocity.x, 0, -tempVelocity.y);
            }
            if(keys.get(Input.Keys.D) && !keys.get(Input.Keys.A)) {
                tempPosition.add(tempVelocity.x, 0, tempVelocity.y);
            }
        }

        ArrayList<GameObject> collisions = checkCollisions(tempPosition);
        resolveCollisions(collisions, tempPosition);
    }

    private ArrayList<GameObject> checkCollisions(Vector3 position) {
        ArrayList<GameObject> collisions = new ArrayList<GameObject>();
        if(Main.debug) return collisions;
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            Collidable collidable;
            try {
                collidable = (Collidable)object;
                if(collidable.inBounds(position, this.getClass())) {
                    collisions.add(object);
                }
            } catch(Exception ignored) {}
        }
        return collisions;
    }

    private void resolveCollisions(ArrayList<GameObject> collisions, Vector3 position) {
        if(collisions.isEmpty()) {
            this.position = position;
        } else if(collisions.size() == 1) {
            Collidable collidable = (Collidable)collisions.get(0);
            if((this.position.x < collidable.minX() || this.position.x > collidable.maxX())) {
                if(this.position.z > collidable.minZ() && this.position.z < collidable.maxZ()) {
                    //If this is on the Z sides (North and South)
                    this.position.z = position.z;
                }
            } else if((this.position.x > collidable.minX() && this.position.x < collidable.maxX())) {
                if((this.position.z < collidable.minZ() || this.position.z > collidable.maxZ())) {
                    //If this is on the X sides (West and East)
                    this.position.x = position.x;
                }
            }
        }/* else { TODO: smooth out collisions of objects near other objects
            Collidable collidableRight = (Collidable)collisions.get(0);
            if((this.position.x < collidableRight.minX() || this.position.x > collidableRight.maxX())) {
                if(this.position.z > collidableRight.minZ() && this.position.z < collidableRight.maxZ()) {
                    //If this is on the Z sides (North and South)
                    if(collisions.get(1).getPosition().x == collisions.get(0).getPosition().x) this.position.z = position.z;
                }
            } else if((this.position.x > collidableRight.minX() && this.position.x < collidableRight.maxX())) {
                if((this.position.z < collidableRight.minZ() || this.position.z > collidableRight.maxZ())) {
                    //If this is on the X sides (West and East)
                    if(collisions.get(1).getPosition().z == collisions.get(0).getPosition().z) this.position.x = position.x;
                }
            }
        }*/
    }

    private void updateYSmoothly() {
        if(!Main.debug) {
            if(position.y > floorY) {
                position.y -= ySpeed;
                if(position.y < floorY) {
                    position.y = floorY;
                }
            } else if(position.y < floorY) {
                position.y += ySpeed;
                if(position.y > floorY) {
                    position.y = floorY;
                }
            }
        }
    }

    private void detectPickup() {
        hud.pickupText = "";
        currentPickUp = null;
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(object.getClass() == WeaponPickUp.class) {
                WeaponPickUp pickUp = (WeaponPickUp)object;
                if(pickUp.withinRange(position)) {
                    currentPickUp = pickUp;
                    String name = pickUp.getName();
                    if(!name.equals("")) {
                        hud.pickupText = "(RMB) to pick up "+name;
                    }
                }
            }
        }
    }

    private void switchWeapon() {
        if(weapon != null) {
            throwSound.playSound();
            Main.levelHandler.addObjectToCurrentLevel(new WeaponPickUp(new Vector3(position), weapon, new Vector3(mainCam.direction)));
        }
        if(currentPickUp != null) {
            pickupSound.playSound();
            weapon = currentPickUp.getItem();
            Main.levelHandler.getActiveObjects().remove(currentPickUp);
        } else {
            weapon = null;
        }
    }

    private void useWeapon() {
        if(!PauseMenuHandler.isPaused) {
            weapon.use(position, mainCam.direction.cpy());
        }
    }

    public void dismount() {
        weapon.switchType();
        mounted = false;
    }

    private void detectInteraction() {
        hud.interactionText = "";
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(InteractiveModel.class.isAssignableFrom(object.getClass())) {
                InteractiveModel model = ((InteractiveModel)object);
                if(model.canInteract(this)) {
                    hud.interactionText = model.getInteractionText();
                }
            }
        }
    }

    public void controlCamera() {
        if(!(PauseMenuHandler.isPaused || textBox.isOpen() || !inControl || mounted)) {
            float mouseX = -Gdx.input.getDeltaX() * 0.1f;
            float mouseY = -Gdx.input.getDeltaY() * 0.1f;

            int maxMove = 12;
            if(mouseX > maxMove) {
                mouseX = maxMove;
            } else if(mouseX < -maxMove) {
                mouseX = -maxMove;
            }

            if(mouseY > maxMove) {
                mouseY = maxMove;
            } else if(mouseY < -maxMove) {
                mouseY = -maxMove;
            }

            if((mainCam.direction.y < -0.99 && mouseY < 0) || (mainCam.direction.y > 0.99 && mouseY > 0)) {
                mouseY = 0;
            }

            mainCam.direction.rotate(mainCam.up, mouseX);
            tempForCam.set(mainCam.direction).crs(mainCam.up).nor();
            mainCam.direction.rotate(tempForCam, mouseY);
        }
    }

    public void lookAt(float xPos, float zPos, float yRot) {
        mainCam.lookAt(xPos, position.y, zPos);
        Vector3 tempForCam = new Vector3(Main.camera.direction).crs(Main.camera.up).nor();
        Main.camera.direction.rotate(tempForCam, yRot);
    }

    public void kill() {
        dead = true;
        weapon = null;
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        if(weapon != null) weapon.render(renderer);
        hud.render(renderer);
        textBox.render(renderer);
    }

    @Override
    public void dispose() {
        pickupSound.dispose();
        throwSound.dispose();
    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
