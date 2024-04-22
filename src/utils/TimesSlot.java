package utils;

import Manager.LessonManager;
import Model.Lesson;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Handles the display of lesson timetables for a swimming school management system.
 * It provides methods to view timetables by date, grade level, coach, or day of the week.
 */
public class TimesSlot {
    private LessonManager lessonManager;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs a TimeTableDisplay instance.
     * Initializes the lesson manager and sets the timetable display period from today to two weeks later.
     */
    public TimesSlot() {
        this.lessonManager = new LessonManager();
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusWeeks(2);
    }

    /**
     * Displays the entire timetable of lessons scheduled between the initialized start and end dates.
     */
    public void viewTimetable() {
        try {
            consoleTimeHeading();
            List<Lesson> lessons = lessonManager.getAllLessons();
            consoleTimeBody(lessons);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable: " + e.getMessage());
        }
    }

    /**
     * Displays the timetable filtered by a specific grade level.
     * @param gradeLevel The grade level to filter the lessons by.
     */
    public void viewTimetableByGradeLevel(int gradeLevel) {
        try {
            System.out.println("Grade Level: " + gradeLevel);
            consoleTimeHeading();

            List<Lesson> lessons = lessonManager.getLessonsByGradesLevel(gradeLevel);
            if (lessons.isEmpty()) System.out.println("No lessons found for grade level: " + gradeLevel);

            consoleTimeBody(lessons);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable by grade level");
        }
    }

    /**
     * Displays the timetable filtered by a specific coach's name.
     * @param coachName The name of the coach to filter the lessons by.
     */
    public void viewTimetableByCoachName(String coachName) {
        try {
            System.out.println("Coach Name: " + coachName);
            consoleTimeHeading();

            List<Lesson> lessons = lessonManager.getLessonsByCoachs(coachName);
            if (lessons.isEmpty()) System.out.println("No lessons found for coach name: " + coachName);

            consoleTimeBody(lessons);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable by coach name");
        }
    }

    /**
     * Displays the timetable filtered by a specific day of the week.
     * @param dayOfWeek The day of the week to filter the lessons by.
     */
    public void viewTimetableByDayOfWeek(DayOfWeek dayOfWeek) {
        try {
            System.out.println("Day of the Week: " + dayOfWeek);
            consoleTimeHeading();

            List<Lesson> lessons = lessonManager.getLessonsByDayWeek(dayOfWeek);
            if (lessons.isEmpty()) System.out.println("No lessons found for this day: " + dayOfWeek);

            consoleTimeBody(lessons);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable by day of the week");
        }
    }

    /**
     * Prints the heading for the timetable display, showing the date range.
     */
    private void consoleTimeHeading() {
        System.out.println("\nTimetable from " + startDate + " to " + endDate);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.println("| Date       | Day         | Coach            | Grade | Lesson Ref         | Capacity | Status     | Time Slot |");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
    }

    /**
     * Prints the body of the timetable, showing detailed lesson information.
     * @param lessons A list of lessons to display within the given date range.
     */
    private void consoleTimeBody(List<Lesson> lessons) {
        try {
            boolean hasLessons = false;
            for (Lesson lesson : lessons) {
                if (lesson.getDate().isAfter(startDate) && lesson.getDate().isBefore(endDate)) {
                    hasLessons = true;
                    System.out.printf("| %-10s | %-11s | %-16s | %-5d | %-18s | %-8d | %-10s | %-10s |%n",
                            lesson.getDate(), lesson.getDayOfTheWeek().toString(),
                            lesson.getCoach(), lesson.getGradeLevel(),
                            lesson.getLessonRef(), lesson.getCapacity(),
                            lesson.getStatusBooking(), lesson.getTimeLabs());
                }
            }
            if (!hasLessons) {
                System.out.println("|                                     No lessons scheduled for this period                                     |");
            }
            System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        } catch (Exception e) {
            System.err.println("Error occurred while printing timetable body: " + e.getMessage());
        }
    }

}
