package permutations;

import java.io.Console;
import java.util.HashSet;
import java.util.Set;

public class Permutation {

    public static void main(String[] args) {

        Console cons = System.console();

        String input = cons.readLine(">> Enter 4 digits or 4 alphabets: ");
        input = input.trim();

        // if (input.length() != 4) {
        //     System.err.println("Incorrect length entered. System shut down");
        //     System.exit(-1);
        // }

        // Collect the permutations
        Set<String> perms = new HashSet<>();

        perms = recursion("", input);
        perms.forEach(System.out::println);

    }

    public static Set<String> recursion(String prefix, String str) {

        Set<String> result = new HashSet<>();

        if (str.isEmpty()) {
            result.add(prefix);
            return result;
        }

        for (int i = 0; i < str.length(); i++) {

            // remain contains everything but the current character
            String remain = str.substring(0, i) + str.substring(i + 1, str.length());
            String newPrefix = prefix + str.charAt(i);

            result.addAll(recursion(newPrefix, remain));
        }
        return result;
    }

}
