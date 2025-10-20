import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate; // We will need this for the next step!

/**
 * This is the main class for our FinanceTracker application.
 * It runs the user menu and controls the flow of the app.
 */
public class FinanceTracker {

    // --- 1. Fields ---
    private static Scanner scanner = new Scanner(System.in);
    private static Budget budget = new Budget();
    private static ArrayList<Expense> expenses = new ArrayList<>();

    // --- 2. The Main Method (App Entry Point) ---
    public static void main(String[] args) {

        System.out.println("Welcome to your personal Finance Tracker!");

        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the 'Enter' key

            switch (choice) {
                case 1:
                    setBudget();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    viewSummary();
                    break;
                case 4:
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    // --- 3. Helper Methods ---

    /**
     * Displays the main menu options to the user.
     */
    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Set Budget Category");
        System.out.println("2. Add Expense");
        System.out.println("3. View Summary");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Asks the user for a category and a limit, then
     * saves it to the 'budget' object.
     */
    private static void setBudget() {
        System.out.println("--- Set Budget ---");

        // 1. Ask for the category name
        System.out.print("Enter category name (e.g., Groceries): ");
        String category = scanner.nextLine();

        // 2. Ask for the budget limit
        System.out.print("Enter budget limit for " + category + ": $");
        double limit = scanner.nextDouble();
        scanner.nextLine(); // Consume the 'Enter' key after the number

        // 3. Save the data to our 'budget' object
        // We are calling the setBudgetCategory() method
        // from our Budget.java class.
        budget.setBudgetCategory(category, limit);

        // 4. Print a success message
        System.out.println("Budget set! " + category + " = $" + limit);
    }

    /**
     * Placeholder method for adding an expense.
     * We will build this next.
     */
    private static void addExpense() {
        System.out.println("--- Add Expense ---");
        // TODO: We will write this code next
        System.out.println("[Feature coming soon!]");
    }

    /**
     * Placeholder method for viewing the summary.
     * We will build this later.
     */
    private static void viewSummary() {
        System.out.println("--- View Summary ---");
        // TODO: We will write this code later
        System.out.println("[Feature coming soon!]");
    }
}