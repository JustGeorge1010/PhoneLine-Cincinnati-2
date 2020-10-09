package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Renderer;

public class SkyScraper {
    public float x, width;
    private float height, speed, speedModifier;
    private Color color;

    public SkyScraper(Color color, float speedModifier) {
        x = Gdx.graphics.getWidth();
        this.color = color;
        this.speedModifier = speedModifier;

        setUp();
    }

    public SkyScraper(float x, Color color, float speedModifier) {
        this.x = x;
        this.color = color;
        this.speedModifier = speedModifier;

        setUp();
    }

    private void setUp() {
        width = MathUtils.random(Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/5);
        height = MathUtils.random(Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/1.1f);

        speed = height/speedModifier;
    }

    public void update() {
        x -= speed;
    }

    public void render(Renderer renderer) {
        renderer.renderRect(x, 0, width, height, ShapeRenderer.ShapeType.Filled, color);
    }

    public void dispose() {

    }
}
