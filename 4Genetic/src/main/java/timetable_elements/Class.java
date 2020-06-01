package timetable_elements;

public class Class {
    private int classId;
    private int groupId;
    private int moduleId;
    private int lecturerId;
    private int practiceId;
    private int timeSlotId;
    private int roomId;

    public Class(int classId, int groupId, int moduleId) {
        this.classId = classId;
        this.moduleId = moduleId;
        this.groupId = groupId;
    }


    public void addLecturer(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void addPractiсe(int practikId) {
        this.practiceId = practikId;
    }

    public void addTimeSlot(int timeslotId) {
        this.timeSlotId = timeslotId;
    }

    public int getClassId() {
        return this.classId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getModuleId() {
        return this.moduleId;
    }

    public int getLecturerId() {
        return this.lecturerId;
    }

    public int getPracticeId() {
        return this.practiceId;
    }

    public int getTimeSlotId() {
        return this.timeSlotId;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
