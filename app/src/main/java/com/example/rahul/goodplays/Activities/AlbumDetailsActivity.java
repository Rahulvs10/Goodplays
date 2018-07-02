package com.example.rahul.goodplays.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.goodplays.Adapters.TrackListAdapter;
import com.example.rahul.goodplays.Data.FavouriteContract;
import com.example.rahul.goodplays.Model.Album;
import com.example.rahul.goodplays.Model.Artist;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.Model.PrimaryGenres;
import com.example.rahul.goodplays.Model.Track;
import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.R;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailsActivity extends AppCompatActivity {

    TextView artistField, yearField, ratingField;
    ListView trackList;
    ImageView albumImage;
    ProgressBar progress;

    String name, artist, date, url=null;
    int rating, albumID;
    Album album;


    final String API_KEY = "0266bc6a327f383715ae0dd708606641";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_album_details);

        ratingField =findViewById(R.id.albumRatingText);
        yearField = findViewById(R.id.yearReleasedText);
        artistField = findViewById(R.id.artistNameText);
        trackList = findViewById(R.id.albumTracksList);
        albumImage = findViewById(R.id.albumIMG);
        progress = findViewById(R.id.progressbarAlbum);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Bundle extras = getIntent().getExtras();
        Boolean state = extras.getBoolean("boolean",true);

        if(state){
            name = extras.getString("name");
            albumID = extras.getInt("id");

            Call<Example> artistCall = apiService.getAlbum(albumID, API_KEY);
            artistCall.enqueue(new Callback<Example>()

            {
                @Override
                public void onResponse (Call < Example > call, Response< Example > response){
                    album = response.body().getMessage().getBody().getAlbum();

                    setTitle(name);
                    rating = album.getAlbumRating();
                    date = album.getAlbumReleaseDate();
                    artist = album.getArtistName();
                    url = album.getAlbumEditUrl();

                    artistField.setText(artist);

                    if(rating != 0)
                        ratingField.setText(String.valueOf(rating)+"/100");
                    else
                        ratingField.setText("N/A");

                    if(!(date.equals("")))
                        yearField.setText(date.substring(0,4));
                    else
                        yearField.setText("N/A");

                    new Lyrics().execute(url);

                }

                @Override
                public void onFailure (Call < Example > call, Throwable t){
                    Log.i("Failed",t.getMessage());
                }
            });
        }
        else {
            album = (Album) getIntent().getSerializableExtra("current");
            albumID = album.getAlbumId();
            name = album.getAlbumName();
            setTitle(name);
            rating = album.getAlbumRating();
            date = album.getAlbumReleaseDate();
            artist = album.getArtistName();
            url = album.getAlbumEditUrl();

            artistField.setText(artist);

            if(rating != 0)
                ratingField.setText(String.valueOf(rating)+"/100");
            else
                ratingField.setText("N/A");

            if(!(date.equals("")))
                yearField.setText(date.substring(0,4));
            else
                yearField.setText("N/A");

            new Lyrics().execute(url);
        }




        Call<Example> exampleCall = apiService.getAlbumTracks(albumID,30, API_KEY);

        exampleCall.enqueue(new Callback<Example>()

        {
            @Override
            public void onResponse (Call < Example > call, Response< Example > response){
                List<TrackList> trackLists = response.body().getMessage().getBody().getTrackList();
                trackList.setAdapter(new TrackListAdapter(getApplicationContext(), R.layout.track_list_item, trackLists));
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure (Call < Example > call, Throwable t){
                Log.i("Failed",t.getMessage());
            }
        });

        trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrackListAdapter adapter = (TrackListAdapter) trackList.getAdapter();
                Track current = adapter.getItem(position).getTrack();

                Intent intent = new Intent(AlbumDetailsActivity.this,TrackDetailsActivity.class);
                intent.putExtra("current", current);
                startActivity(intent);
            }
        });
    }


    private class Lyrics extends AsyncTask<String, Void, Void> {
        String imgSrc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... url) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url[0]).get();
                Elements img = document.select("div[class=mxm-album-banner__coverart] img[src]");
                // Locate the src attribute
                imgSrc = img.attr("src");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Picasso.get().load(Uri.parse("http:"+imgSrc)).into(albumImage);
        }
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
                        "Album",
                        String.valueOf(albumID)};

                Cursor cursor = getContentResolver().query(uri,projection, where,whereArgs,null );
                if(cursor.getCount()!=0){
                    Toast.makeText(getApplicationContext(),"Already a favourite.",Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(FavouriteContract.FavouriteEntry.NAME,name);
                    values.put(FavouriteContract.FavouriteEntry.APIID,albumID);
                    values.put(FavouriteContract.FavouriteEntry.RATING,rating);
                    values.put(FavouriteContract.FavouriteEntry.YEAR,date.substring(0,4));
                    values.put(FavouriteContract.FavouriteEntry.ARTIST,artist);
                    values.put(FavouriteContract.FavouriteEntry.TYPE,"Album");

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
