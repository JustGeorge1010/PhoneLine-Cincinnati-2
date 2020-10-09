package com.phonelinecincinnati.game.GameObjects.Objects.Enemies.PathFinding;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.GameDecal;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.GameObjects.Objects.SolidWall;
import com.phonelinecincinnati.game.Main;
import com.phonelinecincinnati.game.Models.TextureName;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class AStar {
    private static AStar aStar;

    private static float spacingLength = 1.5f;
    private HashMap<Vector3, Node> nodes;

    private Vector3 startPosition, endPosition;
    private Node current;
    private HashSet<Node> openList;
    private HashSet<Node> closedList;
    private Stack<Vector3> path;
    private HashMap<Vector3, Node> allNodes;

    private AStar() {
        nodes = new HashMap<Vector3, Node>();
        CopyOnWriteArrayList<Node> allNodes = new CopyOnWriteArrayList<Node>();

        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(Collidable.class.isInstance(object)) {
                Collidable collidable = (Collidable)object;
                ArrayList<Vector3> nodePoints = new ArrayList<Vector3>();

                if(SolidWall.class.isInstance(object)) {
                    float wallLength = collidable.maxX() - collidable.minX();
                    float wallDepth = collidable.maxZ() - collidable.minZ();

                    if(wallLength < wallDepth) {
                        nodePoints.add(new Vector3(collidable.minX()+wallLength/2, 0, collidable.minZ()+0.5f));
                        nodePoints.add(new Vector3(collidable.minX()+wallLength/2, 0, collidable.maxZ()-0.5f));
                    } else {
                        nodePoints.add(new Vector3(collidable.minX()+0.5f, 0, collidable.minZ()+wallDepth/2));
                        nodePoints.add(new Vector3(collidable.maxX()-0.5f, 0, collidable.minZ()+wallDepth/2));
                    }
                } else {
                    nodePoints.add(new Vector3(collidable.minX(), 0, collidable.minZ()));
                    nodePoints.add(new Vector3(collidable.maxX(), 0, collidable.minZ()));
                    nodePoints.add(new Vector3(collidable.minX(), 0, collidable.maxZ()));
                    nodePoints.add(new Vector3(collidable.maxX(), 0, collidable.maxZ()));
                }

                for(Vector3 point : nodePoints) {
                    ArrayList<Node> currentNodes = new ArrayList<Node>();
                    currentNodes.add(new Node(new Vector3(point.x-spacingLength, 0, point.z-spacingLength)));
                    currentNodes.add(new Node(new Vector3(point.x+spacingLength, 0, point.z-spacingLength)));
                    currentNodes.add(new Node(new Vector3(point.x-spacingLength, 0, point.z+spacingLength)));
                    currentNodes.add(new Node(new Vector3(point.x+spacingLength, 0, point.z+spacingLength)));

                    for(Node node : currentNodes) {
                        boolean alreadyExists = false;
                        for(Node existingNode : allNodes) {
                            if(node.position.epsilonEquals(existingNode.position)) alreadyExists = true;
                        }
                        if(!alreadyExists) allNodes.add(node);
                    }
                }
            }
        }

        for(Node node : allNodes) {
            node.findNeighbors(allNodes);
            if(!node.neighbors.isEmpty()) nodes.put(node.position, node);
        }

        if(Main.debugDrawPaths) {
            ArrayList<Node> alreadyGenerated = new ArrayList<Node>();
            for(Node node : nodes.values()) {
                Main.levelHandler.addObjectToCurrentLevel(new GameDecal(
                        new Vector3(node.position.x, node.position.y+1, node.position.z),
                        0.5f, 0.5f, new Vector3(90, 0, 0), TextureName.BlueCircle));

                for(Node neighbor : node.neighbors) {
                    if(alreadyGenerated.contains(neighbor)) continue;
                    Main.levelHandler.addObjectToCurrentLevel(new DebugPathLine(node.position, neighbor.position));
                    alreadyGenerated.add(node);
                }
            }
        }
    }

    public static AStar getAStar() throws Exception {
        if(aStar == null) {
            throw new Exception("No AStar generated");
        }
        return aStar;
    }

    public static AStar generateAStar() {
        aStar = new AStar();
        return aStar;
    }

    public ArrayList<Vector3> pathfind(Vector3 startPosition, Vector3 endPosition) {
        reset();
        this.startPosition = new Vector3(startPosition.x, 0, startPosition.z);
        this.endPosition = new Vector3(endPosition.x, 0, endPosition.z);

        algorithm();

        ArrayList<Vector3> vectorPath = new ArrayList<Vector3>();

        if(path != null && path.size() != 0) {
            for(int i = path.size()-1; i >= 0; i--) {
                vectorPath.add(path.get(i));
            }
        }

        System.out.println("Ran pathfinder: "+vectorPath);

        return vectorPath;
    }

    private void init() {
        allNodes = new HashMap<Vector3, Node>();
        allNodes.putAll(nodes);
        allNodes.put(startPosition, getNode(startPosition));
        allNodes.put(endPosition, getNode(endPosition));

        allNodes.get(startPosition).findNeighbors(allNodes.values(), true);
        allNodes.get(endPosition).findNeighbors(allNodes.values(), true);

        if(Main.debugDrawPaths) {
            ArrayList<Node> newNodes = new ArrayList<Node>();
            newNodes.add(allNodes.get(startPosition));
            newNodes.add(allNodes.get(endPosition));
            ArrayList<Node> alreadyGenerated = new ArrayList<Node>();
            for(Node node: newNodes) {
                Main.levelHandler.addObjectToCurrentLevel(new GameDecal(
                        new Vector3(node.position.x, node.position.y+1, node.position.z),
                        0.5f, 0.5f, new Vector3(90, 0, 0), TextureName.BlueCircle));

                for(Node neighbor : node.neighbors) {
                    if(alreadyGenerated.contains(neighbor)) continue;
                    Main.levelHandler.addObjectToCurrentLevel(new DebugPathLine(node.position, neighbor.position));
                    alreadyGenerated.add(node);
                }
            }
        }

        current = allNodes.get(startPosition);
        openList = new HashSet<Node>();
        openList.add(current);
        closedList = new HashSet<Node>();
    }

    private void algorithm() {
        if(current == null) {
            init();
        }

        while(openList.size() != 0 && path == null) {
            current.findNeighbors(allNodes.values());
            List<Node> neighbors = current.neighbors;

            examineNeighbors(neighbors);
            updateCurrentNode();

            path = generatePath(current);
        }
    }

    private void examineNeighbors(List<Node> neighbors) {
        for(Node neighbor : neighbors) {
            int gScore = determineGScore(neighbor.position, current.position);

            if(openList.contains(neighbor)) {
                if(current.G + gScore < neighbor.G) {
                    calcValues(current, neighbor, gScore);
                }
            } else if(!closedList.contains(neighbor)) {
                calcValues(current, neighbor, gScore);

                openList.add(neighbor);
            }
        }
    }

    private void calcValues(Node parent, Node neighbor, int cost) {
        neighbor.parent = parent;

        neighbor.G = parent.G + cost;
        neighbor.H = (Math.abs(neighbor.position.x - endPosition.x) + Math.abs(neighbor.position.z - endPosition.z) * 10);
        neighbor.F = neighbor.G + neighbor.H;
    }

    private int determineGScore(Vector3 neighbor, Vector3 current) {
        int gScore = 10;
        float x = current.x - neighbor.x;
        float z = current.z - neighbor.z;

        if (Math.abs(x - z) % 2 != 1) {
            gScore = 14;
        }

        return gScore;
    }

    class SortByF implements Comparator<Node> {
        public int compare(Node a, Node b)
        {
            return (int)((a.F - b.F)*100000);
        }
    }
    private void updateCurrentNode() {
        openList.remove(current);
        closedList.add(current);
        if (openList.size() > 0) {
            ArrayList<Node> list = new ArrayList<Node>(openList);
            Collections.sort(list, new SortByF());
            current = list.get(0);
        }
    }

    private Node getNode(Vector3 position) {
        if (allNodes.containsKey(position)) {
            return allNodes.get(position);
        }

        Node node = new Node(position);
        allNodes.put(position, node);
        return node;
    }

    private Stack<Vector3> generatePath(Node current) {
        if (current.position.epsilonEquals(endPosition)) {
            Stack<Vector3> finalPath = new Stack<Vector3>();
            while (!current.position.epsilonEquals(startPosition)) {
                finalPath.push(current.position);
                current = current.parent;
            }

            return finalPath;
        }

        return null;
    }

    private void reset() {
        for(Node node : nodes.values()) {
            node.parent = null;
            node.G = 0;
            node.H = 0;
            node.F = 0;
        }

        path = null;
        current = null;
    }
}
