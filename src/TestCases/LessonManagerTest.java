package TestCases;

import Manager.LessonManager;
import Model.Lesson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LessonManagerTest {
    private LessonManager manager;

    @BeforeEach
    void setup() {
        manager = LessonManager.getLessonManager();
    }

    @Test
    void testCreateLesson() {
        String result = manager.createLesson(LocalDate.now(), DayOfWeek.MONDAY, "Coach A", 2, 10);
        assertEquals("LESSON ADDED", result);
    }

    @Test
    void testGetLessonByReference() {
        Lesson lesson = manager.getLessonByReference("Ref001");
        assertNull(lesson);
    }
}
