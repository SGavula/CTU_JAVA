package cz.cvut.fel.pjv;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteForceAttacker extends Thief {

    private ArrayList<String> generateCombinations(int n, char[] characters) {
        ArrayList<String> combinations = new ArrayList<>();

        if (n == 0) {
            combinations.add("");
            return combinations;
        }

        for (String combination : generateCombinations(n - 1, characters)) {
            for (char character : characters) {
                combinations.add(character + combination);
            }
        }

        return combinations;
    }

    @Override
    public void breakPassword(int sizeOfPassword) {
        char[] chars = getCharacters();
        boolean isOpened = false;
        ArrayList<String> allCombinations = generateCombinations(sizeOfPassword, chars);
        for(String passwordCombination : allCombinations) {
            if(isOpened == false) {
                char[] input = passwordCombination.toCharArray();
                isOpened = tryOpen(input);
            }else {
                break;
            }
        }
    }

}
