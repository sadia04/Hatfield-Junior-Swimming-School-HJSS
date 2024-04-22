package TestCases;

import Repository.ReviewRepository;
import Model.Lesson;
import Model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewRepositoryTest {
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository = new ReviewRepository();
    }

    @Test
    void testAddAndRetrieveReview() {
        Lesson lesson = new Lesson();
        Review review = new Review(4, "Very good lesson", lesson);
        reviewRepository.addReview(review);
        
        List<Review> reviews = reviewRepository.getReviewList();
        assertFalse(reviews.isEmpty(), "Review list should not be empty.");
        assertEquals(1, reviews.size(), "There should be one review in the list.");
        assertEquals(review, reviews.getFirst(), "The retrieved review should match the one added.");
    }


}
