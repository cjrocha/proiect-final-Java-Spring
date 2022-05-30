package com.unpontdev.comparator.entities;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * Held's the search results object(urls and pages name).
 * This data will be showed to admin users for analytical purposes.
 * Implemented and data delivered to DB, but not yet served to admin users
 */
@Entity(name = "search_results")
@Table(name = "search_results")
public class SearchResults {
    @Id
    @Column(name="result_id")
    private String id;
    @NotEmpty
    @Column(name="search_result_name")
    private String resultName;
    @NotEmpty
    @Column(name="search_result_url")
    private String resultUrl;

    @ManyToOne
    private SearchTerms searchTerm;

    /**
     * Constyructor for search results object
     * @param ID
     * @param resultName
     * @param resultUrl
     */
    public SearchResults(String ID, String resultName, String resultUrl) {
        this.id = ID;
        this.resultName = resultName;
        this.resultUrl = resultUrl;
    }

    public SearchResults(){}

    /**
     * Overiding equals and hashCode
     * for filtering and sorting purposes
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResults)) return false;
        SearchResults that = (SearchResults) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Setters and getters for search results data
     */
    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public SearchTerms getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(SearchTerms searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public String toString() {
        return "SearchResults{" +
                "id=" + id +
                ", resultName='" + resultName + '\'' +
                ", resultUrl='" + resultUrl + '\'' +
                ", searchTerm=" + searchTerm +
                '}';
    }
}
