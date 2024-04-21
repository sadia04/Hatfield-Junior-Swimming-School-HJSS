package Manager;

import Model.Lesson;
import Repository.LessonRepository;
import utils.InputValidator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Manages operations related to lessons.
 */
public class LessonManager {
    private static final LessonManager LESSON_MANAGER = new LessonManager();
    private final LessonRepository lessonRepository = LessonRepository.getLessonDB();
    private final InputValidator validator = new InputValidator();

    public LessonManager() {}

    /**
     * Returns the singleton instance of LessonManager.
     * @return the LessonManager instance
     */
    public static LessonManager getLessonManager() {
        return LESSON_MANAGER;
    }

    /**
     * Creates a new lesson with specified details.
     * @param date the date of the lesson
     * @param dayOfWeek the day of the week for the lesson
     * @param coach the name of the coach for the lesson
     * @param gradeLevel the grade level targeted by the lesson
     * @param capacity the maximum number of participants
     * @return a confirmation message indicating lesson creation
     */
    public String createLesson(LocalDate date, DayOfWeek dayOfWeek, String coach, int gradeLevel, int capacity){
        Lesson lesson = new Lesson(date, dayOfWeek, coach, gradeLevel, capacity);
        lessonRepository.add_Lessons(lesson);
        return "LESSON ADDED";
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.getLessonList();
    }

    public List<Lesson> getLessonsByCoachs(String coach_Va) {
        return lessonRepository.getLessonsByCoaches(coach_Va);
    }
    public List<Lesson> getLessonsByGradesLevel(int grade_Level_Va) {
        return lessonRepository.getLessonsByGrades(grade_Level_Va);
    }


    public List<Lesson> getLessonsByDayWeek(DayOfWeek dayOfTheWeek_Va) {
        return lessonRepository.getLessonsByDayWeek(dayOfTheWeek_Va);
    }

    public Lesson getLessonByReference(String lessonReference_Va) {
        return lessonRepository.getLessonByReferences(lessonReference_Va);
    }

    /**
     * Updates a lesson entry with new bookings.
     * @param lesson_Ref_Va the reference ID of the lesson to update
     * @param bookings_Va an array of booking IDs
     * @return a message indicating success or failure
     */
    public String update_Lesson_Entry(String lesson_Ref_Va, String [] bookings_Va) {
        Lesson lessonByRef = lessonRepository.getLessonByReferences(lesson_Ref_Va);
        if (lessonByRef != null) {
            lessonByRef.setBookingsList(bookings_Va);
            lessonByRef.updateStatusBooking();
            return "Lesson updated successfully";
        } else {
            return "Lesson with reference " + lesson_Ref_Va + " not found";
        }
    }

    public Lesson getLessonRandomly(){
        return lessonRepository.getLessonRandomly();
    }
}
