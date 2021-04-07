package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.phonelinecincinnati.game.Renderer;

public class MovingText {
    private float offset = 0;
    private float direction = 1;

    private BitmapFont top, bottom;

    public MovingText(BitmapFont top, BitmapFont bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public void update() {
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

    public void render(Renderer renderer, float x, float y, String text) {
        renderer.renderText(x, y, text, bottom);
        renderer.renderText(x-offset, y+offset, text, top);
    }
}
