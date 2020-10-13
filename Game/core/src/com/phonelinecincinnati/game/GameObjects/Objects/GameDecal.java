package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class GameDecal extends GameObject{
    private final Decal decal;
    private final Vector3 rotation;
    private final TextureName textureType;

    public GameDecal(Vector3 position, float width, float height, Vector3 rotation, TextureName textureType) {
        this.rotation = rotation;
        this.textureType = textureType;
        TextureRegion textureRegion = new TextureRegion(Main.modelHandler.textures.get(textureType));
        decal = Decal.newDecal(width, height, textureRegion, true);
        decal.rotateX(rotation.x); decal.rotateY(rotation.y); decal.rotateZ(rotation.z);
        decal.translate(position);
    }

    public GameDecal(ArrayList<String> params) {
        this.rotation = VectorMaths.constructFromString(params.get(3));
        this.textureType = TextureName.valueOf(params.get(4));
        TextureRegion textureRegion = new TextureRegion(Main.modelHandler.textures.get(textureType));
        decal = Decal.newDecal(Float.parseFloat(params.get(1)), Float.parseFloat(params.get(2)), textureRegion, true);
        decal.rotateX(rotation.x); decal.rotateY(rotation.y); decal.rotateZ(rotation.z);
        decal.translate(VectorMaths.constructFromString(params.get(0)));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.decalBatch.add(decal);
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
        params.add(String.valueOf(decal.getWidth()));
        params.add(String.valueOf(decal.getHeight()));
        params.add(rotation.toString());
        params.add(textureType.toString());
        return params;
    }
}
