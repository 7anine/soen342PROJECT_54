import java.util.ArrayList;

public class Client extends Record implements User {
    private String name;
    private int age;
    private String email;
    private String clientID;
    private final boolean isOver18;
    private Client guardian;
    private ArrayList<Booking> bookings;
    public Client(String name, int age, String email, String clientID) {
        super();  // Call the parent class constructor if necessary
        this.name = name;
        this.age = age;
        this.email = email;
        this.clientID = clientID;
        this.bookings = new ArrayList<Booking>();

        if(age>18){
            isOver18 = true;
            this.guardian = null;

        } else{
            this.guardian = getGuardianInfo(new Client("parent1", 40, "123@gmail.com", "ID1"));
            isOver18 = false;
        }
    }

    public String getName() {
        return name;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isOver18() {
        return isOver18;
    }

    public Client getGuardian() {
        return guardian;
    }

    public void setGuardian(Client guardian) {
        this.guardian = guardian;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void delete(){
    }

    private Client getGuardianInfo(Client client){
        //add persitence logic here
        return client;
    }
    private void makeBooking (Lesson lesson){
        if (lesson.isAvailable()){
            Booking booking = new Booking("bookingID", "Sandra", "lessonID");
            this.bookings.add(booking);
            //add booking to db as new booking
        }
        else{
            //handle fail case
        }
    }
}
