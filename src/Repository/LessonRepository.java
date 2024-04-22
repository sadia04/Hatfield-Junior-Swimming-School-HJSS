package Repository;

import Model.Lesson;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages storage and retrieval operations for Lesson objects in a list-based repository.
 */
public class LessonRepository {
    private final List<Lesson> lessonList;
    private static LessonRepository lessonRepository;

    /**
     * Returns a singleton instance of LessonRepository, ensuring that only one instance is used throughout the application.
     * @return the singleton instance of the LessonRepository
     */
    public static LessonRepository getLessonDB() {
        if (lessonRepository == null) {
            lessonRepository = new LessonRepository();
        }
        return lessonRepository;
    }

    /**
     * Constructs a new LessonRepository with an empty list of lessons.
     */
    public LessonRepository() {
        lessonList = new ArrayList<>();
    }

    /**
     * Adds a lesson to the repository.
     * @param lesson the Lesson object to be added to the list
     */
    public void add_Lessons(Lesson lesson) {
        lessonList.add(lesson);
    }

    /**
     * Retrieves all lessons currently stored in the repository.
     * @return a list of all Lesson objects
     */
    public List<Lesson> getLessonList() {
        return lessonList;
    }

    /**
     * Retrieves all lessons that match a specific grade level.
     * @param gradeLevel the grade level to filter lessons by
     * @return a list of Lesson objects that match the specified grade level
     */
    public List<Lesson> getLessonsByGrades(int gradeLevel) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            if (lesson.getGradeLevel() == gradeLevel) {
                filteredLessons.add(lesson);
            }
        }
        return filteredLessons;
    }

    /**
     * Retrieves all lessons taught by a specified coach.
     * @param coach the name of the coach to filter lessons by
     * @return a list of Lesson objects that are taught by the specified coach
     */
    public List<Lesson> getLessonsByCoaches(String coach) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            if (lesson.getCoach().equals(coach)) {
                filteredLessons.add(lesson);
            }
        }
        return filteredLessons;
    }

    /**
     * Retrieves a Lesson by its unique reference.
     * @param lessonReference the unique reference of the lesson to retrieve
     * @return the Lesson object if found, null otherwise
     */
    public Lesson getLessonByReferences(String lessonReference) {
        for (Lesson lesson : lessonList) {
            if (lesson.getLessonRef().equals(lessonReference)) {
                return lesson;
            }
        }
        return null;
    }

    /**
     * Retrieves all lessons that occur on a specific day of the week.
     * @param dayOfWeek the day of the week to filter lessons by
     * @return a list of Lesson objects that occur on the specified day
     */
    public List<Lesson> getLessonsByDayWeek(DayOfWeek dayOfWeek) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            if (lesson.getDayOfTheWeek().equals(dayOfWeek)) {
                filteredLessons.add(lesson);
            }
        }
        return filteredLessons;
    }

    /**
     * Returns the total number of lessons currently stored in the repository.
     * @return the total count of lessons
     */
    public int getLessonCount() {
        return lessonList.size();
    }



    /**
     * Randomly selects a Lesson from the repository.
     * @return the randomly selected Lesson object
     */
    public Lesson getLessonRandomly() {
        int index = (int) (Math.random() * lessonList.size());
        return lessonList.get(index);
    }
}
