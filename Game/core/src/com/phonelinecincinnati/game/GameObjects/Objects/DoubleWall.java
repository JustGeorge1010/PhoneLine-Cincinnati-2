package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class DoubleWall extends GameObject implements Collidable {
    private final Vector3 spawnPosition;
    private final Vector3 size;
    private final TextureName primary, secondary;
    private ModelInstance first = null, second = null;
    BoundingBox boundingBox;

    //Todo: rename to wall after old is removed
    public DoubleWall(Vector3 position, Vector3 size, TextureName primary, TextureName secondary) {
        this.position = position;
        this.spawnPosition = position.cpy();
        this.size = size;
        this.primary = primary;
        this.secondary = secondary;

        setup();
    }

    public DoubleWall(ArrayList<String> params) {
        this.position = VectorMaths.constructFromString(params.get(0));
        this.spawnPosition = position.cpy();
        this.size = VectorMaths.constructFromString(params.get(1));
        this.primary = TextureName.valueOf(params.get(2));
        this.secondary = TextureName.valueOf(params.get(3));

        setup();
    }

    private void setup() {
        if(size.x > size.z) {
            position.x += size.x/2;
            first = Main.modelHandler.getBox(position.x, position.y+size.y/2, position.z-size.z/4,
                    size.x, size.y, size.z/2, primary);
            second = Main.modelHandler.getBox(position.x, position.y+size.y/2, position.z+size.z/4,
                    size.x, size.y, size.z/2, secondary);
        } else {
            position.z += size.z/2;
            first = Main.modelHandler.getBox(position.x-size.x/4, position.y+size.y/2, position.z,
                    size.x/2, size.y, size.z, primary);
            second = Main.modelHandler.getBox(position.x+size.x/4, position.y+size.y/2, position.z,
                    size.x/2, size.y, size.z, secondary);
        }

        ModelInstance bounds = Main.modelHandler.getBox(position.x, position.y+size.y/2, position.z,
                size.x, size.y, size.z, primary);
        boundingBox = new BoundingBox();
        bounds.calculateBoundingBox(boundingBox);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderModel(first);
        renderer.renderModel(second);
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
        params.add(primary.toString());
        params.add(secondary.toString());
        return params;
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.getPosition(), c);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean inBounds(Vector3 position, Class c) {
        float upperX = (this.position.x+boundingBox.getWidth()/2)+0.5f;
        float lowerX = (this.position.x-boundingBox.getWidth()/2)-0.5f;
        float upperZ = (this.position.z+boundingBox.getDepth()/2)+0.5f;
        float lowerZ = (this.position.z-boundingBox.getDepth()/2)-0.5f;

        if(position.x < upperX && position.x > lowerX) {
            if(position.z < upperZ && position.z > lowerZ) {
                if(position.y < this.position.y+boundingBox.getHeight() && position.y > this.position.y-0.5f)
                    return true;
            }
        }
        return false;
    }

    @Override
    public float minX() {
        return this.position.x-(boundingBox.getWidth()/2)-0.5f;
    }

    @Override
    public float maxX() {
        return this.position.x+(boundingBox.getWidth()/2)+0.5f;
    }

    @Override
    public float minY() {
        return this.position.y;
    }

    @Override
    public float maxY() {
        return this.position.y+boundingBox.getHeight();
    }

    @Override
    public float minZ() {
        return this.position.z-(boundingBox.getDepth()/2)-0.5f;
    }

    @Override
    public float maxZ() {
        return this.position.z+(boundingBox.getDepth()/2)+0.5f;
    }
}
