package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class SwayingText extends GameObject {
    public float originX;
    public float originY;

    private float offsetX;
    private float direction = -1;

    private String text;
    private BitmapFont font;

    public SwayingText(float x, float y, String text, BitmapFont font) {
        this.originX = x;
        this.originY = y;
        this.text = text;
        this.font = font;
    }

    public void move(float x, float y) {
        this.originX += x;
        this.originY += y;
    }

    @Override
    public void update() {
        float minOffsetX = -10;
        float maxOffsetX = 10;

        if(offsetX < minOffsetX || offsetX > maxOffsetX) {
            direction *= -1;
        }

        offsetX += direction;
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        renderer.renderText(originX+offsetX, originY, text, font);
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
