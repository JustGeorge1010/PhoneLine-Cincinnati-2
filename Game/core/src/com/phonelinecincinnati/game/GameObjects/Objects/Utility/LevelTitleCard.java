package com.phonelinecincinnati.game.GameObjects.Objects.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.SkyScraper;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.concurrent.CopyOnWriteArrayList;

public class LevelTitleCard extends TitleCard {
    private String tipText;
    private CopyOnWriteArrayList<SkyScraper> skyScrapers;

    private Color backgroundColor;
    private Texture fadeImage;
    private Sprite fadeSprite;

    private BitmapFont preTitle, title, tip;

    private int timer = 0;

    public LevelTitleCard(String textTop, String textBottom, int duration) {
        super(textTop, textBottom, duration);

        rendering = true;
        backgroundColor = new Color(0.03f, 0.05f, 0.06f, 1f);
        fadeImage = new Texture(Gdx.files.internal("Textures/Misc/TitleCardFade.png"));
        fadeSprite = new Sprite(fadeImage);

        generateTip();

        skyScrapers = new CopyOnWriteArrayList<SkyScraper>();
        skyScrapers.add(new SkyScraper(Gdx.graphics.getWidth()/1.7f, new Color(0f, 0, 0f, 1f), 100));
        skyScrapers.add(new SkyScraper(Gdx.graphics.getWidth()/2f, new Color(0f, 0, 0f, 1f), 100));
        skyScrapers.add(new SkyScraper(Gdx.graphics.getWidth()/4, new Color(0f, 0, 0f, 1f), 100));

        preTitle = new BitmapFont(Gdx.files.internal("Fonts/InGame/PreTitle.fnt"));
        preTitle.getData().scale(2);
        title = new BitmapFont(Gdx.files.internal("Fonts/InGame/Title.fnt"));
        title.getData().scale(2);
        tip = new BitmapFont(Gdx.files.internal("Fonts/InGame/Tip.fnt"));
    }

    private void generateTip() {
        int random = MathUtils.random(1, 13);
        switch(random) {
            case 1:
                tipText = "TIP: MELEE WEAPONS ARE SILENT";
                break;
            case 2:
                tipText = "TIP: GUNS ATTRACT ATTENTION";
                break;
            case 3:
                tipText = "TIP: YOU CAN THROW WEAPONS";
                break;
            case 4:
                tipText = "TIP: DON'T BE AFRAID OF DYING";
                break;
            case 5:
                tipText = "TIP: RECKLESSNESS IS REWARDED";
                break;
            case 6:
                tipText = "TIP: USE DOORS STRATEGICALLY";
                break;
            case 7:
                tipText = "TIP: VARIATION IS REWARDING";
                break;
            case 8:
                tipText = "TIP: HIGH SCORES UNLOCK MASKS";
                break;
            case 9:
                tipText = "TIP: ENEMIES ARE PREDICTABLE";
                break;
            case 10:
                tipText = "TIP: PAY ATTENTION TO DETAILS";
                break;
            case 11:
                tipText = "TIP: BE FAST AND EFFICIENT";
                break;
            case 12:
                tipText = "TIP: YOU CAN SHOOT THROUGH SOME WALLS";
                break;
            case 13:
                tipText = "TIP: FINISH OFF FALLEN ENEMIES";
                break;
        }
    }

    @Override
    public void update() {
        if(duration == 0 || (Main.debug && duration != -1)) {
            action.activate();
            duration = -1;
        } else if (duration != -1){
            duration--;
        }
        if(timer <= 0) {
            timer = MathUtils.random(50, 100);
            skyScrapers.add(new SkyScraper(Color.BLACK, 100));
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
    public void postRender(Renderer renderer) {
        if(rendering) {
            renderer.renderRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), ShapeRenderer.ShapeType.Filled, backgroundColor);

            for(SkyScraper building : skyScrapers) {
                building.render(renderer);
            }

            renderer.renderRect(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),-Gdx.graphics.getHeight()/5, ShapeRenderer.ShapeType.Filled, Color.BLACK);
            renderer.renderSprite(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), fadeSprite);

            GlyphLayout layout = new GlyphLayout();
            layout.setText(preTitle, textTop);
            renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2, Gdx.graphics.getHeight()/1.4f, textTop, preTitle);
            layout.setText(title, textBottom);
            renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2, Gdx.graphics.getHeight()/1.8f, textBottom, title);
            layout.setText(tip, tipText);
            renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2, Gdx.graphics.getHeight()/4, tipText, tip);
        }
    }

    @Override
    public void dispose() {
        fadeImage.dispose();
        super.dispose();
    }
}
