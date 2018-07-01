package com.example.rahul.goodplays.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lyrics {
    @SerializedName("lyrics_body")
    @Expose
    private String lyricsBody;

    public Lyrics(String lyricsBody) {
        this.lyricsBody = lyricsBody;
    }

    public String getLyricsBody() {
        return lyricsBody;
    }

    public void setLyricsBody(String lyricsBody) {
        this.lyricsBody = lyricsBody;
    }
}
