package com.phonelinecincinnati.game.GameObjects.Objects.Enemies;

import com.badlogic.gdx.math.Vector3;

public abstract class Body {
    public BodyPart head, leftArm, rightArm, leftLeg, rightLeg;
    public Vector3 position, rotation, baseRotation;
    private boolean activated = false;

    public void activate() {
        activated = true;
    }
}
