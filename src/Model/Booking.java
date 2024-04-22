package Model;

import java.time.LocalDate;

/**
 * Represents a booking for a lesson by a learner.
 */
public class Booking {
    private String bookingId;
    private Lesson lesson;
    private String learnerId;
    private String status;
    private static int count = 1;
    private Learner learner;

    /**
     * Constructs an empty Booking.
     */
    public Booking() {
    }

    /**
     * Constructs a Booking with a specified learner and lesson.
     * Automatically generates a unique booking ID.
     *
     * @param learnerId the ID of the learner making the booking
     * @param lesson the lesson being booked
     */
    public Booking(String learnerId, Lesson lesson) {
        this.bookingId = generateID();
        this.lesson = lesson;
        this.learnerId = learnerId;
        this.status = "Booked";
    }

    /**
     * Gets the booking ID.
     * @return the booking ID
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID.
     * @param bookingId the new booking ID
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the lesson associated with this booking.
     * @return the lesson
     */
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * Sets the lesson for this booking.
     * @param lesson the new lesson
     */
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Gets the learner ID associated with this booking.
     * @return the learner ID
     */
    public String getLearnerId() {
        return learnerId;
    }
    public LocalDate getDate() {
        return lesson.getDate();
    }

    /**
     * Sets the learner ID for this booking.
     * @param learnerId the new learner ID
     */
    public void setLearnerId(String learnerId) {
        this.learnerId = learnerId;
    }

    /**
     * Gets the current status of this booking.
     * @return the booking status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this booking.
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Generates a unique booking ID.
     * @return a unique booking ID string
     */
    public static String generateID() {
        return String.format("BookingS-24-%03d", count++);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", lesson=" + lesson +
                ", learnerId='" + learnerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
