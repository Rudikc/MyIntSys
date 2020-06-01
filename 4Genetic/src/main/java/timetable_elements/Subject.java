package timetable_elements;

public class Subject {
    String subjectCode;
    String subject;
    int subjectId;
    int[] lecturerIds;
    int[] practikIds;

    public Subject(int subjectId, String subjectCode, String subject, int[] lecturerIds, int[] practikIds) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subject = subject;
        this.lecturerIds = lecturerIds;
        this.practikIds = practikIds;
    }

    public String getCourseName() {
        return this.subject;
    }

    public int getRandomLecturerId() {
        return lecturerIds[(int) (lecturerIds.length * Math.random())];
    }

    public int getRandomPractikId() {
        return practikIds[(int) (practikIds.length * Math.random())];
    }
}
