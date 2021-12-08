package renderer.rendering;

// Simple structure to hold a point in 3D space
public class MyPoint {

    public double x;
    public double y;
    public double z;

    public MyPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void addZ(double value) {
        this.z += value;
    }

    public double getX() {
        return this.x;
    }
}
