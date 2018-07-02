package com.example.rahul.goodplays.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Track implements Serializable {

    @SerializedName("track_id")
    @Expose
    private Integer trackId;
    @SerializedName("track_name")
    @Expose
    private String trackName;
    @SerializedName("track_rating")
    @Expose
    private Integer trackRating;
    @SerializedName("track_length")
    @Expose
    private Integer trackLength;
    @SerializedName("has_lyrics")
    @Expose
    private Integer hasLyrics;
    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;
    @SerializedName("album_id")
    @Expose
    private Integer albumId;
    @SerializedName("album_name")
    @Expose
    private String albumName;
    @SerializedName("artist_id")
    @Expose
    private Integer artistId;
    @SerializedName("artist_name")
    @Expose
    private String artistName;
    @SerializedName("album_coverart_100x100")
    @Expose
    private String albumCoverart100x100;
    @SerializedName("first_release_date")
    @Expose
    private String firstReleaseDate;
    @SerializedName("primary_genres")
    @Expose
    private PrimaryGenres primaryGenres;
    @SerializedName("track_share_url")
    @Expose
    private String trackShareUrl;

    public Track(Integer trackId, String trackName, Integer trackRating, Integer trackLength, Integer hasLyrics, Integer lyricsId, Integer albumId, String albumName, Integer artistId, String artistName, String albumCoverart100x100, String firstReleaseDate, PrimaryGenres primaryGenres, String trackShareUrl) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRating = trackRating;
        this.trackLength = trackLength;
        this.hasLyrics = hasLyrics;
        this.lyricsId = lyricsId;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumCoverart100x100 = albumCoverart100x100;
        this.firstReleaseDate = firstReleaseDate;
        this.primaryGenres = primaryGenres;
        this.trackShareUrl = trackShareUrl;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackRating() {
        return trackRating;
    }

    public void setTrackRating(Integer trackRating) {
        this.trackRating = trackRating;
    }

    public Integer getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(Integer trackLength) {
        this.trackLength = trackLength;
    }

    public Integer getHasLyrics() {
        return hasLyrics;
    }

    public void setHasLyrics(Integer hasLyrics) {
        this.hasLyrics = hasLyrics;
    }

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
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

    public String getAlbumCoverart100x100() {
        return albumCoverart100x100;
    }

    public void setAlbumCoverart100x100(String albumCoverart100x100) {
        this.albumCoverart100x100 = albumCoverart100x100;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public PrimaryGenres getPrimaryGenres() {
        return primaryGenres;
    }

    public void setPrimaryGenres(PrimaryGenres primaryGenres) {
        this.primaryGenres = primaryGenres;
    }

    public String getTrackShareUrl() {
        return trackShareUrl;
    }

    public void setTrackShareUrl(String trackShareUrl) {
        this.trackShareUrl = trackShareUrl;
    }
}