package com.phonelinecincinnati.game.GameObjects.Objects.Utility;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Renderer;

public class TitleCard extends GameObject{
    String textTop, textBottom;
    int duration;
    Action action;
    public boolean rendering;

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

    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {

    }
}
