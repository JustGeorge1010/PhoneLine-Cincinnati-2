package com.phonelinecincinnati.game.GameObjects.Objects;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Utility.Action;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class InteractionThreshold extends Threshold {
    private GameObject listeningObject;
    private boolean unlocked;
    private Action action;

    public InteractionThreshold(GameObject listeningObject, Vector3 position, Vector3 size, Action action) {
        super(position, size);
        this.listeningObject = listeningObject;
        this.position = position;
        this.size = size;
        this.unlocked = false;
        this.action = action;
    }

    @Override
    public void update() {

    }

    public void unlock() {
        unlocked = true;
    }

    @Override
    public void render(Renderer renderer) {

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

    @Override
    public boolean inBounds(Vector3 position, Class c) {
        if(position.x > this.position.x && position.x < this.position.x+size.x &&
           position.y > this.position.y && position.y < this.position.y+size.y &&
           position.z > this.position.z && position.z < this.position.z+size.z)
        {
            if(unlocked) {
                action.activate();
            } else return true;
        }
        return false;
    }
}
