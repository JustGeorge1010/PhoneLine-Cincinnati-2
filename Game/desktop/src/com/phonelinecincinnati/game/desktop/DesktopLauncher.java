package com.phonelinecincinnati.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.phonelinecincinnati.game.Main;

import java.awt.*;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //config.width = 1000;
        //config.height = 800;
        config.width = (int) screenSize.getWidth();
        config.height = (int) screenSize.getHeight();

        config.samples = 10;

        config.fullscreen = true;

        new LwjglApplication(new Main(), config);
    }
}
