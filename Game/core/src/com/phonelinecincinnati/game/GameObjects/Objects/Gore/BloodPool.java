package com.phonelinecincinnati.game.GameObjects.Objects.Gore;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;

public class BloodPool extends GameObject {
    private Vector3 rotation;
    private int blindex, timer;
    private Decal decal;

    public BloodPool(Vector3 position, Vector3 rotation) {
        this.position = position;
        this.rotation = new Vector3(90, rotation.y, 0);

        blindex = 0;
        timer = 0;
        updateDecal();
    }

    @Override
    public void update() {
        if(blindex < 62) {
            if(timer > 5) {
                timer = 0;
                blindex += 1;
                updateDecal();
            } else {
                timer++;
            }
        }
    }

    private void updateDecal() {
        int x = 32*blindex;
        TextureRegion textureRegion = new TextureRegion(Main.modelHandler.textures.get(TextureName.BloodPool), x, 0, 32, 32);
        decal = Decal.newDecal(4, 4, textureRegion, true);
        decal.rotateY(rotation.y);
        decal.rotateX(rotation.x); decal.rotateZ(rotation.z);
        decal.translate(position);
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
