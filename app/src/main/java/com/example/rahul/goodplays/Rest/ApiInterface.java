package com.example.rahul.goodplays.Rest;

import com.example.rahul.goodplays.Model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //To get popular tracks
    @GET("chart.tracks.get")
    Call<Example> getPopularTracks(@Query("page_size") int size, @Query("apikey") String apiKey);

    //To get top artists
    @GET("chart.artists.get")
    Call<Example> getTopArtists(@Query("page_size") int size ,@Query("apikey") String apiKey);

    @GET("track.search")
    Call<Example> getArtistTracks(@Query("page_size") int size, @Query("s_track_rating") String sort , @Query("f_artist_id") int id , @Query("apikey") String apiKey);

    @GET("artist.albums.get")
    Call<Example> getArtistAlbums(@Query("page_size") int size, @Query("s_album_rating") String sort , @Query("artist_id") int id , @Query("apikey") String apiKey);

    @GET("artist.get")
    Call<Example> getArtist(@Query("artist_id") int id , @Query("apikey") String apiKey);

    @GET("album.get")
    Call<Example> getAlbum(@Query("album_id") int id , @Query("apikey") String apiKey);

    @GET("album.tracks.get")
    Call<Example> getAlbumTracks(@Query("album_id") int id , @Query("page_size") int size , @Query("apikey") String apiKey);

    @GET("track.lyrics.get")
    Call<Example> getTrackLyrics(@Query("track_id") int id , @Query("apikey") String apiKey);

    @GET("track.search")
    Call<Example> searchTracks(@Query("page_size") int size, @Query("s_track_rating") String sort , @Query("q_track") String trackname , @Query("apikey") String apiKey);

    @GET("artist.search")
    Call<Example> searchArtist(@Query("page_size") int size, @Query("s_artist_rating") String sort , @Query("q_artist") String trackname , @Query("apikey") String apiKey);

    @GET("track.search")
    Call<Example> searchArtistTracks(@Query("page_size") int size, @Query("s_track_rating") String sort, @Query("f_artist_id") int id , @Query("q_track") String trackname , @Query("apikey") String apiKey);


}
