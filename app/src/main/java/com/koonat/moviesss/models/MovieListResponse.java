package com.koonat.moviesss.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Natig on 3/16/17.
 */

public class MovieListResponse {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<Movie> results = null;

    @SerializedName("total_results")
    private Integer totalResults;
    
    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
