package com.phonelinecincinnati.game.Animations.WeaponAnimations;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;

public class PistolFire extends RifleFire {

    public PistolFire(ModelInstance model, boolean canBeInterrupted) {
        super(model, canBeInterrupted);

        gunshot = SoundSource.buildSoundSource(1).setSound("Combat/shootM1911.wav");
    }
}
