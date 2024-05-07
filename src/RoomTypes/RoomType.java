package RoomTypes;

import System_Run.RunBooking;

import java.util.InputMismatchException;
import java.util.Scanner;
//Creating a class template for RoomTypes due to shared characteristics
public class RoomType {
    protected int roomOption;

    protected String bedOption;

    Scanner scanner = new Scanner(System.in);

    //Creating function roomOption to allow selection of certain options depending on roomType.
    public String roomOption() {
        while (true) {
            try {

                roomOption = scanner.nextInt();
                if (inputCheck(roomOption)) {
                    break;
                } else {
                    System.out.println("Please select a valid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a valid option");
                scanner.nextInt();
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
    //Overriden the toString function in order to print the name of the roomType without memory address
    @Override
    public String toString() {
        return "Value Room";
    }

    //created an inputCheck function to ensure correct input is being passed for roomType
    public boolean inputCheck(int option) {
        return (option > 0) && (option < 4);
    }
}
