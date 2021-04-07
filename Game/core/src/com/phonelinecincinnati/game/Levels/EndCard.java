package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.Background;
import com.phonelinecincinnati.game.GameObjects.Objects.MenuObjects.EndCardHandler;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Fade;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.SoundSource;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class EndCard extends Level {
    private ArrayList<String> results;

    public EndCard(final CopyOnWriteArrayList<GameObject> activeObjects, ArrayList<String> results) {
        this.activeObjects = activeObjects;
        this.results = results;
        load(false, false);
    }

    @Override
    public void load(boolean reloading, boolean retainPlayer) {
        activeObjects.add(SoundSource.buildSoundSource(1).setMusic("M.O.O.N - Dust.mp3").playMusic());
        activeObjects.add(new Background());
        activeObjects.add(new EndCardHandler(activeObjects, results));
        activeObjects.add(new Fade(true, null, 0.04f));
    }

    @Override
    public void reload() {

    }
}
