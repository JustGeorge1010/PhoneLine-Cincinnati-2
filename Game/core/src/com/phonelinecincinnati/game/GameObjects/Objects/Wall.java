package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;

public class Wall extends GameObject{
    private ModelInstance modelInstance;
    BoundingBox boundingBox;

    public Wall(Vector3 position, Vector3 size, TextureName name) {
        this.position = position;

        if(size.x > size.z) {
            position.x += size.x/2;
        } else {
            position.z += size.z/2;
        }

        modelInstance = Main.modelHandler.getBox(position.x, position.y+size.y/2, position.z, size.x, size.y, size.z, name);
        boundingBox = new BoundingBox();
        modelInstance.calculateBoundingBox(boundingBox);
    }

    @Override
    public void update() {

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
}
