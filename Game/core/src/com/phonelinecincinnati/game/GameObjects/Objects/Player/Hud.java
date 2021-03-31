package com.phonelinecincinnati.game.GameObjects.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.Ranged;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.MovingText;

public class Hud {
    String pickupText = "";
    String interactionText = "";

    private Weapon weapon;
    private Sprite ammo;
    private MovingText movingText;

    Hud() {
        ammo = new Sprite(Main.modelHandler.textures.get(TextureName.Ammo));
        movingText = new MovingText();
    }

    void update(Weapon weapon) {
        if(weapon == null) {
            this.weapon = null;
            return;
        }
        if(weapon != this.weapon) {
            this.weapon = weapon;
        }
        movingText.update();
    }

    void render(Renderer renderer) {
        String text;
        if(!pickupText.equals("")) {
            text = pickupText;
        } else {
            text = interactionText;
        }
        renderer.renderText(10, Gdx.graphics.getHeight()-10, text, renderer.scriptFont);

        if(weapon == null)
            return;

        if(Ranged.class.isAssignableFrom(weapon.getClass())) {
            Ranged ranged = (Ranged)weapon;
            renderer.renderSprite(10, 5, Gdx.graphics.getWidth()/18, Gdx.graphics.getWidth()/18, ammo);
            movingText.render(renderer, 110, 100, String.valueOf(ranged.rounds));
        }
    }
}
