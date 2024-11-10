import java.util.Scanner;

public class Client extends Record {
    private String name;
    private int age;
    private String email;
    private Client guardian;
    private static int IDincrement = 0;

    private int ID;

    public Client(String name, int age, String email) {
        super();  // Call the parent class constructor if necessary
        this.name = name;
        this.age = age;
        this.email = email;
        if(age<18){
            this.guardian = requestGuardian();
        }
        this.ID = IDincrement;
        IDincrement++;
    }

    public String getName() {
        return name;
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
        //delete the client from the database
    }

    public static void CreateClientAccount(String name, int age, String email, String password){
        Client newClient = new Client(name, age, email);
        newClient.RegisterClientAccountToDB(newClient, password);
    }
    public void RegisterClientAccountToDB(Client client, String password){
        //add logic to register new client to DB
    }

    public Client requestGuardian(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("You are under 18. Please ask a parent to enter his/her information: ");
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your age:");
        int age = scanner.nextInt();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        //not adding the guardian as a user, but as someone who logs into his child<s account with the same password,
        //and is authorized to make bookings in his name (add to db still for persistance)

        return new Client(name, age, email);
    }

}
