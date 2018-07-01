package com.example.rahul.goodplays.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ArtistList implements Serializable{

        @SerializedName("artist")
        @Expose
        private Artist artist;

        /**
         * No args constructor for use in serialization
         *
         */
        public ArtistList() {
        }

        /**
         *
         * @param artist
         */
        public ArtistList(Artist artist) {
            super();
            this.artist = artist;
        }

        public Artist getArtist() {
            return artist;
        }

        public void setArtist(Artist artist) {
            this.artist = artist;
        }

    }

