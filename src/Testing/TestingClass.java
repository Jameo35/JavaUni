package Testing;

import RoomTypes.DeluxeRoomType;
import RoomTypes.RoomType;
import RoomTypes.ValueRoomType;
import System_Run.Trip;
import System_Run.BookingManager;
import SystemUtilities.Utilities;

import java.io.*;
import java.text.ParseException;
import java.util.NoSuchElementException;


public class TestingClass extends Utilities {
    private static String[] positiveDateTestCases = new String[]{"20 12 2023", "31 2 2023", "20 1 2021", "20 12 2024", "10 4 2021", "5 6 2023"};
    private static String[] negativeDateTestCases = new String[]{"20,12 ,2023", "40 2 2023", "20 2021 2021", "20 12 21", "a", "hello world"};
    private static int[] readFileTestCases = new int[]{235768, 122345, 1, 238679};

    //Test cases for room selection to include the boundary values and enable Boundary Value Analysis testing method.
    private static String[] roomOptionTestCases = new String[]{"0", "1", "2", "3", "4", "5"};
    public static Trip trip = new Trip();
    //Assigning the PrintStream Object to the standard System.out to resume error messages.
    public static PrintStream originalStream = System.out;
    //Creating a new PrintStream object to supress error handling within functions during testing.
    public static PrintStream supressedStream = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {

        }
    });

    public static void main(String[] args) throws ParseException {
        System.out.println("Testing the positive test cases for the date selector function......");
        positiveDateSelectorTest();
        System.out.println("Testing the negative test cases for the date selector function......");
        negativeDateSelectorTest();
        System.out.println("Testing that the read existing booking function is able to find valid bookings in the file system......");
        readExistingBookingTest();
        System.out.println("Testing that the write booking function is able to write valid bookings in the file system......");
        writeNewBookingTest();
        System.out.println("Testing that the menu for booking manager functions as expected using C, Q, N, c, q, n, l, L ......");
        menuOptionTest();
        System.out.println("Testing date combinations for Pricing Calculations.....");
        priceCalculatorTest();
        System.out.println("Testing the RoomType classes to ensure only the correct bedOptions can be selected.....");
        roomOptionTest();
    }

    public static void positiveDateSelectorTest() {
        int pass = 0;
        int totalTests = positiveDateTestCases.length;
        InputStream standard = System.in;
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
        System.out.println(pass + " tests passed out of a total of " + totalTests + " for the positive date selector function.");
        System.setIn(standard);
    }

    public static void negativeDateSelectorTest() {
        int pass = 0;
        int totalTests = negativeDateTestCases.length;
        System.out.println("Due to being negative tests, the expected outcome of the test is a fail.");
        InputStream standard = System.in;
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
        System.out.println(pass + " tests passed out of a total of " + totalTests + " for the negative date selector function.");
        System.setIn(standard);
    }

    public static void readExistingBookingTest() {
        int pass = 0;
        int totalTests = readFileTestCases.length;
        for (int testCase : readFileTestCases) {
            trip = Utilities.readTripData(testCase);
            if (trip != null) {
                System.out.println("Test Passed");
                pass++;
            } else {
                System.out.println("Test Failed");
            }
        }
        System.out.println(pass + " tests passed out of a total of " + totalTests + " for the read file test.");
    }

    public static void writeNewBookingTest() {
        //Instantiating a trip object to test writing and reading using variables in order to make comparisons once written + read.
        String firstname = "James";
        String surname = "Hirst";
        String checkIn = "20-12-2021";
        String checkOut = "23-12-2021";
        int checkedDays = 3;
        int bookingRef = 123542;
        RoomType roomType = new ValueRoomType();
        String bedOption = "Double Bed";
        Trip testTrip = new Trip(firstname, surname, checkIn, checkOut, checkedDays, bookingRef, roomType, bedOption);
        //Calling the function under test, to write a file using the details found in the variables above.
        Utilities.writeTripToFile(testTrip);
        testTrip = Utilities.readTripData(bookingRef);
        if (testTrip != null) {
            System.out.println("Test Passed, the file has been created and can be found in the directory with file name " + bookingRef + ".txt");
        } else {
            System.out.println("Test Failed");
        }
    }

    public static void menuOptionTest() throws ParseException {
        //Creating a list of test options
        String options[] = new String[]{"C", "Q", "N", "c", "q", "n", "l", "L"};
        int i = 0;
        String expectedResult;
        //Iterating through test options
        for (String option : options) {
            //creating input stream object to simulate user input based on test options
            InputStream topMenu = new ByteArrayInputStream(option.getBytes());
            System.setIn(topMenu);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);
            try {
                //instantiating a bookingmanager object
                BookingManager bookingManager = new BookingManager();
                //calling the manageBooking function
                bookingManager.manageBooking();
                //Catch block allows the test to run through and ignore errors due to only testing menu level.
            } catch (NoSuchElementException ignored) {
            }
            System.setOut(originalStream);
            String testingOutput = outputStream.toString();
            i++;
            switch (i) {
                case 1, 4:
                    expectedResult = "Please enter the booking reference you wish to access the details of:";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + option);
                    } else {
                        System.out.println("Test Failed for test case: " + option);
                    }
                    break;
                case 2, 5:
                    expectedResult = "Exiting the programme";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + option);
                    } else {
                        System.out.println("Test Failed for test case: " + option);
                    }
                    break;
                case 3, 6:
                    expectedResult = "Please enter your first name:";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + option);
                    } else {
                        System.out.println("Test Failed for test case: " + option);
                    }
                    break;
                case 7, 8:
                    expectedResult = "You did not select a valid option, please select an option listed on the Menu.";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + option);
                    } else {
                        System.out.println("Test Failed for test case: " + option);
                    }
                    break;
            }

        }
    }

    public static void priceCalculatorTest() throws ParseException {
        //Creating a new object and adding test data in, pre-requisites of this test are for dates and date difference to be calculated.
        Trip priceTest = new Trip();
        String[] date1 = new String[]{"20-12-2023", "13-2-2023", "20-1-2021", "20-12-2024", "10-4-2021", "5-6-2023"};
        String[] date2 = new String[]{"22-12-2023", "14-2-2023", "28-2-2021", "25-12-2024", "15-5-2021", "5-6-2023"};
        Double[] expectedResult = new Double[]{271.0, 110.00, 5284.5, 677.5, 3849.0, 0.0};
        int i = 0;

        for (String date : date1) {
            priceTest.SetCheckIn(date);
            priceTest.SetCheckOut(date2[i]);
            Utilities.dateDifference(date, date2[i], priceTest);
            double testPrice = Utilities.priceCalculator(priceTest);
            if (testPrice == expectedResult[i]) {
                System.out.println("Test Passed for test case: " + date + " and " + date2[i] + " with price calculated as " + testPrice + " and expected price of " + expectedResult[i]+"\n");
            } else {
                System.out.println("Test Failed for test case: " + date + " and " + date2[i] + " with price calculated as " + testPrice + " and expected price of " + expectedResult[i]+"\n");
            }
            i++;
        }
    }

    public static void roomOptionTest() {
        int i = 0;
        String expectedResult;
        for (String testCase : roomOptionTestCases) {
            //Using InputStream to simulate user input to the system.
            InputStream in = new ByteArrayInputStream(testCase.getBytes());
            //Setting the system in to the InputStream in to pass the test cases.
            System.setIn(in);
            //Creating an outputStream object to capture the console output to my test result.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);
            try {
                RoomType testRoom = new DeluxeRoomType();
                testRoom.roomOption();
            } catch (NoSuchElementException ignored) {
            }
            //Reverting system out to standard out, allows me to print the results.
            System.setOut(originalStream);
            String testingOutput = outputStream.toString();
            i++;
            switch (i){
                case 1,6:
                    expectedResult = "Please select a valid option";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + testCase + " with message " + testingOutput);
                    } else {
                        System.out.println("Test Failed for test case: " + testCase + " with message " + testingOutput);
                    }
                    break;
                case 2:
                    expectedResult = "You have selected a Single bed";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + testCase + " with message " + testingOutput);
                    } else {
                        System.out.println("Test Failed for test case: " + testCase + " with message " + testingOutput);
                    }
                    break;
                case 3:
                    expectedResult = "You have selected a Twin bed";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + testCase + " with message " + testingOutput);
                    } else {
                        System.out.println("Test Failed for test case: " + testCase + " with message " + testingOutput);
                    }
                    break;
                case 4:
                    expectedResult = "You have selected a Double bed";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + testCase + " with message " + testingOutput);
                    } else {
                        System.out.println("Test Failed for test case: " + testCase + " with message " + testingOutput);
                    }
                    break;
                case 5:
                    expectedResult = "You have selected a Queen bed";
                    if (testingOutput.contains(expectedResult)) {
                        System.out.println("Test Passed for test case: " + testCase + " with message " + testingOutput);
                    } else {
                        System.out.println("Test Failed for test case: " + testCase + " with message " + testingOutput);
                    }
                    break;

            }
        }
    }
}

