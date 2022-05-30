package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
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

}
