package com.phonelinecincinnati.game.Levels;

import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Level {
    CopyOnWriteArrayList<GameObject> activeObjects;

    public abstract void reload();
}
