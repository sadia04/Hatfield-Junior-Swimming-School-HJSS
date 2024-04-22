package view;

import Manager.LearnerManager;
import Model.Learner;
import utils.InputValidator;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Handles display and interaction for learner management within the swim school system.
 */
public class LearnerDisplay {
    private final Scanner scanner = new Scanner(System.in);
    private final LearnerManager learnerManager = LearnerManager.getLearnerManager();
    private final InputValidator validator = new InputValidator();


    /**
     * Displays all learners and their activities in a structured table format.
     */
    public void viewLearners() {
        List<Learner> allLearners = learnerManager.getAllLearners();
        if (allLearners.isEmpty()) {
            System.out.println("No learners available.");
            return;
        }

        // Header for the table
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-30s | %-30s | %-6s | %-10s | %-15s | %-5s | %-45s | %-45s |\n","ID", "Name", "Gender", "DOB", "Emergency No", "Grade", "Category Count", "Activities Summary");
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");

        // Body of the table, iterating over each learner
        for (Learner learner : allLearners) {
            String activitiesSummary = formatActivitiesSummary(learner);
            String activitiesSummary2 = formatActivitiesSummary2(learner);
            System.out.printf("| %-30s | %-30s | %-6s | %-10s | %-15s | %-5d | %-45s | %-45s |\n",
                    learner.getId(),
                    learner.getName(),
                    learner.getGender(),
                    learner.getDOB().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    learner.getEmergencyNumber(),
                    learner.getCurrentGradeLevel(),
                    activitiesSummary2,
                    activitiesSummary
            );
        }
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------+");
    }

    /**
     * Creates a summary of the booked, attended, and cancelled lessons for a learner.
     * @param learner The learner whose activities are summarized.
     * @return A string summarizing the learner's activities.
     */
    private String formatActivitiesSummary(Learner learner) {
        return String.format("Booked: %s, Attended: %s, Cancelled: %s",
                learner.getBookedLessonsList(),
                learner.getAttendedLessonsList(),
                learner.getCancelledLessonsList());
    }
    private String formatActivitiesSummary2(Learner learner) {
        return String.format("Booked: %d, Attended: %d, Cancelled: %d",
                learner.getBookedLessonsList().size(),
                learner.getAttendedLessonsList().size(),
                learner.getCancelledLessonsList().size());
    }
    /**
     * Facilitates user interaction to register a new learner with validated inputs.
     */
    public void registerLearner() {
        String name = getInput("Enter your name:", validator::validateUserName);
        String gender = getInput("Enter your gender (Male, Female, or Others):", validator::validateGender);
        LocalDate dob = getDateInput("Please enter your date of birth (YYYY-MM-DD):", validator::validateDOB);
        String emergencyContact = getInput("Enter your Emergency Contact number:", validator::validateEmergencyContact);
        int gradeLevel = getIntInput("Enter your Swimming Grade Level (1, 2, 3, 4, or 5):", validator::validateGradeLevel);

        String result = learnerManager.registerLearner(name, dob, gender, emergencyContact, gradeLevel);
        System.out.println(result);
    }

    /**
     * Utility method to get validated string input.
     * @param prompt The prompt to display to the user.
     * @param validate The validation method to apply to the input.
     * @return A validated string input.
     */
    private String getInput(String prompt, InputValidator.IValidateString validate) {
        while (true) {
            try {
                System.out.println(prompt);
                String input = scanner.nextLine().trim();
                validate.validate(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Utility method to get validated integer input.
     * @param prompt The prompt to display to the user.
     * @param validate The validation method to apply to the input.
     * @return A validated integer input.
     */
    private int getIntInput(String prompt, InputValidator.IValidateInt validate) {
        while (true) {
            try {
                System.out.println(prompt);
                int input = Integer.parseInt(scanner.nextLine());
                validate.validate(input);
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Utility method to get validated LocalDate input.
     * @param prompt The prompt to display to the user.
     * @param validate The validation method to apply to the input.
     * @return A validated LocalDate input.
     */
    private LocalDate getDateInput(String prompt, InputValidator.IValidateLocalDate validate) {
        while (true) {
            try {
                System.out.println(prompt);
                LocalDate input = LocalDate.parse(scanner.nextLine());
                validate.validate(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
