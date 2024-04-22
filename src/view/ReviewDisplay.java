package view;

import Repository.ReviewRepository;
import Model.Review;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles the display of reviews for the swim school management system.
 */
public class ReviewDisplay {
    private final ReviewRepository reviewRepository;
    private final Scanner scanner;

    /**
     * Constructs a ReviewDisplay with a reference to the ReviewRepository.
     */
    public ReviewDisplay() {
        this.reviewRepository = ReviewRepository.getReviewDB();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays all reviews stored in the repository.
     */
    public void displayAllReviews() {
        List<Review> reviews = reviewRepository.getReviewList();
        if (reviews.isEmpty()) {
            System.out.println("No reviews available.");
            return;
        }

        printReviews(reviews);
    }

    /**
     * Displays reviews filtered by a coach's name.
     */
    public void displayReviewsByCoach() {
        System.out.println("Enter the coach's name to filter reviews:");
        String coachName = scanner.nextLine();
        List<Review> reviews = reviewRepository.findReviewsByCoachName(coachName);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for coach: " + coachName);
            return;
        }

        System.out.println("Reviews for Coach: " + coachName);
        printReviews(reviews);
    }

    /**
     * Utility method to print a list of reviews in a formatted manner.
     * @param reviews the list of reviews to print
     */
    private void printReviews(List<Review> reviews) {
        System.out.println("+-------------------------------------------------------------------------------------------------+");
        System.out.println("| Coach        | Lesson Ref         | Rating | Review                                             |");
        System.out.println("+-------------------------------------------------------------------------------------------------+");
        for (Review review : reviews) {
            String coachName = review.getLesson().getCoach();
            String lessonRef = review.getLesson().getLessonRef();
            int rating = review.getRating();
            String reviewText = review.getReview();

            // Ensure the review text does not exceed 50 characters for display purposes
            if (reviewText.length() > 50) {
                reviewText = reviewText.substring(0, 47) + "...";
            }

            System.out.printf("| %-12s | %-18s | %-6d | %-50s |%n", coachName, lessonRef, rating, reviewText);
        }
        System.out.println("+-------------------------------------------------------------------------------------------------+");
    }
    public void generateMonthlyCoachReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the month number for the report (e.g., 1 for January, 12 for December):");
        int month = scanner.nextInt();

        Map<String, Double> averageRatings = reviewRepository.getAverageRatingsByCoachForMonth(month);
        if (averageRatings.isEmpty()) {
            System.out.println("No ratings available for this period.");
            return;
        }

        System.out.println("Average Ratings for Coaches in " + month);
        System.out.println("-------------------------------------------------------");
        averageRatings.forEach((coach, average) ->
                System.out.printf("%s: %.2f\n", coach, average));
        System.out.println("-------------------------------------------------------");
    }
}
