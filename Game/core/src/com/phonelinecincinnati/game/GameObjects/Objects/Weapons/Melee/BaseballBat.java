package com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Melee;

import com.phonelinecincinnati.game.Animations.WeaponAnimations.OverheadSwingFinisher;
import com.phonelinecincinnati.game.Animations.WeaponAnimations.SwingWeaponAnimation;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.WeaponType;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

public class BaseballBat extends Melee{

    public BaseballBat() {
        name = ModelName.Bat;
        type = WeaponType.Blunt;
        model = Main.modelHandler.getModel(name);
        reach = 5;
        attackAnim = new SwingWeaponAnimation(model, false);
        attackAnim.setUp();
        finisherAnim = new OverheadSwingFinisher(model, false);
    }

}
