package com.example.rahul.goodplays.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Artist implements Serializable {

    @SerializedName("artist_id")
    @Expose
    private Integer artistId;
    @SerializedName("artist_name")
    @Expose
    private String artistName;
    @SerializedName("artist_country")
    @Expose
    private String artistCountry;
    @SerializedName("artist_rating")
    @Expose
    private Integer artistRating;
    @SerializedName("primary_genres")
    @Expose
    private PrimaryGenres primaryGenres;;
    @SerializedName("artist_twitter_url")
    @Expose
    private String artistTwitterUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public Artist() {
    }

    public Artist(Integer artistId, String artistName, String artistCountry, Integer artistRating, PrimaryGenres primaryGenres, String artistTwitterUrl) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistCountry = artistCountry;
        this.artistRating = artistRating;
        this.primaryGenres = primaryGenres;
        this.artistTwitterUrl = artistTwitterUrl;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistCountry() {
        return artistCountry;
    }

    public void setArtistCountry(String artistCountry) {
        this.artistCountry = artistCountry;
    }

    public Integer getArtistRating() {
        return artistRating;
    }

    public void setArtistRating(Integer artistRating) {
        this.artistRating = artistRating;
    }

    public PrimaryGenres getPrimaryGenres() {
        return primaryGenres;
    }

    public void setPrimaryGenres(PrimaryGenres primaryGenres) {
        this.primaryGenres = primaryGenres;
    }

    public String getArtistTwitterUrl() {
        return artistTwitterUrl;
    }

    public void setArtistTwitterUrl(String artistTwitterUrl) {
        this.artistTwitterUrl = artistTwitterUrl;
    }
}
