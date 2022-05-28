package com.unpontdev.comparator.entities;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

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

    public SearchResults(){}

    public SearchResults(String ID, String resultName, String resultUrl) {
        this.id = ID;
        this.resultName = resultName;
        this.resultUrl = resultUrl;
    }

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
