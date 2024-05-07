package RoomTypes;

import System_Run.Booking;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomType {
    protected int roomOption;

    protected String bedOption;

    Scanner scanner = new Scanner(System.in);

    public String roomOption() {
        while (true) {
            try {

                roomOption = scanner.nextInt();
                scanner.nextLine();
                if (inputCheck(roomOption)) {
                    break;
                } else {
                    System.err.println("Please select a valid option");
                }
            } catch (InputMismatchException e) {
                System.err.println("Please select a valid option");
                scanner.nextLine();
            }
        }

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
    }
    @Override
    public String toString() {
        return "Value Room";
    }

    public boolean inputCheck(int option) {
        return (option > 0) && (option < 4);
    }
}
