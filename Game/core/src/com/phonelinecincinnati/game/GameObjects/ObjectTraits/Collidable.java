package com.phonelinecincinnati.game.GameObjects.ObjectTraits;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;

public interface Collidable {
    boolean inBounds(GameObject object, Class c);
    boolean inBounds(Vector3 position, Class c);

    float minX();
    float maxX();
    float minY();
    float maxY();
    float minZ();
    float maxZ();
}
