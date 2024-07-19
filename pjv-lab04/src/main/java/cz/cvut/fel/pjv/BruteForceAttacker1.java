package cz.cvut.fel.pjv;

import java.util.ArrayList;

public class BruteForceAttacker1 extends Thief {
    boolean isOpened = false;
    private ArrayList<String> generateCombinations(int n, char[] characters, int length) {
        ArrayList<String> combinations = new ArrayList<>();
        if(n == 1) {
            for(char c : characters) {
                String str = String.valueOf(c);
                combinations.add(str);
            }
            return combinations;
        }else {
            ArrayList<String> oldCombinations = generateCombinations(n-1, characters, length);
            ArrayList<String> newCombinations = new ArrayList<>();
            for(String string : oldCombinations) {
                if(isOpened == true) {
                    break;
                }
                for(char letter : characters) {
                    String newStr = string + letter;
                    if(newStr.length() == length) {
                        char[] input = newStr.toCharArray();
                        if(isOpened == false) {
                            isOpened = tryOpen(input);
                        }else {
                            break;
                        }
                    }else {
                        newCombinations.add(newStr);
                    }
                }
            }
            return newCombinations;
        }
    }

    @Override
    public void breakPassword(int sizeOfPassword) {
        char[] chars = getCharacters();
        generateCombinations(sizeOfPassword, chars, sizeOfPassword);
    }
    
}
