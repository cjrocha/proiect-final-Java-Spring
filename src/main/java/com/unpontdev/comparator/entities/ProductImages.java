package com.unpontdev.comparator.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Helds the product additional images object
 * Implemented but not used yet!
 * Data for this object will come from scrapers
 */
@Entity
public class ProductImages {
    public static final String SEQUENCE_NAME = "MY_CLASS_id_seq";

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    private Long id;
    private String imageUrl;
    @ManyToOne
    private Product products;

    /**
     * Constructor for product images
     * @param id
     * @param imageUrl
     */
    public ProductImages(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public ProductImages() {
    }

    /**
     * Setters and getters for product images data
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }
}
