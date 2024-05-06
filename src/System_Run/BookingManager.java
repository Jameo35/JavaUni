package System_Run;

//Importing the necessary modules in order for the BookingManager class to run.
import ExceptionHandling.InvalidBookingRefException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import SystemUtilities.Utilities;

public class BookingManager {
    //Initialising the scanner, this allows the user to interact with the programme via the command line, throughout the programme the user will be prompted to enter information.
    Scanner scanner = new Scanner(System.in);

    //Primary Method to create a System_Run.Trip object, either a new trip, or manage an existing trip, this subsequently creates a new trip object called foundBooking
    public void manageBooking() throws ParseException {
        System.out.println("Hello and welcome to the Hirst Hotel Group System_Run.Booking Management System.");

        while (true) {
            System.out.println("Would you like to Book a new trip? (N) or Change an existing booking? (C). Alternatively enter Q to exit the System");
            //The string input is commonly converted into a lowercase option, this helps to handle errors and reduce the amount of options required in a conditional logic tree. Halving potential outcomes.
            String option = scanner.nextLine().toLowerCase();

            if (option.equals("n")) {
                Trip trip = new Trip();
                trip.CreateTrip();

            } else if (option.equals("c")) {
                System.out.println("Please enter the booking reference you wish to access the details of:");

                try {
                    int BookingRef = scanner.nextInt();
                    scanner.nextLine();

                    if (BookingRef < 100000)
                        throw new InvalidBookingRefException("Booking Reference was less than 100000 please try again with another Reference. System_Run.Booking Ref: ", BookingRef);
                    else if (BookingRef > 999999)
                        throw new InvalidBookingRefException("Booking Reference was more than 999999, please try again with another Reference. System_Run.Booking Ref: ", BookingRef);

                    /*This instantiates a Trip object called foundbooking. The ReadTripData function is called, this searches the project's directory.
                    if the .txt file is found in the directory an object is created and populated with data using the object constructor on a line by line basis */

                    Trip foundBooking = Utilities.ReadTripData(BookingRef);

                    //if the creation of the foundbooking object returns as null. i.e no data found. An exception will be thrown to notify the user that the booking wasn't found.
                    if (foundBooking == null)
                        throw new InvalidBookingRefException("Booking was not found, returning to the Menu. Booking Ref: ", BookingRef);


                    System.out.println("Booking Found with details: ");
                    Utilities.EchoDetails(foundBooking);
                    while (true) {
                        System.out.println("Would you like to make any changes to these details? Y/N");
                        String changes = scanner.nextLine().toLowerCase();
                        if (changes.equals("y")) {
                            foundBooking.ChangeTrip();
                            break;
                        } else if (changes.equals("n")) {
                            System.out.println("No Changes have been made to booking: " + foundBooking.GetBookingRef());
                            break;
                        } else {
                            System.out.println("Please select a valid menu option");
                        }
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Please enter a valid integer for the Booking Reference.");
                    scanner.nextLine();
                } catch (InvalidBookingRefException e) {
                    System.err.println(e.getMessage());
                }

            } else if (option.equals("q")) {
                break;
            } else {
                System.out.println("You did not select a valid option, please select an option listed on the Menu.");
            }
        }
    }
}
