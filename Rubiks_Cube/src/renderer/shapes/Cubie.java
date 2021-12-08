package renderer.shapes;

import renderer.rendering.MyPoint;

import java.awt.*;

// Group of polygons, one of the 26 cubies in a Rubik's Cube
public class Cubie {

    private MyPolygon[] polygons;
    private Color color;
    private int location; // The index value of which the cubie is located on the cube

    public Cubie(double x, double y, double z, int size, int location, int[] position) {
        this.color = Color.WHITE;
        this.location = location;
        createCubie(x, y, z, size, position);
    }

    public void render(Graphics g) {
        for (MyPolygon poly : this.polygons) {
            poly.render(g);
        }
    }

    // Sorts the polygons such that faces in front are displayed in front of rear faces
    public void sortPolygons() {
        MyPolygon.sortPolygons(this.polygons);
    }

    private void createCubie(double x, double y, double z, int size, int[] position) {
        MyPoint p1 = new MyPoint(size / 2 + x, -size / 2 + y, -size / 2 + z);
        MyPoint p2 = new MyPoint(size / 2 + x, size / 2 + y, -size / 2 + z);
        MyPoint p3 = new MyPoint(size / 2 + x, size / 2 + y, size / 2 + z);
        MyPoint p4 = new MyPoint(size / 2 + x, -size / 2 + y, size / 2 + z);
        MyPoint p5 = new MyPoint(-size / 2 + x, -size / 2 + y, -size / 2 + z);
        MyPoint p6 = new MyPoint(-size / 2 + x, size / 2 + y, -size / 2 + z);
        MyPoint p7 = new MyPoint(-size / 2 + x, size / 2 + y, size / 2 + z);
        MyPoint p8 = new MyPoint(-size / 2 + x, -size / 2 + y, size / 2 + z);


        polygons = new MyPolygon[6];

        Color orange = new Color(255, 162, 0);
        Color white = new Color(252, 254, 255);
        Color blue = new Color(0, 145, 255);
        Color green = new Color(63, 252, 20);
        Color yellow = new Color(245, 245, 32);
        Color red = new Color(250, 30, 10);

        polygons[0] = new MyPolygon(orange, this.location, p5,p6,p7,p8);
        polygons[1] = new MyPolygon(white, this.location, p1,p2,p6,p5);
        polygons[2] = new MyPolygon(blue, this.location, p1,p5,p8,p4);
        polygons[3] = new MyPolygon(green, this.location, p2,p6,p7,p3);
        polygons[4] = new MyPolygon(yellow, this.location, p4,p3,p7,p8);
        polygons[5] = new MyPolygon(red, this.location, p1,p2,p3,p4);

        Color middleColor = new Color(10, 10, 10);

        if (position[0] < 1) {
            polygons[4] = new MyPolygon(middleColor, this.location, p4,p3,p7,p8);
        }
        if (position[0] == 1 || position[0] == 0) {
            polygons[1] = new MyPolygon(middleColor, this.location, p1,p2,p6,p5);
        }
        if (position[2] < 1) {
            polygons[3] = new MyPolygon(middleColor, this.location, p2,p6,p7,p3);
        }
        if (position[2] == 0 || position[2] == 1) {
            polygons[2] = new MyPolygon(middleColor, this.location, p1,p5,p8,p4);
        }
        if (position[1] == 0 || position[1] == -1) {
            polygons[5] = new MyPolygon(middleColor, this.location, p1,p2,p3,p4);
        }
        if (position[1] == 0 || position[1] == 1) {
            polygons[0] = new MyPolygon(middleColor, this.location, p5,p6,p7,p8);
        }
    }

    private void setPolygonColor() {
        for (MyPolygon poly : this.polygons) {
            poly.setColor(this.color);
        }
    }

    public void rotate(boolean clockWise, double xDegrees, double yDegrees, double zDegrees) {
        for (MyPolygon polygon : this.polygons) {
            polygon.rotate(clockWise, xDegrees, yDegrees, zDegrees);
        }
        this.sortPolygons();
    }

    public void translateZ(double z) {
        for (MyPolygon polygon : this.polygons) {
            for (MyPoint point : polygon.getPoints()) {
                point.addZ(z);
            }
        }
    }

    public double getAverageX() {
        double sum = 0;

        for (MyPolygon polygon : this.polygons) {
            for (MyPoint point : polygon.getPoints()) {
                sum += point.getX();
            }
        }
        return sum;
    }
}
