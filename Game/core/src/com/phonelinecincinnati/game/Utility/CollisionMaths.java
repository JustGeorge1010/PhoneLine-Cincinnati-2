package com.phonelinecincinnati.game.Utility;

import com.badlogic.gdx.math.Vector3;
import com.phonelinecincinnati.game.GameObjects.ObjectTraits.Collidable;
import com.phonelinecincinnati.game.GameObjects.Objects.Door;
import com.phonelinecincinnati.game.GameObjects.Objects.GameObject;
import com.phonelinecincinnati.game.Main;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class CollisionMaths {

     /*********************************************
     * @return Array of faces. Length 3, [x,y,z]  *
     * [0] x axis: z, y, depth, height            *
     * [1] y axis: x, z, width, depth             *
     * [2] z axis: x, y, width, height            *
     *********************************************/
    public static Rectangle2D[] collidableTo2DFaces(Collidable collidable) {
        return collidableTo2DFaces(collidable, 0);
    }

     /*********************************************
     * @param modifier adds this amount to rect   *
     * @return Array of faces. Length 3, [x,y,z]  *
     * [0] x axis: z, y, depth, height            *
     * [1] y axis: x, z, width, depth             *
     * [2] z axis: x, y, width, height            *
     *********************************************/
    public static Rectangle2D[] collidableTo2DFaces(Collidable collidable, float modifier) {
        Rectangle2D[] faces = new Rectangle2D[3];

        faces[0] = new Rectangle2D.Float(
                collidable.minZ()-modifier,
                collidable.minY()-modifier,
                (collidable.maxZ()+modifier) - (collidable.minZ()-modifier),
                collidable.maxY()-collidable.minY()
        );
        faces[1] = new Rectangle2D.Float(
                collidable.minX()-modifier,
                collidable.minZ()-modifier,
                (collidable.maxX()+modifier) - (collidable.minX()-modifier),
                (collidable.maxZ()+modifier) - (collidable.minZ()-modifier)
        );
        faces[2] = new Rectangle2D.Float(
                collidable.minX()-modifier,
                collidable.minY()-modifier,
                (collidable.maxX()+modifier) - (collidable.minX()-modifier),
                (collidable.maxY()+modifier) - (collidable.minY()-modifier)
        );

        return faces;
    }

    /*********************************************
     * @return Array of lines. Length 3, [x,y,z]  *
     * [0] x axis: z, y, depth, height            *
     * [1] y axis: x, z, width, depth             *
     * [2] z axis: x, y, width, height            *
     *********************************************/
    public static Line2D[] lineTo2DLines(Vector3 a, Vector3 b) {
        Line2D[] lines = new Line2D[3];

        lines[0] = new Line2D.Float(
                a.z, a.y,
                b.z, b.y
        );
        lines[1] = new Line2D.Float(
                a.x, a.z,
                b.x, b.z
        );
        lines[2] = new Line2D.Float(
                a.x, a.y,
                b.x, b.y
        );

        return lines;
    }

    public static boolean lineOfSightClear(Vector3 a, Vector3 b) {
        return lineOfSightClear(a, b, 0);
    }

    public static boolean lineOfSightClear(Vector3 a, Vector3 b, float modifier) {
        Line2D[] line3D = CollisionMaths.lineTo2DLines(a, b);

        for(GameObject object : Main.levelHandler.getActiveObjects()) {
            if(lineCollision(line3D, object, modifier)) return false;
//            if(Door.class.isInstance(object)) { TODO: delete
//                if(((Door)object).open) continue;
//                Rectangle2D[] faces = CollisionMaths.collidableTo2DFaces((Collidable)object, modifier);
//                if(line3D[0].intersects(faces[0]) &&
//                        line3D[1].intersects(faces[1]) &&
//                        line3D[2].intersects(faces[2])) {
//                    return false;
//                }
//            }
//            if(Collidable.class.isInstance(object)) {
//                Rectangle2D[] faces = CollisionMaths.collidableTo2DFaces((Collidable)object, modifier);
//                if(line3D[0].intersects(faces[0]) &&
//                        line3D[1].intersects(faces[1]) &&
//                        line3D[2].intersects(faces[2])) {
//                    return false;
//                }
//            }
        }

        return true;
    }

    public static boolean lineCollision(Line2D[] line, GameObject object) {
        return lineCollision(line, object, 0f);
    }

    public static boolean lineCollision(Line2D[] line, GameObject object, float modifier) {
        if(Door.class.isInstance(object)) {
            if(((Door)object).open) return false;
            Rectangle2D[] faces = CollisionMaths.collidableTo2DFaces((Collidable)object, modifier);
            return line[0].intersects(faces[0]) && line[1].intersects(faces[1]) && line[2].intersects(faces[2]);
        }
        if(Collidable.class.isInstance(object)) {
            Rectangle2D[] faces = CollisionMaths.collidableTo2DFaces((Collidable)object, modifier);
            return line[0].intersects(faces[0]) && line[1].intersects(faces[1]) && line[2].intersects(faces[2]);
        }
        return false;
    }
}
