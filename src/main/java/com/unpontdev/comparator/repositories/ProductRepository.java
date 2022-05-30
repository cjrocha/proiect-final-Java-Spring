package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    public Product findBypId(Long pId);

}
