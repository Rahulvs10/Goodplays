package com.example.rahul.goodplays.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Body {
    private List<TrackList> track_list = null;

    private List<ArtistList> artist_list = null;

    private List<AlbumList> album_list;

    private Artist artist;

    private Album album;

    private Lyrics lyrics;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<TrackList> getTrackList() {
        return track_list;
    }

    public void setTrackList(List<TrackList> trackList) {
        this.track_list = trackList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public List<ArtistList> getArtistList() {
        return artist_list;
    }

    public void setArtistList(List<ArtistList> artistList) {
        this.artist_list = artistList;
    }

    public Body(List<AlbumList> albumList) {
        super();
        this.album_list = albumList;
    }

    public List<AlbumList> getAlbum_list() {
        return album_list;
    }

    public void setAlbum_list(List<AlbumList> album_list) {
        this.album_list = album_list;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Lyrics getLyrics() {
        return lyrics;
    }

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
    }
}
