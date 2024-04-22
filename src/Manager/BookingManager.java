package Manager;

import Repository.BookingRepository;
import utils.InputValidator;
import Model.Booking;
import Model.Learner;
import Model.Lesson;

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

    public BookingRepository getBookingRepository() {
        return bookingDatabase;
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
     * @param newLessonRef the reference of the new lesson
     * @return a success message or an error message
     */
    public String updateBooking(String bookingId, String newLessonRef) throws IllegalArgumentException, IllegalStateException {
        Booking booking = bookingDatabase.getBookingByBooking(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("No booking found with ID: " + bookingId);
        }
        if (!booking.getStatus().equals("Booked")) {
            throw new IllegalStateException("Only booked sessions can be changed.");
        }

        Lesson oldLesson = booking.getLesson();
        Lesson newLesson = lessonManager.getLessonByReference(newLessonRef);
        if (newLesson == null) {
            throw new IllegalArgumentException("No lesson found with reference: " + newLessonRef);
        }
        if (!newLesson.isAvailable()) {
            throw new IllegalStateException("Selected lesson is fully booked.");
        }

        // Release the spot from the old lesson
        oldLesson.removeBookedLearner(booking.getLearnerId());
        // Book the new lesson
        newLesson.addBookedLearner(booking.getLearnerId());
        // Update booking details
        booking.setLesson(newLesson);

        // Update learner's booked lessons list with new lesson reference
        Learner learner = learnerManager.getLearnerById(booking.getLearnerId());
        learner.getBookedLessonsList().remove(oldLesson.getLessonRef());
        learner.getBookedLessonsList().add(newLesson.getLessonRef());

        return "Booking ID " + bookingId + " successfully updated to new lesson: " + newLessonRef;
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
            Objects.requireNonNull(bookingId, "Invalid booking ID: " + bookingId);

            // Retrieve the booking and the associated learner
            Booking booking = getBookingByBkId(bookingId);
            if (booking == null) {
                throw new IllegalArgumentException("Booking not found with ID: " + bookingId);
            }

            Learner learner = getLearnerByLeId(booking.getLearnerId());
            if (learner == null) {
                throw new IllegalArgumentException("Learner not found for booking ID: " + bookingId);
            }

            // Check if the booking is already attended
            if (booking.getStatus().equals(ATTENDED_ST)) {
                return "This lesson has already been attended.";
            }

            // Remove booking ID from booked list and add to attended list
            learner.getBookedLessonsList().remove(bookingId);
            learner.getAttendedLessonsList().add(bookingId);

            // Update the status of the booking
            booking.setStatus(ATTENDED_ST);

            // Check if the lesson grade is exactly one level above the learner's current grade
            if (booking.getLesson().getGradeLevel() == learner.getCurrentGradeLevel() + 1) {
                // Update the learner's grade to the level of the lesson attended
                learner.setCurrentGradeLevel(booking.getLesson().getGradeLevel());
            }

            return "Lesson attended successfully. " + (learner.getCurrentGradeLevel() == booking.getLesson().getGradeLevel() ?
                    "Learner's grade level has been updated to " + learner.getCurrentGradeLevel() : "Learner's grade level remains unchanged.");
        } catch (IllegalArgumentException e) {
            return handleException(e);  // Ensure handleException method can properly log or deal with the exception
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
    public List<Booking> getAllBookings() {
        return bookingDatabase.getAllBookings();
    }

    public List<Booking> getBookingsByLearnerId(String learnerId) {
        return bookingDatabase.getBookingsByLearner(learnerId);
    }
}