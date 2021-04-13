package com.phonelinecincinnati.game.GameObjects.Objects.Model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelName;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class Model extends GameObject {
    public ModelName name;
    public boolean rendering = true;

    private Vector3 offset;

    protected ModelInstance modelInstance;
    protected float rotation;

    BoundingBox boundingBox;

    public Model(Vector3 position, float rotation, ModelName name) {
        this.position = position;
        this.rotation = rotation;
        this.name = name;

        setup();
    }

    public Model(ArrayList<String> params) {
        this.position = VectorMaths.constructFromString(params.get(0));
        this.rotation = Float.parseFloat(params.get(1));
        this.name = ModelName.valueOf(params.get(2));

        setup();
    }

    private void setup() {
        boundingBox = new BoundingBox();

        modelInstance = Main.modelHandler.getModel(name);
        modelInstance.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));

        modelInstance.calculateBoundingBox(boundingBox);
        offset = new Vector3(-boundingBox.getCenterX(), 0, -boundingBox.getCenterZ());
        modelInstance.transform.translate(position.x, position.y, position.z);
        modelInstance.transform.rotate(0, 1, 0, rotation);
        modelInstance.transform.translate(offset);

        Vector3[] bound = new Vector3[8];
        for(int i = 0; i < bound.length; i++) {
            bound[i] = new Vector3();
        }

        boundingBox.getCorner000(bound[0]);
        boundingBox.getCorner001(bound[1]);
        boundingBox.getCorner010(bound[2]);
        boundingBox.getCorner011(bound[3]);
        boundingBox.getCorner100(bound[4]);
        boundingBox.getCorner101(bound[5]);
        boundingBox.getCorner110(bound[6]);
        boundingBox.getCorner111(bound[7]);

        for(Vector3 corner : bound) {
            corner.rotate(rotation, 0, 1, 0);
        }

        boundingBox.set(bound);
    }

    public void updateModel(ModelName name) {
        this.name = name;
        boundingBox = new BoundingBox();

        modelInstance = Main.modelHandler.getModel(name);
        modelInstance.calculateBoundingBox(boundingBox);
        offset = new Vector3(-boundingBox.getCenterX(), 0, -boundingBox.getCenterZ());
        modelInstance.transform.translate(position.x, position.y, position.z);
        modelInstance.transform.rotate(0, 1, 0, rotation);
        modelInstance.transform.translate(offset);

        Vector3[] bound = new Vector3[8];
        for(int i = 0; i < bound.length; i++) {
            bound[i] = new Vector3();
        }

        boundingBox.getCorner000(bound[0]);
        boundingBox.getCorner001(bound[1]);
        boundingBox.getCorner010(bound[2]);
        boundingBox.getCorner011(bound[3]);
        boundingBox.getCorner100(bound[4]);
        boundingBox.getCorner101(bound[5]);
        boundingBox.getCorner110(bound[6]);
        boundingBox.getCorner111(bound[7]);

        for(Vector3 corner : bound) {
            corner.rotate(rotation, 0, 1, 0);
        }

        boundingBox.set(bound);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Renderer renderer) {
        if(rendering) {
            renderer.renderModel(modelInstance);
        }
    }

    @Override
    public void postRender(Renderer renderer) {
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(position.toString());
        params.add(String.valueOf(rotation));
        params.add(name.toString());
        return params;
    }
}
