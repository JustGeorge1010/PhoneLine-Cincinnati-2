package com.phonelinecincinnati.game.GameObjects.Objects.Vertical;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;

public class Step{
    private ModelInstance modelInstance;
    private BoundingBox box;

    private Vector3 position;
    private float rotation;
    private float y;

    public Step(Vector3 position, float rotation) {
        this.position = new Vector3(position);
        this.rotation = rotation;

        box = new BoundingBox();
        y = position.y;

        modelInstance = Main.modelHandler.getModel(ModelName.Step);
        modelInstance.transform.translate(position);
        modelInstance.transform.rotate(0, 1, 0, rotation);

        box = new BoundingBox();
        modelInstance.calculateBoundingBox(box);
    }

    float getY() {
        return y;
    }

    float getDepth() {
        return box.getDepth();
    }

    float getWidth() {
        return box.getWidth();
    }

    boolean inBounds(Vector3 position) {
        float x = this.position.x;
        float z = this.position.z;

        if(rotation == 90) {
            x = x-box.getDepth();
        }

        if(position.x > x && position.z > z) {
            if(position.x < x+box.getDepth() && position.z < z+box.getWidth()) {
                return true;
            }
        }
        return false;
    }

    void render(Renderer renderer) {
        renderer.renderModel(modelInstance);
    }
}
