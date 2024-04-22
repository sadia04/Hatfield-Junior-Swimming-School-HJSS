
import EntryData.BookingsData;
import EntryData.LearnData;
import EntryData.LessonData;
import Repository.LessonRepository;
import utils.TimesSlot;
import view.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class to start and manage the Hatfield Junior School System.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LessonRepository lessonRepository = new LessonRepository();
    private static final LearnerDisplay learnerDisplay = new LearnerDisplay();
    private static final LessonDisplay lessonDisplay = new LessonDisplay();
    private static final BookingDisplay bookingDisplay = new BookingDisplay();
    private static final ReviewDisplay reviewDisplay = new ReviewDisplay();

    private static final TimesSlot TIMES_SLOT = new TimesSlot();
    public static void main(String[] args) {
        initializeSystem();
        runApplication();
    }

    /**
     * Initializes the system by loading all necessary data.
     */
    private static void initializeSystem() {
        System.out.println("Loading data...");

        // Create instances of data generators
        BookingsData bookingDataGenerator = new BookingsData();
        LearnData learnDataGenerator = new LearnData();
        LessonData lessonDataGenerator = new LessonData();

        // Generate data
        learnDataGenerator.generateEntries(20);
        lessonDataGenerator.generateRandomlyLessonData();
        lessonDataGenerator.updateData();
        bookingDataGenerator.randomlyGenerateBookings();
        learnDataGenerator.updateLearnerEntries();

    }

    /**
     * Runs the main loop of the application, displaying the menu and processing user input.
     */
    private static void runApplication() {
        while (true) {
            System.out.println("""
            Welcome to Hatfield Junior School System
            +-----------------------------------------------------+
            1. Register a new learner
            2. Monthly learner report
            3. Book a swimming lesson
            4. Change/Cancel a booking
            5. Attend a swimming lesson
            6. View all bookings
            7. Monthly Lesson report
            8. Monthly coach report
            9. View all reviews
            10. Search reviews by coach name
            11. Search reviews by Month Report
            12. Exit
            +-----------------------------------------------------+
            Enter your choice:
            """);
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> learnerDisplay.registerLearner();
                case 2 -> learnerDisplay.viewLearners();
                case 3 -> bookingDisplay.bookingLesson();
                case 4 -> bookingDisplay.cancelAndChangeBooking();
                case 5 -> bookingDisplay.attendingLesson();
                case 6 -> bookingDisplay.viewBookings();
                case 7 -> lessonDisplay.displayLessons();
                case 8 -> TIMES_SLOT.viewTimetable();
                case 9 -> reviewDisplay.displayAllReviews();
                case 10 ->reviewDisplay.displayReviewsByCoach();
                case 11 ->reviewDisplay.generateMonthlyCoachReport();

                case 12 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 11.");
            }
        }




    }
}
