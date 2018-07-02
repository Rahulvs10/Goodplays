package com.example.rahul.goodplays.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.goodplays.Activities.TrackDetailsActivity;
import com.example.rahul.goodplays.Adapters.TrackListAdapter;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.Model.Track;
import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.R;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TracksFragment extends Fragment {

    final String API_KEY = "0266bc6a327f383715ae0dd708606641";
    ProgressBar progressBar;
    ListView trackList;
    SwipeRefreshLayout swipeRefreshLayout;

    public TracksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list_view, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        progressBar = rootView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        trackList = rootView.findViewById(R.id.listView);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);

        loaddata();

        trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrackListAdapter adapter = (TrackListAdapter) trackList.getAdapter();
                Track current = adapter.getItem(position).getTrack();

                Intent intent = new Intent(getActivity(),TrackDetailsActivity.class);
                intent.putExtra("current", current);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata();
            }
        });

        return rootView;

    }

    private void loaddata() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<Example> exampleCall = apiService.getPopularTracks(50, API_KEY);

            exampleCall.enqueue(new Callback<Example>()

            {
                @Override
                public void onResponse (Call < Example > call, Response< Example > response){
                    List<TrackList> trackLists = response.body().getMessage().getBody().getTrackList();
                    trackList.setAdapter(new TrackListAdapter(getActivity(), R.layout.track_list_item, trackLists));
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure (Call < Example > call, Throwable t){
                    Log.i("Failed",t.getMessage());
                }
            });
        }
        else{
            Toast.makeText(getActivity(),"Not connected to internet",Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem search_item = menu.findItem(R.id.mi_search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search tracks");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                ConnectivityManager cm =
                        (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){
                    progressBar.setVisibility(View.VISIBLE);
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);

                    Call<Example> searchCall = apiService.searchTracks(50,"desc",s, API_KEY);

                    searchCall.enqueue(new Callback<Example>()

                    {
                        @Override
                        public void onResponse (Call < Example > call, Response< Example > response){
                            List<TrackList> trackLists = response.body().getMessage().getBody().getTrackList();
                            trackList.setAdapter(new TrackListAdapter(getActivity(), R.layout.track_list_item, trackLists));
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure (Call < Example > call, Throwable t){
                            Log.i("Failed",t.getMessage());
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(),"Not connected to internet",Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
