package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class Plane extends GameObject {
    private final ModelInstance modelInstance;
    public Vector3 size;
    private final TextureName textureName;

    public Plane(Vector3 position, Vector3 size, TextureName name) {
        this.position = position;
        this.size = size;
        this.textureName = name;

        modelInstance = Main.modelHandler.getBox(position.x+size.x/2, position.y, position.z+size.z/2, size.x, size.y, size.z, textureName);
    }

    public Plane(ArrayList<String> params) {
        this.position = VectorMaths.constructFromString(params.get(0));
        this.size = VectorMaths.constructFromString(params.get(1));
        this.textureName = TextureName.valueOf(params.get(2));

        modelInstance = Main.modelHandler.getBox(position.x+size.x/2, position.y, position.z+size.z/2, size.x, size.y, size.z, textureName);
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

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(position.toString());
        params.add(size.toString());
        params.add(textureName.toString());
        return params;
    }
}
