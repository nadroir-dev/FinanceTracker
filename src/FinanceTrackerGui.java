// These are the new imports for JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox; // A simple layout container
import javafx.stage.Stage;

/**
 * This is our new main class for the GUI application.
 * Notice it "extends Application"
 */
public class FinanceTrackerGui extends Application {

    // --- 1. The 'start' Method ---
    /**
     * This is the new "entry point" for our JavaFX app.
     * The 'main' method (below) just calls this.
     * The 'Stage' is the main application window.
     */
    @Override
    public void start(Stage primaryStage) {

        // --- 2. Create UI Controls ---
        // A 'Label' is just simple text.
        Label greetingLabel = new Label("Welcome to our Finance Tracker GUI!");
        Label nextStepsLabel = new Label("Next, we will add buttons and text fields here.");

        // --- 3. Create a Layout ---
        // A 'VBox' is a "Vertical Box". It stacks things on top of each other.
        VBox rootLayout = new VBox();
        rootLayout.setSpacing(10); // Add 10 pixels of space between items

        // Add our labels to the layout
        rootLayout.getChildren().add(greetingLabel);
        rootLayout.getChildren().add(nextStepsLabel);

        // --- 4. Create a Scene ---
        // The 'Scene' is the content inside the window.
        // We put our layout into the scene.
        // We also set the size of the window (400 pixels wide, 300 tall).
        Scene scene = new Scene(rootLayout, 400, 300);

        // --- 5. Show the Stage ---
        primaryStage.setTitle("Finance Tracker v2.0"); // Set the window title
        primaryStage.setScene(scene); // Put the scene in the window
        primaryStage.show(); // Open the window!
    }

    // --- 6. The 'main' Method ---
    /**
     * This main method is still the entry point for Java itself.
     * Its only job is to "launch" the JavaFX application,
     * which then calls our 'start' method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}