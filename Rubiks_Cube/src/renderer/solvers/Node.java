package renderer.solvers;

import renderer.cubes.FastCube;
import renderer.rendering.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    ArrayList<Node> childList = new ArrayList<>();
    short[][][] cube;
    private Node parent;
    private int depth;
    private String solution = "";

    private final int SIZE = 3;

    public static final short RED = 0;
    public static final short WHITE = 1;
    public static final short ORANGE = 2;
    public static final short YELLOW = 3;
    public static final short GREEN = 4;
    public static final short BLUE = 5;

    public Node(ArrayList<Node> neighborList, short[][][] cube,Node parent, String solution) {
        this.cube = new short[6][3][3];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    this.cube[i][j][k] = cube[i][j][k];
                }
            }
        }

        this.parent = parent;

        if (parent == null) {
            depth = 0;
        } else {
            this.depth = parent.getDepth() + 1;
            this.solution = solution;
        }
    }


    public String getSolution() {
        return this.solution;
    }

    public void clearSolution() {
        this.solution = "";
    }

    public void setCube(short[][][] newCube) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    this.cube[i][j][k] = newCube[i][j][k];
                }
            }
        }
    }

    public int getDepth() {
        return this.depth;
    }

    public ArrayList<Node> getChildren() {
        return this.childList;
    }

    public short[][][] getCube() {
        return this.cube;
    }

    public Node getParent() {
        return parent;
    }

    public void setDepth(int newDepth) {
        this.depth = newDepth;
    }

    public void setChildren() {

        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = this.cube[i][j][k];
                }
            }
        }

        for (short face = 0; face < (SIZE * 2); face++) {

            String solution = "";

            switch (face) {
                case 0:
                    solution += "F ";
                    break;
                case 1:
                    solution += "D ";
                    break;
                case 2:
                    solution += "B ";
                    break;
                case 3:
                    solution += "U ";
                    break;
                case 4:
                    solution += "R ";
                    break;
                case 5:
                    solution += "L ";
                    break;
            }

            rotateClockWise(face);
//            performMoves(solution);
            Node nodeCW = new Node(null, this.cube, this, this.getSolution() + solution);

//            copyThisCube(tempCube);


            rotateCounterClockWise(face);

//            System.out.println(face);
//            for (int i = 0; i < (SIZE * 2); i++) {
//                for (int j = 0; j < SIZE; j++) {
//                    for (int k = 0; k < SIZE; k++) {
//                        System.out.print(this.cube[i][j][k] +" "+ tempCube[i][j][k] + " ");
//                    }
//                    System.out.println();
//                }
//                System.out.println();
//            }

            rotateCounterClockWise(face);

//            performMoves(solution.toLowerCase());
            Node nodeCCW = new Node(null, this.cube, this, this.getSolution() + solution.toLowerCase());
            rotateClockWise(face);
//            copyThisCube(tempCube);

            if (this.getSolution().length() > 2) {

                char lastMove = this.getSolution().charAt(this.getSolution().length() - 2);
                boolean isLastUpper = true;
                if (Character.isLowerCase(lastMove)) {
                    isLastUpper = false;
                }

                char thisMove = solution.charAt(0);

                if (isLastUpper && lastMove == thisMove) {
                    // do not add nodeCCW
                    this.childList.add(nodeCW);
                }

                else if (!isLastUpper && lastMove == (Character.toLowerCase(thisMove)))
                {
                    // do not add nodeCW
                    this.childList.add(nodeCCW);
                } else if (MattSolver.bannedMoves.contains(thisMove)) {
                } else {
                    this.childList.add(nodeCW);
                    this.childList.add(nodeCCW);
                }


            } else {
                this.childList.add(nodeCW);
                this.childList.add(nodeCCW);
            }

        }



    }

    public void setChildrenF2L() {
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];
        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = this.cube[i][j][k];
                }
            }
        }


        this.performMoves("U R u r u f U F ");
        Node child1 = new Node(null, this.cube, this, this.getSolution() + "U R u r u f U F ");
        this.performMoves("f u F U R U r u ");

        this.performMoves("U B u b u r U R ");
        Node child2 = new Node(null, this.cube, this, this.getSolution() + "U B u b u r U R ");
        this.performMoves("r u R U B U b u ");

        this.performMoves("U L u l u b U B ");
        Node child3 = new Node(null, this.cube, this, this.getSolution() + "U L u l u b U B ");
        this.performMoves("b u B U L U l u ");

        this.performMoves("U F u f u l U L ");
        Node child4 = new Node(null, this.cube, this, this.getSolution() + "U F u f u l U L ");
        this.performMoves("l u L U F U f u ");

        this.performMoves("u l U L U F u f ");
        Node child5 = new Node(null, this.cube, this, this.getSolution() + "u l U L U F u f ");
        this.performMoves("F U f u l u L U ");

        this.performMoves("u b U B U L u l ");
        Node child6 = new Node(null, this.cube, this, this.getSolution() + "u b U B U L u l ");
        this.performMoves("L U l u b u B U ");

        this.performMoves("u r U R U B u b ");
        Node child7 = new Node(null, this.cube, this, this.getSolution() + "u r U R U B u b ");
        this.performMoves("B U b u r u R U ");

        this.performMoves("u f U F U R u r ");
        Node child8 = new Node(null, this.cube, this, this.getSolution() + "u f U F U R u r ");
        this.performMoves("R U r u f u F U ");

        this.rotateClockWise(YELLOW);
        Node child9 = new Node(null, this.cube, this, this.getSolution() + "U ");
        this.rotateCounterClockWise(YELLOW);

        this.rotateCounterClockWise(YELLOW);
        Node child10 = new Node(null, this.cube, this, this.getSolution() + "u ");
        this.rotateClockWise(YELLOW);


        this.childList.clear();
        this.childList.add(child1);
        this.childList.add(child2);
        this.childList.add(child3);
        this.childList.add(child4);
        this.childList.add(child5);
        this.childList.add(child6);
        this.childList.add(child7);
        this.childList.add(child8);
        this.childList.add(child9);
        this.childList.add(child10);
    }

    public void setChildrenOLL() {
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];
        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = this.cube[i][j][k];
                }
            }
        }


        this.performMoves("f R U r u f ");
        Node child1 = new Node(null, this.cube, this, this.getSolution() + "f R U r u f ");
        this.performMoves("F U R u r F ");

        this.performMoves("F R U r u f ");
        Node child2 = new Node(null, this.cube, this, this.getSolution() + "F R U r u f ");
        this.performMoves("F U R u r f ");

        this.performMoves("f R U r u f ");
        Node child3 = new Node(null, this.cube, this, this.getSolution() + "f R U r u f ");
        this.performMoves("F U R u r F ");


        this.performMoves("R U r U R U U r ");
        Node child4 = new Node(null, this.cube, this, this.getSolution() + "R U r U R U U r ");
        this.performMoves("R u u r u R u r ");

        this.performMoves("R U U r u R u r ");
        Node child5 = new Node(null, this.cube, this, this.getSolution() + "R U U r u R u r ");
        this.performMoves("R U r U R u u r ");

        this.performMoves("F R U r u R U r u R U r u f ");
        Node child6 = new Node(null, this.cube, this, this.getSolution() + "F R U r u R U r u R U r u f ");
        this.performMoves("F U R u r U R u r U R u r f ");

        this.performMoves("R U r u r F R f ");
        Node child7 = new Node(null, this.cube, this, this.getSolution() + "R U r u r F R f ");
        this.performMoves("F r f R U R u r ");

        this.performMoves("R U U r r u R R u R R u u R ");
        Node child8 = new Node(null, this.cube, this, this.getSolution() + "R U U r r u R R u R R u u R ");
        this.performMoves("r U U r r U r r U R R u u r ");

        this.performMoves("R R D r U U R d r U U r ");
        Node child9 = new Node(null, this.cube, this, this.getSolution() + "R R D r U U R d r U U r ");
        this.performMoves("R u u R D r u u R d r r ");

        this.performMoves("r F R b r f R B ");
        Node child10 = new Node(null, this.cube, this, this.getSolution() + "r F R b r f R B ");
        this.performMoves("b r F R B r f R ");


        this.rotateClockWise(YELLOW);
        Node child11 = new Node(null, this.cube, this, this.getSolution() + "U ");
        this.rotateCounterClockWise(YELLOW);

        this.rotateCounterClockWise(YELLOW);
        Node child12 = new Node(null, this.cube, this, this.getSolution() + "u ");
        this.rotateClockWise(YELLOW);


        this.childList.clear();
        this.childList.add(child1);
        this.childList.add(child2);
        this.childList.add(child3);
        this.childList.add(child4);
        this.childList.add(child5);
        this.childList.add(child6);
        this.childList.add(child7);
        this.childList.add(child8);
        this.childList.add(child9);
        this.childList.add(child10);
        this.childList.add(child11);
        this.childList.add(child12);
    }

    public void setChildrenPLL() {
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];
        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = this.cube[i][j][k];
                }
            }
        }

        this.performMoves("F R u r u R U r f R U r u r F R f ");
        Node child1 = new Node(null, this.cube, this, this.getSolution() + "F R u r u R U r f R U r u r F R f ");
        this.performMoves("F r f R U R u r F R u r U R U r f ");

        this.performMoves("R U r u r F R R u r u R U r f ");
        Node child2 = new Node(null, this.cube, this, this.getSolution() + "R U r u r F R R u r u R U r f ");
        this.performMoves("F R u r U R U r r f R U R u r ");

        this.performMoves("R u R U R U R u r u R R ");
        Node child3 = new Node(null, this.cube, this, this.getSolution() + "R u R U R U R u r u R R ");
        this.performMoves("r r U R U r u r u r U r ");

        this.performMoves("R R U R U r u r u r U r ");
        Node child4 = new Node(null, this.cube, this, this.getSolution() + "R R U R U r u r u r U r ");
        this.performMoves("R u R U R U R u r u r r ");

        this.rotateClockWise(YELLOW);
        Node child7 = new Node(null, this.cube, this, this.getSolution() + "U ");
        this.rotateCounterClockWise(YELLOW);

        this.rotateCounterClockWise(YELLOW);
        Node child8 = new Node(null, this.cube, this, this.getSolution() + "u ");
        this.rotateClockWise(YELLOW);


        this.childList.clear();
        this.childList.add(child1);
        this.childList.add(child2);
        this.childList.add(child3);
        this.childList.add(child4);
        this.childList.add(child7);
        this.childList.add(child8);
    }

    public void performMoves(String moves) {
        String[] singleMove = moves.split(" ", moves.length());
        List<String> moveList = Arrays.asList(singleMove);


        for (int i = 0; i < moveList.size(); i++) {
            String face = moveList.get(i);
            switch (face) {
                case "f":
                    this.rotateCounterClockWise(RED);
                    break;
                case "F":
                    this.rotateClockWise(RED);
                    break;

                case "u":
                    this.rotateCounterClockWise(YELLOW);
                    break;
                case "U":
                    this.rotateClockWise(YELLOW);
                    break;

                case "d":
                    this.rotateCounterClockWise(WHITE);
                    break;
                case "D":
                    this.rotateClockWise(WHITE);
                    break;

                case "l":
                    this.rotateCounterClockWise(BLUE);
                    break;
                case "L":
                    this.rotateClockWise(BLUE);
                    break;

                case "r":
                    this.rotateCounterClockWise(GREEN);
                    break;
                case "R":
                    this.rotateClockWise(GREEN);
                    break;

                case "b":
                    this.rotateCounterClockWise(ORANGE);
                    break;
                case "B":
                    this.rotateClockWise(ORANGE);
                    break;
            }

        }
    }

    public void rotateClockWise(short face) {

        // Copies entire cube so we can do matrix transformations for rotations
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = this.cube[i][j][k];
                }
            }
        }

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
        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    this.cube[i][j][k] = tempCube[i][j][k];
                }
            }
        }

    }

    public void rotateCounterClockWise(short face) {

        // Copies entire cube so we can do matrix transformations for rotations
        short[][][] tempCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    tempCube[i][j][k] = cube[i][j][k];
                }
            }
        }

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
        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    this.cube[i][j][k] = tempCube[i][j][k];
                }
            }
        }
    }

}
