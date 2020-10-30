package com.phonelinecincinnati.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.PauseMenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Fade;

import java.util.HashMap;
import java.util.Map;

public class ControlHandler extends InputAdapter {
    private Player player;
    public Map<Integer, Boolean> keys;

    public ControlHandler() {
        keys = new HashMap<Integer, Boolean>();
        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.D, false);
        keys.put(Input.Keys.R, false);
        keys.put(Input.Keys.SHIFT_LEFT, false);
        keys.put(Input.Keys.UP, false);
        keys.put(Input.Keys.DOWN, false);
        keys.put(Input.Keys.LEFT, false);
        keys.put(Input.Keys.RIGHT, false);
        keys.put(Input.Keys.ENTER, false);
        keys.put(Input.Keys.SPACE, false);
        keys.put(Input.Keys.ESCAPE, false);
    }

    @Override
    public boolean keyDown(int keycode) {
        keys.put(keycode, true);
        if(keycode == Input.Keys.SPACE) {
            if(player != null) {
                player.spb();
            } else {
                findPlayer();
                if(player != null) {
                    player.spb();
                }
            }
        }

        debugKeys(keycode);
        return true;
    }

    private void debugKeys(int keycode) {
        if(Main.debug) {
            if(keycode == Input.Keys.F1) {
                Main.postUpdateSchedule.add(new Action() {
                    @Override
                    public void activate() {
                        Main.levelHandler.clearActiveObjects();
                        Main.levelHandler.currentLevel.load(true, true);
                    }
                });
            }
            if(keycode == Input.Keys.J) {
                Main.debugBlindEnemies = !Main.debugBlindEnemies;
            }
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        keys.put(keycode, false);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controlCamera();
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(player == null) {
            findPlayer();
            if(player == null) {
                return true;
            }
        }
        if(button == Input.Buttons.LEFT) player.LMB();
        if(button == Input.Buttons.RIGHT) player.RMB();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        controlCamera();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(player == null) {
            findPlayer();
            if(player == null) {
                return true;
            }
        }
        if(button == Input.Buttons.LEFT) player.LMBReleased();
        return true;
    }

    private void controlCamera() {
        if(player == null) {
            findPlayer();
            if(player != null) {
                player.controlCamera();
            }
        } else {
            player.controlCamera();
        }
    }

    private void findPlayer() {
        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(object.getClass() == Player.class) {
                player = (Player)object;
            }
        }
    }
    public void resetPlayer() {
        player = null;
    }
}
