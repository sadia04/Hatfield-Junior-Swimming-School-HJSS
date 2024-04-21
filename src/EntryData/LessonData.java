package EntryData;

import Manager.BookingManager;
import Manager.LearnerManager;
import Manager.LessonManager;
import Model.Lesson;
import appManager.SwimSchoolManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class generates and manages lesson data for a swimming school application.
 * It schedules lessons on specific days of the week and assigns learners to those lessons randomly.
 */
public class LessonData {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate currentDate;
    private LearnData dataGenerator;
    private SwimSchoolManager schoolManager;
    private LessonManager lessonManager;
    private LearnerManager learnerManager;
    private BookingManager bookingManager;

    /**
     * Constructs a LessonData generator with predefined date ranges and initializes managers.
     */
    public LessonData() {
        currentDate = LocalDate.now();
        startDate = currentDate.minusWeeks(4);
        endDate = currentDate.plusWeeks(2);
        dataGenerator = new LearnData();
        schoolManager = SwimSchoolManager.getSwimSchoolManager();
        lessonManager = LessonManager.getLessonManager();
        learnerManager = LearnerManager.getLearnerManager();
        bookingManager = new BookingManager();
    }

    /**
     * Generates lesson schedules between two dates, targeting specific weekdays and managing capacity and coach assignments.
     */
    public void generateRandomlyLessonData() {
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY ||
                    dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) {
                int sessionCount = (dayOfWeek == DayOfWeek.SATURDAY) ? 2 : 3;

                for (int i = 0; i < sessionCount; i++) {
                    lessonManager.createLesson(date, dayOfWeek, schoolManager.assignCoach(),
                            schoolManager.assignGradeLevel(), schoolManager.setLessonCapacity());
                }
            }
            date = date.plusDays(1);
        }
    }

    /**
     * Updates the lesson entries by randomly assigning learners to each lesson.
     */
    public void updateData() {
        List<Lesson> allLessons = lessonManager.getAllLessons();

        for (Lesson lesson : allLessons) {
            List<String> learnerIds = new ArrayList<>();
            int learnerCount = getRandomNumbers(1, 4);

            while (learnerIds.size() < learnerCount) {
                String randomLearnerId = learnerManager.getRandomLearnerID();
                if (!learnerIds.contains(randomLearnerId)) {
                    learnerIds.add(randomLearnerId);
                }
            }

            String[] learnersArray = learnerIds.toArray(new String[0]);
            lessonManager.update_Lesson_Entry(lesson.getLessonRef(), learnersArray);
        }
    }

    /**
     * Generates a random number within a specified range.
     * @param min the minimum value inclusive
     * @param max the maximum value inclusive
     * @return a random number between min and max
     */
    private int getRandomNumbers(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
