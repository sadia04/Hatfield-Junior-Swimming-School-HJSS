package TestCases;

import Manager.ReviewManager;
import Model.Lesson;
import Model.Review;
import Repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewManagerTest {
    private ReviewManager reviewManager;

    @BeforeEach
    void setUp() {
        reviewManager = new ReviewManager();
    }

    @Test
    void testAddReview() {
        Lesson lesson = new Lesson();
        String result = reviewManager.addReview("Great class!", 5, lesson);
        assertEquals("Review added successfully", result);
    }
}
