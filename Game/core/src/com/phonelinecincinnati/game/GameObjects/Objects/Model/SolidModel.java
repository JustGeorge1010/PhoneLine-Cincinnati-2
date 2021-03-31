package com.phonelinecincinnati.game.GameObjects.Objects.Model;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class SolidModel extends Model implements Collidable{

    public SolidModel(Vector3 position, float rotation, ModelName name) {
        super(position, rotation, name);
    }

    public SolidModel(ArrayList<String> params) {
        super(VectorMaths.constructFromString(params.get(0)), Float.parseFloat(params.get(1)), ModelName.valueOf(params.get(2)));
    }

    @Override
    public void update() {
    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {

    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.getPosition(), c);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean inBounds(Vector3 position, Class c) {
        float upperY = this.position.y+boundingBox.getHeight();
        float lowerY = this.position.y-0.5f;
        float upperX = (this.position.x+boundingBox.getWidth()/2)+0.5f;
        float lowerX = (this.position.x-boundingBox.getWidth()/2)-0.5f;
        float upperZ = (this.position.z+boundingBox.getDepth()/2)+0.5f;
        float lowerZ = (this.position.z-boundingBox.getDepth()/2)-0.5f;

        if(position.y > lowerY && position.y < upperY ) {
            if(position.x < upperX && position.x > lowerX) {
                if(position.z < upperZ && position.z > lowerZ) {
                    return true;
                }
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
