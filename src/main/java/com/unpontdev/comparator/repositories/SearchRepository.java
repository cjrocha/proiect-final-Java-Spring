package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.SearchTerms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchTerms, String> {
    public SearchTerms findById(Long Id);
    public List<SearchTerms> findAllByOrderByIdDesc();
    public List<SearchTerms> findAllByOrderByIdAsc();
}
