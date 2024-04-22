package utils;

import java.time.LocalDate;
import java.util.Objects;

import Model.Booking;
import Model.Learner;
import Model.Lesson;
public class InputValidator {

    // Functional interface for string validation
    @FunctionalInterface
    public interface IValidateString {
        void validate(String value) throws IllegalArgumentException;
    }

    // Functional interface for integer validation
    @FunctionalInterface
    public interface IValidateInt {
        void validate(int value) throws IllegalArgumentException;
    }

    // Functional interface for LocalDate validation
    @FunctionalInterface
    public interface IValidateLocalDate {
        void validate(LocalDate date) throws IllegalArgumentException;
    }
    /**
     * Validates that an object is not null.
     * @param object the object to validate
     * @param message the error message to throw if the object is null
     */
    public void validateNotNull(Object object, String message) {
        Objects.requireNonNull(object, message);
    }
    /**
     * Validates input parameters are not null.
     * @param inputs array of input objects to be validated
     */
    public void validateInputs(Object... inputs) {
        for (Object input : inputs) {
            Objects.requireNonNull(input, "Invalid input: one of the inputs is null");
        }
    }
    /**
     * Validates that a learner exists.
     * @param learner the Learner to validate
     */
    public void validateLearnerExists(Learner learner) {
        if (learner == null) {
            throw new IllegalArgumentException("Learner not found");
        }
    }
    /**
     * Validates the specified lesson for availability.
     * @param lesson the lesson to validate
     */
    public void validateLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson not found");
        }
        if (!lesson.isAvailable()) {
            throw new IllegalStateException("Lesson is fully booked");
        }
    }

    /**
     * Validates the specified learner for the given lesson.
     * @param learner the learner to validate
     * @param lessonRef the lesson reference for further validation
     * @param lesson the lesson object for detailed validation
     */
    public void validateLearner(Learner learner, String lessonRef, Lesson lesson) {
        if (learner == null) {
            throw new IllegalArgumentException("Learner not found");
        }
        if (learner.getBookedLessonsList().contains(lessonRef)) {
            throw new IllegalStateException("Learner has already booked this lesson");
        }
        int currentGrade = learner.getCurrentGradeLevel();
        if (lesson.getGradeLevel() > currentGrade + 1 || lesson.getGradeLevel() < currentGrade) {
            throw new IllegalStateException("Learner is not eligible to book this lesson");
        }
    }

    /**
     * Validates the status of a booking before updates.
     * @param booking the booking to validate
     * @param expectedStatus the expected status for the booking
     */
    public void validateBooking(Booking booking, String expectedStatus) {
        if (!booking.getStatus().equals(expectedStatus)) {
            throw new IllegalStateException("Booking status is: " + booking.getStatus() + " and cannot be changed");
        }
    }

    /**
     * Validates if a new lesson is available for booking.
     * @param lesson the lesson to check for availability
     */
    public void validateNewLesson(Lesson lesson) {
        if (!lesson.isAvailable()) {
            throw new IllegalStateException("Lesson is fully booked");
        }
    }
    public void validateUserName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid name.");
        }
    }

    public void validateGender(String gender) {
        if (gender == null || !gender.matches("^(?i)(male|female|others)$")) {
            throw new IllegalArgumentException("Gender must be 'Male', 'Female', or 'Others'.");
        }
    }



    public void validateDOB(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        LocalDate now = LocalDate.now();
        LocalDate justOverFiveYearsAgo = now.minusYears(5).plusDays(1);
        LocalDate elevenYearsAgo = now.minusYears(11);

        // Date of birth must be strictly between eleven years ago and just over five years ago
        if (dateOfBirth.isBefore(elevenYearsAgo) || dateOfBirth.isEqual(elevenYearsAgo) || dateOfBirth.isAfter(justOverFiveYearsAgo)) {
            throw new IllegalArgumentException("Date of birth must be strictly between 11 and just over 5 years ago.");
        }
    }



    public void validateEmergencyContact(String emergencyContact) {
        if (emergencyContact == null || !emergencyContact.matches("^((\\+44)|(0))(7\\d{9})$")) {
            throw new IllegalArgumentException("Please enter a valid UK phone number (e.g., +447123456789).");
        }
    }

    public void validateGradeLevel(int gradeLevel) {
        if (gradeLevel < 1 || gradeLevel > 5) {
            throw new IllegalArgumentException("Grade level must be between 1 and 5.");
        }
    }
}