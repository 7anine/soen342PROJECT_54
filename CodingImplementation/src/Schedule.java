

public class Schedule {
    private int ID;
    private static int IDincrement = 1;
    private String day;
    private String startTime;
    private String endTime;



    public Schedule(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ID = IDincrement;
        IDincrement++;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
