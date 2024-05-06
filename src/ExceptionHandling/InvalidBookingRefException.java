package ExceptionHandling;

//Class which inherits the methods of the Exception class and extends them with customised exception handling.
public class InvalidBookingRefException extends Exception{
    //Creation of method to handle errors when user is inputting invalid booking references.
    public InvalidBookingRefException(String message, int value)
    {
        super("InvalidBookingRefException: " + message + value);
    }
}

