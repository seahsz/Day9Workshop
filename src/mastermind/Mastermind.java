package mastermind;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Mastermind {

    public static void main(String[] args) {

        // start console
        Console cons = System.console();

        boolean toEnd = false;

        while (!toEnd) {

            // generate secret number
            String secretNum = generateSecret();

            boolean gameEnd = false;

            // keep track of tries
            int tries = 0;

            while (!gameEnd) {

                System.out.println();
                System.out.printf("%d tries left\n", 12 - tries);

                String userGuess = cons.readLine(">> Guess a 4 digit number, each digit is between 1 to 6: ").trim();

                if (userGuess.equalsIgnoreCase("end"))
                    gameEnd = true;

                // do not count the attempt if it is invalid guess
                if (!isValid(userGuess))
                    continue;

                // find the score
                List<Integer> scores = checkScore(userGuess, secretNum);

                // display number of correct guesses
                System.out.printf("Correct number & position: %d\n", scores.get(0));
                System.out.printf("Correct number & wrong position: %d\n", scores.get(1));

                if (scores.get(0) == 4) {
                    System.out.println("You win!");
                    gameEnd = true;
                } else {
                    tries++;
                    if (tries == 12) {
                        System.out.printf("You lose. Secret number is: %s\n", secretNum);
                        gameEnd = true;
                    }
                }

            }

            String toPlay = cons.readLine(">> Play again? (Y/N): ");
            if (toPlay.equalsIgnoreCase("N")) {
                System.out.println("Thanks for playing. Goodbye.");
                toEnd = true;
            }
        }

    }

    public static String generateSecret() {

        StringBuilder sb = new StringBuilder();

        Random rand = new Random();

        // generate random number from 1111 to 6666;
        for (int numInt = 0; numInt < 4; numInt++) {
            int digit = rand.nextInt(1, 7);
            sb.append(digit);
        }

        return sb.toString();
    }

    public static boolean isValid(String _userGuess) {

        // check length
        if (_userGuess.length() != 4) {
            System.out.println("Please enter 4 digits to guess");
            return false;
        }

        String[] chars = _userGuess.split("");
        int[] digits = Arrays.stream(chars)
                .mapToInt(Integer::parseInt)
                .toArray();

        // check if each digit is within 1 and 6 (inclusive)
        for (int i : digits) {
            if (i < 1 || i > 6) {
                System.out.println("One or more digits not within 1 and 6");
                return false;
            }
        }

        return true;
    }

    public static List<Integer> checkScore(String _userGuess, String _secretNum) {

        int correctNumPosCount = 0;
        int correctNumWrongPosCount = 0;

        Map<Character, Integer> guessDigitMap = new HashMap<>();

        for (char c : _userGuess.toCharArray()) {

            int count = 0;

            if (guessDigitMap.containsKey(c))
                count = guessDigitMap.get(c);

            count++;
            guessDigitMap.put(c, count);
        }

        for (int i = 0; i < _secretNum.length(); i++) {

            char currChar = _userGuess.charAt(i);

            if (currChar == _secretNum.charAt(i)) {
                correctNumPosCount++;
            } else if (_secretNum.contains(Character.toString(currChar))) {

                // only record num in wrong position if occurence of num in guess <= occurence
                // in secret number

                int currCharInSecretCount = (int) _secretNum.chars()
                        .mapToObj(c -> (char) c)
                        .filter(b -> b == currChar)
                        .count();

                if (guessDigitMap.get(currChar) <= currCharInSecretCount)
                    correctNumWrongPosCount++;

                guessDigitMap.put(currChar, guessDigitMap.get(currChar) - 1);

            }
        }
        List<Integer> result = Arrays.asList(correctNumPosCount, correctNumWrongPosCount);
        return result;

    }

}
