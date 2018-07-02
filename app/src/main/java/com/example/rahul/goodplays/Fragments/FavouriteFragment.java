package com.example.rahul.goodplays.Fragments;



import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;


import com.example.rahul.goodplays.Data.FavouriteContract;
import com.example.rahul.goodplays.Data.ListCursorAdapter;
import com.example.rahul.goodplays.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public FavouriteFragment() {
        // Required empty public constructor
    }

    private static final int FAVOURITE_LOADER = 0;
    ListView favList;
    ListCursorAdapter mAdapter;
    TextView empty;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list_view, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        favList = rootView.findViewById(R.id.listView);
        empty = rootView.findViewById(R.id.listEmptyView);
        empty.setText("Its loney here. Add your favourites to this list.");
        progressBar = rootView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        mAdapter = new ListCursorAdapter(getActivity(),null);
        favList.setAdapter(mAdapter);
        favList.setEmptyView(empty);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), FavouriteContract.FavouriteEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
