package Model;

import Manager.SwimSchoolManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a lesson scheduled within the swim school.
 */
public class Lesson {
    private LocalDate date;
    private DayOfWeek dayOfTheWeek;
    private String coach;
    private int gradeLevel;
    private String lessonRef;
    private int capacity;
    private String[] bookingsList;
    private String statusBooking;
    private LocalTime startingTime;
    private String timeLabs;
    private static int lessonCount = 1;

    /**
     * Constructs a Lesson with specified details and initializes the lesson reference and time slot.
     * @param date The date of the lesson.
     * @param dayOfTheWeek The day of the week the lesson occurs.
     * @param coach The name of the coach for the lesson.
     * @param gradeLevel The grade level intended for the lesson.
     * @param capacity The maximum number of learners that can attend the lesson.
     */
    public Lesson(LocalDate date, DayOfWeek dayOfTheWeek, String coach, int gradeLevel, int capacity) {
        this.date = date;
        this.dayOfTheWeek = dayOfTheWeek;
        this.coach = coach;
        this.gradeLevel = gradeLevel;
        generatingLessonRef();
        this.capacity = capacity;
        this.bookingsList = new String[capacity];
        this.statusBooking = "Available";
        this.startingTime = SwimSchoolManager.getSwimSchoolManager().setStartTime(dayOfTheWeek);
        this.timeLabs = createTimeSlot(startingTime);
    }

    /**
     * Default constructor for creating an empty Lesson instance.
     */
    public Lesson() {}

    /**
     * Generates a unique lesson reference based on the lesson's date and a static counter.
     */
    private void generatingLessonRef() {
        this.lessonRef = String.format("%s-%02d-%02d-%d-SLT%d",
                dayOfTheWeek.toString().toLowerCase().substring(0, 3), date.getDayOfMonth(),
                date.getMonthValue(), date.getYear(), lessonCount++);
    }

    /**
     * Creates a time slot string from the starting time.
     * @param startTime The starting time of the lesson.
     * @return A formatted string representing the time slot of the lesson.
     */
    private String createTimeSlot(LocalTime startTime) {
        LocalTime endTime = startTime.plusHours(1);
        return startTime + " - " + endTime;
    }

    /**
     * Checks if the lesson is available for more bookings.
     * @return true if available, false if fully booked.
     */
    public Boolean isAvailable() {
        return this.statusBooking.equals("Available");
    }

    /**
     * Updates the booking status of the lesson based on current bookings and capacity.
     */
    public void updateStatusBooking() {
        long count = Arrays.stream(bookingsList)
                .filter(Objects::nonNull)
                .count();
        statusBooking = count >= this.capacity ? "Fully Booked" : "Available";
    }

    /**
     * Adds a booking to the lesson if there is available capacity.
     * @param bookingId The ID of the booking to add.
     */
    public void addBookedLearner(String bookingId) {
        for (int i = 0; i < bookingsList.length; i++) {
            if (bookingsList[i] == null) {
                bookingsList[i] = bookingId;
                updateStatusBooking();
                return;
            }
        }
    }

    /**
     * Removes a booking from the lesson.
     * @param bookingId The ID of the booking to remove.
     */
    public void removeBookedLearner(String bookingId) {
        for (int i = 0; i < bookingsList.length; i++) {
            if (bookingsList[i] != null && bookingsList[i].equals(bookingId)) {
                bookingsList[i] = null;
                updateStatusBooking();
                return;
            }
        }
    }

    // Getters and Setters
    public LocalDate getDate() { return date; }
    public void setBookingsList(String[] bookingsList) {
        this.bookingsList = bookingsList;
    }
    public String[] getBookingsList() {
        return bookingsList;
    }
    public void setDate(LocalDate date) { this.date = date; }
    public DayOfWeek getDayOfTheWeek() { return dayOfTheWeek; }
    public void setDayOfTheWeek(DayOfWeek dayOfTheWeek) { this.dayOfTheWeek = dayOfTheWeek; }
    public String getCoach() { return coach; }
    public void setCoach(String coach) { this.coach = coach; }
    public int getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(int gradeLevel) { this.gradeLevel = gradeLevel; }
    public String getLessonRef() { return lessonRef; }
    public void setLessonRef(String lessonRef) { this.lessonRef = lessonRef; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getStatusBooking() { return statusBooking; }
    public void setStatusBooking(String statusBooking) { this.statusBooking = statusBooking; }
    public LocalTime getStartingTime() { return startingTime; }
    public void setStartingTime(LocalTime startingTime) { this.startingTime = startingTime; }
    public String getTimeLabs() { return timeLabs; }
    public void setTimeLabs(String timeLabs) { this.timeLabs = timeLabs; }

    @Override
    public String toString() {
        return "Lesson{" +
                "date=" + date +
                ", dayOfTheWeek=" + dayOfTheWeek +
                ", coach='" + coach + '\'' +
                ", gradeLevel=" + gradeLevel +
                ", lessonRef='" + lessonRef + '\'' +
                ", capacity=" + capacity +
                ", bookings=" + Arrays.toString(bookingsList) +
                ", status='" + statusBooking + '\'' +
                ", startTime=" + startingTime +
                ", timeSlot='" + timeLabs + '\'' +
                '}';
    }
}
