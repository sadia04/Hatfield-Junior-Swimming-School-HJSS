package Repository;

import Model.Learner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages storage and retrieval of Learner objects within a simulated database context.
 */
public class LearnersRepository {

    private final List<Learner> learnerList;
    private static LearnersRepository learnersRepository;

    /**
     * Provides a singleton instance of the LearnersRepository.
     * This design pattern ensures that only one instance of the repository exists throughout the application.
     *
     * @return the singleton instance of LearnersRepository
     */
    public static LearnersRepository getLearnersDB() {
        if (learnersRepository == null) {
            learnersRepository = new LearnersRepository();
        }
        return learnersRepository;
    }

    /**
     * Constructs a new LearnersRepository with an empty list of learners.
     */
    public LearnersRepository() {
        learnerList = new ArrayList<>();
    }

    /**
     * Adds a Learner to the repository.
     *
     * @param learner the Learner to be added to the repository
     */
    public void addLearner(Learner learner) {
        learnerList.add(learner);
    }

    /**
     * Retrieves the list of all learners in the repository.
     *
     * @return a list containing all the learners
     */
    public List<Learner> getLearnerList() {
        return learnerList;
    }

    /**
     * Retrieves a Learner by their ID.
     *
     * @param id the ID of the learner to retrieve
     * @return the Learner if found, null otherwise
     */
    public Learner getLearner(String id) {
        for (Learner learner : learnerList) {
            if (learner.getId().equals(id)) {
                return learner;
            }
        }
        return null;
    }

    /**
     * Selects a Learner at random from the repository.
     * This method is useful for scenarios where a random learner needs to be selected, such as for demonstrations or testing.
     *
     * @return a randomly selected Learner, or null if the list is empty
     */
    public Learner getRandomLearners() {
        if (learnerList.isEmpty()) {
            return null;
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(learnerList.size());
            return learnerList.get(randomIndex);
        }
    }
}
