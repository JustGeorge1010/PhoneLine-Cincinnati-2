package com.phonelinecincinnati.game.GameObjects.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class TextBox {
    private boolean open, opening, closing;
    private int index = 0;
    private Action exitAction;
    private float currentHeight;

    public ArrayList<String> text;
    public Texture image;

    TextBox() {
        open = false;
        index = 0;
        currentHeight = 0;

        text = new ArrayList<String>();
    }

    public void setExitAction(Action action) {
        this.exitAction = action;
    }

    public void open() {
        index = 0;
        opening = true;
    }

    public boolean isOpen() {
        return open || opening || closing;
    }

    public void next() {
        if(!opening) {
            index += 1;
            if(index == text.size()) {
                closing = true;
            }
        }
    }

    void update() {
        if(opening) {
            if(currentHeight < Gdx.graphics.getHeight()/4) {
                currentHeight += 10;
            } else {
                opening = false;
                open = true;
            }
        } else if(closing) {
            if(currentHeight > 0) {
                currentHeight -= 10;
            } else {
                open = false;
                closing = false;
                if(exitAction != null) {
                    exitAction.activate();
                }
            }
        }
    }

    void render(Renderer renderer) {
        float whiteHeight = Gdx.graphics.getHeight()/40;

        renderer.renderRect(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), -currentHeight, ShapeRenderer.ShapeType.Filled, Color.BLACK);
        renderer.renderRect(0, Gdx.graphics.getHeight()-currentHeight, Gdx.graphics.getWidth(), whiteHeight, ShapeRenderer.ShapeType.Filled, Color.WHITE);

        renderer.renderRect(0, 0, Gdx.graphics.getWidth(), +currentHeight, ShapeRenderer.ShapeType.Filled, Color.BLACK);
        renderer.renderRect(0, currentHeight-whiteHeight, Gdx.graphics.getWidth(), whiteHeight, ShapeRenderer.ShapeType.Filled, Color.WHITE);

        if(open && !closing) {
            GlyphLayout layout = new GlyphLayout();
            float maxWidth = Gdx.graphics.getWidth()/1.5f;
            float y = Gdx.graphics.getHeight()/6f;
            StringBuilder builder = new StringBuilder();
            for(Character character : text.get(index).toCharArray()) {
                if(character != ' ') {
                    builder.append(character);
                } else {
                    builder.append(' ');
                    layout.setText(renderer.scriptFont, builder.toString());
                    if(layout.width > maxWidth) {
                        renderer.renderText(Gdx.graphics.getWidth()/20, y, builder.toString(), renderer.scriptFont);
                        y -= layout.height*2.2f;
                        builder = new StringBuilder();
                    }
                }
            }
            if(image != null) {
                Sprite sprite = new Sprite(image);
                float size = Gdx.graphics.getHeight()/4;
                renderer.renderSprite((Gdx.graphics.getWidth()/2)-size/2, (Gdx.graphics.getHeight()/2)-size/2, size, size, sprite);
            }
            renderer.renderText(Gdx.graphics.getWidth()/20, y, builder.toString(), renderer.scriptFont);
            layout.setText(renderer.scriptFont, "[RMB]");
            renderer.renderText(Gdx.graphics.getWidth()-layout.width*1.5f, layout.height*2, "[RMB]", renderer.scriptFont);
        }
    }
}
