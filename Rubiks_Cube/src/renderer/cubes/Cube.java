package renderer.cubes;

import renderer.rendering.Display;
import renderer.shapes.Cubie;
import renderer.shapes.Face;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Cube {

    private Cubie[] cubies;
    public HashMap<String, Face> faces = new HashMap<>();
    private int size;
    private int spacing;
    private final int delay = 0;

    // x,y,z are referring to the rotated degrees on the axis
    private double x;
    private double y;
    private double z;

    public static boolean isCalculating = false;

    public Cube(int size, int spacing) {
        this.size = size;
        this.spacing = spacing;
        createCube();
    }

    private void createCube() {
        cubies = new Cubie[27];

        int amtCubies = 0;

        for (int layer = -1; layer < 2; layer++) {
            for (int row = -1; row < 2; row++) {
                for (int col = -1; col < 2; col++) {
                    int[] position = {layer, row, col};
                    cubies[amtCubies] = new Cubie(row * (size + spacing), col * (size + spacing), layer * (size + spacing), size, amtCubies, position);
                    amtCubies++;
                }
            }
        }

        createFaces();
    }

    private void createFaces() {
        // White Face
        Face whiteFace = new Face(this, "white");
        for (int i = 0; i < 9; i++) {
            whiteFace.addCubie(cubies[i]);
        }
        faces.put("white", whiteFace);

        // Yellow Face
        Face yellowFace = new Face(this, "yellow");
        for (int i = 18; i < 27; i++) {
            yellowFace.addCubie(cubies[i]);
        }
        faces.put("yellow", yellowFace);

        // Green Face
        Face greenFace = new Face(this, "green");
        for (int i = 2; i <= 26; i += 3) {
            greenFace.addCubie(cubies[i]);
        }
        faces.put("green", greenFace);

        // Blue Face
        Face blueFace = new Face(this, "blue");
        for (int i = 0; i <= 24; i += 3) {
            blueFace.addCubie(cubies[i]);
        }
        faces.put("blue", blueFace);

        // Orange Face
        Face orangeFace = new Face(this, "orange");
        orangeFace.addCubie(cubies[0]);
        orangeFace.addCubie(cubies[1]);
        orangeFace.addCubie(cubies[2]);
        orangeFace.addCubie(cubies[9]);
        orangeFace.addCubie(cubies[10]);
        orangeFace.addCubie(cubies[11]);
        orangeFace.addCubie(cubies[18]);
        orangeFace.addCubie(cubies[19]);
        orangeFace.addCubie(cubies[20]);
        faces.put("orange", orangeFace);

        // Red Face
        Face redFace = new Face(this, "red");
        redFace.addCubie(cubies[6]);
        redFace.addCubie(cubies[7]);
        redFace.addCubie(cubies[8]);
        redFace.addCubie(cubies[15]);
        redFace.addCubie(cubies[16]);
        redFace.addCubie(cubies[17]);
        redFace.addCubie(cubies[24]);
        redFace.addCubie(cubies[25]);
        redFace.addCubie(cubies[26]);
        faces.put("red", redFace);
    }

    public void performMoves(String moves) throws InterruptedException {

        String[] singleMove = moves.split(" ", moves.length());
        List<String> moveList = Arrays.asList(singleMove);

        for (int i = 0; i < (moves.length() + 1) / 2; i++) {

            String face = moveList.get(i);
            int ccw = 1;
            if (Character.isLowerCase(face.charAt(0))) {
                ccw = -1;
            }

            switch (face) {
                case "f":
                    rotateRed();
                    rotateRed();
                    Display.getFastCube().rotateCounterClockWise(FastCube.RED);
                    Display.getFastCube().rotateCounterClockWise(FastCube.RED);

                case "F":
                    rotateRed();
                    for (int deg = 0; deg < 90; deg++) {
                        isCalculating = true;
                        Thread.sleep(delay);
                        undoRotation();
                        this.faces.get("red").turnClockWise(ccw);
                        redoRotation();
                        isCalculating = false;
                    }
                    Display.getFastCube().rotateClockWise(FastCube.RED);

                    break;

                case "u":
                    rotateYellow();
                    rotateYellow();
                    Display.getFastCube().rotateCounterClockWise(FastCube.YELLOW);
                    Display.getFastCube().rotateCounterClockWise(FastCube.YELLOW);

                case "U":
                    rotateYellow();
                    for (int deg = 0; deg < 90; deg++) {
                        isCalculating = true;
                        Thread.sleep(delay);
                        undoRotation();
                        this.faces.get("yellow").turnClockWise(ccw);
                        redoRotation();
                        isCalculating = false;
                    }
                    Display.getFastCube().rotateClockWise(FastCube.YELLOW);
                    break;

                case "d":
                    rotateWhite();
                    rotateWhite();
                    Display.getFastCube().rotateCounterClockWise(FastCube.WHITE);
                    Display.getFastCube().rotateCounterClockWise(FastCube.WHITE);

                case "D":
                    rotateWhite();
                    for (int deg = 0; deg < 90; deg++) {
                        isCalculating = true;
                        Thread.sleep(delay);
                        undoRotation();
                        this.faces.get("white").turnClockWise(ccw);
                        redoRotation();
                        isCalculating = false;
                    }
                    Display.getFastCube().rotateClockWise(FastCube.WHITE);
                    break;

                case "l":
                    rotateBlue();
                    rotateBlue();
                    Display.getFastCube().rotateCounterClockWise(FastCube.BLUE);
                    Display.getFastCube().rotateCounterClockWise(FastCube.BLUE);

                case "L":
                    rotateBlue();
                    for (int deg = 0; deg < 90; deg++) {
                        isCalculating = true;
                        Thread.sleep(delay);
                        undoRotation();
                        this.faces.get("blue").turnClockWise(ccw);
                        redoRotation();
                        isCalculating = false;
                    }
                    Display.getFastCube().rotateClockWise(FastCube.BLUE);
                    break;

                case "r":
                    rotateGreen();
                    rotateGreen();
                    Display.getFastCube().rotateCounterClockWise(FastCube.GREEN);
                    Display.getFastCube().rotateCounterClockWise(FastCube.GREEN);

                case "R":
                    rotateGreen();
                    for (int deg = 0; deg < 90; deg++) {
                        isCalculating = true;
                        Thread.sleep(delay);
                        undoRotation();
                        this.faces.get("green").turnClockWise(ccw);
                        redoRotation();
                        isCalculating = false;
                    }
                    Display.getFastCube().rotateClockWise(FastCube.GREEN);
                    break;

                case "b":
                    rotateOrange();
                    rotateOrange();
                    Display.getFastCube().rotateCounterClockWise(FastCube.ORANGE);
                    Display.getFastCube().rotateCounterClockWise(FastCube.ORANGE);

                case "B":
                    rotateOrange();
                    for (int deg = 0; deg < 90; deg++) {
                        isCalculating = true;
                        Thread.sleep(delay);
                        undoRotation();
                        this.faces.get("orange").turnClockWise(ccw);
                        redoRotation();
                        isCalculating = false;
                    }
                    Display.getFastCube().rotateClockWise(FastCube.ORANGE);
                    break;
            }

        }

    }

    public void render(Graphics g) {
        for (Cubie cubie : this.cubies) {
            cubie.render(g);
        }
    }

    public void rotate(boolean clockWise, double xDegrees, double yDegrees, double zDegrees) {

        for (Cubie cubie : this.cubies) {
            cubie.rotate(clockWise, xDegrees, yDegrees, zDegrees);
        }
        this.cubies = sortCubies(this.cubies);
    }

    public void undoRotation() {
        List<Double> degreeList = Display.degreeList;
        List<Boolean> axisList = Display.axisList;

        for (int i = degreeList.size() - 1; i > 0; i--) {
            if (axisList.get(i)) {
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, -degreeList.get(i), 0);
                }
            } else {
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, 0, -degreeList.get(i));
                }
            }
        }
        this.cubies = sortCubies(this.cubies);
    }

    public void redoRotation() {
        List<Double> degreeList = Display.degreeList;
        List<Boolean> axisList = Display.axisList;

        for (int i = 0; i < degreeList.size(); i++) {
            if (axisList.get(i)) {
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, degreeList.get(i), 0);
                }
            } else {
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, 0, degreeList.get(i));
                }
            }
        }
        this.cubies = sortCubies(this.cubies);
    }

    public static Cubie[] sortCubies(Cubie[] cubieArray) {

        List<Cubie> cubieList = new ArrayList<>();

        for (Cubie c : cubieArray) {
            cubieList.add(c);
        }

        Collections.sort(cubieList, new Comparator<Cubie>() {
            @Override
            public int compare(Cubie c1, Cubie c2) {

                double c1AverageX = c1.getAverageX();
                double c2AverageX = c2.getAverageX();
                double diff = c2AverageX - c1AverageX;

                if (diff == 0) {
                    return 0;
                }

                return c2.getAverageX() - c1.getAverageX() < 0 ? 1 : -1;



            }
        });

        for (int i = 0; i < cubieArray.length; i++) {
            cubieArray[i] = cubieList.get(i);
        }

        return cubieArray;
    }

    public void addY(double value) {
        this.y += value;
    }

    public void addZ(double value) {
        this.z += value;
    }

    public List<Cubie> getCubies() {
        return Arrays.asList(this.cubies);
    }

    public void rotateRed() {
        List<Cubie> redList = this.faces.get("red").getCubies();
        // Fix the four adjacent sides

        // Blue Face
        List<Cubie> blueList = this.faces.get("blue").getCubies();
        blueList.set(2, redList.get(2));
        blueList.set(5, redList.get(1));
        blueList.set(8, redList.get(0));

        // Yellow Face
        List<Cubie> yellowList = this.faces.get("yellow").getCubies();
        yellowList.set(6, redList.get(0));
        yellowList.set(7, redList.get(3));
        yellowList.set(8, redList.get(6));

        // Green Face
        List<Cubie> greenList = this.faces.get("green").getCubies();
        greenList.set(2, redList.get(8));
        greenList.set(5, redList.get(7));
        greenList.set(8, redList.get(6));

        // White Face
        List<Cubie> whiteList = this.faces.get("white").getCubies();
        whiteList.set(6, redList.get(2));
        whiteList.set(7, redList.get(5));
        whiteList.set(8, redList.get(8));

        // Rotate all cubies of this red face clockwise once
        List<Cubie> redListFinal = new ArrayList<>();
        redListFinal.add(faces.get("red").getCubies().get(2));
        redListFinal.add(faces.get("red").getCubies().get(5));
        redListFinal.add(faces.get("red").getCubies().get(8));
        redListFinal.add(faces.get("red").getCubies().get(1));
        redListFinal.add(faces.get("red").getCubies().get(4));
        redListFinal.add(faces.get("red").getCubies().get(7));
        redListFinal.add(faces.get("red").getCubies().get(0));
        redListFinal.add(faces.get("red").getCubies().get(3));
        redListFinal.add(faces.get("red").getCubies().get(6));

        this.faces.get("red").setCubies(redListFinal);
        this.faces.get("blue").setCubies(blueList);
        this.faces.get("yellow").setCubies(yellowList);
        this.faces.get("green").setCubies(greenList);
        this.faces.get("white").setCubies(whiteList);
    }

    public void rotateYellow() {
        List<Cubie> yellowList = this.faces.get("yellow").getCubies();

        List<Cubie> redList = this.faces.get("red").getCubies();
        redList.set(6, yellowList.get(8));
        redList.set(7, yellowList.get(5));
        redList.set(8, yellowList.get(2));

        List<Cubie> blueList = this.faces.get("blue").getCubies();
        blueList.set(6, yellowList.get(6));
        blueList.set(7, yellowList.get(7));
        blueList.set(8, yellowList.get(8));

        List<Cubie> orangeList = this.faces.get("orange").getCubies();
        orangeList.set(8, yellowList.get(0));
        orangeList.set(7, yellowList.get(3));
        orangeList.set(6, yellowList.get(6));

        List<Cubie> greenList = this.faces.get("green").getCubies();
        greenList.set(8, yellowList.get(2));
        greenList.set(7, yellowList.get(1));
        greenList.set(6, yellowList.get(0));

        List<Cubie> yellowListFinal = new ArrayList<>();
        yellowListFinal.add(faces.get("yellow").getCubies().get(6));
        yellowListFinal.add(faces.get("yellow").getCubies().get(3));
        yellowListFinal.add(faces.get("yellow").getCubies().get(0));
        yellowListFinal.add(faces.get("yellow").getCubies().get(7));
        yellowListFinal.add(faces.get("yellow").getCubies().get(4));
        yellowListFinal.add(faces.get("yellow").getCubies().get(1));
        yellowListFinal.add(faces.get("yellow").getCubies().get(8));
        yellowListFinal.add(faces.get("yellow").getCubies().get(5));
        yellowListFinal.add(faces.get("yellow").getCubies().get(2));

        this.faces.get("yellow").setCubies(yellowListFinal);
        this.faces.get("red").setCubies(redList);
        this.faces.get("blue").setCubies(blueList);
        this.faces.get("orange").setCubies(orangeList);
        this.faces.get("green").setCubies(greenList);
    }

    public void rotateBlue() {
        List<Cubie> blueList = this.faces.get("blue").getCubies();

        List<Cubie> yellowList = this.faces.get("yellow").getCubies();
        yellowList.set(0, blueList.get(0));
        yellowList.set(3, blueList.get(3));
        yellowList.set(6, blueList.get(6));
        List<Cubie> redList = this.faces.get("red").getCubies();
        redList.set(0, blueList.get(8));
        redList.set(3, blueList.get(7));
        redList.set(6, blueList.get(6));
        List<Cubie> whiteList = this.faces.get("white").getCubies();
        whiteList.set(0, blueList.get(2));
        whiteList.set(3, blueList.get(5));
        whiteList.set(6, blueList.get(8));
        List<Cubie> orangeList = this.faces.get("orange").getCubies();
        orangeList.set(0, blueList.get(2));
        orangeList.set(3, blueList.get(1));
        orangeList.set(6, blueList.get(0));

        List<Cubie> blueListFinal = new ArrayList<>();
        blueListFinal.add(faces.get("blue").getCubies().get(2));
        blueListFinal.add(faces.get("blue").getCubies().get(5));
        blueListFinal.add(faces.get("blue").getCubies().get(8));
        blueListFinal.add(faces.get("blue").getCubies().get(1));
        blueListFinal.add(faces.get("blue").getCubies().get(4));
        blueListFinal.add(faces.get("blue").getCubies().get(7));
        blueListFinal.add(faces.get("blue").getCubies().get(0));
        blueListFinal.add(faces.get("blue").getCubies().get(3));
        blueListFinal.add(faces.get("blue").getCubies().get(6));

        this.faces.get("blue").setCubies(blueListFinal);
        this.faces.get("yellow").setCubies(yellowList);
        this.faces.get("red").setCubies(redList);
        this.faces.get("white").setCubies(whiteList);
        this.faces.get("orange").setCubies(orangeList);
    }

    public void rotateGreen() {
        List<Cubie> greenList = this.faces.get("green").getCubies();

        List<Cubie> redList = this.faces.get("red").getCubies();
        redList.set(2, greenList.get(0));
        redList.set(5, greenList.get(1));
        redList.set(8, greenList.get(2));
        List<Cubie> yellowList = this.faces.get("yellow").getCubies();
        yellowList.set(2, greenList.get(8));
        yellowList.set(5, greenList.get(5));
        yellowList.set(8, greenList.get(2));
        List<Cubie> orangeList = this.faces.get("orange").getCubies();
        orangeList.set(2, greenList.get(6));
        orangeList.set(5, greenList.get(7));
        orangeList.set(8, greenList.get(8));
        List<Cubie> whiteList = this.faces.get("white").getCubies();
        whiteList.set(2, greenList.get(6));
        whiteList.set(5, greenList.get(3));
        whiteList.set(8, greenList.get(0));

        List<Cubie> greenListFinal = new ArrayList<>();
        greenListFinal.add(faces.get("green").getCubies().get(6));
        greenListFinal.add(faces.get("green").getCubies().get(3));
        greenListFinal.add(faces.get("green").getCubies().get(0));
        greenListFinal.add(faces.get("green").getCubies().get(7));
        greenListFinal.add(faces.get("green").getCubies().get(4));
        greenListFinal.add(faces.get("green").getCubies().get(1));
        greenListFinal.add(faces.get("green").getCubies().get(8));
        greenListFinal.add(faces.get("green").getCubies().get(5));
        greenListFinal.add(faces.get("green").getCubies().get(2));

        this.faces.get("green").setCubies(greenListFinal);
        this.faces.get("red").setCubies(redList);
        this.faces.get("yellow").setCubies(yellowList);
        this.faces.get("orange").setCubies(orangeList);
        this.faces.get("white").setCubies(whiteList);
    }

    public void rotateWhite() {
        List<Cubie> whiteList = this.faces.get("white").getCubies();

        List<Cubie> greenList = this.faces.get("green").getCubies();
        greenList.set(2, whiteList.get(6));
        greenList.set(1, whiteList.get(7));
        greenList.set(0, whiteList.get(8));
        List<Cubie> orangeList = this.faces.get("orange").getCubies();
        orangeList.set(2, whiteList.get(8));
        orangeList.set(1, whiteList.get(5));
        orangeList.set(0, whiteList.get(2));
        List<Cubie> blueList = this.faces.get("blue").getCubies();
        blueList.set(0, whiteList.get(2));
        blueList.set(1, whiteList.get(1));
        blueList.set(2, whiteList.get(0));
        List<Cubie> redList = this.faces.get("red").getCubies();
        redList.set(0, whiteList.get(0));
        redList.set(1, whiteList.get(3));
        redList.set(2, whiteList.get(6));

        List<Cubie> whiteListFinal = new ArrayList<>();
        whiteListFinal.add(faces.get("white").getCubies().get(2));
        whiteListFinal.add(faces.get("white").getCubies().get(5));
        whiteListFinal.add(faces.get("white").getCubies().get(8));
        whiteListFinal.add(faces.get("white").getCubies().get(1));
        whiteListFinal.add(faces.get("white").getCubies().get(4));
        whiteListFinal.add(faces.get("white").getCubies().get(7));
        whiteListFinal.add(faces.get("white").getCubies().get(0));
        whiteListFinal.add(faces.get("white").getCubies().get(3));
        whiteListFinal.add(faces.get("white").getCubies().get(6));

        this.faces.get("white").setCubies(whiteListFinal);
        this.faces.get("green").setCubies(greenList);
        this.faces.get("orange").setCubies(orangeList);
        this.faces.get("blue").setCubies(blueList);
        this.faces.get("red").setCubies(redList);
    }

    public void rotateOrange() {
        List<Cubie> orangeList = this.faces.get("orange").getCubies();

        List<Cubie> blueList = this.faces.get("blue").getCubies();
        blueList.set(0, orangeList.get(6));
        blueList.set(3, orangeList.get(7));
        blueList.set(6, orangeList.get(8));
        List<Cubie> whiteList = this.faces.get("white").getCubies();
        whiteList.set(0, orangeList.get(6));
        whiteList.set(1, orangeList.get(3));
        whiteList.set(2, orangeList.get(0));
        List<Cubie> greenList = this.faces.get("green").getCubies();
        greenList.set(0, orangeList.get(0));
        greenList.set(3, orangeList.get(1));
        greenList.set(6, orangeList.get(2));
        List<Cubie> yellowList = this.faces.get("yellow").getCubies();
        yellowList.set(0, orangeList.get(8));
        yellowList.set(1, orangeList.get(5));
        yellowList.set(2, orangeList.get(2));

        List<Cubie> orangeListFinal = new ArrayList<>();
        orangeListFinal.add(faces.get("orange").getCubies().get(6));
        orangeListFinal.add(faces.get("orange").getCubies().get(3));
        orangeListFinal.add(faces.get("orange").getCubies().get(0));
        orangeListFinal.add(faces.get("orange").getCubies().get(7));
        orangeListFinal.add(faces.get("orange").getCubies().get(4));
        orangeListFinal.add(faces.get("orange").getCubies().get(1));
        orangeListFinal.add(faces.get("orange").getCubies().get(8));
        orangeListFinal.add(faces.get("orange").getCubies().get(5));
        orangeListFinal.add(faces.get("orange").getCubies().get(2));

        this.faces.get("orange").setCubies(orangeListFinal);
        this.faces.get("blue").setCubies(blueList);
        this.faces.get("white").setCubies(whiteList);
        this.faces.get("green").setCubies(greenList);
        this.faces.get("yellow").setCubies(yellowList);
    }

}
