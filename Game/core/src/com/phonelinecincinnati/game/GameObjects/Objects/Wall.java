package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class Wall extends GameObject{
    private final Vector3 spawnPosition;
    private final Vector3 size;
    private final TextureName textureName;
    private ModelInstance modelInstance = null;
    BoundingBox boundingBox;

    //Todo: remove and fix
    public Wall(Vector3 position, Vector3 size, TextureName name) {
        this.position = position;
        this.spawnPosition = position.cpy();
        this.size = size;
        this.textureName = name;

        setup();
    }

    public Wall(ArrayList<String> params) {
        this.position = VectorMaths.constructFromString(params.get(0));
        this.spawnPosition = position.cpy();
        this.size = VectorMaths.constructFromString(params.get(1));
        this.textureName = TextureName.valueOf(params.get(2));

        setup();
    }

    private void setup() {
        if(size.x > size.z) {
            position.x += size.x/2;
        } else {
            position.z += size.z/2;
        }

        modelInstance = Main.modelHandler.getBox(position.x, position.y+size.y/2, position.z, size.x, size.y, size.z, textureName);
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

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(spawnPosition.toString());
        params.add(size.toString());
        params.add(textureName.toString());
        return params;
    }
}
