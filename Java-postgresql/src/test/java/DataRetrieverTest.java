import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.DataRetriever;
import model.Category;
import model.Product;

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
        categories.forEach(c -> assertNotNull(c.getName(), "Chaque catégorie doit avoir un nom"));

        // Affichage pour vérification visuelle
        System.out.println("==== getAllCategories ====");
        categories.forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
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

            // Affichage pour vérification visuelle
            System.out.println("==== getProductList: Page " + page + ", Size " + size + " ====");
            products.forEach(p -> System.out.println(
                    "Product ID: " + p.getId() +
                            ", Name: " + p.getName() +
                            ", Created: " + p.getCreationDate() +
                            ", Category: " + (p.getCategory() != null ? p.getCategoryName() : "null")
            ));
        }
    }
}

