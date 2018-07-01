package com.example.rahul.goodplays.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlbumList {

    @SerializedName("album")
    @Expose
    private Album album;

    /**
     * No args constructor for use in serialization
     *
     */
    public AlbumList() {
    }

    /**
     *
     * @param album
     */
    public AlbumList(Album album) {
        super();
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

}