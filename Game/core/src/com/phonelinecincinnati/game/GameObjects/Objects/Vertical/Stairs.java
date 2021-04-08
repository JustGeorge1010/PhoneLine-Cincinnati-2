package com.phonelinecincinnati.game.GameObjects.Objects.Vertical;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.ModelHandler;
import com.phonelinecincinnati.game.Renderer;
import com.phonelinecincinnati.game.Utility.Direction;
import com.phonelinecincinnati.game.Utility.VectorMaths;

import java.util.ArrayList;

public class Stairs extends GameObject implements Collidable{
    private Vector3 start, end, originStart;
    private ArrayList<Step> steps;
    private boolean up;
    private Direction direction;

    private Player player;
    private float stepHeight;

    public Stairs(boolean up, Direction direction, Vector3 startPos, int steps, float stepHeight) {
        this.up = up;
        this.direction = direction;
        start = startPos.cpy();
        originStart = startPos.cpy();
        this.stepHeight = stepHeight;
        position = startPos;
        setup(startPos, steps);
    }

    public Stairs(ArrayList<String> params) {
        this.up = Boolean.parseBoolean(params.get(0));
        this.direction = Direction.valueOf(params.get(1));
        start = VectorMaths.constructFromString(params.get(2));
        originStart = start.cpy();
        this.stepHeight = Float.parseFloat(params.get(4));
        position = start.cpy();
        setup(start.cpy(), Integer.parseInt(params.get(3)));
    }

    private void setup(Vector3 startPos, int steps) {
        Step first;
        float rotation;
        if (direction == Direction.North || direction == Direction.South) {
            if (direction == Direction.North) {
                rotation = -90;
            } else {
                rotation = 90;
            }
            first = new Step(startPos, rotation);
            float endX;
            float endY = startPos.y - (stepHeight * steps);
            float endZ;
            if(up) {
                endY = startPos.y + (stepHeight * steps);
            }
            if(direction == Direction.North) {
                start.add(0, 0, -first.getWidth() * steps+1);
                endZ = start.z + (first.getWidth() * steps);
            }
            else {
                start.add(-first.getDepth(), -0.2f, -first.getWidth());
                endZ = start.z + (first.getWidth() * steps);
            }

            end = new Vector3(start.x + first.getDepth(), endY, endZ);

        } else {
            if(direction == Direction.East) {
                rotation = 180;
            } else {
                rotation = 0;
            }
            first = new Step(startPos, rotation);
            end = new Vector3(startPos.x +(first.getWidth() * steps), startPos.y-(stepHeight*steps), startPos.z + first.getDepth());
        }

        this.steps = new ArrayList<Step>();
        this.steps.add(first);
        for(int i = 0; i < steps-1; i++) {
            float stepDepth;
            if(up) {
                startPos.y += stepHeight;
                stepDepth = -first.getWidth();
            } else {
                startPos.y -= stepHeight;
                stepDepth = first.getWidth();
            }
            if(direction == Direction.North) {
                startPos.z += stepDepth;
            } else if (direction == Direction.South) {
                startPos.z -= stepDepth;
            } else if (direction == Direction.East) {
                startPos.x -= stepDepth;
            } else {
                startPos.x += stepDepth;
            }

            this.steps.add(new Step(startPos, rotation));
        }

        createBoundLines();
    }

    private void createBoundLines() {
        boundLines = new ArrayList<ModelInstance>();
        Vector3 p1 = start.cpy();
        Vector3 p2 = new Vector3(start.x, start.y, end.z);
        Vector3 p3 = new Vector3(start.x, end.y, start.z);
        Vector3 p4 = new Vector3(end.x, start.y, start.z);

        Vector3 p5 = new Vector3(end.x, end.y, start.z);
        Vector3 p6 = new Vector3(end.x, start.y, end.z);
        Vector3 p7 = new Vector3(start.x, end.y, end.z);
        Vector3 p8 = end.cpy();

        //corner 1 lower
        boundLines.add(Main.modelHandler.getLine(p1, p2));
        boundLines.add(Main.modelHandler.getLine(p1, p3));
        boundLines.add(Main.modelHandler.getLine(p1, p4));

        //corner 2 upper
        boundLines.add(Main.modelHandler.getLine(p5, p8));
        boundLines.add(Main.modelHandler.getLine(p6, p8));
        boundLines.add(Main.modelHandler.getLine(p7, p8));

        //corner 3 lower xz upper y
        boundLines.add(Main.modelHandler.getLine(p3, p5));
        boundLines.add(Main.modelHandler.getLine(p3, p7));

        //corner 4 upper xz lower y
        boundLines.add(Main.modelHandler.getLine(p6, p2));
        boundLines.add(Main.modelHandler.getLine(p6, p4));

        //Remaining lines
        boundLines.add(Main.modelHandler.getLine(p2, p7));
        boundLines.add(Main.modelHandler.getLine(p4, p5));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {
        for(Step step : steps) {
            step.render(renderer);
        }
    }

    @Override
    public void postRender(Renderer renderer) {
        if(Main.debugDrawBounds) {
            drawBoundingBox(renderer);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(String.valueOf(up));
        params.add(direction.toString());
        params.add(originStart.toString());
        params.add(String.valueOf(steps.size()));
        params.add(String.valueOf(stepHeight));
        return params;
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.position, c);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public boolean inBounds(Vector3 position, Class c) {
        if(c == Player.class) {
            if(player == null) {
                for(GameObject object : Main.levelHandler.getActiveObjects()) {
                    if(object.getClass() == Player.class) {
                        player = (Player)object;
                    }
                }
            }

            if(position.x > start.x-1 && position.z > start.z-1) {
                if(position.x < end.x+1 && position.z < end.z+1) {
                    if(position.y > start.y-1 && position.y < end.y+1){
                        for(Step step : steps) {
                            if(step.inBounds(position)) {
                                if(player != null) player.setFloorY(step.getY(), stepHeight/4);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public float minX() {
        return 0;
    }

    @Override
    public float maxX() {
        return 0;
    }

    @Override
    public float minY() {
        return 0;
    }

    @Override
    public float maxY() {
        return 0;
    }

    @Override
    public float minZ() {
        return 0;
    }

    @Override
    public float maxZ() {
        return 0;
    }
}
