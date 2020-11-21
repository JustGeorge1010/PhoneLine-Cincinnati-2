package com.phonelinecincinnati.game.Animations.WeaponAnimations;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;

public class SwingWeaponAnimation extends WeaponAnimation {
    private int side = 1; //1 = left, 2 = right
    private int animStage = 3;

    private SoundSource swing1, swing2;

    public SwingWeaponAnimation(ModelInstance model, boolean canBeInterrupted) {
        super(model, canBeInterrupted);

        swing1 = SoundSource.buildSoundSource(1).setSound("Combat/Swing1.wav");
        swing2 = SoundSource.buildSoundSource(2).setSound("Combat/Swing2.wav");
    }

    @Override
    public void setUp() {
        side = 1;
        model.transform.setToTranslation(-0.9f, -1.25f, 0.1f);
        model.transform.scale(0.4f, 0.4f, 0.4f);
        model.transform.rotate(0, 0, 1, 10);
    }

    @Override
    public void animPlay() {
        if(animStage == 3) {
            animStage = 0;
            if(side == 2) {
                swing2.playSound();
                side = 1;
            } else {
                side = 2;
                swing1.playSound();
            }
        }
    }

    @Override
    public void update() {
        if(side == 2) {
            if(animStage == 0) {
                rightP0();
            } else if(animStage == 1) {
                rightP1();
            } else if(animStage == 2) {
                rightP2();
            }
        } else {
            if(animStage == 0) {
                leftP0();
            } else if(animStage == 1) {
                leftP1();
            } else if(animStage == 2) {
                leftP2();
            }
        }
    }

    private void rightP0() {
        Vector3 currentPos = new Vector3();
        model.transform.getTranslation(currentPos);
        if(currentPos.x < -1.5f) {
            animStage = 1;
            model.transform.setToTranslation(-1.5f, -1f, 0.1f);
            model.transform.scale(0.4f, 0.4f, 0.4f);
            model.transform.rotate(0, 1, 0, -40);
            model.transform.rotate(0, 0, 1, 40);
        } else {
            model.transform.translate(-0.3f, 0, 0);
        }
    }
    private void rightP1() {
        Quaternion currentRot = new Quaternion();
        model.transform.getRotation(currentRot);
        float yRot = currentRot.getAngleAround(0, 1, 0);
        if(yRot > 205 && yRot < 215) {
            animStage = 2;
            model.transform.setToTranslation(1.5f, -1.25f, 0.1f);
            model.transform.scale(0.4f, 0.4f, 0.4f);
            model.transform.rotate(0, 0, 1, -10);
        } else {
            model.transform.rotate(0, 0, 1, -40);
            model.transform.translate(0.6f, 0, 0);
            model.transform.rotate(0, 1, 0, 10);
            model.transform.rotate(0, 0, 1, 40);
        }
    }
    private void rightP2() {
        Vector3 currentPos = new Vector3();
        model.transform.getTranslation(currentPos);
        if(currentPos.x < 0.9f) {
            animStage = 3;
        } else {
            model.transform.translate(-0.2f, 0, 0);
        }
    }

    private void leftP0() {
        Vector3 currentPos = new Vector3();
        model.transform.getTranslation(currentPos);
        if(currentPos.x > 1.5f) {
            animStage = 1;
            model.transform.setToTranslation(1.5f, -1f, 0.1f);
            model.transform.scale(0.4f, 0.4f, 0.4f);
            model.transform.rotate(0, 1, 0, 205);
            model.transform.rotate(0, 0, 1, 40);
        } else {
            model.transform.translate(0.3f, 0, 0);
        }
    }
    private void leftP1() {
        Quaternion currentRot = new Quaternion();
        model.transform.getRotation(currentRot);
        float yRot = currentRot.getAngleAround(0, 1, 0);
        if(yRot < 330 && yRot > 320) {
            animStage = 2;
            model.transform.setToTranslation(-1.5f, -1.25f, 0.1f);
            model.transform.scale(0.4f, 0.4f, 0.4f);
            model.transform.rotate(0, 0, 1, 10);
        } else {
            model.transform.rotate(0, 0, 1, -40);
            model.transform.translate(0.6f, 0, 0);
            model.transform.rotate(0, 1, 0, -10);
            model.transform.rotate(0, 0, 1, 40);
        }
    }
    private void leftP2() {
        Vector3 currentPos = new Vector3();
        model.transform.getTranslation(currentPos);
        if(currentPos.x > -0.9f) {
            animStage = 3;
        } else {
            model.transform.translate(0.2f, 0, 0);
        }
    }

}
