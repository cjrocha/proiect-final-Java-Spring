package com.unpontdev.comparator.repositories;

import com.unpontdev.comparator.entities.SearchResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Extending JPA repo, this interface
 * communicates with DB.
 */
public interface SearchResultRepository extends JpaRepository<SearchResults, String> {
    /**
     * Method that finds search results terms
     * from DB by pId
     * @param idToSearch - the searched result term id
     * @return - the search result term data from DB
     */
    @Query(value = "select u from search_results u where cast(result_id as char) like 'idToSearch%'")
    public List<SearchResults> find(@Param("idToSearch") String idToSearch);


}
