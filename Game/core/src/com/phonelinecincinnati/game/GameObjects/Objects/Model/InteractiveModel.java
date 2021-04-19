package com.phonelinecincinnati.game.GameObjects.Objects.Model;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Misc.Action;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;
import javafx.util.Pair;

import java.util.ArrayList;

public class InteractiveModel extends Model{
    public boolean unlocked = true;

    private String interactionText;
    private Action action;

    public InteractiveModel(Vector3 position, float rotation, ModelName name) {
        super(position, rotation, name);
        interactionText = "";
    }

    public void setAction(Action action, String interactionText) {
        this.action = action;
        this.interactionText = interactionText;
    }

    public void removeAction() {
        this.action = null;
        interactionText = "";
    }

    public String getInteractionText() {
        return interactionText;
    }

    @Override
    public void update() {

    }


    @Override
    public ArrayList<String> getConstructParams() {
        return new ArrayList<String>();
    }

    public boolean canInteract(GameObject object) {
        if(!unlocked || action == null) {
            return false;
        }
        float range = 3f;
        if(boundingBox.getWidth() > boundingBox.getDepth()) {
            range += boundingBox.getWidth()/2;
        } else {
            range += boundingBox.getDepth()/2;
        }
        Vector3 pos = object.position.cpy();
        return pos.dst(position.x, position.y, position.z) < range;
    }

    public void interact() {
        if(action != null) {
            action.activate();
        }
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void postRender(Renderer renderer) {
        super.postRender(renderer);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
