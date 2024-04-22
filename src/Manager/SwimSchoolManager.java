package Manager;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Random;

/**
 * Manages the swimming lessons including coach assignments, scheduling, and lesson capacities.
 * Utilizes a singleton pattern to ensure only one instance of this manager is used throughout the application.
 */
public class SwimSchoolManager {
    private static SwimSchoolManager swimSchoolManager;
    private final String[] coaches_name = {"Mason", "Ava", "Liam", "Zoe", "Ethan", "Mia", "Logan", "Ella"};
    private final Random random_Obj = new Random();
    private int LessonCapacity = 4;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private SwimSchoolManager() {
    }

    /**
     * Provides the singleton instance of the SwimSchoolManager.
     * If no instance exists, a new one is created and returned.
     * @return The single instance of SwimSchoolManager.
     */
    public static SwimSchoolManager getSwimSchoolManager() {
        if (swimSchoolManager == null) {
            swimSchoolManager = new SwimSchoolManager();
        }
        return swimSchoolManager;
    }

    /**
     * Sets the start time for swimming lessons based on the day of the week.
     * @param ofWeek The day of the week.
     * @return The starting LocalTime for the lessons on the given day.
     */
    public LocalTime setStartTime(DayOfWeek ofWeek) {
        return switch (ofWeek) {
            case MONDAY, WEDNESDAY, FRIDAY -> LocalTime.of(16, 0);
            case SATURDAY -> LocalTime.of(14, 0);
            default -> LocalTime.of(0, 0);
        };
    }

    /**
     * Gets the current lesson capacity. Currently, this method returns a fixed value.
     * @return The lesson capacity.
     */
    public int setLessonCapacity() {
        return LessonCapacity;
    }

    /**
     * Randomly assigns a grade level for a swimming student, ranging from 1 to 5.
     * @return The grade level assigned.
     */
    public int assignGradeLevel() {
        int gradeLevel = random_Obj.nextInt(5) + 1;
        return gradeLevel;
    }

    /**
     * Randomly assigns a coach from a predefined list of coach names.
     * @return The name of the coach assigned.
     */
    public String assignCoach() {
        return coaches_name[random_Obj.nextInt(coaches_name.length)];
    }
}
