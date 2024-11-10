package CodingImplementation.src;


public class Schedule {
    private int ID;
    private int IDincrement=0;
    private String day;
    private String time;

    public Schedule(String day, String time) {
        this.day = day;
        this.time = time;
        this.ID = IDincrement;
        IDincrement++;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
