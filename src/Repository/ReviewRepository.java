package Repository;

import Model.Lesson;
import Model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages storage and retrieval of Review objects in a simulated database context.
 */
public class ReviewRepository {
    private final List<Review> reviewList;
    private static ReviewRepository reviewRepository;

    /**
     * Provides a singleton instance of ReviewRepository to ensure there is only one instance throughout the application.
     * @return the singleton instance of the ReviewRepository
     */
    public static ReviewRepository getReviewDB() {
        if (reviewRepository == null) {
            reviewRepository = new ReviewRepository();
        }
        return reviewRepository;
    }

    /**
     * Constructs a ReviewRepository with an empty list of reviews.
     */
    public ReviewRepository() {
        reviewList = new ArrayList<>();
    }

    /**
     * Adds a Review to the repository.
     * @param review The Review object to be added to the repository
     */
    public void addReview(Review review) {
        reviewList.add(review);
    }

    /**
     * Retrieves all reviews associated with a specific coach, based on the coach's name.
     * @param coachName The name of the coach to filter reviews by
     * @return a list of Reviews related to the specified coach
     */
    public List<Review> findReviewsByCoachName(String coachName) {
        List<Review> reviewsByCoach = new ArrayList<>();
        for (Review review : reviewList) {
            Lesson lesson = review.getLesson();
            if (lesson != null && lesson.getCoach().equals(coachName)) {
                reviewsByCoach.add(review);
            }
        }
        return reviewsByCoach;
    }

    /**
     * Retrieves all reviews currently stored in the repository.
     * @return a list of all Review objects
     */
    public List<Review> getReviewList() {
        return reviewList;
    }
}
