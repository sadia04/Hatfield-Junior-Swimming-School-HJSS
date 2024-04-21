package EntryData;

import Manager.BookingManager;
import Manager.LearnerManager;
import Model.Booking;
import Model.Learner;
import appManager.SwimSchoolManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates random learner and booking data and populates the system with this data.
 */
public class LearnData {
    private static final String[] fnames = {"Mila", "Aiden", "Zoe", "Theo", "Ella", "Mateo", "Aria", "Finn", "Luna", "Oliver"};
    private static final String[] lnames = {"Sterling", "Knox", "Mercer", "Blair", "Reid", "Wilder", "Quinn", "Hollis", "Vale", "Ash"};
    private static final String[] gender = {"Male", "Female"};
    private static final long minNo = 7000000000L;
    private static final long maxNo = 7999999999L;

    private LearnerManager learnerManager;
    private BookingManager bookingManager;
    private SwimSchoolManager swimSchoolManager;

    /**
     * Constructs a LearnDataGenerator.
     * Initializes the necessary managers to interact with the data model.
     */
    public LearnData() {
        this.learnerManager = LearnerManager.getLearnerManager();
        this.bookingManager = new BookingManager();
        this.swimSchoolManager = SwimSchoolManager.getSwimSchoolManager();
    }

    /**
     * Generates a specified number of random learner entries.
     * @param count the number of learner entries to generate
     */
    public void generateEntries(int count) {
        for (int i = 0; i < count; i++) {
            String name = generateRandomlyNames();
            String gender = generateRandomlyGender();
            LocalDate dob = generateRandomlyDOB();
            String emergencyNo = generateRandomlyEmergencyNo();
            int gradeLevel = swimSchoolManager.assignGradeLevel();
            learnerManager.registerLearner(name, dob, gender, emergencyNo, gradeLevel);
        }
    }

    /**
     * Generates a random full name using predefined first and last names.
     * @return a randomly generated full name
     */
    private String generateRandomlyNames() {
        Random random = new Random();
        String firstName = fnames[random.nextInt(fnames.length)];
        String lastName = lnames[random.nextInt(lnames.length)];
        return firstName + " " + lastName;
    }

    /**
     * Randomly selects a gender.
     * @return a randomly selected gender
     */
    private String generateRandomlyGender() {
        return gender[new Random().nextInt(gender.length)];
    }

    /**
     * Generates a random date of birth within a specified range.
     * @return a randomly generated date of birth
     */
    private LocalDate generateRandomlyDOB() {
        int year = LocalDate.now().getYear() - 11;
        int maxYear = LocalDate.now().getYear() - 4;
        int randomYear = year + new Random().nextInt(maxYear - year + 1);
        int month = new Random().nextInt(12) + 1;
        int day = new Random().nextInt(28) + 1;
        return LocalDate.of(randomYear, month, day);
    }

    /**
     * Generates a random emergency contact number within a specified range.
     * @return a randomly generated emergency contact number
     */
    private String generateRandomlyEmergencyNo() {
        Random random = new Random();
        long number = minNo + (long) (random.nextDouble() * (maxNo - minNo + 1));
        return "+44" + number;
    }

    /**
     * Updates the booking and lesson details for all learners in the system based on their status.
     */
    public void updateLearnerEntries() {
        List<Learner> learners = learnerManager.getAllLearners();

        for (Learner learner : learners) {
            String learnerId = learner.getId();

            List<Booking> bookings = bookingManager.getBookingsByLearnerId(learnerId);
            List<String> bookedLessons = new ArrayList<>();
            List<String> attendedLessons = new ArrayList<>();
            List<String> cancelledLessons = new ArrayList<>();

            for (Booking booking : bookings) {
                switch (booking.getStatus()) {
                    case "Booked":
                        bookedLessons.add(booking.getBookingId());
                        break;
                    case "Cancelled":
                        cancelledLessons.add(booking.getBookingId());
                        break;
                    case "Attended":
                        attendedLessons.add(booking.getBookingId());
                        break;
                }
            }

            learnerManager.updateLearnerEntry(learnerId, bookedLessons, attendedLessons, cancelledLessons);
        }
    }
}
