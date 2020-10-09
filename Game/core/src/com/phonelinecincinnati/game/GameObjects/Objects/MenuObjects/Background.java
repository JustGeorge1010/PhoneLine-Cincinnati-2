package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Renderer;

import java.util.concurrent.CopyOnWriteArrayList;

public class Background extends GameObject {

    private Color background1, background2, currentBackground, nextBackground;

    private CopyOnWriteArrayList<SkyScraper> skyScrapers;
    private int timer = 0;

    private Texture backgroundTexture;
    private Sprite background;

    public Background() {
        background1 = new Color(0.61f, 0, 0.2f, 0.5f);
        background2 = new Color(0.94f, 0, 0.31f, 1);
        currentBackground = new Color(background1);
        nextBackground = background2;

        skyScrapers = new CopyOnWriteArrayList<SkyScraper>();
        skyScrapers.add(new SkyScraper(Gdx.graphics.getWidth()/1.7f, new Color(0.61f, 0, 0.2f, 0.5f), 500));
        skyScrapers.add(new SkyScraper(Gdx.graphics.getWidth()/2f, new Color(0.61f, 0, 0.2f, 0.5f), 500));
        skyScrapers.add(new SkyScraper(Gdx.graphics.getWidth()/4, new Color(0.61f, 0, 0.2f, 0.5f), 500));

        backgroundTexture = new Texture(Gdx.files.internal("Textures/Menu/back.png"));
        background = new Sprite(backgroundTexture);
    }

    @Override
    public void update() {
        updateBackgroundColor();
        updateSkyScrapers();
    }

    private void updateBackgroundColor() {
        if(nextBackground == background2) {
            currentBackground.r += 0.001f;
            currentBackground.b += 0.001f;
            currentBackground.a += 0.001f;
        } else {
            currentBackground.r -= 0.001f;
            currentBackground.b -= 0.001f;
            currentBackground.a -= 0.001f;
        }

        if(nextBackground == background2) {
            if(currentBackground.r > nextBackground.r) {
                nextBackground = background1;
            }
        } else {
            if(currentBackground.r < nextBackground.r) {
                nextBackground = background2;
            }
        }
    }

    private void updateSkyScrapers() {
        if(timer <= 0) {
            timer = MathUtils.random(100, 400);
            skyScrapers.add(new SkyScraper(new Color(0.61f, 0, 0.2f, 0.5f), 500));
        } else {
            timer -= 1;
        }

        for(SkyScraper building : skyScrapers) {
            building.update();
            if(building.x+building.width < 0) {
                skyScrapers.remove(building);
            }
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.setBackgroundColor(currentBackground);

        for(SkyScraper building : skyScrapers) {
            building.render(renderer);
        }

        renderer.renderSprite(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), background);
    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
