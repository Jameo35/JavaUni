import java.text.ParseException;

public class Booking {
    /*Main method instantiates a System_Run.BookingManager object, which runs the programme after calling the manageBooking method belonging to the object.
    included a throws ParseException due to the dateDifference method - which can throw a parsing exception */
    public static void main(String[] args) throws ParseException {
        BookingManager bookingManager = new BookingManager();
        bookingManager.manageBooking();
        System.out.println("Thank you for choosing the Hirst Hotel Group.");
    }

}