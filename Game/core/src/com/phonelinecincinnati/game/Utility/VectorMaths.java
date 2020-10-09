package com.phonelinecincinnati.game.Utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class VectorMaths {
    private static float TriangleArea(Vector2 tri1, Vector2 tri2, Vector2 tri3) {
        return (float)Math.abs((tri1.x*(tri2.y-tri3.y) + tri2.x*(tri3.y-tri1.y) + tri3.x*(tri1.y-tri2.y))/2.0);
    }

    public static boolean vectorIsInsideTriangle(Vector2 point, Vector2 triangle1, Vector2 triangle2, Vector2 triangle3) {
        double triArea = TriangleArea(triangle1, triangle2, triangle3);
        double p23 = TriangleArea(point, triangle2, triangle3);
        double p13 = TriangleArea(point, triangle1, triangle3);
        double p12 = TriangleArea(point, triangle1, triangle2);

        return (Math.round(triArea) == Math.round(p23 + p13 + p12));
    }

    public static boolean vectorIsInsideTriangle(Vector3 point, Vector3 triangle1, Vector3 triangle2, Vector3 triangle3) {
        return vectorIsInsideTriangle(new Vector2(point.x, point.z), new Vector2(triangle1.x, triangle1.z), new Vector2(triangle2.x, triangle2.z), new Vector2(triangle3.x, triangle3.z));
    }
}
