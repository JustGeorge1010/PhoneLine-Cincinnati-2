package com.phonelinecincinnati.game.GameObjects.Objects.Gore;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Plane;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class BloodSplat extends GameObject {
    private boolean collided = false;
    private Decal decal;

    public BloodSplat(Vector3 position) { //TODO: make
        this.position = position;

        TextureRegion textureRegion = new TextureRegion(Main.modelHandler.textures.get(TextureName.Dirt1));
        decal = Decal.newDecal(2, 2, textureRegion, true);
        decal.rotateX(90); //decal.rotateY(rotation.y); decal.rotateZ(rotation.z);
        decal.translate(position);
    }

    @Override
    public void update() {
        if(!collided) {
            position.y -= 0.01f;
            decal.setPosition(position);
            for(GameObject object : Main.levelHandler.getActiveObjects()) {
                if(object.getClass() == Plane.class) {
                    Plane plane = (Plane)object;
                    if(position.x > plane.position.x && position.x < plane.position.x+plane.size.x) {
                        if(position.z > plane.position.z && position.z < plane.position.z+plane.size.z) {
                            if(position.y < plane.position.y+(plane.size.y/2) && position.y > plane.position.y-(plane.size.y/2)) {
                                position.y = plane.position.y+(plane.size.y/2)+0.02f;
                                collided = true;
                                decal.setPosition(position);
                            }
                        }
                    }
                }
            }
        }
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
        return new ArrayList<String>();
    }
}
