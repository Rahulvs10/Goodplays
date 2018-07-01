package com.example.rahul.goodplays.Activities;

import android.content.Context;
import android.content.Intent;
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

import com.example.rahul.goodplays.Adapters.AlbumListAdapter;
import com.example.rahul.goodplays.Adapters.TrackListAdapter;
import com.example.rahul.goodplays.Model.Album;
import com.example.rahul.goodplays.Model.AlbumList;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.Model.Track;
import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.R;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistAlbumsActivity extends AppCompatActivity {

    ListView albumsList;
    ProgressBar progressBar;
    final String API_KEY = "0266bc6a327f383715ae0dd708606641";
    String name;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_artist_albums);

        Bundle extras = getIntent().getExtras();
        name = Objects.requireNonNull(extras).getString("name");
        id = extras.getInt("id");
        setTitle(name + "'s albums");


        albumsList = findViewById(R.id.artistTracksList);
        progressBar = findViewById(R.id.progressbar);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Example> exampleCall = apiService.getArtistAlbums(100, "desc", id, API_KEY);

        exampleCall.enqueue(new Callback<Example>()

        {
            @Override
            public void onResponse (Call < Example > call, Response< Example > response){
                List<AlbumList> albumLists = response.body().getMessage().getBody().getAlbum_list();
                progressBar.setVisibility(View.GONE);
                albumsList.setAdapter(new AlbumListAdapter(getApplicationContext(),R.layout.track_list_item, albumLists));
            }

            @Override
            public void onFailure (Call < Example > call, Throwable t){
                Log.i("Failed",t.getMessage());
            }
        });

        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumListAdapter adapter = (AlbumListAdapter) albumsList.getAdapter();
                Album current = adapter.getItem(position).getAlbum();

                Intent intent = new Intent(getApplicationContext(),AlbumDetailsActivity.class);
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
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
