public class Client extends Record {
    private String name;
    private int age;
    private String email;

    public Client(String name, int age, String email) {
        super();  // Call the parent class constructor if necessary
        this.name = name;
        this.age = age;
        this.email = email;
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

}
