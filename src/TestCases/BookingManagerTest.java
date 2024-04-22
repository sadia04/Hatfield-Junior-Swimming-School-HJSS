package TestCases;

import Manager.BookingManager;
import Model.Booking;
import Model.Learner;
import Model.Lesson;
import Repository.BookingRepository;
import Repository.LearnersRepository;
import Repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerTest {

    private BookingManager bookingManager;
    private BookingRepository bookingRepository;
    private LearnersRepository learnersRepository;
    private LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        // Reset the repositories before each test
        bookingRepository = new BookingRepository();
        learnersRepository = new LearnersRepository();
        lessonRepository = new LessonRepository();
        bookingManager = new BookingManager();
    }

    @Test
    void testCreateBookCheckNullLesson() {
        // Setup specific learner and lesson for this test
        Learner learner = new Learner("khan", "Male", LocalDate.of(2019, 1, 1), "+441234567890", 3);
        learnersRepository.addLearner(learner);
        Lesson lesson = new Lesson(LocalDate.now(), DayOfWeek.MONDAY, "Jane", 3, 4);
        lessonRepository.add_Lessons(lesson);

        String result = bookingManager.createBookLesson(learner.getId(), lesson.getLessonRef());
         // Assertion
        assertTrue(result.contains("Lesson not found"), "Booking should be not successful");
    }




}
