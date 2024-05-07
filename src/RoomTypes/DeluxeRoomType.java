package RoomTypes;
//Inherited the characteristics of roomType
public class DeluxeRoomType extends RoomType {

    //Overiding this function to provide extra options due to roomType
    @Override
    public String roomOption() {
        super.roomOption();
        switch (roomOption) {
            case 4:
                System.out.println("You have selected a Queen bed");
                bedOption = "Queen Bed";
                break;

        }
        return bedOption;
    }
    //Overriden the toString function in order to print the name of the roomType without memory address
    @Override
    public String toString() {
        return "Deluxe Room";
    }

    //Overriden an inputCheck function to ensure correct input is being passed for roomType
    @Override
    public boolean inputCheck(int option) {
        return (option > 0) && (option < 5);
    }
}

