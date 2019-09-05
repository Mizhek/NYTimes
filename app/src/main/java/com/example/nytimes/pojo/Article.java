package com.example.nytimes.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Article implements Serializable {


        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("adx_keywords")
        @Expose
        private String adxKeywords;
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("byline")
        @Expose
        private String byline;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("media")
        @Expose
        private List<Media> media = null;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAdxKeywords() {
            return adxKeywords;
        }

        public void setAdxKeywords(String adxKeywords) {
            this.adxKeywords = adxKeywords;
        }


        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstract() {
            return _abstract;
        }

        public void setAbstract(String _abstract) {
            this._abstract = _abstract;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public List<Media> getMedia() {
            return media;
        }

        public void setMedia(List<Media> media) {
            this.media = media;
        }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", adxKeywords='" + adxKeywords + '\'' +
                ", section='" + section + '\'' +
                ", id=" + id +
                ", byline='" + byline + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", _abstract='" + _abstract + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", media=" + media.toString() +
                '}';
    }
}

