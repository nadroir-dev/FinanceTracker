import javafx.application.Application;
import javafx.geometry.Insets; // Used for adding padding
import javafx.scene.Scene;
import javafx.scene.control.*; // Imports all controls (Button, Label, TextField, etc.)
import javafx.scene.layout.GridPane; // A layout for forms
import javafx.scene.layout.VBox; // Vertical layout
import javafx.stage.Stage;

public class FinanceTrackerGui extends Application {

    // --- 1. Define all our UI components ---
    // We make these "fields" (variables) for the whole class
    // so our helper methods can access them later.

    // "Add Expense" section
    private TextField descriptionInput;
    private TextField amountInput;
    private TextField expenseCategoryInput;
    private Button addExpenseButton;

    // "Set Budget" section
    private TextField budgetCategoryInput;
    private TextField budgetLimitInput;
    private Button setBudgetButton;

    // "Summary" section
    private TextArea summaryArea;

    @Override
    public void start(Stage primaryStage) {

        // --- 2. Initialize all components ---

        // "Add Expense"
        descriptionInput = new TextField();
        descriptionInput.setPromptText("e.g., Weekly shopping"); // Placeholder text
        amountInput = new TextField();
        amountInput.setPromptText("e.g., 75.50");
        expenseCategoryInput = new TextField();
        expenseCategoryInput.setPromptText("e.g., Groceries");
        addExpenseButton = new Button("Add Expense");

        // "Set Budget"
        budgetCategoryInput = new TextField();
        budgetCategoryInput.setPromptText("e.g., Groceries");
        budgetLimitInput = new TextField();
        budgetLimitInput.setPromptText("e.g., 500.00");
        setBudgetButton = new Button("Set Budget");

        // "Summary"
        summaryArea = new TextArea();
        summaryArea.setEditable(false); // Make it read-only
        summaryArea.setText("Welcome! Your summary will appear here.");

        // --- 3. Create the Layout ---

        // Use a GridPane for our forms. It's like a mini-spreadsheet.
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(10)); // Add 10px padding around the grid
        formGrid.setHgap(10); // Horizontal space between columns
        formGrid.setVgap(10); // Vertical space between rows

        // --- "Add Expense" Form (Row 0-1) ---
        formGrid.add(new Label("--- Add Expense ---"), 0, 0, 2, 1); // Span 2 columns

        formGrid.add(new Label("Description:"), 0, 1);
        formGrid.add(descriptionInput, 1, 1);

        formGrid.add(new Label("Amount:"), 0, 2);
        formGrid.add(amountInput, 1, 2);

        formGrid.add(new Label("Category:"), 0, 3);
        formGrid.add(expenseCategoryInput, 1, 3);

        formGrid.add(addExpenseButton, 1, 4); // Add the button

        // --- "Set Budget" Form (Row 5-6) ---
        formGrid.add(new Label("--- Set Budget ---"), 0, 5, 2, 1); // Span 2 columns

        formGrid.add(new Label("Category:"), 0, 6);
        formGrid.add(budgetCategoryInput, 1, 6);

        formGrid.add(new Label("Limit:"), 0, 7);
        formGrid.add(budgetLimitInput, 1, 7);

        formGrid.add(setBudgetButton, 1, 8); // Add the button

        // --- 4. Create the Root Layout ---
        // We'll stack the form grid on top of the summary area.
        VBox rootLayout = new VBox(10); // 10px spacing
        rootLayout.setPadding(new Insets(10));
        rootLayout.getChildren().addAll(formGrid, new Label("--- Summary ---"), summaryArea);

        // --- 5. Create the Scene and Show the Stage ---
        Scene scene = new Scene(rootLayout, 500, 600); // Width 500, Height 600
        primaryStage.setTitle("Finance Tracker v2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}