package com.example.rahul.goodplays.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rahul.goodplays.Adapters.ArtistListAdapter;
import com.example.rahul.goodplays.Adapters.TrackListAdapter;
import com.example.rahul.goodplays.Model.Artist;
import com.example.rahul.goodplays.Model.ArtistList;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;
import com.example.rahul.goodplays.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistsActivity extends AppCompatActivity {

    final String API_KEY = "0266bc6a327f383715ae0dd708606641";
    ProgressBar progressBar;
    ListView artistListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_list_view);


        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        artistListView = findViewById(R.id.listView);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Example> exampleCall = apiService.getTopArtists(50, API_KEY);

            exampleCall.enqueue(new Callback<Example>()

            {
                @Override
                public void onResponse (Call < Example > call, Response< Example > response){
                    final List<ArtistList> artistList = response.body().getMessage().getBody().getArtistList();
                    artistListView.setAdapter(new ArtistListAdapter(getApplicationContext(), R.layout.artist_list_item, artistList));
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure (Call < Example > call, Throwable t){
                    Log.i("Failed",t.getMessage());
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Not connected to internet",Toast.LENGTH_SHORT).show();
        }

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistListAdapter adapter = (ArtistListAdapter) artistListView.getAdapter();
                Artist current = adapter.getItem(position).getArtist();

                Intent intent = new Intent(ArtistsActivity.this,ArtistDetailsActivity.class);
                intent.putExtra("current", current);
                intent.putExtra("boolean",false);
                startActivity(intent);
            }
        });
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
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem search_item = menu.findItem(R.id.mi_search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search artists");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){
                    progressBar.setVisibility(View.VISIBLE);
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);

                    Call<Example> searchCall = apiService.searchArtist(50,"desc",s, API_KEY);

                    searchCall.enqueue(new Callback<Example>()

                    {
                        @Override
                        public void onResponse (Call < Example > call, Response< Example > response){
                            final List<ArtistList> artistList = response.body().getMessage().getBody().getArtistList();
                            artistListView.setAdapter(new ArtistListAdapter(getApplicationContext(), R.layout.artist_list_item, artistList));
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure (Call < Example > call, Throwable t){
                            Log.i("Failed",t.getMessage());
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Not connected to internet",Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

}
