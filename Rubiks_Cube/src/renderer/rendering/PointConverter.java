package renderer.rendering;

import renderer.rendering.Display;
import renderer.rendering.MyPoint;

import java.awt.*;

// Converts 3d point into 2d point
public class PointConverter {

    public static double scale = 1;
    private static final int PERSPECTIVE_DIST = 15;
    private static final int LOCAL_SCALE = 1000;
    private static final double ZOOM = 1.2;
    public static double xDeg;


    public static Point convertPoint(MyPoint point3D) {
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth = point3D.x * scale;
        double[] newVal = scale(x3d, y3d, depth);
        int x2d = (int) (Display.WIDTH / 2 + newVal[0]);
        int y2d = (int) (Display.HEIGHT / 2 - newVal[1]);

        Point point2D = new Point(x2d, y2d);
        return point2D;
    }

    private static double[] scale(double x3d, double y3d, double depth) {

        double distance = Math.sqrt(x3d * x3d + y3d * y3d);
        double vectorAngle = Math.atan2(y3d, x3d);
        double depth2 = PERSPECTIVE_DIST - depth;
        double localScale = Math.abs(LOCAL_SCALE / (depth2 + LOCAL_SCALE));
        distance *= localScale;

        // Some trig to find the actual distance based on vectorAngle
        double[] newVal = new double[2];
        newVal[0] = distance * Math.cos(vectorAngle);
        newVal[1] = distance * Math.sin(vectorAngle);
        return newVal;
    }

    // SOH CAH TOA
    public static void rotateAxisX(MyPoint point, boolean clockWise, double degrees) {
        double radius = Math.sqrt(point.y * point.y + point.z * point.z);

        double vectorAngle = Math.atan2(point.z, point.y);

        // Converting degrees to radians
        vectorAngle += 2 * Math.PI/360 * degrees * -1;

        if (!clockWise) {
            vectorAngle *= -1;
        }

        point.y = radius * Math.cos(vectorAngle);
        point.z = radius * Math.sin(vectorAngle);
    }

    public static void rotateAxisY(MyPoint point, boolean clockWise, double degrees) {
        double radius = Math.sqrt(point.x * point.x + point.z * point.z);

        double vectorAngle = Math.atan2(point.x, point.z);

        // Converting degrees to radians
        vectorAngle += 2 * Math.PI/360 * degrees * -1;

        xDeg += Math.atan(point.x) * degrees;

        if (!clockWise) {
            vectorAngle *= -1;
        }

        point.x = radius * Math.sin(vectorAngle);
        point.z = radius * Math.cos(vectorAngle);

    }

    public static void rotateAxisZ(MyPoint point, boolean clockWise, double degrees) {
        double radius = Math.sqrt(point.y * point.y + point.x * point.x);

        double vectorAngle = Math.atan2(point.y, point.x);

        // Converting degrees to radians
        vectorAngle += 2 * Math.PI/360 * degrees * -1;

        xDeg += Math.atan(point.x) * degrees;

        if (!clockWise) {
            vectorAngle *= -1;
        }

        point.y = radius * Math.sin(vectorAngle);
        point.x = radius * Math.cos(vectorAngle);

    }

    public static void zoomIn() {
        scale *= ZOOM;
    }

    public static void zoomOut() {
        scale /= ZOOM;
    }
}
