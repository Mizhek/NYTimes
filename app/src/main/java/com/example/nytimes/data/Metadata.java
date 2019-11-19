package com.example.nytimes.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Metadata implements Serializable {
        @SerializedName("url")
        @Expose
        private String url;


        public String getUrl() {
            return url;
        }

    @Override
    public String toString() {
        return "Metadata{" +
                "url='" + url + '\'' +
                '}';
    }
}

