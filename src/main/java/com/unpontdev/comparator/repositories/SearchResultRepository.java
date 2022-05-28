package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.SearchResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchResultRepository extends JpaRepository<SearchResults, String> {
    @Query(value = "select u from search_results u where cast(result_id as char) like 'idToSearch%'")
    public List<SearchResults> find(@Param("idToSearch") String IdToSearch);


}
