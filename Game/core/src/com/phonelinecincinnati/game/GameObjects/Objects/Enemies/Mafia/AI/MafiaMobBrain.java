package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.MafiaMob;
import com.phonelinecincinnati.game.GameObjects.Objects.Player.Player;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Utility.CollisionMaths;

import static com.phonelinecincinnati.game.GameObjects.Objects.Enemies.Mafia.AI.MafiaMobBrain.State.*;

public class MafiaMobBrain {
    public enum State {
        Patrolling,
        MovingTo,
        Following,
        Tracking,
        Returning
    }
    public State currentState;
    public Pathfinder pathfinder;

    private MafiaMob mob;
    private Player player;

    public MafiaMobBrain(MafiaMob mob, Player player) {
        currentState = Patrolling;
        pathfinder = new Pathfinder();
        this.mob = mob;
        this.player = player;
    }

    public void update() {
        pathfinder.currentPosition = mob.position;
        if(!mob.currentPath.isEmpty()) pathfinder.goalPosition = mob.currentPath.get(mob.currentPath.size()-1);
        if(CollisionMaths.lineOfSightClear(player.position.cpy(), mob.position.cpy().add(mob.body.head.getPosition()))
                && !Main.debugBlindEnemies) {
            if(canSeePlayer()) {
                if(canReachPlayer()) {
                    currentState = Following;
                } else {
                    currentState = MovingTo;
                }
            }
            if(currentState == MovingTo) {
                pathfinder.moveTo(mob, player.position.cpy());
            }
        }
        else {
            if(currentState == MovingTo || currentState == Following) {
                currentState = Tracking;
                pathfinder.track(mob, player.position.cpy());
            }
            else if(currentState == Tracking && !pathfinder.isTracking()) {
                currentState = Returning;
            }
            else if(currentState == Returning && !pathfinder.isReturning()) {
                currentState = Patrolling;

            }
        }
    }

    private boolean canSeePlayer() {
        return Math.abs(player.position.y - mob.position.y) < 1 &&
                lineOfSightFromPoint(mob.position.cpy().add(mob.body.head.getPosition()));
    }

    public boolean canReachPlayer() {
        return lineOfSightFromPoint(mob.position);
    }

    private boolean lineOfSightFromPoint(Vector3 point) {
        float FOV = 40;

        Vector2 facingDir = new Vector2((float) Math.cos(mob.rotation.y * MathUtils.degreesToRadians), (float) Math.sin(mob.rotation.y * MathUtils.degreesToRadians));
        Vector2 playerDir = new Vector2(player.position.x - mob.position.x, player.position.z - mob.position.z).nor();

        float diff = (float) Math.abs(Math.acos(facingDir.dot(playerDir) / facingDir.len() * playerDir.len())) * MathUtils.radiansToDegrees;

        return !(diff > FOV) && CollisionMaths.lineOfSightClear(player.position, point);
    }
}
