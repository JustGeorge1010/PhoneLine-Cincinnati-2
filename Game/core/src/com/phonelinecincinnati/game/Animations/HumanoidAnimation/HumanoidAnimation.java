package com.phonelinecincinnati.game.Animations.HumanoidAnimation;

import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Body;

public abstract class HumanoidAnimation {
    Body body;

    HumanoidAnimation(Body body) {
        this.body = body;
    }

    public void playAnim() {
        //Unimplemented, optional override
    }

    public abstract void update();
}
