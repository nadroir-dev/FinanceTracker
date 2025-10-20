import java.time.LocalDate;

/**
 * Represents a single financial expense.
 * This class holds all the important data for one expense.
 */
public class Expense {

    // --- 1. Fields ---
    private String description;
    private double amount;
    private String category;
    private LocalDate date;

    // --- 2. Constructor ---
    public Expense(String description, double amount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // --- 3. "Getter" Methods ---
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

    // --- 4. "Setter" Methods ---
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // --- 5. A Helper Method (toString) ---
    @Override
    public String toString() {
        return this.date + ": " + this.category + " - $" + this.amount + " (" + this.description + ")";
    }

    // --- 6. NEW: Data Persistence Methods ---

    /**
     * Converts the Expense object into a simple string for saving to a file.
     * We use a pipe '|' delimiter to avoid problems with commas.
     *
     * @return A pipe-delimited string of the expense data.
     */
    public String toDataString() {
        // Format: description|amount|category|date
        return this.description + "|" + this.amount + "|" + this.category + "|" + this.date;
    }

    /**
     * A "static factory method" that creates a new Expense object
     * from a data string.
     *
     * @param dataString The string read from the file.
     * @return A new Expense object.
     */
    public static Expense fromDataString(String dataString) {
        // The regex "\\|" is used to split on the pipe character.
        String[] parts = dataString.split("\\|");

        // We parse each part back into its original data type
        String description = parts[0];
        double amount = Double.parseDouble(parts[1]);
        String category = parts[2];
        LocalDate date = LocalDate.parse(parts[3]); // LocalDate knows how to parse "2025-10-20"

        return new Expense(description, amount, category, date);
    }
}