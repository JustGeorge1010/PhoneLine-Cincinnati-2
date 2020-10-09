package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;

public class GameDecal extends GameObject{
    private Decal decal;

    public GameDecal(Vector3 position, float width, float height, Vector3 rotation, TextureName textureType) {
        TextureRegion textureRegion = new TextureRegion(Main.modelHandler.textures.get(textureType));
        decal = Decal.newDecal(width, height, textureRegion, true);
        decal.rotateX(rotation.x); decal.rotateY(rotation.y); decal.rotateZ(rotation.z);
        decal.translate(position);
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
}
