package com.phonelinecincinnati.game.GameObjects.Objects.Weapons;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Animations.WeaponAnimations.WeaponAnimation;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Weapon {
    protected WeaponAnimation attackAnim, finisherAnim;
    protected ModelInstance model;
    public ModelName name;
    public WeaponType type;

    private boolean finishing = false;

    @SuppressWarnings("unchecked")
    public static Weapon constructFromClassName(String name)
            throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {

        Class objectClass = Class.forName(name);
        Constructor<?> constructor = objectClass.getConstructor();
        return (Weapon) constructor.newInstance();
    }

    public void switchType() {
        finishing = !finishing;
        if(!finishing) attackAnim.setUp();
        else finisherAnim.setUp();
    }

    public void use(final Vector3 locationOfUse, final Vector3 direction) {
        if(finishing) {
            finisherAnim.animPlay();
        } else {
            attackAnim.animPlay();
        }
    }

    public void update() {
        if(finishing) {
            finisherAnim.update();
        } else {
            attackAnim.update();
        }
    }

    public void render(Renderer renderer) {
        renderer.renderModelToScreen(model);
    }
}
