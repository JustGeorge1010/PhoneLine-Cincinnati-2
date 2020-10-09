package com.phonelinecincinnati.game.Animations.HumanoidAnimation;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Body;

public class Move extends HumanoidAnimation{

    private int legStage = 1, bodyStage = 1;
    private float maxY, minY, speed, bobSpeed;

    public Move(Body body, float speed) {
        super(body);

        maxY = body.position.y+0.1f;
        minY = body.position.y-0.1f;

        this.speed = speed;
        bobSpeed = speed/300;
    }

    @Override
    public void update() {
        if(bodyStage == 1 && body.position.y < maxY) {
            body.position.y += bobSpeed;
        } else {
            bodyStage = 2;
        }
        if(bodyStage == 2 && body.position.y > minY) {
            body.position.y -= bobSpeed;
        } else {
            bodyStage = 1;
        }

        if(legStage == 1) {
            body.leftLeg.setTargetRotation(new Vector3(-70, 0, 0), speed);
            body.leftLeg.child.setTargetRotation(new Vector3(90, 0, 0), speed+2f);
            body.rightLeg.setTargetRotation(new Vector3(40, 0, 0), speed);
            body.rightLeg.child.setTargetRotation(new Vector3(0, 0, 0), speed+2f);
            if(body.leftLeg.getRotation().x == -70) {
                if(body.leftLeg.child.getRotation().x == 90) {
                    if(body.rightLeg.getRotation().x == 40) {
                        legStage = 2;
                    }
                }
            }
        } else if(legStage == 2) {
            body.rightLeg.setTargetRotation(new Vector3(-70, 0, 0), speed);
            body.rightLeg.child.setTargetRotation(new Vector3(90, 0, 0), speed+2f);
            body.leftLeg.setTargetRotation(new Vector3(40, 0, 0), speed);
            body.leftLeg.child.setTargetRotation(new Vector3(0, 0, 0), speed+2f);
            if(body.rightLeg.getRotation().x == -70) {
                if(body.rightLeg.child.getRotation().x == 90) {
                    if(body.leftLeg.getRotation().x == 40) {
                        legStage = 1;
                    }
                }
            }
        }
    }
}
