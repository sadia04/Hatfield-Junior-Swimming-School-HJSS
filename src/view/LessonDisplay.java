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
        for (Lesson lesson : allLessons) {
            System.out.println(lesson);
        }
    }
}
