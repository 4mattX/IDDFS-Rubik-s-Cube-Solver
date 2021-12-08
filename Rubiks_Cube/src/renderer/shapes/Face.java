package renderer.shapes;

import renderer.cubes.Cube;

import java.util.ArrayList;
import java.util.List;

public class Face {

    Cube cube;
    String side;
    List<Cubie> cubies = new ArrayList<>();


    public Face(Cube cube, String side, List<Cubie> cubies) {
        this.cube = cube;
        this.side = side;
        this.cubies = cubies;
    }

    public Face(Cube cube, String side) {
        this.cube = cube;
        this.side = side;
    }

    public void addCubie(Cubie cubie) {
        cubies.add(cubie);
    }

    public void setCubies(List<Cubie> cubieList) {

        for (int i = 0; i < 9; i++) {
            this.cubies.set(i, cubieList.get(i));
        }
    }

    public void turnClockWise(int deg) {

        switch (side) {
            case "white":
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, 0,  -deg);
                }
                break;
            case "yellow":
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, 0,  deg);
                }
                break;
            case "red":
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, deg, 0,  0);
                }
                break;
            case "orange":
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, -deg, 0,  0);
                }
                break;
            case "green":
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, deg,  0);
                }
                break;
            case "blue":
                for (Cubie cubie : this.cubies) {
                    cubie.rotate(true, 0, -deg,  0);
                }
                break;
        }

        this.cube.rotate(true, 0, 0, 0);
    }

    public List<Cubie> getCubies() {
        return this.cubies;
    }
}
