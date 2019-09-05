package com.example.nytimes.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pojo implements Serializable {

    @SerializedName("results")
    @Expose
    private List<Article> mResults = null;

    public List<Article> getResults() {
        return mResults;
    }

}









