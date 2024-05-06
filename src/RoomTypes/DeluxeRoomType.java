package RoomTypes;

public class DeluxeRoomType extends RoomType {

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

    @Override
    public String toString() {
        return "Deluxe Room";
    }

    @Override
    public boolean inputCheck(int option) {
        return (option > 0) && (option < 5);
    }
}

