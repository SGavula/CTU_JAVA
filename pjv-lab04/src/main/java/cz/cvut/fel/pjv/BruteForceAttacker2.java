package cz.cvut.fel.pjv;

import java.util.ArrayList;

public class BruteForceAttacker2 extends Thief {

    private ArrayList<String> generateCombinations(int n, char[] characters) {
        ArrayList<String> combinations = new ArrayList<>();
        if(n == 1) {
            for(char c : characters) {
                String str = String.valueOf(c);
                combinations.add(str);
            }
            return combinations;
        }else {
            ArrayList<String> oldCombinations = generateCombinations(n-1, characters);
            ArrayList<String> newCombinations = new ArrayList<>();
            for(String string : oldCombinations) {
                for(char letter : characters) {
                    String newStr = string + letter;
                    newCombinations.add(newStr);
                }
            }
            return newCombinations;
        }
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
