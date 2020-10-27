package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class DebugPathLine extends GameObject {
    private ModelInstance modelInstance;

    DebugPathLine(Vector3 start, Vector3 end) {
        modelInstance = Main.modelHandler.getLine(
                new Vector3(start.x, start.y+1, start.z),
                new Vector3(end.x, end.y+1, end.z));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderModel(modelInstance);
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
}
