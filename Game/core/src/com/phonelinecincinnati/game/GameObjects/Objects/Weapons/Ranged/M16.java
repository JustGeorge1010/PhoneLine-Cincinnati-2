package com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged;

import com.phonelinecincinnati.game.Animations.WeaponAnimations.RifleFire;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.WeaponType;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

public class M16 extends Ranged {
    public M16() {
        name = ModelName.M16;
        type = WeaponType.Automatic;
        rounds = 32;
        rateOfFire = 100f;

        model = Main.modelHandler.getModel(name);

        attackAnim = new RifleFire(model, true);
        attackAnim.setUp();
    }
}
