package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.Background;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.MenuHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.SoundSource;

import java.util.concurrent.CopyOnWriteArrayList;

public class Menu extends Level {

    public Menu(CopyOnWriteArrayList<GameObject> list, int CurrentProgression) {
        list.add(SoundSource.buildSoundSource(1).setMusic("MenuTheme.mp3").playMusic());
        list.add(new Background());
        list.add(new MenuHandler());
    }

    @Override
    public void load(boolean reloading, boolean retainPlayer) {

    }
    @Override
    public void reload() {

    }
}
