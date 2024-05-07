package RoomTypes;

public class SuperiorRoomType extends DeluxeRoomType {
    @Override
    public String roomOption() {
        super.roomOption();
        switch (roomOption) {
            case 5:
                System.out.println("You have selected a King bed");
                bedOption = "King Bed";

                break;
        }
        return bedOption;
    }

    //Overriden the toString function in order to print the name of the roomType without memory address
    @Override
    public String toString() {
        return "Superior Room";
    }

    //Overriden an inputCheck function to ensure correct input is being passed for roomType
    @Override
    public boolean inputCheck(int option) {
        return (option > 0) && (option < 6);
    }
}

