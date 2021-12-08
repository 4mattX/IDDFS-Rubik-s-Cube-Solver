package renderer.solvers;

import java.util.Random;

public class Randomizer {

    public static String getMoves(int amount) {
        int amtSets = 12;

        String[] moveSets = {"F ", "D ", "B ", "U ", "R ", "L ", "f ", "d ", "b ", "u ", "r ", "l "};
        String moves = "";

        for (int i = 0; i < amount; i++) {

            int random = new Random().nextInt(amtSets - 0) + 0;
            moves += moveSets[random];
        }

        return moves;
    }


}
