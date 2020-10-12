package com.phonelinecincinnati.game.GameObjects.Objects.Vertical;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Renderer;

import java.util.ArrayList;

public class Stairs extends GameObject implements Collidable{
    private Vector3 start, end;
    private ArrayList<Step> steps;
    private boolean up;
    private Direction direction;

    private Player player;
    private float stepHeight;

    public Stairs(boolean up, Direction direction, Vector3 startPos, int steps, float stepHeight) {
        this.up = up;
        this.direction = direction;
        start = new Vector3(startPos);
        this.stepHeight = stepHeight;

        Step first;
        float rotation;
        if (direction == Direction.North || direction == Direction.South) {
            if (direction == Direction.North) {
                rotation = -90;
            } else {
                rotation = 90;
            }
            first = new Step(startPos, rotation);
            if(up) {
                end = new Vector3(startPos.x + first.getDepth(), startPos.y + (stepHeight * steps), startPos.z + (first.getWidth() * steps));
            } else {
                end = new Vector3(startPos.x + first.getDepth(), startPos.y - (stepHeight * steps), startPos.z + (first.getWidth() * steps));
            }
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
            if(up) {
                startPos.y += stepHeight;
            } else {
                startPos.y -= stepHeight;
            }
            if(direction == Direction.North) {
                startPos.z += first.getWidth();
            } else if (direction == Direction.South) {
                startPos.z -= first.getWidth();
            } else if (direction == Direction.East) {
                startPos.x -= first.getWidth();
            } else {
                startPos.x += first.getWidth();
            }

            this.steps.add(new Step(startPos, rotation));
        }
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

    }

    @Override
    public void dispose() {

    }

    @Override
    public ArrayList<String> getConstructParams() {
        ArrayList<String> params = new ArrayList<String>();
        params.add(String.valueOf(up));
        params.add(direction.toString());
        params.add(start.toString());
        params.add(String.valueOf(steps.size()));
        params.add(String.valueOf(stepHeight));
        return params;
    }

    @Override
    public boolean inBounds(GameObject object, Class c) {
        return inBounds(object.position, c);
    }

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
            if(position.x > start.x && position.z > start.z) {
                if(position.x < end.x && position.z < end.z) {
                    for(Step step : steps) {
                        if(step.inBounds(position)) {
                            if(player != null) player.setFloorY(step.getY(), stepHeight/4);
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
