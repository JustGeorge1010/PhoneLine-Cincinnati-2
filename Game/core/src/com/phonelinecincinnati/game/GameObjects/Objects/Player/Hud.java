package com.phonelinecincinnati.game.GameObjects.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Ranged.Ranged;
import com.phonelinecincinnati.game.GameObjects.Objects.Weapons.Weapon;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.MovingText;

public class Hud {
    public String objectiveText = "";

    String pickupText = "";
    String interactionText = "";

    private Weapon weapon;
    private Sprite ammo;
    private MovingText movingText;

    private GlyphLayout glyphLayout;

    Hud() {
        ammo = new Sprite(Main.modelHandler.textures.get(TextureName.Ammo));
        movingText = new MovingText(Renderer.hudTopFont, Renderer.hudBottomFont);
        glyphLayout = new GlyphLayout();
    }

    void update(Weapon weapon) {
        movingText.update();
        if(weapon == null) {
            this.weapon = null;
            return;
        }
        if(weapon != this.weapon) {
            this.weapon = weapon;
        }
    }

    void render(Renderer renderer) {
        String text;
        if(!pickupText.equals("")) {
            text = pickupText;
        } else {
            text = interactionText;
        }
        renderer.renderText(10, Gdx.graphics.getHeight()-10, text, Renderer.scriptFont);

        Main.levelHandler.score.renderHudElement(renderer);

        glyphLayout.setText(Renderer.hudBottomFont, objectiveText);
        float width = glyphLayout.width;
        movingText.render(renderer, (Gdx.graphics.getWidth()-width)-10, 100, objectiveText);

        if(weapon == null)
            return;

        if(Ranged.class.isAssignableFrom(weapon.getClass())) {
            Ranged ranged = (Ranged)weapon;
            renderer.renderSprite(10, 5, Gdx.graphics.getWidth()/18, Gdx.graphics.getWidth()/18, ammo);
            movingText.render(renderer, 110, 100, String.valueOf(ranged.rounds));
        }
    }
}
