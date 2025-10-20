import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; // NEW: For our new buttons
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional; // NEW: For the delete confirmation
import java.util.Scanner;

public class FinanceTrackerGui extends Application {

    // --- 1. UI components ---
    private TextField descriptionInput;
    private TextField amountInput;
    private TextField expenseCategoryInput;
    private Button addExpenseButton; // NEW: Text will change to "Save Changes"
    private TextField budgetCategoryInput;
    private TextField budgetLimitInput;
    private Button setBudgetButton;

    // NEW: We've split the summary area into two parts
    private TextArea budgetSummaryArea; // For the budget report
    private ListView<Expense> expenseListView; // For the clickable list of expenses

    // NEW: Buttons for editing and deleting
    private Button editExpenseButton;
    private Button deleteExpenseButton;

    // --- 2. Backend Logic Fields ---
    private Budget budget = new Budget();
    private ArrayList<Expense> expenses = new ArrayList<>();

    // NEW: This variable will track which expense we're editing
    private Expense expenseBeingEdited = null;

    private static final String BUDGET_FILE = "budget.txt";
    private static final String EXPENSES_FILE = "expenses.txt";

    @Override
    public void start(Stage primaryStage) {

        loadData();

        // --- 3. Initialize UI components ---
        descriptionInput = new TextField();
        descriptionInput.setPromptText("e.g., Weekly shopping");
        amountInput = new TextField();
        amountInput.setPromptText("e.g., 75.50");
        expenseCategoryInput = new TextField();
        expenseCategoryInput.setPromptText("e.g., Groceries");

        // NEW: The "Add" button's text will be dynamic
        addExpenseButton = new Button("Add Expense");

        budgetCategoryInput = new TextField();
        budgetCategoryInput.setPromptText("e.g., Groceries");
        budgetLimitInput = new TextField();
        budgetLimitInput.setPromptText("e.g., 500.00");
        setBudgetButton = new Button("Set Budget");

        // NEW: Initialize our two new summary components
        budgetSummaryArea = new TextArea();
        budgetSummaryArea.setEditable(false);
        budgetSummaryArea.setPrefHeight(150); // Give it a set height

        expenseListView = new ListView<>();
        expenseListView.setPrefHeight(250); // Give it a set height

        // NEW: Initialize edit/delete buttons
        editExpenseButton = new Button("Edit Selected");
        deleteExpenseButton = new Button("Delete Selected");

        // --- 4. Connect Buttons to Event Handlers ---

        // NEW: We rename the handler for clarity
        addExpenseButton.setOnAction(e -> handleSaveExpense());
        setBudgetButton.setOnAction(e -> handleSetBudget());

        // NEW: Wire up our new buttons
        editExpenseButton.setOnAction(e -> handleEditExpense());
        deleteExpenseButton.setOnAction(e -> handleDeleteExpense());

        // --- 5. Layout ---
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(10));
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        formGrid.add(new Label("--- Add / Edit Expense ---"), 0, 0, 2, 1); // NEW: Text changed
        formGrid.add(new Label("Description:"), 0, 1);
        formGrid.add(descriptionInput, 1, 1);
        formGrid.add(new Label("Amount:"), 0, 2);
        formGrid.add(amountInput, 1, 2);
        formGrid.add(new Label("Category:"), 0, 3);
        formGrid.add(expenseCategoryInput, 1, 3);
        formGrid.add(addExpenseButton, 1, 4);

        formGrid.add(new Label("--- Set Budget ---"), 0, 5, 2, 1);
        formGrid.add(new Label("Category:"), 0, 6);
        formGrid.add(budgetCategoryInput, 1, 6);
        formGrid.add(new Label("Limit:"), 0, 7);
        formGrid.add(budgetLimitInput, 1, 7);
        formGrid.add(setBudgetButton, 1, 8);

