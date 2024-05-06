package Testing;

import ExceptionHandling.InvalidBookingRefException;
import RoomTypes.RoomType;
import RoomTypes.ValueRoomType;
import RoomTypes.SuperiorRoomType;
import RoomTypes.DeluxeRoomType;
import System_Run.Trip;
import System_Run.Booking;
import System_Run.BookingManager;
import SystemUtilities.Utilities;

import java.io.*;
import java.awt.print.Book;
import java.util.Arrays;

public class TestingClass extends Utilities{
    private static String[] positiveDateTestCases = new String[]{"20 12 2023", "31 2 2023","20 1 2021", "20 12 2024","10 4 2021","5 6 2023"};
    private static String[] negativeDateTestCases = new String[]{"20,12 ,2023", "40 2 2023","20 2021 2021", "20 12 21","a","hello world"};
    static BookingManager bookingManager = new BookingManager();
    static Trip trip = new Trip();
    //Assigning the PrintStream Object to the standard System.out to resume error messages.
    public static PrintStream originalStream = System.out;
    //Creating a new PrintStream object to supress error handling within functions during testing.
    public static PrintStream supressedStream = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {

        }
    });

    public static void main(String[] args) {
        System.out.println("Testing the positive test cases for the date selector function......");
        positiveDateSelectorTest();
        System.out.println("Testing the negative test cases for the date selector function......");
        negativeDateSelectorTest();


    }

    public static void positiveDateSelectorTest() {
        int pass = 0;
        int totalTests = positiveDateTestCases.length;

        for (String testCase : positiveDateTestCases) {
            //Using the InputStream object allows me to simulate the entry of User Input for this specific function
            InputStream in = new ByteArrayInputStream(testCase.getBytes());
            System.setIn(in);
            //Stops unwanted (and expected) error codes from being printed to console. Also stops the internal printing of the function
            System.setErr(supressedStream);
            System.setOut(supressedStream);
            String output = Utilities.dateSelector();
            //Sets the error output and the standard system.out stream back to stdout.
            System.setErr(originalStream);
            System.setOut(originalStream);
            if (output != null) {
                System.out.println("Test Passed for " + testCase);
                pass++;
            } else {
                System.out.println("Test Failed for " + testCase);
            }
        }
        System.out.println(pass + " tests passed out of a total of " + totalTests + " for the date selector function.");
    }

    public static void negativeDateSelectorTest() {
        int pass = 0;
        int totalTests = negativeDateTestCases.length;
        System.out.println("Due to being negative tests, the expected outcome of the test is a fail.");

        for (String testCase : negativeDateTestCases) {
            //Using the InputStream object allows me to simulate the entry of User Input for this specific function
            InputStream in = new ByteArrayInputStream(testCase.getBytes());
            System.setIn(in);
            //Stops unwanted (and expected) error codes from being printed to console. Also stops the internal printing of the function
            System.setErr(supressedStream);
            System.setOut(supressedStream);
            String output = Utilities.dateSelector();
            //Sets the error output stream and the standard system.out back to stdout.
            System.setErr(originalStream);
            System.setOut(originalStream);
            if (output != null) {
                System.out.println("Test Passed for " + testCase);
            } else {
                System.out.println("Test Failed for " + testCase);
                pass++;
            }
        }
        System.out.println(pass + " tests passed out of a total of " + totalTests + " for the date selector function.");
    }

}
