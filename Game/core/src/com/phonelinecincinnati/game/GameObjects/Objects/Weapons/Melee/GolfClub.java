package com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee;

import com.phonelinecincinnati.game.Animations.WeaponAnimations.OverheadSwingFinisher;
import com.phonelinecincinnati.game.Animations.WeaponAnimations.SwingWeaponAnimation;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.WeaponType;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

public class GolfClub extends Melee {
    public GolfClub() {
        name = ModelName.GolfClub;
        type = WeaponType.Blunt;
        model = Main.modelHandler.getModel(name);
        model.transform.setToTranslation(-0.9f, -1.25f, 0.1f);
        model.transform.scale(0.4f, 0.4f, 0.4f);
        model.transform.rotate(0, 0, 1, 10);
        reach = 5;
        attackAnim = new SwingWeaponAnimation(model, false);
        attackAnim.setUp();
        finisherAnim = new OverheadSwingFinisher(model, false);
    }
}
