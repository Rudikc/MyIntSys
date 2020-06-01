import ga.Genetic;
import timetable_elements.Class;
import timetable_elements.Timetable;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static int POPULATION_SIZE = 200;
    private final static int ELITISM_COUNT = 3;
    private final static int TOURNAMENT_SIZE = 10;
    private final static double MUTATION_RATE = 0.1;
    private final static double CROSSOVER_RATE = 0.6;

    public static void main(String[] args) {




        Timetable timetable = createTimetable();
        Genetic ga = new Genetic(POPULATION_SIZE, MUTATION_RATE, CROSSOVER_RATE, ELITISM_COUNT, TOURNAMENT_SIZE);
        Genetic.Population population = ga.initPopulation(timetable);
        ga.evaluatePopulation(population, timetable);
        int generation = 1;
        while (!ga.isTerminationConditionMet(generation, 1000) && !ga.isTerminationConditionMet(population)) {
            timetable.createClasses(population.getFittest(0));
            population = ga.crossoverPopulation(population);
            population = ga.mutatePopulation(population, timetable);
            ga.evaluatePopulation(population, timetable);
            generation++;
        }
        timetable.createClasses(population.getFittest(0));
        Class[] classes = timetable.getClasses();

        /*
          Вивід
         */
        for (int i = 1; i < 3; i++) {
            int finalI = i;
            List<Class> byGroup = Arrays.stream(classes)
                    .filter(p -> p.getGroupId() == finalI)
                    .collect(Collectors.toList());

            System.out.println("Group " + timetable.getGroup(finalI).getGroupName());

            String leftAlignFormat = "| %-25s | %-25s |%-5s |%-30s |%-30s |%n";
            String[] days = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятнциця"};
            String[] times = {"8:40", "10:35", "12:20", "14:05"};

            System.out.println("|------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|          Час              |          Предмет          | Ауд. |            Лектор             |            Практик            |");
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------|");
            for (String day : days) {
                List<Class> byDay = byGroup
                        .stream()
                        .filter(p -> timetable.getTimeslot(p.getTimeSlotId()).getTimeslot().startsWith(day))
                        .collect(Collectors.toList());

                for (String time : times) {
                    List<Class> byTime = byDay
                            .stream()
                            .filter(p -> timetable.getTimeslot(p.getTimeSlotId()).getTimeslot().split(" ")[1].equals(time))
                            .collect(Collectors.toList());
                    for (Class mClass : byTime) {

                        System.out.printf(leftAlignFormat,
                                timetable.getTimeslot(mClass.getTimeSlotId()).getTimeslot(),
                                timetable.getModule(mClass.getModuleId()).getCourseName(),
                                timetable.getRoom(mClass.getRoomId()).getRoomNumber(),
                                timetable.getlecturer(mClass.getLecturerId()).getLecturerName(),
                                timetable.getPractik(mClass.getPracticeId()).getPractikName());
                    }
                }
            }
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------|");

            System.out.println("\n");


        }

    }

    private static Timetable createTimetable() {
        Timetable timetable = new Timetable();
        String[] days = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятнциця"};

        timetable.addRoom(1, "305", 33);
        timetable.addRoom(2, "41", 33);
        timetable.addRoom(3, "39", 33);
        timetable.addRoom(4, "18", 33);
        timetable.addRoom(5, "17", 33);
        timetable.addRoom(6, "01", 33);

        for (int i = 0; i < days.length; i++) {
            timetable.addTimeslot(1 + i * 6, days[i] + " 8:40 - 10:15");
            timetable.addTimeslot(2 + i * 6, days[i] + " 10:35 - 12:10");
            timetable.addTimeslot(3 + i * 6, days[i] + " 12:20 - 13:55");
            timetable.addTimeslot(4 + i * 6, days[i] + " 14:05 - 15:45");
        }

        timetable.addLecturer(1, "Петренко");
        timetable.addLecturer(2, "Гладишко");
        timetable.addLecturer(3, "Гопанчук");
        timetable.addLecturer(4, "Крамар");
        timetable.addLecturer(5, "Рудіченко");
        timetable.addLecturer(6, "Улітін");

        timetable.addPractik(1, "Петро");
        timetable.addPractik(2, "Марина");
        timetable.addPractik(3, "Ірина");
        timetable.addPractik(4, "Михайло");
        timetable.addPractik(5, "Наталія");
        timetable.addPractik(6, "Максим");

        timetable.addModule(1, "CS01", "Паралельне програмування", new int[]{1, 2}, new int[]{3, 5});
        timetable.addModule(2, "CS02", "Інтелектуальні системи", new int[]{3, 4}, new int[]{4, 3, 1});
        timetable.addModule(3, "CS03", "Менеджмент", new int[]{5, 1}, new int[]{4, 3});
        timetable.addModule(4, "CS04", "Право", new int[]{3, 4}, new int[]{5, 1});
        timetable.addModule(5, "CS05", "SQL", new int[]{6}, new int[]{6});
        timetable.addModule(6, "CS06", "Композиційні семантики", new int[]{1, 4}, new int[]{1, 2});
        timetable.addModule(7, "CS07", "Нейронні мережі", new int[]{3, 5}, new int[]{1, 2, 3, 4, 5});

        timetable.addGroup(1, "ТТП-42", 30, new int[]{1, 2, 3, 5, 5, 2, 7, 7, 7, 2});
        timetable.addGroup(2, "TTП-41", 30, new int[]{1, 2, 3, 7, 2});
//        timetable.addGroup(3, "MI-4", 30, new int[]{1, 1, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7});
        return timetable;
    }
}
