import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CodexChallenge {
    public static void main(String[] args) {
        HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
        Scanner scanner = new Scanner(System.in);
        Integer[] codedValue = {16, 5, 11, 8, 18, 14, 13, 26, 15, 2, 23, 19, 7, 9, 11, 26, 15, 12, 11, 10, 16, 5, 11, 21, 19, 17, 22, 20, 1, 10, 15, 6, 24, 4, 15, 3};

        for (int i = 0; i < codedValue.length; i += 1) {
            dictionary.put(codedValue[i], " ");
        }
        dictionary.replace(11, "e");
        dictionary.replace(23, "j");
        dictionary.replace(8, "l");
        dictionary.replace(14, "z");
        dictionary.replace(21, "q");

        System.out.println("Please see the codex\n" + Arrays.toString(codedValue));
        System.out.println("This dictionary shows the mappings of the codex to the alphabet\n" + dictionary);

//        for (Integer key: dictionary.keySet()) {
//            if (dictionary.get(key) == " ") {
//                System.out.println("Please enter a value for this coded number " + key);
//                String userInput = scanner.nextLine();
//            }
//                System.out.println("This key " + key + " already has an assigned value");
//        }
        while (true) {
            System.out.println("From the Dictionary which entry would you like to update? Type 0 to quit. Type 99 to see the latest version of the codex.");
            int userChoice = scanner.nextInt();
            if (userChoice == 11 || userChoice == 23 || userChoice == 8 || userChoice == 14 || userChoice == 21) {
                System.out.println("Please choose a different number to 11,23,8,14 or 21.");
            } else if (userChoice == 0) {
                System.out.println("Thanks for trying");
                break;
            } else if (userChoice == 99) {
                for (int i = 0; i < codedValue.length; i += 1) {
                    System.out.print(dictionary.get(codedValue[i]));
                }
            }
            else {
                System.out.println("What character would you like to assign to " + userChoice);
                scanner.nextLine();
                String userInput = scanner.nextLine();
                dictionary.replace(userChoice, userInput);
                System.out.println("This dictionary shows the updated mappings of the codex to the alphabet\n" + dictionary);

            }
        }

    }
}
