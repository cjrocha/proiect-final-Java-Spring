package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, String> {
    public Product findById(Long id);
}
