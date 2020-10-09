package com.phonelinecincinnati.game.Animations.HumanoidAnimation;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Body;

public class Stand extends HumanoidAnimation {

    public Stand(Body body) {
        super(body);
    }

    @Override
    public void update() {
        body.leftLeg.setTargetRotation(new Vector3(0, 0, 0), 6f);
        body.leftLeg.child.setTargetRotation(new Vector3(0, 0, 0), 10f);
        body.rightLeg.setTargetRotation(new Vector3(0, 0, 0), 6f);
        body.rightLeg.child.setTargetRotation(new Vector3(0, 0, 0), 10f);
    }
}
