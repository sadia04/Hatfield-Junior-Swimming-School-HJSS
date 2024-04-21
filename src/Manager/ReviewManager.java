package Manager;

import Model.Lesson;
import Model.Review;
import Repository.ReviewRepository;

/**
 * Manages operations related to reviews of lessons.
 */
public class ReviewManager {
    private final ReviewRepository reviewRepository;

    /**
     * Constructs a ReviewManager and initializes the review repository.
     */
    public ReviewManager() {
        reviewRepository = ReviewRepository.getReviewDB();
    }

    /**
     * Adds a review for a given lesson.
     *
     * @param reviewText The textual content of the review.
     * @param rating The numerical rating associated with the review, typically on a scale.
     * @param lesson The lesson to which the review is related.
     * @return A success message indicating the review has been added.
     */
    public String addReview(String reviewText, int rating, Lesson lesson) {
        Review newReview = new Review(rating, reviewText, lesson);
        reviewRepository.addReview(newReview);
        return "Review added successfully";
    }
}
