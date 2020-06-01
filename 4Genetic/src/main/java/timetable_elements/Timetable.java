package timetable_elements;

import ga.Genetic;

import java.util.HashMap;

public class Timetable {
    private final HashMap<Integer, Classroom> rooms;
    private final HashMap<Integer, Lecturer> lecturers;
    private final HashMap<Integer, Practik> practiks;
    private final HashMap<Integer, Subject> modules;
    private final HashMap<Integer, Group> groups;
    private final HashMap<Integer, Timeslot> timeslots;
    private Class[] classes;
    private int numOfClasses = 0;

    public Timetable() {
        this.rooms = new HashMap<>();
        this.lecturers = new HashMap<>();
        this.practiks = new HashMap<>();
        this.modules = new HashMap<>();
        this.groups = new HashMap<>();
        this.timeslots = new HashMap<>();
    }

    public Timetable(Timetable cloneable) {
        this.rooms = cloneable.getRooms();
        this.lecturers = cloneable.getLecturers();
        this.practiks = cloneable.getPractiks();
        this.modules = cloneable.getModules();
        this.groups = cloneable.getGroups();
        this.timeslots = cloneable.getTimeslots();
    }

    private HashMap<Integer, Group> getGroups() {
        return this.groups;
    }

    private HashMap<Integer, Timeslot> getTimeslots() {
        return this.timeslots;
    }

    private HashMap<Integer, Subject> getModules() {
        return this.modules;
    }

    private HashMap<Integer, Lecturer> getLecturers() {
        return this.lecturers;
    }

    private HashMap<Integer, Practik> getPractiks() {
        return this.practiks;
    }

    public void addRoom(int roomId, String roomName, int capacity) {
        this.rooms.put(roomId, new Classroom(roomId, capacity, roomName));
    }

    public void addLecturer(int lecturerId, String lecturerName) {
        this.lecturers.put(lecturerId, new Lecturer(lecturerId, lecturerName));
    }

    public void addPractik(int practikid, String practikName) {
        this.practiks.put(practikid, new Practik(practikid, practikName));
    }

    public void addModule(int moduleId, String moduleCode, String module, int[] lecturerIds, int[] practikIds) {
        this.modules.put(moduleId, new Subject(moduleId, moduleCode, module, lecturerIds, practikIds));
    }

    public void addGroup(int groupId, String groupName, int groupSize, int[] moduleIds) {
        this.groups.put(groupId, new Group(groupId, groupName, groupSize, moduleIds));
        this.numOfClasses = 0;
    }

    public void addTimeslot(int timeslotId, String timeslot) {
        this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot));
    }

    public void createClasses(Genetic.Individual individual) {
        Class[] classes = new Class[this.getNumOfClasses()];

        int[] chromosome = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;

        for (Group group : this.getGroupsAsArray()) {
            int[] moduleIds = group.getModuleIds();
            for (int moduleId : moduleIds) {
                classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);
                classes[classIndex].addTimeSlot(chromosome[chromosomePos]);
                chromosomePos++;
                classes[classIndex].setRoomId(chromosome[chromosomePos]);
                chromosomePos++;
                classes[classIndex].addLecturer(chromosome[chromosomePos]);
                chromosomePos++;
                classes[classIndex].addPractiсe(chromosome[chromosomePos]);
                chromosomePos++;
                classIndex++;
            }
        }
        this.classes = classes;
    }


    public Classroom getRoom(int roomId) {
        if (!this.rooms.containsKey(roomId)) {
            System.out.println("Room doesn't contain key " + roomId);
        }
        return this.rooms.get(roomId);
    }

    public HashMap<Integer, Classroom> getRooms() {
        return this.rooms;
    }

    public Classroom getRandomRoom() {
        Object[] roomsArray = this.rooms.values().toArray();
        return (Classroom) roomsArray[(int) (roomsArray.length * Math.random())];
    }

    public Lecturer getlecturer(int lecturerId) {
        return this.lecturers.get(lecturerId);
    }

    public Practik getPractik(int practikId) {
        return this.practiks.get(practikId);
    }

    public Subject getModule(int moduleId) {
        return this.modules.get(moduleId);
    }

    public Group getGroup(int groupId) {
        return this.groups.get(groupId);
    }

    public Group[] getGroupsAsArray() {
        return this.groups.values().toArray(new Group[0]);
    }

    public Timeslot getTimeslot(int timeslotId) {
        return this.timeslots.get(timeslotId);
    }

    public Timeslot getRandomTimeslot() {
        Object[] timeslotArray = this.timeslots.values().toArray();
        return (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
    }

    public Class[] getClasses() {
        return this.classes;
    }

    public int getNumOfClasses() {
        if (this.numOfClasses > 0) {
            return this.numOfClasses;
        }

        int numClasses = 0;
        Group[] groups = this.groups.values().toArray(new Group[0]);
        for (Group group : groups) {
            numClasses += group.getModuleIds().length;
        }
        this.numOfClasses = numClasses;

        return this.numOfClasses;
    }

    public int calcClashes() {
        int clashes = 0;

        for (Class classA : this.classes) {

            int roomCapacity = this.getRoom(classA.getRoomId()).getRoomCapacity();
            int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();

            if (roomCapacity <= groupSize) {
                clashes++;
            }

            for (Class classB : this.classes) {
                if (classA.getRoomId() == classB.getRoomId() && classA.getTimeSlotId() == classB.getTimeSlotId()
                        && classA.getClassId() != classB.getClassId()) {
                    clashes++;
                    break;
                }
            }

            for (Class classB : this.classes) {
                if (classA.getLecturerId() == classB.getLecturerId() && classA.getTimeSlotId() == classB.getTimeSlotId()
                        && classA.getClassId() != classB.getClassId()) {
                    clashes++;
                    break;
                }
            }

            for (Class classB : this.classes) {
                if (classA.getPracticeId() == classB.getPracticeId() && classA.getTimeSlotId() == classB.getTimeSlotId()
                        && classA.getClassId() != classB.getClassId()) {
                    clashes++;
                    break;
                }
            }

            for (Class classB : this.classes) {
                if (classA.getGroupId() == classB.getGroupId() && classA.getTimeSlotId() == classB.getTimeSlotId()
                        && classA.getClassId() != classB.getClassId()) {
                    clashes++;
                    break;
                }
            }
        }
        return clashes;
    }
}
