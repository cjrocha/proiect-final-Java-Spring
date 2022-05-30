package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.SearchTerms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Extending JPA repo, this interface
 * communicates with DB.
 */
public interface SearchRepository extends JpaRepository<SearchTerms, String> {
    /**
     * Contract method that finds search terms
     * from DB by pId
     * @param id - the searched term id
     * @return - the search term data from DB
     */
    public SearchTerms findById(Long id);
    /**
     * Contract method that finds search terms
     * from DB
     * @return - the list of search term data from DB
     */
    public List<SearchTerms> findAllByOrderByIdDesc();

}
