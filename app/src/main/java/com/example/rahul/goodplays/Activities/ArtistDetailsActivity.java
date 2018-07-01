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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.goodplays.Adapters.AlbumListAdapter;
import com.example.rahul.goodplays.Adapters.TrackListAdapter;
import com.example.rahul.goodplays.Data.FavouriteContract;
import com.example.rahul.goodplays.Model.Album;
import com.example.rahul.goodplays.Model.AlbumList;
import com.example.rahul.goodplays.Model.Artist;
import com.example.rahul.goodplays.Model.ArtistList;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.Model.PrimaryGenres;
import com.example.rahul.goodplays.Model.Track;
import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.R;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistDetailsActivity extends AppCompatActivity {

    TextView ratingField, nationalityField, genresField, emptyField, emptyField2;
    LinearLayout tracksLayout;
    FrameLayout albumsLayout;
    ListView topTracks, topAlbums;
    ImageView twitterButton;
    Boolean aBoolean;

    String name, Nationality, genre;
    int rating, artistID;
    PrimaryGenres primaryGenres;
    Artist artist;

    ProgressBar progressBar1, progressBar2;

    final String API_KEY = "0266bc6a327f383715ae0dd708606641";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_artist_details);

        ratingField = findViewById(R.id.ratingTextView);
        nationalityField = findViewById(R.id.nationalityTextView);
        genresField = findViewById(R.id.genreTextView);
        emptyField = findViewById(R.id.empty2);
        emptyField2 = findViewById(R.id.empty1);

        tracksLayout = findViewById(R.id.moreTracksLayout);
        albumsLayout = findViewById(R.id.moreAlbumsLayout);

        progressBar1 = findViewById(R.id.progressbar1);
        progressBar2 = findViewById(R.id.progressbar2);

        topAlbums = findViewById(R.id.topAlbumsList);
        topAlbums.setClickable(false);
        topTracks = findViewById(R.id.topTracksList);
        topTracks.setClickable(false);

        twitterButton = findViewById(R.id.twitterButton);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Bundle extras = getIntent().getExtras();
         aBoolean = extras.getBoolean("boolean", true);

         if(aBoolean){

             name = extras.getString("name");
             artistID = extras.getInt("id");

             Call<Example> artistCall = apiService.getArtist(artistID, API_KEY);
             artistCall.enqueue(new Callback<Example>()

             {
                 @Override
                 public void onResponse (Call < Example > call, Response< Example > response){
                     artist = response.body().getMessage().getBody().getArtist();

                     setTitle(artist.getArtistName());
                     artistID = artist.getArtistId();
                     rating = artist.getArtistRating();
                     Nationality = artist.getArtistCountry();
                     primaryGenres =  artist.getPrimaryGenres();
                     final String uri = artist.getArtistTwitterUrl();

                     if(rating != 0)
                         ratingField.setText(String.valueOf(rating)+"/100");
                     else
                         ratingField.setText("N/A");

                     if(Nationality.isEmpty())
                         nationalityField.setText("N/A");
                     else
                         nationalityField.setText(Nationality);

                     genre = getGenre(primaryGenres);
                     if(primaryGenres.getMusicGenreList().size()!=0){
                         genresField.setText(genre);
                     }
                     else
                         genresField.setText("Genre N/A");

                     twitterButton.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             if(!(uri.equals(""))){
                                 Uri twitterURI = Uri.parse(uri);
                                 Intent details = new Intent(Intent.ACTION_VIEW, twitterURI);
                                 startActivity(details);}
                             else
                                 Toast.makeText(getApplicationContext(),"Twitter id not available", Toast.LENGTH_SHORT).show();
                         }
                     });
                 }

                 @Override
                 public void onFailure (Call < Example > call, Throwable t){
                     Log.i("Failed",t.getMessage());
                 }
             });

         }

         else{
             artist = (Artist) getIntent().getSerializableExtra("current");
             name =artist.getArtistName();
             setTitle(name);
             artistID = artist.getArtistId();
             rating = artist.getArtistRating();
             Nationality = artist.getArtistCountry();
             primaryGenres =  artist.getPrimaryGenres();
             final String uri = artist.getArtistTwitterUrl();

             if(rating != 0)
                 ratingField.setText(String.valueOf(rating)+"/100");
             else
                 ratingField.setText("N/A");

             if(Nationality.isEmpty())
                 nationalityField.setText("N/A");
             else
                 nationalityField.setText(Nationality);

             genre = getGenre(primaryGenres);
             if(primaryGenres.getMusicGenreList().size()!=0){
                 genresField.setText(genre);
             }
             else
                 genresField.setText("Genre N/A");

             twitterButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(!(uri.equals(""))){
                         Uri twitterURI = Uri.parse(uri);
                         Intent details = new Intent(Intent.ACTION_VIEW, twitterURI);
                         startActivity(details);}
                     else
                         Toast.makeText(getApplicationContext(),"Twitter id not available", Toast.LENGTH_SHORT).show();
                 }
             });

         }

        Call<Example> exampleCall = apiService.getArtistTracks(3, "desc", artistID, API_KEY);

        exampleCall.enqueue(new Callback<Example>()

        {
            @Override
            public void onResponse (Call < Example > call, Response< Example > response){
                List<TrackList> trackLists = response.body().getMessage().getBody().getTrackList();
                topTracks.setAdapter(new TrackListAdapter(getApplicationContext(),R.layout.track_list_item, trackLists));
                ratingField.requestFocus();
                progressBar1.setVisibility(View.INVISIBLE);
                emptyField2.setVisibility(View.VISIBLE);
                justifyListViewHeightBasedOnChildren(topTracks);
            }

            @Override
            public void onFailure (Call < Example > call, Throwable t){
                Log.i("Failed",t.getMessage());
            }
        });

        Call<Example> call2 = apiService.getArtistAlbums(3, "desc", artistID, API_KEY);

        call2.enqueue(new Callback<Example>()

        {
            @Override
            public void onResponse (Call < Example > call, Response< Example > response){
                List<AlbumList> albumLists = response.body().getMessage().getBody().getAlbum_list();
                topAlbums.setAdapter(new AlbumListAdapter(getApplicationContext(),R.layout.track_list_item, albumLists));
                topAlbums.setEmptyView(emptyField);
                ratingField.requestFocus();
                progressBar2.setVisibility(View.INVISIBLE);
                emptyField.setVisibility(View.VISIBLE);
                justifyListViewHeightBasedOnChildren(topAlbums);

                if(albumLists.size() == 0)
                    albumsLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure (Call < Example > call, Throwable t){
                Log.i("Failed",t.getMessage());
                ratingField.requestFocus();
            }
        });

        topTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrackListAdapter adapter = (TrackListAdapter) topTracks.getAdapter();
                Track current = adapter.getItem(position).getTrack();

                Intent intent = new Intent(ArtistDetailsActivity.this,TrackDetailsActivity.class);
                intent.putExtra("current", current);
                startActivity(intent);
            }
        });

        tracksLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ArtistTracksActivity.class);
                i.putExtra("name",name);
                i.putExtra("id",artistID);
                startActivity(i);
            }
        });

        topAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumListAdapter adapter = (AlbumListAdapter) topAlbums.getAdapter();
                Album current = adapter.getItem(position).getAlbum();

                Intent intent = new Intent(ArtistDetailsActivity.this,AlbumDetailsActivity.class);
                intent.putExtra("current", current);
                intent.putExtra("boolean",false);
                startActivity(intent);
            }
        });

        albumsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ArtistAlbumsActivity.class);
                i.putExtra("name",name);
                i.putExtra("id",artistID);
                startActivity(i);
            }
        });
    }

    public static void justifyListViewHeightBasedOnChildren (ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private String getGenre(PrimaryGenres primaryGenre) {

        String val = "";
        for(int i = 0; i < primaryGenre.getMusicGenreList().size(); i++){
            val = val + primaryGenre.getMusicGenreList().get(i).getMusicGenre().getMusicGenreName()+", ";
        }

        if(val.equals(""))
            return val;
        else
            return val.substring(0,val.length() - 2);
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
                        "Artist",
                        String.valueOf(artistID)};

                Cursor cursor = getContentResolver().query(uri,projection, where,whereArgs,null );
                if(cursor.getCount()!=0){
                    Toast.makeText(getApplicationContext(),"Already a favourite.",Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(FavouriteContract.FavouriteEntry.NAME,name);
                    values.put(FavouriteContract.FavouriteEntry.APIID,artistID);
                    values.put(FavouriteContract.FavouriteEntry.RATING,rating);
                    values.put(FavouriteContract.FavouriteEntry.LENGTH,Nationality);
                    values.put(FavouriteContract.FavouriteEntry.GENRE,genre);
                    values.put(FavouriteContract.FavouriteEntry.TYPE,"Artist");

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
