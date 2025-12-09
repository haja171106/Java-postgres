package mg.haja;

import service.DataRetriever;
import model.Category;
import model.Product;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("==== TEST getAllCategories() ====");
        List<Category> categories = dataRetriever.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            for (Category c : categories) {
                System.out.println("Category ID: " + c.getId() + ", Name: " + c.getName());
            }
        }

        System.out.println("\n==== TEST getProductList(page, size) ====");

        int[] pages = {1, 1, 1, 2};
        int[] sizes = {10, 5, 3, 2};

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            int size = sizes[i];
            System.out.println("\nPage " + page + ", Size " + size);
            List<Product> products = dataRetriever.getProductList(page, size);
            displayProducts(products);
        }
    }

    private static void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product p : products) {
                System.out.print("Product ID: " + p.getId());
                System.out.print(", Name: " + p.getName());
                System.out.print(", Created: " + p.getCreationDate());
                System.out.print(", Category: " + (p.getCategory() != null ? p.getCategoryName() : "null"));
                System.out.println();
            }
        }
    }
}
