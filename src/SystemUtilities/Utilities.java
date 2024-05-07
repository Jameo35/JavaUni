package SystemUtilities;

import ExceptionHandling.InvalidDateCombinationException;
import RoomTypes.DeluxeRoomType;
import RoomTypes.RoomType;
import RoomTypes.SuperiorRoomType;
import RoomTypes.ValueRoomType;
import System_Run.Trip;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

//SystemUtilities.Utilities to capture the associated methods that are commonly used throughout the programme.
public class Utilities {

    //File Directory stored as a final variable as do not want it to be changed via any method.
    private static final String DIRECTORY_BANK_ACCOUNTS = "src" + File.separator + "TripBookings";

    /*Usage of String Array Variables to use in other methods/be called on within the programme.
    This creates a single place of modification for the string to be modified and changes to be applied across the programme.
     */
    private static final String[] roomTypes = new String[]{"1. Value", "2. Deluxe", "3. Superior"};
    private static final String[] bedOptions = new String[]{"1. Single", "2. Twin", "3. Double", "4. Queen", "5. King"};

    private static final int[] premiumMonths = {12, 1, 6, 7, 8};


    //Method to help customer to select dates in required formatting, takes user input and a try-catch method to avoid input mismatches.
    public static String dateSelector() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date;
        try {
            int checkInDay = scanner.nextInt();
            int checkInMonth = scanner.nextInt();
            int checkInYear = scanner.nextInt();
            if (checkInMonth < 10 && checkInDay < 10) {
                date = LocalDate.parse(checkInYear + "-0" + checkInMonth + "-0" + checkInDay);
            } else if (checkInMonth < 10) {
                date = LocalDate.parse(checkInYear + "-0" + checkInMonth + "-" + checkInDay);
            } else if (checkInDay < 10) {
                date = LocalDate.parse(checkInYear + "-" + checkInMonth + "-0" + checkInDay);
            } else {
                date = LocalDate.parse(checkInYear + "-" + checkInMonth + "-" + checkInDay);
            }
            DateTimeFormatter gbDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return date.format(gbDate);
        } catch (InputMismatchException e) {
            System.err.println("Please enter the date in the specified format DD MM YYYY.");
            scanner.nextLine();
            return null;
        } catch (DateTimeParseException | NullPointerException e) {
            System.err.println("Invalid Date Selected");
            System.out.println("Please re-try to enter a valid booking date to continue the booking process.");
            return null;
        }
    }

    //Method used to work out the difference in dates, that returns an integer of the number of days between two dates. Used for price calculation
    public static void dateDifference(String string1, String string2, Trip trip) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date checkInDate = sdf.parse(string1);
            Date checkOutDate = sdf.parse(string2);

            long difference_In_Time
                    = checkOutDate.getTime() - checkInDate.getTime();
            int dayDifference = (int) TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
            if (dayDifference < 0) {
                trip.setCheckedDays(dayDifference);
                throw new InvalidDateCombinationException();
            } else {
                trip.setCheckedDays(dayDifference);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        } catch (InvalidDateCombinationException e) {
            System.err.println("Please change the trip dates using the change existing booking function as these dates are invalid when confirming the details please select \"N\" and change the dates");
        }

    }

    //Method used to play-back user details gathered via the trip object.
    public static void echoDetails(Trip trip) {
        System.out.println("Booking Ref: " + trip.getBookingRef());
        System.out.println("First Name: " + trip.getFirstname());
        System.out.println("Last Name: " + trip.getSurname());
        System.out.println("Check In Date: " + trip.getCheckIn());
        System.out.println("Check Out Date: " + trip.getCheckOut());
        System.out.println("Total Length of Stay: " + trip.getCheckedDays() + " Days");
        System.out.println("Room Type: " + trip.getRoomType());
        System.out.println("Bed Option: " + trip.getBedOption());
        System.out.println("Total Price: " + trip.getPrice());

    }

    //Utility method to write a trip object to an output file. If the parent directory doesn't exist, this method will create the directory.
    public static void writeTripToFile(Trip trip) {
        String filePath = trip.getBookingRef() + ".txt";
        File file = new File(DIRECTORY_BANK_ACCOUNTS, filePath);

        file.getParentFile().mkdir();
        //using a try-catch in order to mitigate the risk of exceptions.
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(trip.getBookingRef() + "\n");
            bufferedWriter.write(trip.getFirstname() + "\n");
            bufferedWriter.write(trip.getSurname() + "\n");
            bufferedWriter.write(trip.getCheckIn() + "\n");
            bufferedWriter.write(trip.getCheckOut() + "\n");
            bufferedWriter.write(trip.getCheckedDays() + "\n");
            bufferedWriter.write(trip.getRoomType() + "\n");
            bufferedWriter.write(trip.getBedOption() + "\n");
            bufferedWriter.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    //Utility method to consume information from an output file in the TripBookings folder, this will then create a Trip object with the associated details.
    public static Trip readTripData(int BookingRef) {
        String filePath = DIRECTORY_BANK_ACCOUNTS + File.separator + BookingRef + ".txt";
        File file = new File(filePath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int bookingRef = Integer.parseInt(bufferedReader.readLine());
            String forename = bufferedReader.readLine();
            String surname = bufferedReader.readLine();
            String checkIn = bufferedReader.readLine();
            String CheckOut = bufferedReader.readLine();
            int CheckedDays = Integer.parseInt(bufferedReader.readLine());
            String roomTypeFile = bufferedReader.readLine();
            RoomType roomType = null;
            if (roomTypeFile.equals("Value Room")) {
                roomType = new ValueRoomType();
            } else if (roomTypeFile.equals("Deluxe Room")) {
                roomType = new DeluxeRoomType();

            } else if (roomTypeFile.equals("Superior Room")) {
                roomType = new SuperiorRoomType();
            }
            String BedOption = bufferedReader.readLine();
            bufferedReader.close();
            return new Trip(forename, surname, checkIn, CheckOut, CheckedDays, bookingRef, roomType, BedOption);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    //Method called from Trip, this is to ensure the correct selection of a room. Which makes use of inheritance and classes.
    public static void roomSelection(Trip trip) {
        RoomType _roomType;
        int option;
        Scanner scanner = new Scanner(System.in);
        System.out.println("What room would you like to choose?\n" + Arrays.toString(roomTypes));
        while (true) {
            try {

                option = scanner.nextInt();
                scanner.nextLine();
                if ((option < 4) && (option > 0)) {
                    break;
                } else {
                    System.err.println("Please select a valid option");
                }
            } catch (InputMismatchException e) {
                System.err.println("Please select a valid option");
                scanner.nextLine();
            }
        }

        switch (option) {
            case 1:
                System.out.println("You have selected a Value room, now to choose a bed");
                _roomType = new ValueRoomType();
                trip.setRoomType(_roomType);
                System.out.println("Please select from: 1.Single Bed | 2.Twin Bed | 3. Double Bed");
                trip.setBedOption(_roomType.roomOption());
                break;
            case 2:
                System.out.println("You have selected a Deluxe room, now to choose a bed");
                _roomType = new DeluxeRoomType();
                trip.setRoomType(_roomType);
                System.out.println("Please select from: 1.Single Bed | 2.Twin Bed | 3. Double Bed | 4. Queen Bed");
                trip.setBedOption(_roomType.roomOption());
                break;
            case 3:
                System.out.println("You have selected a Superior room, now to choose a bed");
                _roomType = new SuperiorRoomType();
                trip.setRoomType(_roomType);
                System.out.println("Please select from " + Arrays.toString(bedOptions));
                trip.setBedOption(_roomType.roomOption());
                break;
        }


    }

    /*New Method to calculate price depending on if the month is a member of the premiumMonths array.
    This method iterates through the list and compares the checkInMonth variable from the trip object
    depending on whether the month is a 'premium' month there is different pricing logic. The function returns a double
    as prices can be shown in pounds and pence, not integer values.
     */

    public static double priceCalculator(Trip trip) {
        int length = trip.getCheckedDays();
        double price = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date checkInDate = sdf.parse(trip.getCheckIn());
            int checkInMonth = checkInDate.getMonth() + 1;
            for (int month : premiumMonths) {
                if (month == checkInMonth) {
                    price = length * 135.5;
                    break;
                } else {
                    price = length * 110;
                }
            }

        } catch (ParseException e) {
            System.err.println("Invalid Check In Date");
        }
        return price;
    }
}

