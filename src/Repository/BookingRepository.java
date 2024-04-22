package Repository;

import Model.Booking;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles storage and retrieval of Booking objects.
 */
public class BookingRepository {
    private final List<Booking> bookingList;
    private static BookingRepository bookingRepository;

    /**
     * Singleton pattern to return a single instance of BookingRepository.
     * @return the singleton instance of BookingRepository
     */
    public static BookingRepository getBookingDB() {
        if (bookingRepository == null) {
            bookingRepository = new BookingRepository();
        }
        return bookingRepository;
    }

    /**
     * Constructs a BookingRepository with an empty list of bookings.
     */
    public BookingRepository() {
        bookingList = new ArrayList<>();
    }
    /**
     * Deletes a booking from the repository.
     * @param bookingId the ID of the booking to delete
     * @return true if the booking was deleted, false if it was not found
     */
    public boolean deleteBooking(String bookingId) {
        return bookingList.removeIf(booking -> booking.getBookingId().equals(bookingId));
    }

    /**
     * Adds a booking to the repository.
     * @param booking the Booking object to add to the repository
     */
    public void addBooking(Booking booking) {
        bookingList.add(booking);
    }

    /**
     * Returns a list of all bookings in the repository.
     * @return a list of all bookings
     */
    public List<Booking> getBookingList() {
        return bookingList;
    }

    /**
     * Returns the count of bookings in the repository.
     * @return the number of bookings in the repository
     */
    public int getBookingCount() {
        return bookingList.size();
    }

    /**
     * Returns a list of bookings for a specific learner.
     * @param learnerId the ID of the learner whose bookings are to be retrieved
     * @return a list of bookings associated with the specified learner
     */
    public List<Booking> getBookingsByLearner(String learnerId) {
        List<Booking> filteredBookings = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (booking.getLearnerId().equals(learnerId)) {
                filteredBookings.add(booking);
            }
        }
        return filteredBookings;
    }


    /**
     * Retrieves a booking by its ID.
     * @param bookingId the ID of the booking to retrieve
     * @return the Booking object if found, null otherwise
     */
    public Booking getBookingByBooking(String bookingId) {
        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Returns a list of all bookings currently held in the repository.
     * @return a list containing all bookings
     */
    public List<Booking> getAllBookings(){
        return bookingList;
    }
}
