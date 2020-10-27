package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.CollisionMaths;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Threshold extends GameObject implements Collidable {
    Vector3 position, size;

    public Threshold(Vector3 position, Vector3 size) {
        this.position = position;
        this.size = size;
    }

    public Threshold(ArrayList<String> params) {
        this.position = VectorMaths.constructFromString(params.get(0));
        this.size = VectorMaths.constructFromString(params.get(1));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {

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
        return params;
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.position, c);
    }

    @Override
    public boolean inBounds(Vector3 position, Class c) {
        if(position.x > this.position.x && position.x < this.position.x+size.x &&
                position.y > this.position.y && position.y < this.position.y+size.y &&
                position.z > this.position.z && position.z < this.position.z+size.z)
        {
            return true;
        }
        return false;
    }

    @Override
    public float minX() {
        return position.x;
    }

    @Override
    public float maxX() {
        return position.x+size.x;
    }

    @Override
    public float minY() {
        return position.y;
    }

    @Override
    public float maxY() {
        return position.y+size.y;
    }

    @Override
    public float minZ() {
        return position.z;
    }

    @Override
    public float maxZ() {
        return position.z+size.z;
    }
}
