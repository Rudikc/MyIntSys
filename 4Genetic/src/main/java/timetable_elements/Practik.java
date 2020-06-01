package timetable_elements;

public class Practik {
    int practikId;
    String practikName;

    public Practik(int practikId, String practikName) {
        this.practikId = practikId;
        this.practikName = practikName;
    }

    public String getPractikName() {
        return practikName;
    }
}
