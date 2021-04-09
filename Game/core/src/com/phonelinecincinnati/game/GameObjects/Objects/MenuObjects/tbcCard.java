package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Fade;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class tbcCard extends GameObject {
    int countDown = 1000;

    @Override
    public void update() {
        countDown--;
        if(countDown < 0) {
            Main.levelHandler.getActiveObjects().add(new Fade(false, new Action() {
                @Override
                public void activate() {
                    Main.levelHandler.loadMenu();
                }
            }, 0.001f));
        }
    }

    @Override
    public void render(Renderer renderer) {
        GlyphLayout layout = new GlyphLayout();
        String text = "To be continued";
        layout.setText(Renderer.scriptFont, text);
        renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2,
                Gdx.graphics.getHeight()/2, text, Renderer.scriptFont);
    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return null;
    }
}
