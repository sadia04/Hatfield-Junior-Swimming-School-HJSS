package view;

import Manager.LearnerManager;
import Model.Learner;
import utils.InputValidator;

import java.time.LocalDate;
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
     * Displays all registered learners.
     */
    public void viewLearners() {
        List<Learner> allLearners = learnerManager.getAllLearners();
        for (Learner learner : allLearners) {
            System.out.println(learner);
        }
    }

    /**
     * Facilitates user interaction to register a new learner with validated inputs.
     */
    public void registerLearner() {
        String name = getInput("Enter your name:", validator::validateUserName);
        String gender = getInput("Enter your gender (Male, Female, or Others):", validator::validateGender);
        LocalDate dob = getDateInput("Please enter your date of birth (YYYY-MM-DD):", validator::validateDOB);
        String emergencyContact = getInput("Enter your Emergency Contact number (+441234567890):", validator::validateEmergencyContact);
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
