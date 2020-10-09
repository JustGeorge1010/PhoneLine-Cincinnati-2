package com.phonelinecincinnati.game.Animations.HumanoidAnimation;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Body;

public class SwingingWeapon extends HumanoidAnimation {
    private Vector3 firstRightArmRot = new Vector3(-10, 0, 5);
    private Vector3 firstRightArmChildRot = new Vector3(-70, -20, 20);
    private Vector3 firstLeftArmRot = new Vector3(-30, 0, -10);
    private Vector3 firstLeftArmChildRot = new Vector3(-35, -30, -40);

    private Vector3 secondRightArmRot = new Vector3(-10, 0, -10);
    private Vector3 secondRightArmChildRot = new Vector3(-120, -50, -10);
    private Vector3 secondLeftArmRot = new Vector3(-50, 0, -30);
    private Vector3 secondLeftArmChildRot = new Vector3(-50, -20, -30);

    private Vector3 thirdRightArmRot = new Vector3(-30, 0, 10);
    private Vector3 thirdRightArmChildRot = new Vector3(-35, 20, 40);
    private Vector3 thirdLeftArmRot = new Vector3(-10, 0, -5);
    private Vector3 thirdLeftArmChildRot = new Vector3(-70, 20, -20);

    private boolean playingAnim = false;
    private float speed = 5f;

    private Vector3 currentRightArmRot, currentRightArmChildRot, currentLeftArmRot, currentLeftArmChildRot;

    public SwingingWeapon(Body body) {
        super(body);

        body.rightArm.setTargetRotation(firstRightArmRot, 2f);
        body.rightArm.child.setTargetRotation(firstRightArmChildRot, 2f);
        body.leftArm.setTargetRotation(firstLeftArmRot, 2f);
        body.leftArm.child.setTargetRotation(firstLeftArmChildRot, 2f);
    }

    @Override
    public void playAnim() {
        playingAnim = true;
        currentRightArmRot = secondRightArmRot;
        currentRightArmChildRot = secondRightArmChildRot;
        currentLeftArmRot = secondLeftArmRot;
        currentLeftArmChildRot = secondLeftArmChildRot;
    }

    @Override
    public void update() {
        if(playingAnim) {
            body.rightArm.setTargetRotation(currentRightArmRot, speed);
            body.rightArm.child.setTargetRotation(currentRightArmChildRot, speed);
            body.leftArm.setTargetRotation(currentLeftArmRot, speed);
            body.leftArm.child.setTargetRotation(currentLeftArmChildRot, speed);

            if(rotationMatchesThird()) {
                currentRightArmRot = thirdRightArmRot;
                currentRightArmChildRot = thirdRightArmChildRot;
                currentLeftArmRot = thirdLeftArmRot;
                currentLeftArmChildRot = thirdLeftArmChildRot;
                speed = 10f;
            }
        }
    }

    private boolean rotationMatchesThird() {
        boolean a = body.rightArm.getRotation().toString().equals(currentRightArmRot.toString());
        boolean b = body.rightArm.child.getRotation().toString().equals(currentRightArmChildRot.toString());
        boolean c = body.leftArm.getRotation().toString().equals(currentLeftArmRot.toString());
        boolean d = body.leftArm.child.getRotation().toString().equals(currentLeftArmChildRot.toString());

        return a && b && c && d;
    }
}
