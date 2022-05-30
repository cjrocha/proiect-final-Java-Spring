package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Extending JPA repo, this interface
 * communicates with DB.
 */
public interface ProductImagesRepository extends JpaRepository<ProductImages, String> {
    /**
     * Contract method that finds product images
     * from DB by id
     * @param id - the searched id
     * @return - the product images data from DB
     */
    public ProductImages findById(Long id);
}
