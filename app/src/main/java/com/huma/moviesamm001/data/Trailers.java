package com.huma.moviesamm001.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Trailers {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<Trailer> results = new ArrayList<>();

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Trailer> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
