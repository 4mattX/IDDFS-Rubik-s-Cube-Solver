package renderer.solvers;

import renderer.cubes.Cube;
import renderer.rendering.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MattSolver {

    private short[][][] cubeX;
    private String solution = "";
    private final int SIZE = 3;
    private Cube displayCube;

    private final int amtNeighbors = 6;
    private int layers = 4;
    public static List<Character> bannedMoves = new ArrayList<>();

    private static final short RED = 0;
    private static final short WHITE = 1;
    private static final short ORANGE = 2;
    private static final short YELLOW = 3;
    private static final short GREEN = 4;
    private static final short BLUE = 5;

    private boolean goalFound = false;
    private int maxDepth = 100;
    private int depth = 0;
    private Stack<Node> frontier = new Stack<>();

    Node root;

    public MattSolver(short[][][] cube) {
    }

    public void solve(Cube displayCube) throws InterruptedException {
        this.displayCube = displayCube;
        setMattSolver();

        this.root = new Node(null, cubeX, null, "");
        iterativeDeepening();
    }

    public void setMattSolver() {
        solution = "";
        this.cubeX = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    this.cubeX[i][j][k] = Display.getFastCube().getCube()[i][j][k];
                }
            }
        }
    }

    private void iterativeDeepening() throws InterruptedException {
        // IDDFS for single child
        for (int goal = 1; goal <= 8; goal++) {
            while (depth < maxDepth && !goalFound) {
                depthLimitedSearch(this.root, goal);
                depth++;
            }

            if (goal == 4) {
                System.out.println("-=-= Cross Sub-Goal Complete =-=-");
            }

            solution = "";
            goalFound = false;

            if (goal == 4) {
                bannedMoves.add('D');
                bannedMoves.add('d');
            }
        }
        System.out.println("-=-= Corner Sub-Goal Complete =-=-");
        // IDDFS for multi child
        setMattSolver();
        this.root = new Node(null, cubeX, null, "");
        iterativeDeepeningMulti();
        System.out.println("-=-= PLL Sub-Goal Complete =-=-");
        System.out.println("\n    ##### CUBE SOLVED #####");
    }

    private void iterativeDeepeningMulti() throws InterruptedException {
        for (int goal = 1; goal <= 6; goal++) {
            while (depth < maxDepth && !goalFound) {
                depthLimitedSearch2(this.root, goal);
                depth++;
            }
            solution = "";
            goalFound = false;

            if (goal == 4) {
                System.out.println("-=-= Middle Edge Sub-Goal Complete =-=-");
            }
            if (goal == 5) {
                System.out.println("-=-= OLL Sub-Goal Complete =-=-");
            }

        }
    }

    private void depthLimitedSearch(Node initialNode, int goalNum) throws InterruptedException {

        frontier.push(initialNode);
        while (!frontier.isEmpty()) {
            Node currentNode = frontier.pop();

            if (goalNum < 4) {

                if (isCross(currentNode, goalNum)) {
                    System.out.println("Sub-Goal at depth: " + currentNode.getDepth());
                    System.out.println("Solution = " + currentNode.getSolution());
                    currentNode.setDepth(0);
                    solution = currentNode.getSolution();
                    goalFound = true;

                    displayCube.performMoves(solution);

                    currentNode.clearSolution();
                    this.root = currentNode;
                    depth = 0;

                    return;
                }
            }
            else if (goalNum >= 4 && goalNum <= 8){


                if (isCorner(currentNode, goalNum)) {
                    System.out.println("Sub-Goal at depth: " + currentNode.getDepth());
                    System.out.println("Solution = " + currentNode.getSolution());
                    currentNode.setDepth(0);
                    solution = currentNode.getSolution();
                    goalFound = true;

                    displayCube.performMoves(solution);

                    currentNode.clearSolution();
                    this.root = currentNode;
                    depth = 0;


                    return;
                }
            }
            // If not at max depth, then expand
            if (currentNode.getDepth() < depth) {

                currentNode.setChildren();
                currentNode.getChildren().forEach(x -> {
                    frontier.push(x);
                });
            }
        }
    }

    private void depthLimitedSearch2(Node initialNode, int goalNum) throws InterruptedException {

        frontier.push(initialNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.pop();

            if (goalNum <= 4) {

                if (isF2L(currentNode, goalNum)) {

                    System.out.println("Sub-Goal at depth: " + currentNode.getDepth());
                    System.out.println("Solution = " + currentNode.getSolution());
                    currentNode.setDepth(0);
                    solution = currentNode.getSolution();
                    goalFound = true;

                    displayCube.performMoves(solution);

                    currentNode.clearSolution();

                    this.root = currentNode;
                    depth = 0;

                    return;
                }
            }

            else if (goalNum == 5) {

                if (isOLL(currentNode)) {
                    System.out.println("Sub-Goal at depth: " + currentNode.getDepth());
                    System.out.println("Solution = " + currentNode.getSolution());
                    currentNode.setDepth(0);
                    solution = currentNode.getSolution();
                    goalFound = true;

                    displayCube.performMoves(solution);

                    currentNode.clearSolution();

                    this.root = currentNode;
                    depth = 0;

                    return;
                }
            }

            else if (goalNum == 6) {

                if (isPLL(currentNode)) {
                    System.out.println("Sub-Goal at depth: " + currentNode.getDepth());
                    System.out.println("Solution = " + currentNode.getSolution());
                    currentNode.setDepth(0);
                    solution = currentNode.getSolution();
                    goalFound = true;

                    displayCube.performMoves(solution);

                    currentNode.clearSolution();

                    this.root = currentNode;
                    depth = 0;
                    return;
                }

            }

            // If not at max depth, then expand
            if (currentNode.getDepth() < depth) {

                if (goalNum <= 4) {
                    currentNode.setChildrenF2L();
                } else if (goalNum > 4) {
                    currentNode.setChildrenOLL();
                } else if (goalNum > 5){
                    currentNode.setChildrenPLL();
                }

                currentNode.getChildren().forEach(x -> {
                    frontier.push(x);
                });

            }

        }
    }


    public void printCube(Node node) {

        short[][][] cube = node.getCube();

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    System.out.print(cube[i][j][k] + "  ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    private boolean isCross(Node node, int amount) {
        short[][][] testCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < SIZE * 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    testCube[i][j][k] = node.getCube()[i][j][k];
                }
            }
        }

        int amtCross = 0;

        if ((testCube[WHITE][0][1] == WHITE && testCube[GREEN][2][1] == GREEN)){
            amtCross++;
        }

        if (testCube[WHITE][1][0] == WHITE && testCube[RED][1][2] == RED) {
            amtCross++;
        }

        if (testCube[WHITE][2][1] == WHITE && testCube[BLUE][0][1] == BLUE) {
            amtCross++;
        }

        if (testCube[WHITE][1][2] == WHITE && testCube[ORANGE][1][0] == ORANGE) {
            amtCross++;
        }

        if (amtCross >= amount) {
            return true;
        }

        return false;
    }

    private boolean isCorner(Node node, int amount) {
        amount -= 4;

        short[][][] testCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < SIZE * 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    testCube[i][j][k] = node.getCube()[i][j][k];
                }
            }
        }

        if (!isCross(node, 4)) {
            return false;
        }

        int amtCorner = 0;
        List<Integer> cornerList = new ArrayList<>();

        if ((testCube[WHITE][0][0] == WHITE && testCube[GREEN][2][0] == GREEN && testCube[RED][0][2] == RED)){
            amtCorner++;
            if (!cornerList.contains(3)) {
                cornerList.add(3);
            }
        }

        if (testCube[WHITE][0][2] == WHITE && testCube[GREEN][2][2] == GREEN && testCube[ORANGE][0][0] == ORANGE) {
            amtCorner++;
            if (!cornerList.contains(0)) {
                cornerList.add(0);
            }
        }

        if (testCube[WHITE][2][0] == WHITE && testCube[BLUE][0][0] == BLUE && testCube[RED][2][2] == RED) {
            amtCorner++;
            if (!cornerList.contains(2)) {
                cornerList.add(2);
            }
        }

        if (testCube[WHITE][2][2] == WHITE && testCube[ORANGE][2][0] == ORANGE && testCube[BLUE][0][2] == BLUE) {
            amtCorner++;
            if (!cornerList.contains(1)) {
                cornerList.add(1);
            }
        }

        if (amtCorner >= 3) {

            if (!cornerList.contains(0)) {
                bannedMoves.add('f');
                bannedMoves.add('F');
                bannedMoves.add('l');
                bannedMoves.add('L');
            }

            if (!cornerList.contains(1)) {
                bannedMoves.add('f');
                bannedMoves.add('F');
                bannedMoves.add('r');
                bannedMoves.add('R');
            }

            if (!cornerList.contains(2)) {
                bannedMoves.add('r');
                bannedMoves.add('R');
                bannedMoves.add('b');
                bannedMoves.add('B');
            }

            if (!cornerList.contains(3)) {
                bannedMoves.add('l');
                bannedMoves.add('L');
                bannedMoves.add('b');
                bannedMoves.add('B');
            }
        }

        if (amtCorner >= amount) {
            return true;
        }
        return false;
    }

    private boolean isF2L(Node node, int amount) {

        short[][][] testCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    testCube[i][j][k] = node.getCube()[i][j][k];
                }
            }
        }

        int amtf2l = 0;

        if ((testCube[RED][0][1] == RED && testCube[GREEN][1][0] == GREEN)){
            amtf2l++;
        }

        if (testCube[GREEN][1][2] == GREEN && testCube[ORANGE][0][1] == ORANGE) {
            amtf2l++;
        }

        if (testCube[ORANGE][2][1] == ORANGE && testCube[BLUE][1][2] == BLUE) {
            amtf2l++;
        }

        if (testCube[BLUE][1][0] == BLUE && testCube[RED][2][1] == RED) {
            amtf2l++;
        }

        if (amtf2l >= amount) {
            return true;
        }

        return false;
    }

    private boolean isOLL(Node node) {
        short[][][] testCube = new short[SIZE * 2][SIZE][SIZE];

        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    testCube[i][j][k] = node.getCube()[i][j][k];
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (node.getCube()[YELLOW][i][j] != YELLOW) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isPLL(Node node) {
        for (int i = 0; i < (SIZE * 2); i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {

                    if (node.getCube()[i][j][k] != i) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    public String getSolution() {
        return this.solution;
    }

}
