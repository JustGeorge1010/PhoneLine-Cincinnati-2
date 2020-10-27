package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class MenuHandler extends GameObject {
    private Title title;
    private MenuOptions menuOptions;

    private boolean exitMenu = false, exitSwitch = false;
    private Color darken;

    private SoundSource up, down, back, accept;

    public MenuHandler() {
        title = new Title();

        up = SoundSource.buildSoundSource(1).setSound("Menu/MenuUp.wav");
        down = SoundSource.buildSoundSource(2).setSound("Menu/MenuDown.wav");
        back = SoundSource.buildSoundSource(3).setSound("Menu/MenuBack.wav");
        accept = SoundSource.buildSoundSource(4).setSound("Menu/MenuAccept.wav");

        menuOptions = MenuOptions.buildMenuOptions(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2.5f,"New Game", new Action() {
            @Override
            public void activate() {
                Main.levelHandler.loadHouse();
            }
        }).addOption("Continue", new Action() {
            @Override
            public void activate() {
                Main.levelHandler.loadCurrent();
            }
        }).addOption("Options", new Action() {
            @Override
            public void activate() {
                menuOptions.goToSettings();
            }
        }).addSounds(up, down, back, accept);

        darken = new Color(0, 0, 0, 0.5f);
    }

    @Override
    public void update() {
        if(!exitMenu) {
            title.update();
            menuOptions.update();
        } else {
            if(Main.controlHandler.keys.get(Input.Keys.ENTER)) {
                Gdx.app.exit();
            }
        }

        if(Main.controlHandler.keys.get(Input.Keys.ESCAPE) && !exitSwitch) {
            if(menuOptions.inSettings) {
                menuOptions.exitSettings();
            } else {
                if(exitMenu) {
                    accept.playSound();
                } else {
                    back.playSound();
                }
                exitMenu = !exitMenu;
            }
            exitSwitch = true;

        } else if(!Main.controlHandler.keys.get(Input.Keys.ESCAPE) && exitSwitch) {
            exitSwitch = false;
        }
    }

    @Override
    public void render(Renderer renderer) {
        if(!exitMenu) {
            title.render(renderer);
            menuOptions.render(renderer);
        } else {
            renderer.renderTransparentRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), ShapeRenderer.ShapeType.Filled, darken);
            GlyphLayout layout = new GlyphLayout(menuOptions.selectedFont, "Press enter to exit");
            renderer.renderText(Gdx.graphics.getWidth()/2-(layout.width/2), Gdx.graphics.getHeight()/2+(layout.height/2), "Press enter to exit", menuOptions.selectedFont);
        }
    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {
        title.dispose();
        menuOptions.dispose();
    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
