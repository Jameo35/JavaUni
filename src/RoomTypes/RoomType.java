package RoomTypes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomType {
    protected int roomOption;

    protected String bedOption;

    protected Scanner scanner = new Scanner(System.in);


    public String roomOption() {
        while (true) {
            try {
                roomOption = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.err.println("Please select a valid integer");
                scanner.nextLine();
            }
        }

        while (true) {
            if (inputCheck(roomOption)) {
                switch (roomOption) {
                    case 1:
                        System.out.println("You have selected a Single bed");
                        bedOption = "Single Bed";
                        break;
                    case 2:
                        System.out.println("You have selected a Twin bed");
                        bedOption = "Twin Bed";
                        break;
                    case 3:
                        System.out.println("You have selected a Double bed");
                        bedOption = "Double Bed";
                        break;
                }
                return bedOption;

            } else {
                System.out.println("Please select a valid option");
                roomOption = scanner.nextInt();
            }

        }
    }

    @Override
    public String toString() {
        return "Value Room";
    }

    public boolean inputCheck(int option) {
        return (option > 0) && (option < 4);
    }
}
