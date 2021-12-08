package renderer.cubes;

public class FastCube {

    private short[][][] cube;
    private final int SIZE = 3;

    public static final short RED = 0;
    public static final short WHITE = 1;
    public static final short ORANGE = 2;
    public static final short YELLOW = 3;
    public static final short GREEN = 4;
    public static final short BLUE = 5;

    public FastCube() {
        cube = new short[6][3][3];
        populateCube();
    }

    // Initializes the cube such that it has different colors for each face
    private void populateCube() {
        for (int i = 0; i < cube.length; i++) {
            for (int j = 0; j < cube[0].length; j++) {
                for (int k = 0; k < cube[0][0].length; k++) {

                    switch (i) {
                        case RED:
                            cube[i][j][k] = RED;
                            break;
                        case WHITE:
                            cube[i][j][k] = WHITE;
                            break;
                        case ORANGE:
                            cube[i][j][k] = ORANGE;
                            break;
                        case YELLOW:
                            cube[i][j][k] = YELLOW;
                            break;
                        case GREEN:
                            cube[i][j][k] = GREEN;
                            break;
                        case BLUE:
                            cube[i][j][k] = BLUE;
                            break;
                    }

                }
            }
        }
    }

    public void rotateClockWise(short face) {

        // Copies entire cube so we can do matrix transformations for rotations
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];

        copyCube(tempCube, cube);

        // This rotates the face's 3x3 Matrix 90 degrees clockwise
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tempCube[face][j][SIZE - 1 - i] = cube[face][i][j];
            }
        }

        switch (face) {
            case RED:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[YELLOW][i][2] = cube[BLUE][2 - i][0];
                    tempCube[GREEN][i][0] = cube[YELLOW][2 - i][2];
                    tempCube[WHITE][i][0] = cube[GREEN][i][0];
                    tempCube[BLUE][i][0] = cube[WHITE][i][0];
                }

                break;
            case WHITE:

                // Shifts the 1x3 layer's adjacent to the face being rotated
                for (int i = 0; i < SIZE; i++) {
                    tempCube[RED][i][2] = cube[BLUE][0][i];
                    tempCube[GREEN][2][i] = cube[RED][2 - i][2];
                    tempCube[ORANGE][i][0] = cube[GREEN][2][i];
                    tempCube[BLUE][0][i] = cube[ORANGE][2 - i][0];
                }

                break;
            case ORANGE:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[GREEN][i][2] = cube[WHITE][i][2];
                    tempCube[YELLOW][i][0] = cube[GREEN][2 - i][2];
                    tempCube[BLUE][i][2] = cube[YELLOW][2 - i][0];
                    tempCube[WHITE][i][2] = cube[BLUE][i][2];
                }

                break;
            case YELLOW:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[GREEN][0][i] = cube[ORANGE][i][2];
                    tempCube[ORANGE][i][2] = cube[BLUE][2][2 - i];
                    tempCube[BLUE][2][i] = cube[RED][i][0];
                    tempCube[RED][i][0] = cube[GREEN][0][2 - i];
                }

                break;
            case GREEN:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[YELLOW][0][i] = cube[RED][0][i];
                    tempCube[RED][0][i] = cube[WHITE][0][i];
                    tempCube[WHITE][0][i] = cube[ORANGE][0][i];
                    tempCube[ORANGE][0][i] = cube[YELLOW][0][i];
                }

                break;
            case BLUE:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[WHITE][2][i] = cube[RED][2][i];
                    tempCube[RED][2][i] = cube[YELLOW][2][i];
                    tempCube[YELLOW][2][i] = cube[ORANGE][2][i];
                    tempCube[ORANGE][2][i] = cube[WHITE][2][i];
                }

                break;
        }

        // Copies rotated cube to cube
        copyCube(cube, tempCube);
    }

    public void rotateCounterClockWise(short face) {

        // Copies entire cube so we can do matrix transformations for rotations
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];

        copyCube(tempCube, cube);

        // This rotates the face's 3x3 Matrix 90 degrees clockwise

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tempCube[face][SIZE - 1 - j][i] = cube[face][i][j];
            }
        }

        switch (face) {
            case RED:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[BLUE][i][0] = cube[YELLOW][2 - i][2];
                    tempCube[YELLOW][i][2] = cube[GREEN][2 - i][0];
                    tempCube[GREEN][i][0] = cube[WHITE][i][0];
                    tempCube[WHITE][i][0] = cube[BLUE][i][0];
                }

                break;
            case WHITE:

                // Shifts the 1x3 layer's adjacent to the face being rotated
                for (int i = 0; i < SIZE; i++) {
                    tempCube[BLUE][0][i] = cube[RED][i][2];
                    tempCube[RED][i][2] = cube[GREEN][2][2 - i];
                    tempCube[GREEN][2][i] = cube[ORANGE][i][0];
                    tempCube[ORANGE][i][0] = cube[BLUE][0][2 - i];
                }

                break;
            case ORANGE:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[WHITE][i][2] = cube[GREEN][i][2];
                    tempCube[GREEN][i][2] = cube[YELLOW][2 - i][0];
                    tempCube[YELLOW][i][0] = cube[BLUE][2 - i][2];
                    tempCube[BLUE][i][2] = cube[WHITE][i][2];
                }

                break;
            case YELLOW:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[ORANGE][i][2] = cube[GREEN][0][i];
                    tempCube[BLUE][2][i] = cube[ORANGE][2 - i][2];
                    tempCube[RED][i][0] = cube[BLUE][2][i];
                    tempCube[GREEN][0][i] = cube[RED][2 - i][0];
                }

                break;
            case GREEN:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[RED][0][i] = cube[YELLOW][0][i];
                    tempCube[WHITE][0][i] = cube[RED][0][i];
                    tempCube[ORANGE][0][i] = cube[WHITE][0][i];
                    tempCube[YELLOW][0][i] = cube[ORANGE][0][i];
                }

                break;
            case BLUE:

                for (int i = 0; i < SIZE; i++) {
                    tempCube[RED][2][i] = cube[WHITE][2][i];
                    tempCube[YELLOW][2][i] = cube[RED][2][i];
                    tempCube[ORANGE][2][i] = cube[YELLOW][2][i];
                    tempCube[WHITE][2][i] = cube[ORANGE][2][i];
                }

                break;
        }

        // Copies rotated cube to cube
        copyCube(cube, tempCube);
    }

    private void copyCube(short[][][] tempCube, short[][][] cube) {
        for (int i = 0; i < SIZE * 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = cube[i][j][k];
                }
            }
        }
    }

    public short[][][] getCube() {
        return cube;
    }
}
