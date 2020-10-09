package com.phonelinecincinnati.game.Animations.WeaponAnimations;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;

public class OverheadSwingFinisher extends WeaponAnimation{
    private boolean down = false;
    private float swingRot = -90;
    private SoundSource swing1, swing2;
    private int sound = 1;

    public OverheadSwingFinisher(ModelInstance model, boolean canBeInterrupted) {
        super(model, canBeInterrupted);

        swing1 = SoundSource.buildSoundSource(1).setSound("Combat/Swing1.wav");
        swing2 = SoundSource.buildSoundSource(2).setSound("Combat/Swing2.wav");
    }

    @Override
    public void setUp() {
        model.transform.setToTranslation(0f, 0f, -1f);
        model.transform.rotate(1, 0, 0, -180);
        model.transform.translate(0f, -1f, 0f);
        model.transform.scale(0.4f, 0.4f, 0.4f);
        model.transform.rotate(1, 0, 0, 60);
    }

    @Override
    public void animPlay() {
        down = true;
        if(sound == 1) {
            swing1.playSound();
            sound = 2;
        } else {
            swing2.playSound();
            sound = 1;
        }
    }

    @Override
    public void update() {
        if(!down) {
            if(swingRot > -180) {
                swingRot -= 5;
                model.transform.setToTranslation(0f, 0f, -1f);
                model.transform.rotate(1, 0, 0, swingRot);
                model.transform.translate(0f, -1f, 0f);
                model.transform.scale(0.4f, 0.4f, 0.4f);
                model.transform.rotate(1, 0, 0, 60);
            }
        } else {
            swingRot += 20;
            model.transform.setToTranslation(0f, 0f, -1f);
            model.transform.rotate(1, 0, 0, swingRot);
            model.transform.translate(0f, -1f, 0f);
            model.transform.scale(0.4f, 0.4f, 0.4f);
            model.transform.rotate(1, 0, 0, 60);
            if(swingRot > 0) {
                down = false;
            }
        }
    }
}
