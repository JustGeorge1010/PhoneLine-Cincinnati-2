package com.phonelinecincinnati.game.GameObjects.Objects.Model;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Models.ModelName;

public class InteractiveSolidModel extends InteractiveModel implements Collidable {
    public InteractiveSolidModel(Vector3 position, float rotation, ModelName name) {
        super(position, rotation, name);
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.getPosition(), c);
    }

    @SuppressWarnings({"Duplicates", "RedundantIfStatement"})
    @Override
    public boolean inBounds(Vector3 position, Class c) {
        float upperX = (this.position.x+boundingBox.getWidth()/2);
        float lowerX = (this.position.x-boundingBox.getWidth()/2);
        float upperZ = (this.position.z+boundingBox.getDepth()/2);
        float lowerZ = (this.position.z-boundingBox.getDepth()/2);

        if(position.x < upperX && position.x > lowerX) {
            if(position.z < upperZ && position.z > lowerZ) {
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
