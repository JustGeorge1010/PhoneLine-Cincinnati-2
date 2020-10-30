package com.phonelinecincinnati.game.GameObjects.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.Ranged;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;

public class Hud {
    String pickupText = "";
    String interactionText = "";

    private Weapon weapon;
    private float offset = 0;
    private float direction = 1;

    private Sprite ammo;

    Hud() {
        ammo = new Sprite(Main.modelHandler.textures.get(TextureName.Ammo));
    }

    void update(Weapon weapon) {
        if(weapon == null) {
            this.weapon = null;
            return;
        }
        if(weapon != this.weapon) {
            this.weapon = weapon;
        }

        float maxOffset = 8;
        float speed = maxOffset/50;

        if(direction == 1) {
            if(offset < maxOffset) {
                offset += speed;
            } else {
                direction = -1;
            }
        } else {
            if(offset > 0) {
                offset -= speed;
            } else {
                direction = 1;
            }
        }
    }

    void render(Renderer renderer) {
        String text;
        if(!pickupText.equals("")) {
            text = pickupText;
        } else {
            text = interactionText;
        }
        renderer.renderText(10, Gdx.graphics.getHeight()-10, text, renderer.scriptFont);
        //renderer.renderText(10-offset, Gdx.graphics.getHeight()-10+offset, text, renderer.hudTopFont);

        if(weapon == null)
            return;

        if(Ranged.class.isAssignableFrom(weapon.getClass())) {
            Ranged ranged = (Ranged)weapon;
            renderer.renderSprite(10, 5, Gdx.graphics.getWidth()/18, Gdx.graphics.getWidth()/18, ammo);
            renderer.renderText(110, 100, String.valueOf(ranged.rounds), renderer.hudBottomFont);
            renderer.renderText(110-offset, 100+offset, String.valueOf(ranged.rounds), renderer.hudTopFont);
        }
    }
}
