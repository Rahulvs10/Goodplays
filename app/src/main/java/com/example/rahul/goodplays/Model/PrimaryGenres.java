package com.example.rahul.goodplays.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrimaryGenres implements Serializable {

    @SerializedName("music_genre_list")
    @Expose
    private ArrayList<MusicGenreList> musicGenreList = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public PrimaryGenres() {
    }

    /**
     *
     * @param musicGenreList
     */
    public PrimaryGenres(ArrayList<MusicGenreList> musicGenreList) {
        super();
        this.musicGenreList = musicGenreList;
    }

    public ArrayList<MusicGenreList> getMusicGenreList() {
        return musicGenreList;
    }

    public void setMusicGenreList(ArrayList<MusicGenreList> musicGenreList) {
        this.musicGenreList = musicGenreList;
    }

}