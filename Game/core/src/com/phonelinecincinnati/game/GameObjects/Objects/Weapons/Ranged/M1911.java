package com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged;

import com.phonelinecincinnati.game.Animations.WeaponAnimations.PistolFire;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.WeaponType;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;

public class M1911 extends Ranged{
    public M1911() {
        name = ModelName.M1911;
        type = WeaponType.SemiAutomatic;
        rounds = 14;
        rateOfFire = 150f;

        model = Main.modelHandler.getModel(name);

        attackAnim = new PistolFire(model, true);
        attackAnim.setUp();
    }
}
