public class Instructor extends Record {
    private String name;
    private String specialization;
    private String[] cities;
    private String phone;
    private boolean availability;
    private Lesson[] assignedLessons;

    public Instructor(String name, String specialization, String[] cities, String phone, boolean availability, Lesson[] assignedLessons) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phone = phone;
        this.availability = availability;
        this.assignedLessons = assignedLessons;
    }

    public Instructor(String name, String specialization, String[] cities, String phone, boolean availability) {
        this.name = name;
        this.specialization = specialization;
        this.cities = cities;
        this.phone = phone;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String[] getCities() {
        return cities;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Lesson[] getAssignedLessons() {
        return assignedLessons;
    }

    public void setAssignedLessons(Lesson[] assignedLessons) {
        this.assignedLessons = assignedLessons;
    }
    public void delete(){}
}
