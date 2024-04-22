package view;

import Manager.LessonManager;
import Model.Lesson;

import java.util.List;

/**
 * Displays lesson information from the lesson management system.
 */
public class LessonDisplay {
    private final LessonManager lessonManager = new LessonManager();

    /**
     * Retrieves all lessons from the lesson manager and prints their details.
     * This method displays all lessons stored in the lesson management system.
     */
    public void displayLessons() {
        List<Lesson> allLessons = lessonManager.getAllLessons();
        if (allLessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        System.out.println("Available Lessons:");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-10s %-15s %-10s %-20s %-7s %-15s\n",
                "Date", "Day", "Coach", "Grade", "Lesson Ref", "Capacity", "Time Slot");
        System.out.println("-------------------------------------------------------------------------------------");
        for (Lesson lesson : allLessons) {
            System.out.printf("%-10s %-10s %-15s %-10d %-20s %-7d %-15s\n",
                    lesson.getDate(),
                    lesson.getDayOfTheWeek(),
                    lesson.getCoach(),
                    lesson.getGradeLevel(),
                    lesson.getLessonRef(),
                    lesson.getCapacity(),
                    lesson.getTimeLabs());
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
