package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImages, String> {
    public ProductImages findById(Long id);
}
