package com.phonelinecincinnati.game.Animations.WeaponAnimations;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

public abstract class WeaponAnimation {
    ModelInstance model;
    private boolean canBeInterrupted;

    WeaponAnimation(ModelInstance model, boolean canBeInterrupted) {
        this.model = model;
        this.canBeInterrupted = canBeInterrupted;
    }

    public abstract void setUp();
    public abstract void animPlay();
    public abstract void update();
}
