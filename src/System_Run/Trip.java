package System_Run;

import RoomTypes.DeluxeRoomType;
import RoomTypes.RoomType;
import RoomTypes.SuperiorRoomType;
import RoomTypes.ValueRoomType;
import SystemUtilities.Utilities;

import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


//Creation of the trip class, which holds customer details denoted by private variables.
public class Trip {

    private int _bookingRef;
    private String _firstname = "", _surname = "", _checkIn = "", _checkOut = "";

    private static int _checkedDays;

    private static RoomType _roomType;

    private static String _bedOption;
    private Scanner scanner = new Scanner(System.in);

    //Getters and setters. These allow the retrieval/getting of object information and the updating/setting of the associated variables for an object.
    public int GetBookingRef() {
        return _bookingRef;
    }

    public String GetFirstname() {
        return _firstname;
    }

    public String GetSurname() {
        return _surname;
    }

    public String GetCheckIn() {
        return _checkIn;
    }

    public String GetCheckOut() {
        return _checkOut;
    }

    public int GetCheckedDays() {
        return _checkedDays;
    }

    public RoomType GetRoomType() {
        return _roomType;
    }

    public String GetBedOption() {
        return _bedOption;
    }

    public void SetBookingRef(int bookingRef) {
        _bookingRef = bookingRef;
    }

    public void SetForename(String firstname) {
        _firstname = firstname;
    }

    public void SetSurname(String surname) {
        _surname = surname;
    }

    public void SetCheckIn(String checkIn) {
        _checkIn = checkIn;
    }

    public void SetCheckOut(String checkOut) {
        _checkOut = checkOut;
    }

    public void SetCheckedDays(int checkedDays) {
        _checkedDays = checkedDays;
    }

    public void SetRoomType(RoomType roomType) {
        _roomType = roomType;
    }

    public void SetBedOption(String bedOption) {
        _bedOption = bedOption;
    }
    //Getters and Setters now created.


    //This method allows the creation of an empty trip object.
    public Trip() {
    }
    /*This method is an object constructor method, outlining a way to construct a Trip object with the required variables as outlined below.
    This method is useful when a user wishes to retrieve an existing booking, and the constructor is used to create the trip object
     */
    public Trip(String firstname, String surname, String checkIn, String checkOut, int checkedDays, int bookingRef, RoomType roomType, String bedOption) {
        _firstname = firstname;
        _surname = surname;
        _checkIn = checkIn;
        _checkOut = checkOut;
        _checkedDays = checkedDays;
        _bookingRef = bookingRef;
        _roomType = roomType;
        _bedOption = bedOption;

    }
    /* The CreateTrip method allows the user to navigate through the trip creation journey.
    This makes use of the scanner class so the user is able to input the desired information into each step of the way.
     */
    public void CreateTrip() throws ParseException {
        System.out.println("Please enter your first name:");
        _firstname = scanner.nextLine();

        System.out.println("Please enter your surname:");
        _surname = scanner.nextLine();

        System.out.println("Please enter your check in date in format DD MM YYYY:");
        //dateSelector method is used to avoid repeated code, and to make the code more repeatable and manageable.
        _checkIn = Utilities.dateSelector();
        //This ensures that the journey does not continue unless the customer's entry creates a satisfactory date entry.
        while (_checkIn == null){
            _checkIn = Utilities.dateSelector();
        }

        System.out.println("Please enter your check out date in format DD MM YYYY for a date after " + _checkIn);
        //dateSelector method is used to avoid repeated code, and to make the code more repeatable and manageable.
        _checkOut = Utilities.dateSelector();
        //This ensures that the journey does not continue unless the customer's entry creates a satisfactory date entry.
        while (_checkOut == null){
            _checkOut = Utilities.dateSelector();
        }

        //No user interaction required here, background calculations are completed.
        Utilities.dateDifference(_checkIn, _checkOut,this);

        Random ran = new Random();
        //This ensures that the booking reference is always 6 digits long, for continuity for the customer.
        _bookingRef = ran.nextInt(899999) + 100000;

        //Invokes the RoomSelection method.
        Utilities.RoomSelection(this);

        while (true) {
            System.out.println("These are the inputted details we have collected. Can you confirm they are correct? Y/N");
            Utilities.EchoDetails(this);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("y")) {
                System.out.println("Thanks for confirming, the booking details will be sent to you via Email.");
                Utilities.WriteTripToFile(this);
                break;
            } else if (input.equals("n")) {
                ChangeTrip();

            } else {
                System.out.println("Please select either Y or N");
            }
        }

    }

    public void ChangeTrip() {
        //Labelled loop to break out of the switch statement
        loop:
        while (true) {
            System.out.println("What changes would you like to make on the trip? Please choose from the options below");
            System.out.println("1. First Name, 2. Last Name, 3. Check In Date, 4. Check Out Date, 5. Room Type and Bed Options, 6. No Changes Required");
            try {
                int changeOption = scanner.nextInt();
                scanner.nextLine();
                if ((changeOption > 0) && (changeOption < 7)) {
                    switch (changeOption) {
                        case 1:
                            System.out.println("Your First Name currently reads: " + GetFirstname() + " what would you like to change it to?");
                            SetForename(scanner.nextLine());
                            System.out.println("Your First Name now reads as: " + GetFirstname());
                            break;
                        case 2:
                            System.out.println("Your Last Name currently reads: " + GetSurname() + " what would you like to change it to?");
                            SetSurname(scanner.nextLine());
                            System.out.println("Your Last Name now reads as: " + GetSurname());
                            break;
                        case 3:
                            System.out.println("Your Check in Date is currently: " + GetCheckIn() + " what would you like to change it to?");
                            SetCheckIn(Utilities.dateSelector());
                            System.out.println("Your Check in Date is now: " + GetCheckIn());
                            Utilities.dateDifference(GetCheckIn(),GetCheckOut(),this);
                            System.out.println("You will now be staying for: " + GetCheckedDays() + " days");
                            break;
                        case 4:
                            System.out.println("Your Check out Date is currently: " + GetCheckOut() + " what would you like to change it to?");
                            SetCheckOut(Utilities.dateSelector());
                            System.out.println("Your Check out Date is now: " + GetCheckOut());
                            Utilities.dateDifference(GetCheckIn(),GetCheckOut(),this);
                            System.out.println("You will now be staying for: " + GetCheckedDays() + " days");
                            break;
                        case 5:
                            System.out.println("Your Room selection is currently: " + GetRoomType() + " you also have the " + GetBedOption() + " selected.");
                            Utilities.RoomSelection(this);
                            System.out.println("Your Room selection is now: " + GetRoomType() + " you have also selected the " + GetBedOption());
                            break;
                        case 6:
                            System.out.println("Any changes you have made have been updated on your booking: " + GetBookingRef());
                            break loop;
                    }

                } else {
                    System.out.println("Please select a valid Menu Option");
                }
            } catch (InputMismatchException e) {
                System.err.println("Please enter a valid integer for the Change Menu.");
                scanner.nextLine();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        Utilities.WriteTripToFile(this);
    }



}