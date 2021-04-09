package com.phonelinecincinnati.game.GameObjects.Objects.Misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class TitleCard extends GameObject{
    String textTop, textBottom;
    private int duration;
    private Action action;
    private boolean activated = false;
    public boolean rendering = true;

    //Specific to normal Title card
    private boolean startedFade = false;
    private boolean renderingText = true;

    public TitleCard(String textTop, String textBottom, int duration) {
        this.textTop = textTop;
        this.textBottom = textBottom;
        this.duration = duration;
    }

    public void addAction(Action action) {
        this.action = action;
    }

    @Override
    public void update() {
        if(duration == 0 || (Main.debug && duration != -1)) {
            action.activate();
            activated = true;
            duration = -1;
        } else if (duration != -1){
            duration--;
        }
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        if(rendering) {
            if(!activated) {
                 renderer.renderRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                         ShapeRenderer.ShapeType.Filled, Color.BLACK);
            }
        }
        else if(!startedFade) {
            renderer.renderRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                    ShapeRenderer.ShapeType.Filled, Color.BLACK);
            Main.levelHandler.getActiveObjects().add(new Fade(true, new Action() {
                @Override
                public void activate() {
                    renderingText = false;
                }
            }, 0.01f));
            startedFade = true;
        }
        if(renderingText) {
            GlyphLayout layout = new GlyphLayout();
            layout.setText(Renderer.scriptFont, textTop);
            renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2,
                    Gdx.graphics.getHeight()/2+(layout.height/1.5f), textTop, Renderer.scriptFont);

            layout.setText(Renderer.scriptFont, textBottom);
            renderer.renderText((Gdx.graphics.getWidth()/2)-layout.width/2,
                    Gdx.graphics.getHeight()/2-(layout.height/1.5f), textBottom, Renderer.scriptFont);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
