package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Renderer;

public abstract class GameObject {
    public Vector3 position;

    public abstract void update();
    public abstract void render(Renderer renderer);
    public abstract void postRender(Renderer renderer);
    public abstract void dispose();

    public void moveTo(Vector3 location) {
        position.set(location);
    }
    public void moveTo(float x, float y, float z) {
        position.set(x, y, z);
    }
    public Vector3 getPosition() {
        return position;
    }

}
