import service.DataRetriever;
import model.Product;
import model.Category;

import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever dr = new DataRetriever();

        System.out.println("============== TEST A : getAllCategories() ==============");
        List<Category> categories = dr.getAllCategories();
        categories.forEach(c ->
                System.out.println("Category: " + c.getId() + " - " + c.getName())
        );


        System.out.println("\n============== TEST B : getProductList(page, size) ==============");
        int[] pages = {1, 1, 1, 2};
        int[] sizes = {10, 5, 3, 2};

        for (int i = 0; i < pages.length; i++) {
            System.out.println("\n-- Page: " + pages[i] + ", Size: " + sizes[i] + " --");
            List<Product> products = dr.getProductList(pages[i], sizes[i]);
            products.forEach(p ->
                    System.out.println(
                            p.getId() + " - " + p.getName() + " - " +
                                    p.getCreationDate() + " - Cat: " + p.getCategoryName()
                    )
            );
        }


        System.out.println("\n============== TEST C : getProductsByCriteria(...) ==============");

        Object[][] criteriaTests = {
                {"Dell", null, null, null},
                {null, "info", null, null},
                {"iPhone", "mobile", null, null},
                {null, null, Instant.parse("2024-02-01T00:00:00Z"), Instant.parse("2024-03-01T00:00:00Z")},
                {"Samsung", "bureau", null, null},
                {"Sony", "informatique", null, null},
                {null, "audio", Instant.parse("2024-01-01T00:00:00Z"), Instant.parse("2024-12-01T00:00:00Z")},
                {null, null, null, null}
        };

        for (Object[] test : criteriaTests) {
            System.out.println("\n--- Test Criteria ---");
            System.out.println("productName=" + test[0] +
                    ", categoryName=" + test[1] +
                    ", creationMin=" + test[2] +
                    ", creationMax=" + test[3]);

            List<Product> filtered = dr.getProductsByCriteria(
                    (String) test[0],
                    (String) test[1],
                    (Instant) test[2],
                    (Instant) test[3]
            );

            filtered.forEach(p ->
                    System.out.println(
                            p.getId() + " - " + p.getName() + " - " +
                                    p.getCreationDate() + " - Cat: " + p.getCategoryName()
                    )
            );
        }


        System.out.println("\n============== TEST D : getProductsByCriteria(...) + pagination ==============");

        Object[][] criteriaPageTests = {
                {"Dell", null, null, null, 1, 10},
                {null, null, null, null, 1, 5},
                {null, "informatique", null, null, 1, 10}
        };

        for (Object[] test : criteriaPageTests) {
            System.out.println("\n--- Test Criteria + Pagination ---");
            System.out.println("productName=" + test[0] +
                    ", categoryName=" + test[1] +
                    ", creationMin=" + test[2] +
                    ", creationMax=" + test[3] +
                    ", page=" + test[4] +
                    ", size=" + test[5]);

            List<Product> filtered = dr.getProductsByCriteria(
                    (String) test[0],
                    (String) test[1],
                    (Instant) test[2],
                    (Instant) test[3],
                    (int) test[4],
                    (int) test[5]
            );

            filtered.forEach(p ->
                    System.out.println(
                            p.getId() + " - " + p.getName() + " - " +
                                    p.getCreationDate() + " - Cat: " + p.getCategoryName()
                    )
            );
        }
    }
}
