// This imports the LocalDate class, which lets us work with dates (like "2025-10-20")
import java.time.LocalDate;

/**
 * Represents a single financial expense.
 * This is a "model" or "blueprint" class. It doesn't *do* much
 * by itself, but it holds all the important data for one expense.
 */
public class Expense {

    // --- 1. Fields (The Data) ---
    // These are the variables that every Expense object will have.
    // We make them 'private' so they can only be changed by methods
    // inside this class (which is good practice!).
    private String description;
    private double amount;
    private String category;
    private LocalDate date;

    // --- 2. Constructor (The "Builder") ---
    /**
     * This is a special method called a constructor.
     * Its job is to create a new Expense object and set all its
     * initial values (its fields).
     */
    public Expense(String description, double amount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // --- 3. "Getter" Methods (The "Accessors") ---
    // Since our fields are 'private', we need public methods
    // to allow other parts of our code to *read* the data.
    // These are called "getters".

    public String getDescription() {
        return this.description;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public LocalDate getDate() {
        return this.date;
    }

    // --- 4. "Setter" Methods (The "Mutators") ---
    // We might also want to *change* data after an object is created.
    // (For example, to fix a typo in the description).
    // These are called "setters".

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Note: We probably don't want setters for 'amount' or 'date'
    // to prevent accidental changes. If you want to change an amount,
    // you'd probably just delete the expense and add a new one.

    // --- 5. A Helper Method (toString) ---
    /**
     * This is a special built-in Java method. We are "overriding" it
     * to provide a clean, human-readable String representation
     * of an Expense object. This is great for debugging and for
     * printing a list of expenses to the console.
     */
    @Override
    public String toString() {
        // Example output: "2025-10-20: Groceries - $75.50 (Weekly shopping)"
        return this.date + ": " + this.category + " - $" + this.amount + " (" + this.description + ")";
    }
}