package service;

import db.DBConnection;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.sql.Timestamp;

public class DataRetriever {
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, product_id FROM product_category";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql = "SELECT id, name, price, creation_datetime FROM product ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTimestamp("creation_datetime").toInstant(),
                            null
                    );
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public List<Product> getProductsByCriteria(String productName,
                                               String categoryName,
                                               Instant creationMin,
                                               Instant creationMax) {

        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.price, p.creation_datetime, " +
                        "c.id AS cat_id, c.name AS cat_name " +
                        "FROM product p " +
                        "LEFT JOIN product_category c ON c.product_id = p.id " +
                        "WHERE 1=1 "
        );

        // Liste pour stocker les paramètres
        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isEmpty()) {
            sql.append("AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            sql.append("AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append("AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append("AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append("ORDER BY p.id");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Remplir les paramètres
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = null;
                    int catId = rs.getInt("cat_id");
                    if (!rs.wasNull()) {
                        category = new Category(catId, rs.getString("cat_name"));
                    }

                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTimestamp("creation_datetime").toInstant(),
                            category
                    );

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getProductsByCriteria(String productName,
                                               String categoryName,
                                               Instant creationMin,
                                               Instant creationMax,
                                               int page,
                                               int size) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.price, p.creation_datetime, " +
                        "c.id AS cat_id, c.name AS cat_name " +
                        "FROM product p " +
                        "LEFT JOIN product_category c ON c.product_id = p.id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // Filtrage dynamique
        if (productName != null && !productName.isEmpty()) {
            sql.append("AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            sql.append("AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append("AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append("AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        // Pagination
        sql.append("ORDER BY p.id LIMIT ? OFFSET ?");

        // Calcul de l'offset
        int offset = (page - 1) * size;
        params.add(size);
        params.add(offset);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Remplir les paramètres dynamiques
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = null;
                    int catId = rs.getInt("cat_id");
                    if (!rs.wasNull()) {
                        category = new Category(catId, rs.getString("cat_name"));
                    }

                    Product product = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTimestamp("creation_datetime").toInstant(),
                            category
                    );

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
