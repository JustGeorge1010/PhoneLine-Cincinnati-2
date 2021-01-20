package com.phonelinecincinnati.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.phonelinecincinnati.game.Main;

import java.awt.*;

public class DesktopLauncher {
    @SuppressWarnings("FieldCanBeLocal")
    private static final boolean fullscreen = false;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //old code here: config.width = 1000;
        //old code also: config.height = 800;
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        if(fullscreen) {
            config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
        } else {
            config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height-60;
        }

        config.samples = 50;

        config.fullscreen = fullscreen;
        if(!fullscreen) {
            config.height -= 10;
        }
        
        new LwjglApplication(new Main(), config);
    }
}
