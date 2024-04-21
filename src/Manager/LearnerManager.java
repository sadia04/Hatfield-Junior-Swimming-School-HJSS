package Manager;

import Model.Learner;
import Repository.LearnersRepository;
import utils.InputValidator;

import java.time.LocalDate;
import java.util.List;

/**
 * Manages learner-related operations.
 */
public class LearnerManager {
    private static final LearnerManager LEARNER_MANAGER = new LearnerManager();
    private final LearnersRepository db;
    private final InputValidator validator = new InputValidator();

    private LearnerManager() {
        db = LearnersRepository.getLearnersDB();
    }

    /**
     * Returns the singleton instance of LearnerManager.
     * @return the LearnerManager instance
     */
    public static LearnerManager getLearnerManager() {
        return LEARNER_MANAGER;
    }

    /**
     * Registers a new learner with the provided details.
     * @param name the name of the learner
     * @param dob the date of birth of the learner
     * @param gender the gender of the learner
     * @param emergencyContact the emergency contact number
     * @param currentGradeLevel the current grade level of the learner
     * @return a success message indicating registration status
     */
    public String registerLearner(String name, LocalDate dob, String gender, String emergencyContact, int currentGradeLevel) {
        validator.validateInputs(name, dob, gender, emergencyContact); // Validate inputs are not null
        Learner learner = new Learner(name, gender, dob, emergencyContact, currentGradeLevel);
        db.addLearner(learner);
        return "Learner registered successfully!";
    }

    /**
     * Retrieves all registered learners.
     * @return a list of all learners
     */
    public List<Learner> getAllLearners() {
        return db.getLearnerList();
    }

    /**
     * Retrieves a learner by their ID.
     * @param id the ID of the learner to retrieve
     * @return the Learner object
     */
    public Learner getLearnerById(String id) {
        return db.getLearner(id);
    }

    /**
     * Retrieves a random learner's ID.
     * @return the ID of a randomly selected learner
     */
    public String getRandomLearnerID() {
        Learner learner = db.getRandomLearners();
        return learner.getId();
    }

    /**
     * Updates the records for a learner.
     * @param learnerId the ID of the learner to update
     * @param bookedLessons a list of booked lesson IDs
     * @param attendedLessons a list of attended lesson IDs
     * @param cancelledLessons a list of cancelled lesson IDs
     */
    public void updateLearnerEntry(String learnerId, List<String> bookedLessons, List<String> attendedLessons, List<String> cancelledLessons){
        Learner learner = db.getLearner(learnerId);
        validator.validateLearnerExists(learner); // Validate learner exists
        learner.setBookedLessonsList(bookedLessons);
        learner.setAttendedLessonsList(attendedLessons);
        learner.setCancelledLessonsList(cancelledLessons);
        System.out.println("Learner updated successfully!");
    }
}
