package EntryData;

import Manager.LessonManager;
import Model.Booking;
import Model.Lesson;
import Repository.BookingRepository;

import java.util.Random;

/**
 * Generates random booking data and populates the booking repository with generated bookings.
 */
public class BookingsData {
    private static BookingRepository bookingRepository;
    private static Random random;
    private LessonManager manager;

    /**
     * Constructs a BookingsDataGenerator.
     * Initializes the booking repository, a random generator, and the lesson manager.
     */
    public BookingsData() {
        bookingRepository = BookingRepository.getBookingDB();
        random = new Random();
        manager = new LessonManager();
    }

    /**
     * Generates a specified number of random bookings and adds them to the booking repository.
     */
    public void randomlyGenerateBookings() {
        for (int i = 0; i < 60; i++) {
            Booking randomBooking = randomBooking();
            bookingRepository.addBooking(randomBooking);
        }
    }

    /**
     * Creates a random booking using available lessons and random statuses.
     * @return A randomly generated Booking object.
     */
    private Booking randomBooking() {
        String[] statuses = {"Booked", "Cancelled", "Attended"};
        int index = random.nextInt(statuses.length);
        String randomStatus = statuses[index];

        Booking booking = new Booking();
        booking.setStatus(randomStatus);
        booking.setBookingId(booking.generateID());

        Lesson lesson = manager.getLessonRandomly();
        booking.setLesson(lesson);

        String[] bookings = lesson.getBookingsList();
        String learnerId = bookings[random.nextInt(bookings.length)];
        booking.setLearnerId(learnerId);

        return booking;
    }
}
