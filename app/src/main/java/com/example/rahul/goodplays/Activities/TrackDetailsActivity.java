package com.example.rahul.goodplays.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.goodplays.Adapters.TrackListAdapter;
import com.example.rahul.goodplays.Data.FavouriteContract;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.Model.Lyrics;
import com.example.rahul.goodplays.Model.PrimaryGenres;
import com.example.rahul.goodplays.Model.Track;
import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.R;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackDetailsActivity extends AppCompatActivity {

    TextView genreText, yearText, lengthText, ratingText, artistText, albumText, lyricsText, lyricsView;
    LinearLayout artistLayout, albumLayout;
    ImageView trackImage;
    ProgressBar progressBar;
    int trackID, rating, length;
    String name, date, genre, artist, album;

    final String API_KEY = "0266bc6a327f383715ae0dd708606641";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_track_details);

        Bundle extras = getIntent().getExtras();
        Track track = (Track) getIntent().getSerializableExtra("current");
        name = track.getTrackName();
        setTitle(name);

         trackID = track.getTrackId();
         rating = track.getTrackRating();
         length = track.getTrackLength();
         date = track.getFirstReleaseDate();
        PrimaryGenres primaryGenres = track.getPrimaryGenres();
          artist = track.getArtistName();
          album = track.getAlbumName();
        final int artistID = track.getArtistId();
        final int albumID = track.getAlbumId();

        genreText = findViewById(R.id.genreText);
        ratingText = findViewById(R.id.ratingText);
        lengthText = findViewById(R.id.lengthText);
        yearText = findViewById(R.id.yearText);
        artistText = findViewById(R.id.artistText);
        albumText = findViewById(R.id.albumText);
        artistLayout = findViewById(R.id.artistLayout);
        albumLayout = findViewById(R.id.albumLayout);
        lyricsView = findViewById(R.id.lyricsDetails);
        lyricsText = findViewById(R.id.lyricsText);
        trackImage = findViewById(R.id.trackIMG);
        progressBar = findViewById(R.id.progressDetail);

        if(primaryGenres.getMusicGenreList().size()!=0){
            genre = getGenre(primaryGenres);
            genreText.setText(genre);
        }
        else
            genreText.setText("");

        if(rating != 0)
            ratingText.setText("Rating : " + String.valueOf(rating) + "/100");
        else
            ratingText.setText("Rating : N/A");

        if(length != 0)
        lengthText.setText(getLength(length));
        else
            lengthText.setText("Length N/A");
        artistText.setText(artist);
        albumText.setText(album);

        if(date != null)
            yearText.setText(date.substring(0,4));
        else
            yearText.setText("Year N/A");

        Picasso.get().load(track.getAlbumCoverart100x100()).into(trackImage);

        artistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ArtistDetailsActivity.class);
                i.putExtra("id",artistID);
                i.putExtra("name", artist);
                i.putExtra("boolean",true);
                startActivity(i);
            }
        });
        albumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AlbumDetailsActivity.class);
                i.putExtra("id",albumID);
                i.putExtra("name", album);
                i.putExtra("boolean",true);
                startActivity(i);
            }
        });

        if(track.getHasLyrics() != 0){
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Example> exampleCall = apiService.getTrackLyrics(trackID, API_KEY);

            exampleCall.enqueue(new Callback<Example>()

            {
                @Override
                public void onResponse (Call < Example > call, Response< Example > response){
                    lyricsView.setVisibility(View.VISIBLE);
                    Lyrics lyrics = response.body().getMessage().getBody().getLyrics();
                    progressBar.setVisibility(View.GONE);

                    if(lyrics.getLyricsBody() != null) {
                        lyricsText.setText(lyrics.getLyricsBody());
                        lyricsText.setVisibility(View.VISIBLE);
                    }
                    else{
                        lyricsView.setVisibility(View.INVISIBLE);
                        lyricsText.setText("Lyrics not found!!");
                    }
                }

                @Override
                public void onFailure (Call < Example > call, Throwable t){
                    Log.i("Failed",t.getMessage());
                }
            });
        }

    }

    private String getGenre(PrimaryGenres primaryGenre) {
        String val = "";
        for(int i = 0; i < primaryGenre.getMusicGenreList().size(); i++){
            val = val + primaryGenre.getMusicGenreList().get(i).getMusicGenre().getMusicGenreName()+", ";
        }
        return val.substring(0,val.length() - 2);
    }

    private String getLength(int length) {
        int min = (int)(length/60);
        String sec = String.format("%02d", length % 60);
        return min+":"+sec;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    onBackPressed();
                }
                return true;
            case R.id.fav_menu:

                Uri uri = FavouriteContract.FavouriteEntry.CONTENT_URI;
                String[] projection = {FavouriteContract.FavouriteEntry.ID, FavouriteContract.FavouriteEntry.NAME};
                String where = "Type = ? AND Api_ID = ?";
                String[] whereArgs = new String[] {
                        "Track",
                        String.valueOf(trackID)};

                Cursor cursor = getContentResolver().query(uri,projection, where,whereArgs,null );
                if(cursor.getCount()!=0){
                    Toast.makeText(getApplicationContext(),"Already a favourite.",Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(FavouriteContract.FavouriteEntry.NAME,name);
                    values.put(FavouriteContract.FavouriteEntry.APIID,trackID);
                    values.put(FavouriteContract.FavouriteEntry.RATING,rating);
                    values.put(FavouriteContract.FavouriteEntry.LENGTH,getLength(length));
                    values.put(FavouriteContract.FavouriteEntry.YEAR,date.substring(0,4));
                    values.put(FavouriteContract.FavouriteEntry.GENRE,genre);
                    values.put(FavouriteContract.FavouriteEntry.ALBUM,album);
                    values.put(FavouriteContract.FavouriteEntry.ARTIST,artist);
                    values.put(FavouriteContract.FavouriteEntry.TYPE,"Track");

                    Uri newUri = getContentResolver().insert(FavouriteContract.FavouriteEntry.CONTENT_URI, values);
                    // Show a toast message depending on whether or not the insertion was successful
                    if (newUri == null) {
                        // If the new content URI is null, then there was an error with insertion.
                        Toast.makeText(this, "Error adding to favourites.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Otherwise, the insertion was successful and we can display a toast.
                        Toast.makeText(this, "Added to favourites.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        return true;
    }
}
