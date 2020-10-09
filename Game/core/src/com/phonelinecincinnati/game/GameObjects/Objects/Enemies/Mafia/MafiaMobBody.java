package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Body;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.BodyPart;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;

public class MafiaMobBody extends Body {
    private ModelInstance modelInstance;

    private boolean rightHanded;
    private ModelName weapon;


    public MafiaMobBody(Vector3 position, Vector3 rotation, boolean rightHanded, ModelName weapon) {
        modelInstance = Main.modelHandler.getModel(ModelName.EnemyBody);
        this.rightHanded = rightHanded;
        this.weapon = weapon;

        modelInstance.transform.setToTranslation(position);
        modelInstance.transform.rotate(1, 0, 0, rotation.x);
        modelInstance.transform.rotate(0, 1, 0, rotation.y);
        modelInstance.transform.rotate(0, 0, 1, rotation.z);
        modelInstance.transform.translate(0, 1.8f, 0);

        head = new BodyPart(new Vector3(0f, 3.45f, 0f), new Vector3(), 1, Main.modelHandler.getModel(ModelName.EnemyHead));

        leftArm = new BodyPart(new Vector3(0.65f, 3.2f, 0), new Vector3(), 1, Main.modelHandler.getModel(ModelName.UpperArm));
        leftArm.child = new BodyPart(new Vector3(0, -0.9f, 0), new Vector3(), 1, Main.modelHandler.getModel(ModelName.LowerLeftArm));

        rightArm = new BodyPart(new Vector3(-0.65f, 3.2f, 0), new Vector3(), 1, Main.modelHandler.getModel(ModelName.UpperArm));
        rightArm.child = new BodyPart(new Vector3(0, -0.9f, 0), new Vector3(), 1, Main.modelHandler.getModel(ModelName.LowerRightArm));

        leftLeg = new BodyPart(new Vector3(0.21f, 1.8f, -0.015f), new Vector3(), 1, Main.modelHandler.getModel(ModelName.UpperLeg));
        leftLeg.child = new BodyPart(new Vector3(-0.01f, -0.9f, 0), new Vector3(), 1, Main.modelHandler.getModel(ModelName.LowerLeg));

        rightLeg = new BodyPart(new Vector3(-0.21f, 1.8f, -0.015f), new Vector3(), 1, Main.modelHandler.getModel(ModelName.UpperLeg));
        rightLeg.child = new BodyPart(new Vector3(-0.01f, -0.9f, 0), new Vector3(), 1, Main.modelHandler.getModel(ModelName.LowerLeg));

        if(weapon != null) {
            giveWeapon(weapon);
        }

        this.position = position;
        this.rotation = rotation;
        this.baseRotation = new Vector3(rotation);

        resetPosition();
    }

    public void rotateToFace(Vector3 facePosition) {
        float angle = MathUtils.atan2(facePosition.z-position.z, facePosition.x-position.x);
        float yAngle = angle*(360/MathUtils.PI2);
        rotation.set(0, -(baseRotation.y+yAngle), 0);
    }

    public void giveWeapon(ModelName weapon) {
        if(rightHanded) {
            rightArm.child.child = new BodyPart(new Vector3(0.05f, -0.8f, -0.65f), new Vector3(90, 0, 0), 0.5f, Main.modelHandler.getModel(weapon));
        } else {
            leftArm.child.child = new BodyPart(new Vector3(0.05f, -0.8f, -0.65f), new Vector3(90, 0, 0), 0.5f, Main.modelHandler.getModel(weapon));
        }
    }

    public void resetPosition() {
        modelInstance.transform.setToTranslation(position);
        modelInstance.transform.rotate(1, 0, 0, rotation.x);
        modelInstance.transform.rotate(0, 1, 0, -rotation.y+90);
        modelInstance.transform.rotate(0, 0, 1, rotation.z);
        modelInstance.transform.translate(0, 1.8f, 0);

        head.resetPosition(new Vector3(position));
        leftArm.resetPosition(new Vector3(position));
        rightArm.resetPosition(new Vector3(position));
        leftLeg.resetPosition(new Vector3(position));
        rightLeg.resetPosition(new Vector3(position));

        head.updatePosition(new Vector3(rotation.x, -rotation.y+90, rotation.z));
        leftArm.updatePosition(new Vector3(rotation.x, -rotation.y+90, rotation.z));
        rightArm.updatePosition(new Vector3(rotation.x, -rotation.y+90, rotation.z));
        leftLeg.updatePosition(new Vector3(rotation.x, -rotation.y+90, rotation.z));
        rightLeg.updatePosition(new Vector3(rotation.x, -rotation.y+90, rotation.z));
    }

    public void render(Renderer renderer) {
        renderer.renderModel(modelInstance);
        head.render(renderer);
        leftArm.render(renderer);
        rightArm.render(renderer);
        leftLeg.render(renderer);
        rightLeg.render(renderer);
    }
}
