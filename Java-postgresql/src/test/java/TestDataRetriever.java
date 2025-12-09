import service.DataRetriever;
import model.Category;

import java.util.List;

public class TestDataRetriever {
    public static void main(String[] args) {

        DataRetriever dr = new DataRetriever();

        System.out.println("Reading categories from database...");

        List<Category> categories = dr.getAllCategories();

        for (Category c : categories) {
            System.out.println(c.getId() + " - " + c.getName());
        }

        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        }
    }
}
