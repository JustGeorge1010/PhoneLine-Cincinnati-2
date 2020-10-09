package com.phonelinecincinnati.game.GameObjects.Objects.Enemies;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Renderer;

public class BodyPart {
    public BodyPart child;
    private Vector3 position, rotation, rotationTarget;
    private float rotationSpeed, scale;
    private ModelInstance modelInstance;

    public BodyPart(Vector3 position, Vector3 rotation, float scale, ModelInstance modelInstance) {
        this.position = position;
        this.rotation = rotation;
        this.rotationTarget = new Vector3(rotation);
        this.scale = scale;
        this.modelInstance = modelInstance;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public void setTargetRotation(Vector3 target, float speed) {
        this.rotationTarget = target;
        this.rotationSpeed = speed;
    }

    public void rotate(Vector3 rotation) {
        this.rotation.add(rotation);
    }

    public void resetPosition(Vector3 bodyPosition) {
        modelInstance.transform.setToTranslation(bodyPosition);

        if(child != null) {
            child.resetPosition(bodyPosition);
        }
    }

    public void updatePosition(Vector3 parentRotation) {
        handleRotation();

        BodyPart part = this;
        while(part != null) {
            part.modelInstance.transform.rotate(0, 1, 0, parentRotation.y);
            part.modelInstance.transform.translate(position);
            part.modelInstance.transform.rotate(1, 0, 0, rotation.x);
            part.modelInstance.transform.rotate(0, 1, 0, rotation.y);
            part.modelInstance.transform.rotate(0, 0, 1, rotation.z);
            part.modelInstance.transform.scale(scale, scale, scale);

            part = part.child;
        }

        if(child != null) {
            child.updatePosition(new Vector3());
        }
    }

    private void handleRotation() {
        float rotX = rotationHelper(true, false);
        float rotY = rotationHelper(false, true);
        float rotZ = rotationHelper(false, false);

        rotate(new Vector3(rotX, rotY, rotZ));
    }
    private float rotationHelper(boolean x, boolean y) {
        float difference, returnValue = 0;
        if(x) {
            difference = rotationTarget.x - rotation.x;
        } else if(y) {
            difference = rotationTarget.y - rotation.y;
        } else {
            difference = rotationTarget.z - rotation.z;
        }

        if(difference > 0) {
            if(difference < rotationSpeed) {
                returnValue = difference;
            } else {
                returnValue = rotationSpeed;
            }
        } else if(difference < 0) {
            if(difference > -rotationSpeed) {
                returnValue = difference;
            } else {
                returnValue = -rotationSpeed;
            }
        }

        return returnValue;
    }

    public void render(Renderer renderer) {
        renderer.renderModel(modelInstance);

        if(child != null) {
            child.render(renderer);
        }
    }
}
