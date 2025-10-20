import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate; // We will use this in our new method!

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

        System.out.print("Enter category name (e.g., Groceries): ");
        String category = scanner.nextLine();

        System.out.print("Enter budget limit for " + category + ": $");
        double limit = scanner.nextDouble();
        scanner.nextLine(); // Consume the 'Enter' key

        budget.setBudgetCategory(category, limit);

        System.out.println("Budget set! " + category + " = $" + limit);
    }

    /**
     * Asks the user for expense details, creates an
     * Expense object, and adds it to our 'expenses' list.
     */
    private static void addExpense() {
        System.out.println("--- Add Expense ---");

        // 1. Get expense description
        System.out.print("Enter expense description (e.g., Weekly shopping): ");
        String description = scanner.nextLine();

        // 2. Get expense amount
        System.out.print("Enter expense amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the 'Enter' key

        // 3. Get expense category
        System.out.print("Enter expense category (e.g., Groceries): ");
        String category = scanner.nextLine();

        // 4. Get today's date automatically
        LocalDate date = LocalDate.now();

        // 5. Create a new Expense object using our blueprint
        Expense newExpense = new Expense(description, amount, category, date);

        // 6. Add the new object to our ArrayList
        expenses.add(newExpense);

        // 7. Print a success message
        System.out.println("Expense added!");
        System.out.println(newExpense); // This uses the .toString() method we wrote!
    }

    /**
     * Placeholder method for viewing the summary.
     * We will build this next.
     */
    private static void viewSummary() {
        System.out.println("--- View Summary ---");
        // TODO: We will write this code next
        System.out.println("[Feature coming soon!]");
    }
}