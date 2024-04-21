package utils;

import Manager.LessonManager;
import Model.Lesson;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class TimeTableDisplay {
    private LessonManager lessonManager;
    private LocalDate localDate;
    private LocalDate localDate1;

    public TimeTableDisplay() {
        this.lessonManager = new LessonManager();
        this.localDate = LocalDate.now();
        this.localDate1 = localDate.plusWeeks(2);
    }

    public void ViewTimetable() {
        try {
            consoleTimeHeading();
            List<Lesson> lessons_Va = lessonManager.getAllLessons();
            consoleTimeBody(lessons_Va);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable: " + e.getMessage());
        }
    }

    public void viewTimetableByGradeLevel(int gradeLevel) {
        try {
            System.out.println("Grade Level: " + gradeLevel);
            consoleTimeHeading();

            List<Lesson> lessons_Va = lessonManager.getLessonsByGradesLevel(gradeLevel);
            if (lessons_Va.isEmpty()) System.out.println("No lessons found for grade level: " + gradeLevel);

            consoleTimeBody(lessons_Va);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable by grade level");
        }
    }

    public void viewTimetableByCoachName(String s) {
        try {
            System.out.println("Coach Name: " + s);
            consoleTimeHeading();

            List<Lesson> lessons_va = lessonManager.getLessonsByCoachs(s);
            if (lessons_va.isEmpty()) System.out.println("No lessons found for coach name: " + s);

            consoleTimeBody(lessons_va);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable by coach name");
        }
    }

    public void viewTimetableByDayOfWeek(DayOfWeek ofWeek) {


        try {
            System.out.println("Day of the Week: " + ofWeek);
            consoleTimeHeading();

            List<Lesson> lessons = lessonManager.getLessonsByDayWeek(ofWeek);
            if (lessons.isEmpty()) System.out.println("No lessons found for this Day: " + ofWeek);

            consoleTimeBody(lessons);
        } catch (Exception e) {
            System.err.println("Error occurred while displaying timetable by day of the week");
        }
    }

    public void consoleTimeHeading() {
        System.out.println("Timetable for from: " + localDate + " " + "to" + " " + localDate1);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("| Date       | Day         | Coach            | Grade Level | Lesson Ref           | Capacity | Status       | Time Slot           |");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }

    public void consoleTimeBody(List<Lesson> lessons) {
        try {
            for (Lesson lesson : lessons) {
                if (lesson.getDate().isAfter(localDate) && lesson.getDate().isBefore(localDate1))
                    System.out.printf("| %-10s | %-11s | %-16s | %-11d | %-20s | %-8d | %-12s | %-19s |%n",
                            lesson.getDate(), lesson.getDayOfTheWeek(), lesson.getCoach(), lesson.getGradeLevel(),
                            lesson.getLessonRef(), lesson.getCapacity(), lesson.getStatusBooking(), lesson.getTimeLabs());
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        } catch (Exception e) {
            System.err.println("Error occurred while printing timetable body.");
        }
    }
}