package com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuOptions extends GameObject{
    private float x, y;

    private CopyOnWriteArrayList<Pair<String, Action>> options;
    private CopyOnWriteArrayList<Pair<String, Action>> settingsOptions;

    private Pair<String, Action> currentSelection;
    private static boolean pressed = false, accepted = false;

    private BitmapFont deSelectedFont;
    BitmapFont selectedFont;

    private SoundSource up, down, back, accept;

    boolean inSettings = false;
    private boolean settingGlobalMusic = false;
    private boolean settingGlobalSound = false;

    private MenuOptions(float x, float y) {
        this.x = x;
        this.y = y;

        options = new CopyOnWriteArrayList<Pair<String, Action>>();
        settingsOptions = new CopyOnWriteArrayList<Pair<String, Action>>();
        settingsOptions.add(new Pair<String, Action>("Music: ", new Action() {
            @Override
            public void activate() {
                settingGlobalMusic = true;
            }
        }));

        settingsOptions.add(new Pair<String, Action>("SFX: ", new Action() {
            @Override
            public void activate() {
                settingGlobalSound = true;
            }
        }));

        deSelectedFont = new BitmapFont(Gdx.files.internal("Fonts/Menu/MenuItem.fnt"));
        selectedFont = new BitmapFont(Gdx.files.internal("Fonts/Menu/MenuItemSelected.fnt"));
        deSelectedFont.getData().scale(0.5f);
        selectedFont.getData().scale(0.5f);
    }

    public static MenuOptions buildMenuOptions(float x, float y, String text, Action action) {
        MenuOptions temp = new MenuOptions(x, y);
        temp.addOption(text, action);
        temp.currentSelection = temp.options.get(0);
        return temp;
    }

    public MenuOptions addOption(String text, Action action) {
        options.add(new Pair<String, Action>(text, action));
        return this;
    }

    public MenuOptions addSounds(SoundSource up, SoundSource down, SoundSource back, SoundSource accept) {
        this.up = up;
        this.down = down;
        this.back = back;
        this.accept = accept;
        return this;
    }

    @Override
    public void update() {
        if(!inSettings) {
            selectionManagement(options);
        } else {
            settingManagement();
        }
        if((Main.controlHandler.keys.get(Input.Keys.ENTER) || Main.controlHandler.keys.get(Input.Keys.SPACE)) && !accepted) {
            accepted = true;
            accept.playSound();
            currentSelection.getValue().activate();
        } else if(!(Main.controlHandler.keys.get(Input.Keys.ENTER) || Main.controlHandler.keys.get(Input.Keys.SPACE)) && accepted) {
            accepted = false;
        }
    }

    private void selectionManagement(CopyOnWriteArrayList<Pair<String, Action>> options) {
        if(Main.controlHandler.keys.get(Input.Keys.DOWN) && !pressed) {
            down.playSound();
            int index = options.indexOf(currentSelection)+1;
            if(index >= options.size()) index = 0;
            currentSelection = options.get(index);
            pressed = true;
        } else if(Main.controlHandler.keys.get(Input.Keys.UP) && !pressed) {
            up.playSound();
            int index = options.indexOf(currentSelection)-1;
            if(index < 0) index = options.size()-1;
            currentSelection = options.get(index);
            pressed = true;
        } else if(pressed && !(Main.controlHandler.keys.get(Input.Keys.DOWN) || Main.controlHandler.keys.get(Input.Keys.UP))) {
            pressed = false;
        }
    }

    public void goToSettings() {
        currentSelection = settingsOptions.get(0);
        inSettings = true;
    }

    public void exitSettings() {
        back.playSound();
        if(settingGlobalMusic) {
            settingGlobalMusic = false;
        } else if(settingGlobalSound) {
            settingGlobalSound = false;
        } else {
            inSettings = false;
            currentSelection = options.get(options.size()-1);
        }
    }

    private void settingManagement() {
        if(!settingGlobalSound && !settingGlobalMusic) {
            selectionManagement(settingsOptions);
        } else if(settingGlobalMusic) {
            if(Main.controlHandler.keys.get(Input.Keys.LEFT) && !pressed) {
                float current = SoundSource.getGlobalMusicVolume();
                if(SoundSource.getGlobalMusicVolume() > 0f) current -= 0.1f;
                else current = 0f;
                SoundSource.setGlobalMusicVolume(Float.parseFloat(String.format("%.1g%n", current)));
                pressed = true;
                down.playSound();
            } else if(Main.controlHandler.keys.get(Input.Keys.RIGHT) && !pressed) {
                float current = SoundSource.getGlobalMusicVolume();
                if(SoundSource.getGlobalMusicVolume() < 1f) current += 0.1f;
                else current = 1f;
                SoundSource.setGlobalMusicVolume(Float.parseFloat(String.format("%.1g%n", current)));
                pressed = true;
                up.playSound();
            } else if(pressed && !(Main.controlHandler.keys.get(Input.Keys.LEFT) || Main.controlHandler.keys.get(Input.Keys.RIGHT))) {
                pressed = false;
            }
        } else {
            if(Main.controlHandler.keys.get(Input.Keys.LEFT) && !pressed) {
                float current = SoundSource.getGlobalSFXVolume();
                if(SoundSource.getGlobalSFXVolume() > 0f) current -= 0.1f;
                else current = 0f;
                SoundSource.setGlobalSFXVolume(Float.parseFloat(String.format("%.1g%n", current)));
                pressed = true;
                down.playSound();
            } else if(Main.controlHandler.keys.get(Input.Keys.RIGHT) && !pressed) {
                float current = SoundSource.getGlobalSFXVolume();
                if(SoundSource.getGlobalSFXVolume() < 1f) current += 0.1f;
                else current = 1f;
                SoundSource.setGlobalSFXVolume(Float.parseFloat(String.format("%.1g%n", current)));
                pressed = true;
                up.playSound();
            } else if(pressed && !(Main.controlHandler.keys.get(Input.Keys.LEFT) || Main.controlHandler.keys.get(Input.Keys.RIGHT))) {
                pressed = false;
            }
        }
    }

    @Override
    public void render(Renderer renderer) {
        float currentYOffset = 0;
        GlyphLayout layout = new GlyphLayout();

        if(!inSettings) {
            for(Pair<String, Action> option : options) {
                if(option == currentSelection) {
                    layout.setText(selectedFont, option.getKey());
                    renderer.renderText(x-(layout.width/2), y-currentYOffset, option.getKey(), selectedFont);
                } else {
                    layout.setText(deSelectedFont, option.getKey());
                    renderer.renderText(x-(layout.width/2), y-currentYOffset, option.getKey(), deSelectedFont);
                }
                currentYOffset += layout.height*1.4f;
            }
        } else {
            float xOffset = -150f;
            for(Pair<String, Action> option : settingsOptions) {
                if(option == currentSelection) {
                    layout.setText(selectedFont, option.getKey());
                    renderer.renderText(x-layout.width+xOffset, y-currentYOffset, option.getKey(), selectedFont);
                } else {
                    layout.setText(deSelectedFont, option.getKey());
                    renderer.renderText(x-layout.width+xOffset, y-currentYOffset, option.getKey(), deSelectedFont);
                }
                StringBuilder temp = new StringBuilder("   ");
                if(option.getKey().equals("Music: ")) {
                    for(float i = 0.0f; i < SoundSource.getGlobalMusicVolume(); i+=0.1f) {
                        temp.append("|");
                    }
                } else if(option.getKey().equals("SFX: ")) {
                    for(float i = 0.0f; i < SoundSource.getGlobalSFXVolume(); i+=0.1f) {
                        temp.append("|");
                    }
                }
                BitmapFont font = deSelectedFont;
                if((option.getKey().equals("Music: ") && settingGlobalMusic) || (option.getKey().equals("SFX: ") && settingGlobalSound))
                    font = selectedFont;
                renderer.renderText(x+xOffset, y-currentYOffset, "Min     Max", font);
                renderer.renderText(x+xOffset, y-currentYOffset, temp.toString(), font);
                currentYOffset += layout.height*1.4f;
            }
        }
    }

    @Override
    public void postRender(Renderer renderer) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }
}
