package System_Run;

import RoomTypes.RoomType;
import SystemUtilities.Utilities;

import java.text.ParseException;
import java.util.Random;
import java.util.Scanner;



//Creation of the trip class, which holds customer details denoted by private variables.
public class Trip {
    Scanner scanner = new Scanner(System.in);
    //Declaring the variables/attributes of the Trip class
    private int _bookingRef;
    private String _firstname = "", _surname = "", _checkIn = "", _checkOut = "";

    private int _checkedDays;

    private RoomType _roomType;

    private String _bedOption;
    private double _price;

    //Getters and setters. These allow the retrieval/getting of object information and the updating/setting of the associated variables for an object.
    public int getBookingRef() {
        return _bookingRef;
    }

    public String getFirstname() {
        return _firstname;
    }

    public String getSurname() {
        return _surname;
    }

    public String getCheckIn() {
        return _checkIn;
    }

    public String getCheckOut() {
        return _checkOut;
    }

    public int getCheckedDays() {
        return _checkedDays;
    }

    public RoomType getRoomType() {
        return _roomType;
    }

    public String getBedOption() {
        return _bedOption;
    }

    public double getPrice() {
        return _price;
    }

    public void setBookingRef(int bookingRef) {
        _bookingRef = bookingRef;
    }

    public void setForename(String firstname) {
        _firstname = firstname;
    }

    public void setSurname(String surname) {
        _surname = surname;
    }

    public void setCheckIn(String checkIn) {
        _checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        _checkOut = checkOut;
    }

    public void setCheckedDays(Integer checkedDays) {
        _checkedDays = checkedDays;
    }

    public void setRoomType(RoomType roomType) {
        _roomType = roomType;
    }

    public void setBedOption(String bedOption) {
        _bedOption = bedOption;
    }

    public void setPrice(Double price) {
        _price = price;
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
     this method was intended to be protected, however due to testing reasons have designated it as public */
    public void createTrip() throws ParseException {
        System.out.println("Please enter your first name:");
        _firstname = scanner.nextLine();

        System.out.println("Please enter your surname:");
        _surname = scanner.nextLine();

        System.out.println("Please enter your check in date in format DD MM YYYY:");
        //dateSelector method is used to avoid repeated code, and to make the code more repeatable and manageable.
        _checkIn = Utilities.dateSelector();
        //This ensures that the journey does not continue unless the customer's entry creates a satisfactory date entry.
        while (_checkIn == null) {
            _checkIn = Utilities.dateSelector();
        }

        System.out.println("Please enter your check out date in format DD MM YYYY for a date after " + _checkIn);
        //dateSelector method is used to avoid repeated code, and to make the code more repeatable and manageable.
        _checkOut = Utilities.dateSelector();
        //This ensures that the journey does not continue unless the customer's entry creates a satisfactory date entry.
        while (_checkOut == null) {
            _checkOut = Utilities.dateSelector();
        }

        //No user interaction required here, background calculations are completed.
        Utilities.dateDifference(_checkIn, _checkOut, this);

        Random ran = new Random();
        //This ensures that the booking reference is always 6 digits long, for continuity for the customer.
        _bookingRef = ran.nextInt(899999) + 100000;

        //Invokes the RoomSelection method.
        Utilities.roomSelection(this);

        //Invokes the Price Calculation for the trip.
        if(getCheckedDays()>0) {
            setPrice(Utilities.priceCalculator(this));
        }else{
            setPrice(null);
        }

        //Creates loop in order to provide user with the opportunity to change details
        while (true) {
            System.out.println("These are the inputted details we have collected. Can you confirm they are correct? Y/N");
            Utilities.echoDetails(this);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("y")) {
                System.out.println("Thanks for confirming, the booking details will be sent to you via Email.\nAlong with the invoice for the amount" + getPrice());
                Utilities.writeTripToFile(this);
                break;
            } else if (input.equals("n")) {
                changeTrip();

            } else {
                System.out.println("Please select either Y or N");
            }
        }
    }

    protected void changeTrip() {
        //Labelled loop as loop to break out of the switch statement on case 6.
        loop:
        while (true) {
            System.out.println("What changes would you like to make on the trip? Please choose from the options below");
            System.out.println("1. First Name, 2. Last Name, 3. Check In Date, 4. Check Out Date, 5. Room Type and Bed Options, 6. No Changes Required/Save Changes Made");
            try {
                int changeOption = scanner.nextInt();
                scanner.nextLine();
                if ((changeOption > 0) && (changeOption < 7)) {
                    switch (changeOption) {
                        case 1:
                            System.out.println("Your First Name currently reads: " + getFirstname() + " what would you like to change it to?");
                            setForename(scanner.nextLine());
                            System.out.println("Your First Name now reads as: " + getFirstname());
                            break;
                        case 2:
                            System.out.println("Your Last Name currently reads: " + getSurname() + " what would you like to change it to?");
                            setSurname(scanner.nextLine());
                            System.out.println("Your Last Name now reads as: " + getSurname());
                            break;
                        case 3:
                            System.out.println("Your Check in Date is currently: " + getCheckIn() + " what would you like to change it to?");
                            String changedCheckIn = Utilities.dateSelector();
                            while (changedCheckIn == null) {
                                changedCheckIn = Utilities.dateSelector();
                            }
                            setCheckIn(changedCheckIn);
                            System.out.println("Your Check in Date is now: " + getCheckIn());
                            Utilities.dateDifference(getCheckIn(), getCheckOut(), this);
                            setPrice(Utilities.priceCalculator(this));
                            System.out.println("You will now be staying for: " + getCheckedDays() + " days");
                            break;
                        case 4:
                            System.out.println("Your Check out Date is currently: " + getCheckOut() + " what would you like to change it to?");
                            String changedCheckOut = Utilities.dateSelector();
                            while (changedCheckOut == null) {
                                changedCheckOut = Utilities.dateSelector();
                            }
                            setCheckOut(changedCheckOut);
                            System.out.println("Your Check out Date is now: " + getCheckOut());
                            Utilities.dateDifference(getCheckIn(), getCheckOut(), this);
                            setPrice(Utilities.priceCalculator(this));
                            System.out.println("You will now be staying for: " + getCheckedDays() + " days");
                            break;
                        case 5:
                            System.out.println("Your Room selection is currently: " + getRoomType() + " you also have the " + getBedOption() + " selected.");
                            Utilities.roomSelection(this);
                            System.out.println("Your Room selection is now: " + getRoomType() + " you have also selected the " + getBedOption());
                            break;
                        case 6:
                            System.out.println("Any changes you have made have been updated on your booking: " + getBookingRef());
                            System.out.println("Please find new booking details below\n");
                            Utilities.echoDetails(this);
                            break loop;
                    }

                } else {
                    System.out.println("Please select a valid Menu Option");
                }
                //DateDifference can throw a ParseException, this has been error handled so it is ignored.
            } catch (ParseException ignore) {
            }


        }
        //This calls the write method again, and updates any user changes.
        Utilities.writeTripToFile(this);

    }
}



