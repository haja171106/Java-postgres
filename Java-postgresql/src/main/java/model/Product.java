package model;

import java.time.Instant;
import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private Instant creationDate;
    private Category category;

    public Product(int id, String name, Instant creationDate, Category category) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : null;
    }

    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDate = creationDatetime;
    }

}
