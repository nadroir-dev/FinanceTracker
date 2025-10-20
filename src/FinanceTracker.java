// Import the classes we need
import java.util.ArrayList; // To store our list of expenses
import java.util.Scanner;   // To read user input from the console
import java.time.LocalDate; // We'll need this soon for adding expenses

/**
 * This is the main class for our FinanceTracker application.
 * It runs the user menu and controls the flow of the app.
 */
public class FinanceTracker {

    // --- 1. Fields ---
    // We create our objects here so they can be used
    // by all the methods inside this class.

    // 'private static' means these variables belong to the class itself
    // and can be accessed from our 'static main' method.
    private static Scanner scanner = new Scanner(System.in);
    private static Budget budget = new Budget();
    private static ArrayList<Expense> expenses = new ArrayList<>();

    // --- 2. The Main Method (App Entry Point) ---
    public static void main(String[] args) {

        System.out.println("Welcome to your personal Finance Tracker!");

        // This 'while(true)' loop will run forever, keeping our
        // application alive until the user chooses to exit.
        while (true) {
            displayMenu(); // Call our helper method to show the menu

            // Get the user's choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // This is a small trick to "consume" the 'Enter' key
            // after the user types a number. It prevents bugs.

            // This 'switch' statement is a clean way to handle
            // the different menu choices.
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
                    // We'll add saving to a file here later (in Phase 4)
                    System.out.println("Exiting. Goodbye!");
                    return; // This 'return' statement breaks out of the 'while(true)'
                // loop and ends the main method, closing the app.
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
     * Placeholder method for setting the budget.
     * We will build this in Phase 3.
     */
    private static void setBudget() {
        System.out.println("--- Set Budget ---");
        // TODO: We will write this code in Phase 3
        System.out.println("[Feature coming soon!]");
    }

    /**
     * Placeholder method for adding an expense.
     * We will build this in Phase 3.
     */
    private static void addExpense() {
        System.out.println("--- Add Expense ---");
        // TODO: We will write this code in Phase 3
        System.out.println("[Feature coming soon!]");
    }

    /**
     * Placeholder method for viewing the summary.
     * We will build this in Phase 3.
     */
    private static void viewSummary() {
        System.out.println("--- View Summary ---");
        // TODO: We will write this code in Phase 3
        System.out.println("[Feature coming soon!]");
    }
}