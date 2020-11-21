package com.phonelinecincinnati.game.GameObjects.Objects.Misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class Fade extends GameObject {
    private boolean in;
    private Color color;
    private Action action;
    private float speed;

    public Fade(boolean in, Action action, float speed) {
        this.in = in;
        if(in) {
            color = new Color(0, 0, 0, 1);
        } else {
            color = new Color(0, 0, 0, 0);
        }
        this.action = action;
        this.speed = speed;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public void update() {
        if(!PauseMenuHandler.isPaused) {
            if(in) {
                color.a -= speed;
                if(color.a < 0) {
                    color.a = 0;
                    if(action != null) action.activate();
                    Main.levelHandler.getActiveObjects().remove(this);
                }
            } else {
                color.a += speed;
                if(color.a > 1) {
                    color.a = 1;
                    if(action != null) action.activate();
                    Main.levelHandler.getActiveObjects().remove(this);
                }
            }
        }
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        renderer.renderTransparentRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), ShapeRenderer.ShapeType.Filled, color);
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(String.valueOf(in));
        params.add(String.valueOf(speed));
        return params;
    }
}
