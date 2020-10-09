package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;

public class Plane extends GameObject {
    private ModelInstance modelInstance;
    public Vector3 size;

    public Plane(Vector3 position, Vector3 size, TextureName name) {
        this.position = position;
        this.size = size;

        modelInstance = Main.modelHandler.getBox(position.x+size.x/2, position.y, position.z+size.z/2, size.x, size.y, size.z, name);
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
