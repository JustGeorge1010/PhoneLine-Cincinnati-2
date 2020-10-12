package com.phonelinecincinnati.game.GameObjects.Objects.Player;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Model.InteractiveSolidModel;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;

public class PlayerCar extends InteractiveSolidModel {
    private Vector3 doorPosition;
    private float yRotation, baseRotation, rotation, maxRotation, targetRotation;
    private ModelInstance door;
    private SoundSource doorOpen, doorClose;

    private Player player;

    public PlayerCar(Vector3 position, float rotation, Player player) {
        super(position, rotation, ModelName.PlayerCar);
        modelInstance.transform.scale(1.3f, 1.6f, 1.5f);

        if(rotation == -90) {
            doorPosition = new Vector3(position.x+0.46f, position.y+3.35f, position.z+0.61f);
        } else if(rotation == 0 || rotation == 180){
            doorPosition = new Vector3(position.x+0.61f, position.y+3.35f, position.z+0.46f);
        }

        yRotation = rotation;

        baseRotation = 00f;
        this.rotation = baseRotation;
        targetRotation = this.rotation;
        maxRotation = this.rotation + 103.3f;

        door = Main.modelHandler.getModel(ModelName.PlayerCarDoor);
        door.transform.translate(doorPosition);
        door.transform.rotate(0, 1, 0, rotation);
        door.transform.scale(1.25f, 1.6f, 1.5f);
        door.transform.rotate(1, 0, 0, baseRotation);

        doorOpen = SoundSource.buildSoundSource(0).setSound("Misc/CarOpen.wav");
        doorClose = SoundSource.buildSoundSource(1).setSound("Misc/CarClose.wav");

        this.player = player;
    }

    @Override
    public void update() {
        if(yRotation == -90) {
            if(player.position.x < position.x + 8f) {
                targetRotation = maxRotation;
            } else {
                targetRotation = baseRotation;
            }
        } else {
            if(player.position.z < position.z + 8f) {
                targetRotation = maxRotation;
            } else {
                targetRotation = baseRotation;
            }
        }

        if(targetRotation == maxRotation) {
            if(rotation == baseRotation) doorOpen.playSound();
            if(rotation < maxRotation) {
                rotation += 4f;
            } else {
                rotation = maxRotation;
            }
        } else {
            if(rotation == maxRotation) doorClose.playSound();
            if(rotation > baseRotation) {
                rotation -= 8f;
            } else {
                rotation = baseRotation;
            }
        }

        door.transform.setToTranslation(doorPosition);
        door.transform.rotate(0, 1, 0, yRotation);
        door.transform.scale(1.25f, 1.6f, 1.5f);
        door.transform.rotate(1, 0, 0, rotation);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderModel(door);
        super.render(renderer);
    }

    @Override
    public void postRender(Renderer renderer) {
        super.postRender(renderer);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
