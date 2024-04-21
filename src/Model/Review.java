package Model;

/**
 * Represents a review of a lesson, encapsulating the review content, a numerical rating, and the associated lesson.
 */
public class Review {
    private int rating;
    private String review;
    private Lesson lesson;

    /**
     * Constructs a Review object with a specified rating, review text, and the associated lesson.
     *
     * @param rating the numerical rating of the review (typically on a scale from 1 to 5)
     * @param review the text content of the review
     * @param lesson the lesson to which this review is associated
     */
    public Review(int rating, String review, Lesson lesson) {
        this.rating = rating;
        this.review = review;
        this.lesson = lesson;
    }

    /**
     * Returns the lesson associated with this review.
     *
     * @return the Lesson object associated with this review
     */
    public Lesson getLesson() {
        return lesson;
    }

    /**
     * Sets the lesson associated with this review.
     *
     * @param lesson the Lesson object to associate with this review
     */
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Returns the rating of the review.
     *
     * @return the numerical rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the review.
     *
     * @param rating the numerical rating to set (should be within the predefined scale)
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the review text.
     *
     * @return the text of the review
     */
    public String getReview() {
        return review;
    }

    /**
     * Sets the review text.
     *
     * @param review the text to set as this review's content
     */
    public void setReview(String review) {
        this.review = review;
    }
}
