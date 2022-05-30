package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Extending JPA repo, this interface
 * communicates with DB.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    /**
     * Contract method that finds products
     * from DB by pId
     * @param pId - the searched id
     * @return - the product data from DB
     */
    public Product findBypId(Long pId);

    /**
     * Method used for searching product by a keyword
     * Search is being done on product's name and on product's description
     * @param query - the searched keyword
     * @return - list of products that contain that keyword
     */
    @Query("SELECT p FROM product p WHERE lower(product_name) LIKE lower(CONCAT('%',:query, '%')) Or lower(product_description) LIKE lower(CONCAT('%', :query, '%'))"+
            "Or lower(product_name) LIKE lower(CONCAT('%',:query)) Or lower(product_name) LIKE lower(CONCAT (:query, '%'))"+
            "Or lower(product_description) LIKE lower(CONCAT(:query, '%')) Or lower(product_description) LIKE lower(CONCAT('%', :query))")
    List<Product> searchProducts(String query);

}
