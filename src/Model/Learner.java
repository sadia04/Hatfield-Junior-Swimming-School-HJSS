package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a learner with personal details and lesson interaction records.
 */
public class Learner {
    private String id;
    private String name;
    private String gender;
    private LocalDate DOB;
    private String emergencyNumber;
    private int currentGradeLevel;
    private List<String> bookedLessonsList;
    private List<String> attendedLessonsList;
    private List<String> cancelledLessonsList;
    private List<Booking> bookings;
    private static int count = 0;

    /**
     * Constructs an empty Learner with no initial values.
     */
    public Learner() {
    }

    /**
     * Constructs a Learner with specified personal and educational details.
     *
     * @param name The learner's name.
     * @param gender The learner's gender.
     * @param DOB The learner's date of birth.
     * @param emergencyNumber The emergency contact number for the learner.
     * @param currentGradeLevel The current educational grade level of the learner.
     */
    public Learner(String name, String gender, LocalDate DOB, String emergencyNumber, int currentGradeLevel) {
        this.id = generateID(DOB);
        this.name = name;
        this.gender = gender;
        this.DOB = DOB;
        this.emergencyNumber = emergencyNumber;
        this.currentGradeLevel = currentGradeLevel;
        this.bookedLessonsList = new ArrayList<>();
        this.attendedLessonsList = new ArrayList<>();
        this.cancelledLessonsList = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Generates a unique identifier for the learner based on their date of birth and a static counter.
     *
     * @param DOB The date of birth of the learner.
     * @return A unique identifier string.
     */
    private String generateID(LocalDate DOB) {
        String yyyyMMdd = DOB.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "HJSS-22-t-" + yyyyMMdd + "-" + String.format("%03d", count++);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public int getCurrentGradeLevel() {
        return currentGradeLevel;
    }

    public void setCurrentGradeLevel(int currentGradeLevel) {
        this.currentGradeLevel = currentGradeLevel;
    }

    public List<String> getBookedLessonsList() {
        return bookedLessonsList;
    }

    public void setBookedLessonsList(List<String> bookedLessonsList) {
        this.bookedLessonsList = bookedLessonsList;
    }

    public List<String> getAttendedLessonsList() {
        return attendedLessonsList;
    }

    public void setAttendedLessonsList(List<String> attendedLessonsList) {
        this.attendedLessonsList = attendedLessonsList;
    }

    public List<String> getCancelledLessonsList() {
        return cancelledLessonsList;
    }

    public void setCancelledLessonsList(List<String> cancelledLessonsList) {
        this.cancelledLessonsList = cancelledLessonsList;
    }
    public List<Booking> getBookingsForMonth(LocalDate start, LocalDate end) {
        return bookings.stream()
                .filter(booking -> !booking.getLesson().getDate().isBefore(start) &&
                        !booking.getLesson().getDate().isAfter(end))
                .collect(Collectors.toList());
    }
    @Override
    public String toString() {
        return "Learner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + DOB +
                ", emergencyNumber='" + emergencyNumber + '\'' +
                ", currentGradeLevel=" + currentGradeLevel +
                ", bookedLessons=" + bookedLessonsList +
                ", attendedLessons=" + attendedLessonsList +
                ", cancelledLessons=" + cancelledLessonsList +
                '}';
    }
}
