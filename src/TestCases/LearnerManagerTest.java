package TestCases;

import Manager.LearnerManager;
import Model.Learner;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class LearnerManagerTest {
    private LearnerManager manager;

    @Before
    public void setUp() {
        manager = new LearnerManager();
    }

    @Test
    public void testRegisterLearner() {
        String name = "Elia";
        LocalDate dob = LocalDate.of(2019, 1, 1);
        String result = manager.registerLearner(name, dob, "Female", "+447700900123", 2);

        assertNotNull(result);
        assertTrue(result.contains("Learner registered successfully!"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterLearnerWithInvalidDOB() {
        String name = "Test name";
        LocalDate dob = LocalDate.of(1990, 10, 10); // Incorrectly using a date outside the valid range
        manager.registerLearner(name, dob, "Female", "+447700900123", 2);
    }

}
