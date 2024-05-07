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
    private static final String[] roomTypes = new String[]{"1. Value","2. Deluxe","3. Superior"};
    private static final String[] bedOptions = new String[]{"1. Single","2. Twin","3. Double","4. Queen","5. King"};

    private static final int[] premiumMonths = {12,1,6,7,8};



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
            }
            catch (InputMismatchException e)
            {
                System.err.println("Please enter the date in the specified format DD MM YYYY.");
                scanner.nextLine();
                return null;
            } catch (DateTimeParseException | NullPointerException e)
            {
                System.err.println(e.getMessage());
                System.out.println("Please re-try to enter the booking date to continue the booking process.");
                return null;
            }
    }
    //Method used to work out the difference in dates, that returns an integer of the number of days between two dates. Used for price calculation
    public static void dateDifference(String string1, String string2, Trip trip) throws ParseException {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date checkInDate = sdf.parse(string1);
            Date checkOutDate = sdf.parse(string2);

            long difference_In_Time
                    = checkOutDate.getTime() - checkInDate.getTime();
            int dayDifference = (int) TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;
            trip.SetCheckedDays(dayDifference);
            if(dayDifference < 0){
                throw new InvalidDateCombinationException();
            }
        }catch (ParseException e){
            System.err.println(e.getMessage());
        }catch (InvalidDateCombinationException e){
            System.err.println("Please change the trip dates using the change existing booking function as these dates are invalid");
        }

    }

    //Class used to play-back user details gathered via the trip object.
    public static void echoDetails(Trip trip){
        System.out.println("System_Run.Booking Ref: " + trip.GetBookingRef());
        System.out.println("First Name: " + trip.GetFirstname());
        System.out.println("Last Name: " + trip.GetSurname());
        System.out.println("Check In Date: " + trip.GetCheckIn());
        System.out.println("Check Out Date: " + trip.GetCheckOut());
        System.out.println("Total Length of Stay: " + trip.GetCheckedDays() + " Days");
        System.out.println("Room Type: " + trip.GetRoomType());
        System.out.println("Bed Option: " + trip.GetBedOption());
    }

    //Utility class to write a trip object to an output file. If the parent directory doesn't exist, this method will create the directory.
    public static void writeTripToFile(Trip trip){
        String filePath = trip.GetBookingRef() + ".txt";
        File file = new File(DIRECTORY_BANK_ACCOUNTS, filePath);

        file.getParentFile().mkdir();
        //using a try-catch in order to mitigate the risk of exceptions.
        try
        {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(trip.GetBookingRef() + "\n");
            bufferedWriter.write(trip.GetFirstname() + "\n");
            bufferedWriter.write(trip.GetSurname() + "\n");
            bufferedWriter.write(trip.GetCheckIn() + "\n");
            bufferedWriter.write(trip.GetCheckOut() + "\n");
            bufferedWriter.write(trip.GetCheckedDays() + "\n");
            bufferedWriter.write(trip.GetRoomType() + "\n");
            bufferedWriter.write(trip.GetBedOption() + "\n");

            bufferedWriter.close();

        }        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
    //Utility class to consume information from an output file in the TripBookings folder, this will then create a Trip object with the associated details.
    public static Trip readTripData(int BookingRef){
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
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            return null;
        }
        catch(NumberFormatException e)
        {
            System.err.println(e.getMessage());
            return null;
        }

    }
    public static void roomSelection(Trip trip) {
        System.out.println("What room would you like to choose?\n" + Arrays.toString(roomTypes));
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        scanner.nextLine();
        RoomType _roomType;

        switch (option) {
            case 1:
                System.out.println("You have selected a Value room, now to choose a bed");
                _roomType = new ValueRoomType();
                trip.SetRoomType(_roomType);
                System.out.println("Please select from: 1.Single Bed | 2.Twin Bed | 3. Double Bed");
                trip.SetBedOption(_roomType.roomOption());
                break;
            case 2:
                System.out.println("You have selected a Deluxe room, now to choose a bed");
                _roomType = new DeluxeRoomType();
                trip.SetRoomType(_roomType);
                System.out.println("Please select from: 1.Single Bed | 2.Twin Bed | 3. Double Bed | 4. Queen Bed");
                trip.SetBedOption(_roomType.roomOption());
                break;
            case 3:
                System.out.println("You have selected a Superior room, now to choose a bed");
                _roomType = new SuperiorRoomType();
                trip.SetRoomType(_roomType);
                System.out.println("Please select from " + Arrays.toString(bedOptions));
                trip.SetBedOption(_roomType.roomOption());
                break;

        }
    }
    /*New Method to calculate price depending on if the month is a member of the premiumMonths array.
    This method iterates through the list and compares the checkInMonth variable from the trip object
    depending on whether the month is a 'premium' month there is different pricing logic. The function returns a double
    as prices can be shown in pounds and pence, not integer values.
     */

    public static double priceCalculator(Trip trip){
        int length = trip.GetCheckedDays();
        double price = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date checkInDate = sdf.parse(trip.GetCheckIn());
            int checkInMonth = checkInDate.getMonth() + 1;
            for (int month : premiumMonths) {
                if (month == checkInMonth){
                    price = length * 135.5;
                    break;
                } else
                {
                    price = length * 110;
                }
            }

        } catch (ParseException e)
        {
            System.err.println("Invalid Check In Date");
        }
        return price;
    }


}
