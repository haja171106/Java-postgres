import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.DataRetriever;
import model.Category;
import model.Product;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataRetrieverTest {

    private static DataRetriever dataRetriever;

    @BeforeAll
    static void setup() {
        dataRetriever = new DataRetriever();
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = dataRetriever.getAllCategories();
        assertNotNull(categories, "La liste des catégories ne doit pas être null");
        assertFalse(categories.isEmpty(), "Il doit y avoir au moins une catégorie");

        System.out.println("getAllCategories");
        categories.forEach(c ->
                System.out.println(c.getId() + " - " + c.getName())
        );
    }

    @Test
    void testGetProductListPagination() {
        int[] pages = {1, 1, 1, 2};
        int[] sizes = {10, 5, 3, 2};

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            int size = sizes[i];

            List<Product> products = dataRetriever.getProductList(page, size);
            assertNotNull(products, "La liste des produits ne doit pas être null");
            assertTrue(products.size() <= size, "La taille de la liste ne doit pas dépasser size");

            System.out.println(" getProductList: Page " + page + ", Size " + size );
            products.forEach(p ->
                    System.out.println(
                            p.getId() + " | " +
                                    p.getName() + " | " +
                                    p.getCreationDate() + " | Cat: " + p.getCategoryName()
                    )
            );
        }
    }

    @Test
    void testGetProductsByCriteria() {

        Object[][] tests = {
                {"Dell", null, null, null},
                {null, "info", null, null},
                {"iPhone", "mobile", null, null},
                {null, null, Instant.parse("2024-02-01T00:00:00Z"), Instant.parse("2024-03-01T00:00:00Z")},
                {"Samsung", "bureau", null, null},
                {"Sony", "informatique", null, null},
                {null, "audio", Instant.parse("2024-01-01T00:00:00Z"), Instant.parse("2024-12-01T00:00:00Z")},
                {null, null, null, null}
        };

        for (Object[] t : tests) {
            String productName = (String) t[0];
            String categoryName = (String) t[1];
            Instant min = (Instant) t[2];
            Instant max = (Instant) t[3];

            List<Product> products = dataRetriever.getProductsByCriteria(productName, categoryName, min, max);

            assertNotNull(products, "La liste ne doit pas être null");

            System.out.println("\n==== getProductsByCriteria ====");
            System.out.println("productName=" + productName +
                    ", categoryName=" + categoryName +
                    ", creationMin=" + min +
                    ", creationMax=" + max);

            products.forEach(p ->
                    System.out.println(
                            p.getId() + " | " +
                                    p.getName() + " | " +
                                    p.getCreationDate() + " | Cat: " + p.getCategoryName()
                    )
            );
        }
    }
    @Test
    void testGetProductsByCriteriaWithPagination() {

        Object[][] tests = {
                {"Dell", null, null, null, 1, 10},
                {null, null, null, null, 1, 5},
                {null, "informatique", null, null, 1, 10}
        };

        for (Object[] t : tests) {
            String productName = (String) t[0];
            String categoryName = (String) t[1];
            Instant min = (Instant) t[2];
            Instant max = (Instant) t[3];
            int page = (int) t[4];
            int size = (int) t[5];

            List<Product> products = dataRetriever.getProductsByCriteria(
                    productName, categoryName, min, max, page, size
            );

            assertNotNull(products, "La liste ne doit pas être null");
            assertTrue(products.size() <= size, "Après pagination, la liste doit être de taille <= size");

            System.out.println("\n==== getProductsByCriteria WITH pagination ====");
            System.out.println("productName=" + productName +
                    ", categoryName=" + categoryName +
                    ", creationMin=" + min +
                    ", creationMax=" + max +
                    ", page=" + page +
                    ", size=" + size);

            products.forEach(p ->
                    System.out.println(
                            p.getId() + " | " +
                                    p.getName() + " | " +
                                    p.getCreationDate() + " | Cat: " + p.getCategoryName()
                    )
            );
        }
    }
}
