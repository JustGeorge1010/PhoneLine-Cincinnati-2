package com.phonelinecincinnati.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.phonelinecincinnati.game.Main;

import java.awt.*;

public class DesktopLauncher {
    @SuppressWarnings("FieldCanBeLocal")
    private static boolean fullscreen = true;

    public static void main(String[] arg) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //config.width = 1000;
        //config.height = 800;
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;

        config.samples = 50;

        config.fullscreen = fullscreen;
        if(!fullscreen) {
            config.height -= 10;
        }

        new LwjglApplication(new Main(), config);
    }
}
