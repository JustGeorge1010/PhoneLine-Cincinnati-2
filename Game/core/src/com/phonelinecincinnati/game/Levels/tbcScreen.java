package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.tbcCard;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Fade;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;
import com.phonelinecincinnati.game.Main;

import java.util.concurrent.CopyOnWriteArrayList;

public class tbcScreen extends Level {

    public tbcScreen(final CopyOnWriteArrayList<GameObject> activeObjects) {
        this.activeObjects = activeObjects;
        load(false, false);
    }

    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        Main.backgroundColor.set(0, 0, 0, 1f);
        activeObjects.add(SoundSource.buildSoundSource(1).setMusic("M.O.O.N - Dust.mp3").playMusic());
        activeObjects.add(new tbcCard());
        activeObjects.add(new Fade(true, null, 0.04f));
    }

    @Override
    public void reload() {

    }
}