package Repository;

import Model.Review;
import Model.Lesson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Double> getAverageRatingsByCoachForMonth(int month) {
        Map<String, List<Integer>> ratings = new HashMap<>();
        for (Review review : reviewList) {
            Lesson lesson = review.getLesson();
            LocalDate lessonDate = lesson.getDate();
            if (lessonDate.getMonthValue() == month) { // Only checking month, not year
                ratings.computeIfAbsent(lesson.getCoach(), k -> new ArrayList<>()).add(review.getRating());
            }
        }

        Map<String, Double> averages = new HashMap<>();
        ratings.forEach((coach, ratingsList) -> averages.put(coach, ratingsList.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0)));
        return averages;
    }
    /**
     * Retrieves all reviews currently stored in the repository.
     * @return a list of all Review objects
     */
    public List<Review> getReviewList() {
        return reviewList;
    }
}
