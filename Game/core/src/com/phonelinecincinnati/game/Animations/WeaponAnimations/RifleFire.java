package com.phonelinecincinnati.game.Animations.WeaponAnimations;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;

public class RifleFire extends WeaponAnimation {
    private SoundSource gunshot;
    private boolean fired = false;
    private boolean recoiling = false;

    public RifleFire(ModelInstance model, boolean canBeInterrupted) {
        super(model, canBeInterrupted);

        gunshot = SoundSource.buildSoundSource(1).setSound("Combat/shootM16.wav");
    }

    @Override
    public void setUp() {
        model.transform.setToTranslation(-2f, -2f, 0.1f);
        model.transform.rotate(1, 0, 0, 90);
        model.transform.rotate(0, 1, 0, -90);
    }

    @Override
    public void animPlay() {
        gunshot.playSound();
        fired = true;
    }

    @Override
    public void update() {
        if(fired) {
            setUp();
            recoiling = true;
            fired = false;
        }
        if(!recoiling) {
            reset();
            return;
        }
        Vector3 currentPos = new Vector3();
        model.transform.getTranslation(currentPos);
        if(currentPos.z > -1) {
            model.transform.setToTranslation(currentPos.x, currentPos.y+0.25f, currentPos.z-0.5f);
            model.transform.rotate(1, 0, 0, 90);
            model.transform.rotate(0, 1, 0, -90);
            model.transform.rotate(0, 0, 1, 1);
        } else {
            recoiling = false;
            model.transform.setToTranslation(-2f, -1.5f, -1f);
            model.transform.rotate(1, 0, 0, 90);
            model.transform.rotate(0, 1, 0, -90);
            model.transform.rotate(0, 0, 1, 10);
        }
    }

    private void reset() {
        Vector3 currentPos = new Vector3();
        model.transform.getTranslation(currentPos);
        if(currentPos == new Vector3(-2f, -2f, 0.1f)) {
            return;
        }
        if(currentPos.z < 0) {
            model.transform.setToTranslation(currentPos.x, currentPos.y-0.1f, currentPos.z+0.2f);
            model.transform.rotate(1, 0, 0, 90);
            model.transform.rotate(0, 1, 0, -90);
            model.transform.rotate(0, 0, 1, -1);
        } else {
            model.transform.setToTranslation(-2f, -2f, 0.1f);
            model.transform.rotate(1, 0, 0, 90);
            model.transform.rotate(0, 1, 0, -90);
        }
    }
}
