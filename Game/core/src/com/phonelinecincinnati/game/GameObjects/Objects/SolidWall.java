package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class SolidWall extends Wall implements Collidable {
    //Todo: remove and fix
    public SolidWall(Vector3 position, Vector3 size, TextureName name) {
        super(position, size, name);
    }

    public SolidWall(ArrayList<String> params) {
        super(VectorMaths.constructFromString(params.get(0)), VectorMaths.constructFromString(params.get(1)), TextureName.valueOf(params.get(2)));
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
                if(position.y < this.position.y+boundingBox.getHeight() && position.y > this.position.y)
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
