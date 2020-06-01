package timetable_elements;

public class Lecturer {
    private int lecturerId;
    private String lecturerName;

    public Lecturer(int lecturerId, String lecturerName) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
    }

    public String getLecturerName() {
        return this.lecturerName;
    }
}
