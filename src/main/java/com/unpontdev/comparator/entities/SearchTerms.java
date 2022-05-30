package com.unpontdev.comparator.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Held's the search terms object.
 * This data will be showed to admin users for analytical purposes.
 * Implemented and data delivered to DB, but not yet served to admin users
 */
@Entity(name= "search_terms")
@Table(name = "search_terms")
public class SearchTerms implements Serializable {
    public static final String SEQUENCE_NAME = "MY_CLASS_id_seq";

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    @Column(name="search_id")
    private Long id;

    @NotEmpty
    @Column(name = "term")
    private String term;
    @NotEmpty
    @Column(name = "term_url")
    private String termUrl;
    @NotEmpty
    @Column(name = "term_source")
    private String source;

    @OneToMany(mappedBy = "searchTerm")
    private Set<SearchResults> searchResults;

    /**
     * Constructor for search terms object
     * @param searchId
     * @param term
     * @param termUrl
     * @param source
     */
    public SearchTerms(Long searchId, String term, String termUrl, String source) {
        this.id = searchId;
        this.term = term;
        this.termUrl = termUrl;
        this.source = source;
    }
    public SearchTerms(){}

    /**
     * Adds object to DB using repository
     * @param result
     */
    public void addSearchResult(SearchResults result) {
        searchResults.add(result);
        result.setSearchTerm(this);
    }

    /**
     * Removes object from DB using repository
     * @param result
     */
    public void removeSearchResult(SearchResults result) {
        searchResults.remove(result);
        result.setSearchTerm(null);
    }

    /**
     * Setters and getters for search term data
     * @return
     */
    public Long getSearchID() {
        return id;
    }

    public void setSearchID(Long searchID) {
        this.id = searchID;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermUrl() {
        return termUrl;
    }

    public void setTermUrl(String termUrl) {
        this.termUrl = termUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<SearchResults> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(Set<SearchResults> searchResults) {
        this.searchResults = searchResults;
    }

    /**
     * Overiding toString methode to serve readable data
     * @return - readable text
     */
    @Override
    public String toString() {
        return "SearchTerms{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", termUrl='" + termUrl + '\'' +
                ", source='" + source + '\'' +
                ", searchResults=" + searchResults +
                '}';
    }
}
