package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Renderer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class GameObject {
    public Vector3 position;

    @SuppressWarnings("unchecked")
    public static GameObject constructFromClassName(String name, ArrayList<String> params)
            throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {

        Class objectClass = Class.forName(name);
        Constructor<?> constructor = objectClass.getConstructor(ArrayList.class);
        return (GameObject)constructor.newInstance(params);
    }

    public abstract void update();
    public abstract void render(Renderer renderer);
    public abstract void postRender(Renderer renderer);
    public abstract void dispose();
    public abstract ArrayList<String> getConstructParams();

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
