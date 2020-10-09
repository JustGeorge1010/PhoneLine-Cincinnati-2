package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Renderer;

public class Title {
    private float scale = 0.005f;
    private GlyphLayout layout;

    public BitmapFont font;

    public Title() {
        font = new BitmapFont(Gdx.files.internal("Fonts/Menu/Title.fnt"));
        font.getData().scale(1.5f);
        layout = new GlyphLayout();
    }

    public void update() {
        font.getData().scale(scale);
        if(font.getScaleX() > 3 || font.getScaleX() < 2) {
            scale = -scale;
        }
    }

    public void render(Renderer renderer) {
        layout.setText(font, "PhoneLine Cincinnati 2");
        renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2, Gdx.graphics.getHeight()/1.1f, "PhoneLine Cincinnati 2", font);
    }

    public void dispose() {
        font.dispose();
    }
}
