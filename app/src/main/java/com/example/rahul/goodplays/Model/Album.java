package com.example.rahul.goodplays.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Album implements Serializable {

    @SerializedName("album_id")
    @Expose
    private Integer albumId;
    @SerializedName("album_name")
    @Expose
    private String albumName;
    @SerializedName("album_rating")
    @Expose
    private Integer albumRating;
    @SerializedName("album_track_count")
    @Expose
    private Integer albumTrackCount;
    @SerializedName("album_release_date")
    @Expose
    private String albumReleaseDate;
    @SerializedName("artist_id")
    @Expose
    private Integer artistId;
    @SerializedName("artist_name")
    @Expose
    private String artistName;
    @SerializedName("album_label")
    @Expose
    private String albumLabel;
    @SerializedName("album_coverart_100x100")
    @Expose
    private String albumCoverart100x100;
    @SerializedName("album_edit_url")
    @Expose
    private String albumEditUrl;

    public Album(Integer albumId, String albumName, Integer albumRating, Integer albumTrackCount, String albumReleaseDate, Integer artistId, String artistName, String albumLabel, String albumCoverart100x100, String albumEditUrl) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumRating = albumRating;
        this.albumTrackCount = albumTrackCount;
        this.albumReleaseDate = albumReleaseDate;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumLabel = albumLabel;
        this.albumCoverart100x100 = albumCoverart100x100;
        this.albumEditUrl = albumEditUrl;
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

    public Integer getAlbumRating() {
        return albumRating;
    }

    public void setAlbumRating(Integer albumRating) {
        this.albumRating = albumRating;
    }

    public Integer getAlbumTrackCount() {
        return albumTrackCount;
    }

    public void setAlbumTrackCount(Integer albumTrackCount) {
        this.albumTrackCount = albumTrackCount;
    }

    public String getAlbumReleaseDate() {
        return albumReleaseDate;
    }

    public void setAlbumReleaseDate(String albumReleaseDate) {
        this.albumReleaseDate = albumReleaseDate;
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

    public String getAlbumLabel() {
        return albumLabel;
    }

    public void setAlbumLabel(String albumLabel) {
        this.albumLabel = albumLabel;
    }

    public String getAlbumCoverart100x100() {
        return albumCoverart100x100;
    }

    public void setAlbumCoverart100x100(String albumCoverart100x100) {
        this.albumCoverart100x100 = albumCoverart100x100;
    }

    public String getAlbumEditUrl() {
        return albumEditUrl;
    }

    public void setAlbumEditUrl(String albumEditUrl) {
        this.albumEditUrl = albumEditUrl;
    }
}