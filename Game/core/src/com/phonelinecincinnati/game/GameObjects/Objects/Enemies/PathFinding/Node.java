package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.Utility.CollisionMaths;

import java.util.ArrayList;
import java.util.Collection;

public class Node {
    public float G;
    public float H;
    public float F;
    public Node parent;

    public Vector3 position;
    public ArrayList<Node> neighbors;

    Node(Vector3 position) {
        this.position = new Vector3(position);
        neighbors = new ArrayList<Node>();
    }

    void findNeighbors(Collection<Node> nodeList) {
        findNeighbors(nodeList, false);
    }

    void findNeighbors(Collection<Node> nodeList, boolean ignoreLineOfSight) {
        neighbors.clear();
        for(Node node : nodeList) {
            if(node == this) continue;

            if(ignoreLineOfSight) {
                neighbors.add(node);
                continue;
            }
            if(CollisionMaths.lineOfSightClear(this.position, node.position, 0.4f)) {
                neighbors.add(node);
            }
        }
    }
}
