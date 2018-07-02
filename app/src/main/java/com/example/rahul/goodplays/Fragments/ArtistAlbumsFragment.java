package com.example.rahul.goodplays.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.goodplays.Activities.AlbumDetailsActivity;
import com.example.rahul.goodplays.Adapters.AlbumListAdapter;
import com.example.rahul.goodplays.Adapters.ArtistListAdapter;
import com.example.rahul.goodplays.Model.Album;
import com.example.rahul.goodplays.Model.AlbumList;
import com.example.rahul.goodplays.Model.ArtistList;
import com.example.rahul.goodplays.Model.Example;
import com.example.rahul.goodplays.R;
import com.example.rahul.goodplays.Rest.ApiClient;
import com.example.rahul.goodplays.Rest.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistAlbumsFragment extends Fragment {


    ListView albumsList;
    ProgressBar progressBar;
    final String API_KEY = "0266bc6a327f383715ae0dd708606641";
    String name;
    int id;
    SwipeRefreshLayout swipeRefreshLayout;

    public ArtistAlbumsFragment() {
        // Required empty public constructor
    }

    public ArtistAlbumsFragment( int curID) {
        id = curID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list_view, container, false);

        albumsList = rootView.findViewById(R.id.listView);
        progressBar = rootView.findViewById(R.id.progressbar);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);

        loaddata();

        albumsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumListAdapter adapter = (AlbumListAdapter) albumsList.getAdapter();
                Album current = adapter.getItem(position).getAlbum();

                Intent intent = new Intent(getActivity(),AlbumDetailsActivity.class);
                intent.putExtra("current", current);
                intent.putExtra("boolean",false);
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

            Call<Example> exampleCall = apiService.getArtistAlbums(100, "desc", id, API_KEY);

            exampleCall.enqueue(new Callback<Example>()

            {
                @Override
                public void onResponse (Call < Example > call, Response< Example > response){
                    List<AlbumList> albumLists = response.body().getMessage().getBody().getAlbum_list();
                    progressBar.setVisibility(View.GONE);
                    albumsList.setAdapter(new AlbumListAdapter(getActivity(),R.layout.track_list_item, albumLists));
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

}
