// This imports the HashMap class, which we will use
// to store our category-to-limit mappings.
import java.util.HashMap;

/**
 * Represents the user's budget.
 * This class holds all the budget categories (like "Groceries", "Rent")
 * and their assigned monthly spending limits.
 */
public class Budget {

    // --- 1. Field (The Data) ---
    // We will store our budget in a HashMap.
    // The 'String' is the Key (e.g., "Groceries")
    // The 'Double' is the Value (e.g., 500.00)
    private HashMap<String, Double> budgetCategories;

    // --- 2. Constructor (The "Builder") ---
    /**
     * The constructor's job is to initialize our fields.
     * Here, it just creates the new, empty HashMap object
     * so it's ready to have categories added to it.
     */
    public Budget() {
        // 'new HashMap<>()' creates the empty map object
        this.budgetCategories = new HashMap<>();
    }

    // --- 3. Methods (The "Actions") ---

    /**
     * Adds a new category or updates an existing one's limit.
     *
     * @param category The name of the category (e.g., "Gas")
     * @param limit    The monthly spending limit for this category (e.g., 100.0)
     */
    public void setBudgetCategory(String category, double limit) {
        // The .put() method automatically handles both
        // adding a new entry and updating an existing one.
        this.budgetCategories.put(category, limit);
    }

    /**
     * Gets the budget limit for a specific category.
     *
     * @param category The name of the category to check.
     * @return The budget limit as a 'double'. If the category
     * doesn't exist in the budget, it returns 0.0.
     */
    public double getBudgetLimit(String category) {
        // The .getOrDefault() method is very safe.
        // It tries to find the 'category' key. If it finds it,
        // it returns the value. If not, it returns the default
        // value we provide (in this case, 0.0).
        return this.budgetCategories.getOrDefault(category, 0.0);
    }

    /**
     * Returns the entire map of categories and limits.
     * This is useful for our summary report, where we need to
     * loop through all categories.
     *
     * @return The complete HashMap of categories and limits.
     */
    public HashMap<String, Double> getAllCategories() {
        return this.budgetCategories;
    }
}