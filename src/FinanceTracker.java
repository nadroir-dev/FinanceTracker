// NEW: Imports for file handling
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the main class for our FinanceTracker application.
 * It runs the user menu and controls the flow of the app.
 */
public class FinanceTracker {

    // --- 1. Fields ---
    private static Scanner scanner = new Scanner(System.in);
    private static Budget budget = new Budget();
    private static ArrayList<Expense> expenses = new ArrayList<>();

    // NEW: File constants for our save files
    private static final String BUDGET_FILE = "budget.txt";
    private static final String EXPENSES_FILE = "expenses.txt";


    // --- 2. The Main Method (App Entry Point) ---
    public static void main(String[] args) {

        // NEW: Load any saved data as soon as the app starts
        loadData();

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
                    // NEW: Save all data before exiting
                    saveData();
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    // --- 3. Helper Methods (Unchanged) ---

    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Set Budget Category");
        System.out.println("2. Add Expense");
        System.out.println("3. View Summary");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

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

    private static void addExpense() {
        System.out.println("--- Add Expense ---");
        System.out.print("Enter expense description (e.g., Weekly shopping): ");
        String description = scanner.nextLine();
        System.out.print("Enter expense amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the 'Enter' key
        System.out.print("Enter expense category (e.g., Groceries): ");
        String category = scanner.nextLine();
        LocalDate date = LocalDate.now();
        Expense newExpense = new Expense(description, amount, category, date);
        expenses.add(newExpense);
        System.out.println("Expense added!");
        System.out.println(newExpense);
    }

    /**
     * Calculates and displays a summary of spending vs. budget.
     * NOW ALSO displays a list of all recorded expenses.
     */
    /**
     * Calculates and displays a summary of spending vs. budget.
     * NOW ALSO displays a list of all recorded expenses.
     */
    private static void viewSummary() {
        System.out.println("\n--- Your Monthly Summary ---");

        // 1. Calculate total spending per category
        HashMap<String, Double> spendingTotals = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();
            double currentTotal = spendingTotals.getOrDefault(category, 0.0);
            spendingTotals.put(category, currentTotal + amount);
        }

        // 2. Display the budget report
        HashMap<String, Double> budgetCategories = budget.getAllCategories();

        if (budgetCategories.isEmpty()) {
            System.out.println("You haven't set any budget categories yet!");
        }

        for (Map.Entry<String, Double> entry : budgetCategories.entrySet()) {
            String category = entry.getKey();
            double limit = entry.getValue();
            double spent = spendingTotals.getOrDefault(category, 0.0);
            double remaining = limit - spent;

            System.out.printf("Category: %-15s | Budget: $%-10.2f | Spent: $%-10.2f | Remaining: $%-10.2f\n",
                    category, limit, spent, remaining);
            spendingTotals.remove(category);
        }

        // 3. Display unbudgeted spending
        if (!spendingTotals.isEmpty()) {
            System.out.println("\n--- Unbudgeted Spending ---");
            for (Map.Entry<String, Double> entry : spendingTotals.entrySet()) {
                String category = entry.getKey();
                double spent = entry.getValue();
                System.out.printf("Category: %-15s | Spent: $%.2f\n", category, spent);
            }
        }

        // 4. --- NEW FEATURE ---
        //    Display all individual expenses
        System.out.println("\n--- All Recorded Expenses ---");
        if (expenses.isEmpty()) {
            System.out.println("You have not recorded any expenses yet.");
        } else {
            // We just loop through the list and use the
            // toString() method from our Expense class!
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }

        System.out.println("---------------------------------");
    }

    // --- 4. NEW: Data Persistence Methods ---

    /**
     * Loads the budget and expenses from .txt files
     * when the application starts.
     */
    private static void loadData() {
        // --- Load Budget ---
        try {
            File file = new File(BUDGET_FILE);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                String category = parts[0];
                double limit = Double.parseDouble(parts[1]);
                budget.setBudgetCategory(category, limit);
            }
            fileScanner.close();
            System.out.println("Budget data loaded.");
        } catch (FileNotFoundException e) {
            // This is OK! It just means it's the first time running the app.
            System.out.println("No budget file found. Starting fresh.");
        }

        // --- Load Expenses ---
        try {
            File file = new File(EXPENSES_FILE);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // We use our new helper method in the Expense class!
                Expense expense = Expense.fromDataString(line);
                expenses.add(expense);
            }
            fileScanner.close();
            System.out.println("Expense data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No expenses file found. Starting fresh.");
        }
    }

    /**
     * Saves the current budget and expenses to .txt files
     * when the application exits.
     */
    private static void saveData() {
        // --- Save Budget ---
        try (PrintWriter budgetWriter = new PrintWriter(new FileWriter(BUDGET_FILE))) {
            for (Map.Entry<String, Double> entry : budget.getAllCategories().entrySet()) {
                budgetWriter.println(entry.getKey() + "|" + entry.getValue());
            }
            System.out.println("Budget data saved.");
        } catch (IOException e) {
            System.out.println("Error saving budget data: " + e.getMessage());
        }

        // --- Save Expenses ---
        try (PrintWriter expenseWriter = new PrintWriter(new FileWriter(EXPENSES_FILE))) {
            for (Expense exp : expenses) {
                // We use our new helper method in the Expense class!
                expenseWriter.println(exp.toDataString());
            }
            System.out.println("Expense data saved.");
        } catch (IOException e) {
            System.out.println("Error saving expense data: " + e.getMessage());
        }
    }
}