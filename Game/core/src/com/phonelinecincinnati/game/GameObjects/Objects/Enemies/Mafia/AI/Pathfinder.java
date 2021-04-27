package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding.AStar;

public class Pathfinder {
    Vector3 currentPosition;
    Vector3 goalPosition;

    private boolean tracking, returning;

    public void moveTo(MafiaMob mob, Vector3 goalPosition) {
        this.currentPosition = mob.position;
        this.goalPosition = new Vector3(goalPosition);
        try {
            mob.currentPath = AStar.getAStar().pathfind(new Vector3(mob.position), new Vector3(goalPosition));
            mob.index = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTracking() {
        if(tracking && currentPosition.epsilonEquals(goalPosition)) {
            tracking = false;
        }
        return tracking;
    }

    public void track(MafiaMob mob, Vector3 goalPosition) {
        this.currentPosition = mob.position;
        this.goalPosition = new Vector3(goalPosition);
        tracking = true;
        try {
            mob.currentPath = AStar.getAStar().pathfind(new Vector3(mob.position), new Vector3(goalPosition));
            mob.index = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isReturning() {
        if(returning && currentPosition.epsilonEquals(goalPosition)) {
            returning = false;
        }
        return returning;
    }
}
