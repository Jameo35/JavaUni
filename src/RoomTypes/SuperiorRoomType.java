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

    @Override
    public String toString() {
        return "Superior Room";
    }

    @Override
    public boolean inputCheck(int option) {
        return (option > 0) && (option < 6);
    }
}

