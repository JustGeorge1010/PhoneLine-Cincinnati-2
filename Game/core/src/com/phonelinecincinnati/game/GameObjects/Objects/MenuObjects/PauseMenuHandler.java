package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class PauseMenuHandler extends GameObject {
    private MenuOptions menuOptions;

    private Color color;

    private boolean escPressed = false;
    private SoundSource unPause, pause;
    public static boolean isPaused = false, canPause;

    public PauseMenuHandler(boolean canPause) {
        this.canPause = canPause;
        color = new Color(0, 0, 0, 0.5f);

        PauseMenuHandler.isPaused = false;

        SoundSource up = SoundSource.buildSoundSource(1).setSound("Menu/MenuUp.wav");
        SoundSource down = SoundSource.buildSoundSource(2).setSound("Menu/MenuDown.wav");
        SoundSource back = SoundSource.buildSoundSource(3).setSound("Menu/MenuBack.wav");

        unPause = SoundSource.buildSoundSource(4).setSound("Menu/MenuAccept.wav");
        pause = SoundSource.buildSoundSource(5).setSound("Menu/Pause.wav");

        menuOptions = MenuOptions.buildMenuOptions(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.6f, "Continue", new Action() {
            @Override
            public void activate() {
                isPaused = false;
            }
        }).addOption("Back to Menu", new Action() {
            @Override
            public void activate() {
                isPaused = false;
                Main.levelHandler.loadMenu();
            }
        }).addOption("Options", new Action() {
            @Override
            public void activate() {
                menuOptions.goToSettings();
            }
        }).addSounds(up, down, back, unPause);
    }

    public void enablePausing() {
        canPause = true;
    }

    @Override
    public void update() {
        if(canPause) {
            if(isPaused) {
                menuOptions.update();
            }
            if(Main.controlHandler.keys.get(Input.Keys.ESCAPE) && !escPressed) {
                if(menuOptions.inSettings) {
                    menuOptions.exitSettings();
                } else {
                    isPaused = !isPaused;
                    if(isPaused) {
                        pause.playSound();
                    } else {
                        unPause.playSound();
                    }
                }
                escPressed = true;
            } else if(!Main.controlHandler.keys.get(Input.Keys.ESCAPE) && escPressed) {
                escPressed = false;
            }
        }
    }

    @Override
    public void render(Renderer renderer) {

    }

    @Override
    public void postRender(Renderer renderer) {
        if(isPaused) {
            renderer.renderTransparentRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), ShapeRenderer.ShapeType.Filled, color);
            menuOptions.render(renderer);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(String.valueOf(canPause));
        return params;
    }
}