        // NEW: A horizontal box for our edit/delete buttons
        HBox expenseEditBox = new HBox(10, editExpenseButton, deleteExpenseButton);
        expenseEditBox.setPadding(new Insets(5, 0, 5, 0));

        // NEW: The root layout is now our forms, the budget summary,
        // and then the list of expenses with its edit buttons.
        VBox rootLayout = new VBox(10);
        rootLayout.setPadding(new Insets(10));
        rootLayout.getChildren().addAll(
                formGrid,
                new Label("--- Budget Summary ---"),
                budgetSummaryArea,
                new Label("--- Expenses ---"),
                expenseListView,
                expenseEditBox
        );

        // --- 6. Scene & Stage ---
        Scene scene = new Scene(rootLayout, 500, 750); // NEW: Made window taller
        primaryStage.setTitle("Finance Tracker v2.0");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> saveData());

        // NEW: We now call both refresh methods
        updateBudgetSummary();
        updateExpenseList();
    }

    // --- 7. Event Handler Methods ---

    /**
     * NEW: This method is now "smart". It handles
     * both creating new expenses and updating existing ones.
     */
    private void handleSaveExpense() {
        try {
            String description = descriptionInput.getText();
            double amount = Double.parseDouble(amountInput.getText());
            String category = expenseCategoryInput.getText();

            if (description.isEmpty() || category.isEmpty()) {
                showAlert("Input Error", "Description and Category cannot be empty.");
                return;
            }

            if (expenseBeingEdited == null) {
                // This is the "Add New" mode
                Expense newExpense = new Expense(description, amount, category, LocalDate.now());
                expenses.add(newExpense);
            } else {
                // This is the "Save Changes" mode
                // We update the existing object's data
                expenseBeingEdited.setDescription(description);
                expenseBeingEdited.setAmount(amount); // Requires a setAmount() method in Expense.java!
                expenseBeingEdited.setCategory(category);

                // Reset our state
                expenseBeingEdited = null;
                addExpenseButton.setText("Add Expense");
            }

            // 3. Update the UI
            updateBudgetSummary(); // Need to update budget in case amount changed
            updateExpenseList(); // Need to update list to show new/edited item

            // 4. Clear the input fields
            descriptionInput.clear();
            amountInput.clear();
            expenseCategoryInput.clear();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for amount.");
        }
    }

    /**
     * Called when the "Set Budget" button is clicked. (Unchanged)
     */
    private void handleSetBudget() {
        try {
            String category = budgetCategoryInput.getText();
            double limit = Double.parseDouble(budgetLimitInput.getText());

            if (category.isEmpty()) {
                showAlert("Input Error", "Category cannot be empty.");
                return;
            }
            budget.setBudgetCategory(category, limit);
            updateBudgetSummary(); // Just update the budget part
            budgetCategoryInput.clear();
            budgetLimitInput.clear();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for limit.");
        }
    }

    /**
     * NEW: Called when "Edit Selected" button is clicked.
     */
    private void handleEditExpense() {
        // 1. Get the selected item from the ListView
        Expense selectedExpense = expenseListView.getSelectionModel().getSelectedItem();

        if (selectedExpense == null) {
            showAlert("Edit Error", "Please select an expense from the list to edit.");
            return;
        }

        // 2. Set our "state" variable
        expenseBeingEdited = selectedExpense;

        // 3. Populate the text fields
        descriptionInput.setText(selectedExpense.getDescription());
        amountInput.setText(String.valueOf(selectedExpense.getAmount()));
        expenseCategoryInput.setText(selectedExpense.getCategory());

        // 4. Change the button text to "Save Changes"
        addExpenseButton.setText("Save Changes");
    }

    /**
     * NEW: Called when "Delete Selected" button is clicked.
     */
    private void handleDeleteExpense() {
        Expense selectedExpense = expenseListView.getSelectionModel().getSelectedItem();

        if (selectedExpense == null) {
            showAlert("Delete Error", "Please select an expense from the list to delete.");
            return;
        }

        // NEW: Add a confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Expense");
        confirmAlert.setHeaderText("Are you sure you want to delete this expense?");
        confirmAlert.setContentText(selectedExpense.toString());

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK
            expenses.remove(selectedExpense);
            updateBudgetSummary();
            updateExpenseList();
        }
    }


    /**
     * NEW: Renamed and split from refreshSummary().
     * This just updates the top budget text area.
     */
    private void updateBudgetSummary() {
        StringBuilder summaryText = new StringBuilder();
        HashMap<String, Double> spendingTotals = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();
            double currentTotal = spendingTotals.getOrDefault(category, 0.0);
            spendingTotals.put(category, currentTotal + amount);
        }

        summaryText.append("--- Your Monthly Summary ---\n");
        HashMap<String, Double> budgetCategories = budget.getAllCategories();

        if (budgetCategories.isEmpty()) {
            summaryText.append("You haven't set any budget categories yet!\n");
        }

        for (Map.Entry<String, Double> entry : budgetCategories.entrySet()) {
            String category = entry.getKey();
            double limit = entry.getValue();
            double spent = spendingTotals.getOrDefault(category, 0.0);
            double remaining = limit - spent;
            summaryText.append(String.format("Category: %-15s | Budget: $%-10.2f | Spent: $%-10.2f | Remaining: $%-10.2f\n",
                    category, limit, spent, remaining));
            spendingTotals.remove(category);
        }

        if (!spendingTotals.isEmpty()) {
            summaryText.append("\n--- Unbudgeted Spending ---\n");
            for (Map.Entry<String, Double> entry : spendingTotals.entrySet()) {
                summaryText.append(String.format("Category: %-15s | Spent: $%.2f\n",
                        entry.getKey(), entry.getValue()));
            }
        }
        budgetSummaryArea.setText(summaryText.toString());
    }

    /**
     * NEW: This method just updates the ListView of expenses.
     */
    private void updateExpenseList() {
        expenseListView.getItems().clear(); // Clear the old list
        expenseListView.getItems().addAll(expenses); // Add all expenses from our ArrayList

        // NEW: If we were editing, but the list refreshed,
        // reset the edit state to avoid confusion.
        if (expenseBeingEdited != null) {
            expenseBeingEdited = null;
            addExpenseButton.setText("Add Expense");
            descriptionInput.clear();
            amountInput.clear();
            expenseCategoryInput.clear();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- Data Persistence Methods (Unchanged) ---
    private void loadData() {
        try (Scanner fileScanner = new Scanner(new File(BUDGET_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split("\\|");
                budget.setBudgetCategory(parts[0], Double.parseDouble(parts[1]));
            }
            System.out.println("Budget data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No budget file found. Starting fresh.");
        }

        try (Scanner fileScanner = new Scanner(new File(EXPENSES_FILE))) {
            while (fileScanner.hasNextLine()) {
                expenses.add(Expense.fromDataString(fileScanner.nextLine()));
            }
            System.out.println("Expense data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No expenses file found. Starting fresh.");
        }
    }

    private void saveData() {
        // ... (This method is unchanged) ...
        try (PrintWriter budgetWriter = new PrintWriter(new FileWriter(BUDGET_FILE))) {
            for (Map.Entry<String, Double> entry : budget.getAllCategories().entrySet()) {
                budgetWriter.println(entry.getKey() + "|" + entry.getValue());
            }
            System.out.println("Budget data saved.");
        } catch (IOException e) {
            System.out.println("Error saving budget data: " + e.getMessage());
        }

        try (PrintWriter expenseWriter = new PrintWriter(new FileWriter(EXPENSES_FILE))) {
            for (Expense exp : expenses) {
                expenseWriter.println(exp.toDataString());
            }
            System.out.println("Expense data saved.");
        } catch (IOException e) {
            System.out.println("Error saving expense data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}