package Manager;

import Model.Booking;
import Model.Learner;
import Model.Lesson;
import Repository.BookingRepository;
import utils.InputValidator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Manages booking operations for learners and lessons.
 */
public class BookingManager {
    private final BookingRepository bookingDatabase;
    private final LessonManager lessonManager;
    private final LearnerManager learnerManager;
    private final InputValidator validator = new InputValidator();

    private static final String BOOKED_ST = "Booked";
    private static final String CANCELLED_ST = "Cancelled";
    private static final String ATTENDED_ST = "Attended";

    public BookingManager() {
        this.bookingDatabase = BookingRepository.getBookingDB();
        this.lessonManager = new LessonManager();
        this.learnerManager = LearnerManager.getLearnerManager();
    }

    /**
     * Creates a new booking for a lesson.
     * @param learnerID the ID of the learner
     * @param lessonRef the reference of the lesson
     * @return a success message with booking code or an error message
     */
    public String createBookLesson(String learnerID, String lessonRef) {
        try {
            validator.validateInputs(learnerID, lessonRef);

            Lesson lesson = lessonManager.getLessonByReference(lessonRef);
            validator.validateLesson(lesson);

            Learner learner = learnerManager.getLearnerById(learnerID);
            validator.validateLearner(learner, lessonRef, lesson);

            lesson.addBookedLearner(learnerID);
            Booking booking = new Booking(learnerID, lesson);
            bookingDatabase.addBooking(booking);
            learner.getBookedLessonsList().add(booking.getBookingId());

            return "Booking Successful!!! Take your booking code: " + booking.getBookingId();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return handleException(e);
        }
    }

    /**
     * Updates an existing booking with a new lesson.
     * @param bookingId the ID of the booking to update
     * @param lessonRef the reference of the new lesson
     * @return a success message or an error message
     */
    public String updateBooking(String bookingId, String lessonRef) {
        try {
            validator.validateInputs(bookingId, lessonRef);

            Booking booking = getBookingByBkId(bookingId);
            validator.validateBooking(booking, BOOKED_ST);

            Lesson oldLesson = booking.getLesson();
            oldLesson.removeBookedLearner(booking.getLearnerId());

            Learner learner = getLearnerByLeId(booking.getLearnerId());
            learner.getBookedLessonsList().remove(bookingId);

            Lesson newLesson = lessonManager.getLessonByReference(lessonRef);
            validator.validateNewLesson(newLesson);

            updateBookingDetails(booking, newLesson, learner);

            return "Booking updated successfully";
        } catch (IllegalArgumentException | IllegalStateException e) {
            return handleException(e);
        }
    }

    /**
     * Cancels an existing booking.
     * @param bookingId the ID of the booking to cancel
     * @return a success message or an error message
     */
    public String cancelBooking(String bookingId) {
        try {
            Objects.requireNonNull(bookingId, "Booking ID cannot be null or empty");

            Booking booking = getBookingByBkId(bookingId);
            Lesson lesson = booking.getLesson();
            lesson.removeBookedLearner(booking.getLearnerId());

            Learner learner = getLearnerByLeId(booking.getLearnerId());
            learner.getBookedLessonsList().remove(bookingId);
            learner.getCancelledLessonsList().add(bookingId);

            booking.setStatus(CANCELLED_ST);

            return "Booking cancelled successfully";
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    /**
     * Marks a booking as attended by the learner.
     * @param bookingId the ID of the booking to mark as attended
     * @return a success message or an error message
     */
    public String attendingLesson(String bookingId) {
        try {
            Objects.requireNonNull(bookingId, "Invalid booking ID");

            Booking booking = getBookingByBkId(bookingId);
            Learner learner = getLearnerByLeId(booking.getLearnerId());

            learner.getAttendedLessonsList().add(bookingId);
            booking.setStatus(ATTENDED_ST);

            return "Lesson attended successfully";
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    private String handleException(Exception e) {
        return "Failed: " + e.getMessage();
    }

    // Simplified accessors for database operations
    public Booking getBookingByBkId(String bookingId) {
        return Optional.ofNullable(bookingDatabase.getBookingByBooking(bookingId))
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    private Learner getLearnerByLeId(String learnerId) {
        return Optional.ofNullable(learnerManager.getLearnerById(learnerId))
                .orElseThrow(() -> new IllegalArgumentException("Learner not found"));
    }
    private void updateBookingDetails(Booking booking, Lesson newLesson, Learner learner) {
        booking.setLesson(newLesson);
        newLesson.addBookedLearner(booking.getLearnerId());
        booking.setStatus(BOOKED_ST);
    }
    // Additional getters for all bookings or bookings by a specific learner
    public List<Booking> getAllBookings() {
        return bookingDatabase.getAllBookings();
    }

    public List<Booking> getBookingsByLearnerId(String learnerId) {
        return bookingDatabase.getBookingsByLearner(learnerId);
    }
}