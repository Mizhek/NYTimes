package com.example.nytimes.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Media implements Serializable {

        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("media-metadata")
        @Expose
        private List<Metadata> mediaMetadata = null;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }


        public List<Metadata> getMediaMetadata() {
            return mediaMetadata;
        }

        public void setMediaMetadata(List<Metadata> mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
        }

    @Override
    public String toString() {
        return "Media{" +
                "caption='" + caption + '\'' +
                ", mediaMetadata=" + mediaMetadata.toString() +
                '}';
    }
}

