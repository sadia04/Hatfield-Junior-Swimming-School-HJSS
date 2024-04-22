package view;

import Manager.BookingManager;
import Manager.LessonManager;
import Manager.ReviewManager;
import Manager.SwimSchoolManager;
import Model.Booking;
import Model.Lesson;
import utils.InputValidator;
import utils.TimesSlot;

import java.time.DayOfWeek;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions for viewing and managing bookings within the swim school system.
 */
public class BookingDisplay {
    private final Scanner scanner = new Scanner(System.in);
    private final SwimSchoolManager swimSchoolManager = SwimSchoolManager.getSwimSchoolManager();
    private final BookingManager bookingManager = new BookingManager();
    private final TimesSlot timesSlot = new TimesSlot();
    private final InputValidator inputValidator = new InputValidator();
    private final LessonManager lessonManager = new LessonManager();
    private final ReviewManager reviewManager = new ReviewManager();

    /**
     * Displays all current bookings.
     */
    public void viewBookings() {
        List<Booking> allBookings = bookingManager.getAllBookings();
        for (Booking booking : allBookings) {
            System.out.println(booking.toString());
        }
    }

    /**
     * Guides the user through the process of booking a lesson.
     */
    public void bookingLesson() {
        System.out.println("Welcome to the Booking System!!!");
        System.out.println("To book a Lesson, you need to view available lessons in the timetable.");
        System.out.println();

        viewTimeTable();

        System.out.println("Please select a lesson by entering the lesson reference from the timetable.");

        String learnerId = getValidateLearnerId();
        String lessonRef = getValidateLessonReference();
        String result = bookingManager.createBookLesson(learnerId, lessonRef.trim());
        System.out.println(result);
        System.out.println();
    }

    /**
     * Displays the timetable with different viewing options.
     */
    private void viewTimeTable() {
        System.out.println("How do you want to view the timetable?:");
        System.out.println("1. By coach name");
        System.out.println("2. By day of the week");
        System.out.println("3. By grade level");
        System.out.println("Enter your Choice:");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewByCoachName();
                    break;
                case 2:
                    viewByDayOfWeek();
                    break;
                case 3:
                    viewByGradeLevel();
                    break;
                default:
                    System.out.println("Invalid input! Please enter a valid choice (1, 2, or 3).");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            scanner.nextLine();
        }
    }

    private void viewByCoachName() {
        try {
            System.out.println("Enter coach name:");
            String coachName = scanner.nextLine();
            CheckStringValidate(coachName);
            timesSlot.viewTimetableByCoachName(coachName);
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    private void viewByDayOfWeek() {
        System.out.println("Enter day of the week (Monday, Wednesday, Friday, or Saturday):");
        try {
            String day = scanner.nextLine().toUpperCase();
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
            timesSlot.viewTimetableByDayOfWeek(dayOfWeek);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid day of the week! Please enter a valid day.");
        }
    }

    private void viewByGradeLevel() {
        System.out.println("Enter grade level (1 to 5):");
        try {
            int gradeLevel = scanner.nextInt();
            scanner.nextLine();
            inputValidator.validateGradeLevel(gradeLevel);
            timesSlot.viewTimetableByGradeLevel(gradeLevel);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Validates the string for null or empty values.
     * @param string the string to validate
     * @return true if the string is valid, false otherwise
     */
    public boolean CheckStringValidate(String string) {
        return string != null && !string.trim().isEmpty();
    }

    /**
     * Retrieves and validates a learner ID from user input.
     * @return a validated learner ID
     */
    private String getValidateLearnerId() {
        while (true) {
            try {
                System.out.println("Enter your Username:");
                String learnerId = scanner.nextLine().trim();
                CheckStringValidate(learnerId);
                return learnerId;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Retrieves and validates a lesson reference from user input.
     * @return a validated lesson reference
     */
    private String getValidateLessonReference() {
        while (true) {
            try {
                System.out.println("Enter your preferred lesson's lessonRef:");
                String lessonRef = scanner.nextLine().trim();
                CheckStringValidate(lessonRef);
                return lessonRef;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Provides options to either change or cancel a booking.
     */
    public void cancelAndChangeBooking() {
        System.out.println("|Would you like to cancel or change your booking?");
        System.out.println("|Please have your BookingID and New LessonRef ready for changes.");
        System.out.println("|Enter (1) to change booking or (2) to cancel booking.");
        System.out.println();

        try {
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    changeBookingNow();
                    break;
                case 2:
                    cancelBookingNow();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void changeBookingNow() {
        try {
            System.out.println("Enter your BookingID:");
            String bookingId = scanner.nextLine();
            scanner.nextLine();

            viewTimeTable();

            System.out.println("Enter your New LessonRef:");
            String newLessonRef = scanner.nextLine();
            scanner.nextLine();

            String result = bookingManager.updateBooking(bookingId, newLessonRef);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    private void cancelBookingNow() {
        try {
            System.out.println("Enter your BookingID:");
            String bookingId = scanner.nextLine();

            String result = bookingManager.cancelBooking(bookingId);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     * Guides the user through attending a lesson and optionally writing a review afterwards.
     */
    public void attendingLesson() {
        try {
            System.out.println("Welcome to attend your swimming lesson!!!");
            System.out.println("Enter your BookingID:");
            String bookingId = scanner.nextLine();

            String result = bookingManager.attendingLesson(bookingId);
            if (result.contains("Error")) {
                System.out.println(result);
                return;
            }

            System.out.println(result);
            System.out.println();

            Lesson lesson = bookingManager.getBookingByBkId(bookingId).getLesson();

            writeReviewNow(lesson);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to write a review for a lesson they have attended.
     * @param lesson the lesson for which the review is written
     */
    private void writeReviewNow(Lesson lesson) {
        try {
            System.out.println("|Congratulation on Successful completion of your swimming lesson 👏");
            System.out.println("|Kindly write your review for the lesson:");

            System.out.println("Enter a short review for the lesson:");
            String review = scanner.nextLine();

            int rating;
            do {
                System.out.println("|Enter your rating for the lesson (1-5):");
                System.out.println("|1: Very dissatisfied, 2: Dissatisfied, 3: Ok, 4: Satisfied, 5: Very Satisfied");
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    scanner.next();
                }
                rating = scanner.nextInt();
                scanner.nextLine();
            } while (rating < 1 || rating > 5);

            String result = reviewManager.addReview(review, rating, lesson);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
