package com.example.rahul.goodplays.Activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rahul.goodplays.Data.FavouriteContract;
import com.example.rahul.goodplays.Data.ListCursorAdapter;
import com.example.rahul.goodplays.R;

public class FavouriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int FAVOURITE_LOADER = 0;
    ListView favList;
    ListCursorAdapter mAdapter;
    TextView empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        favList = findViewById(R.id.favouritesList);
        empty = findViewById(R.id.emptyfav);
        mAdapter = new ListCursorAdapter(this,null);
        favList.setAdapter(mAdapter);
        favList.setEmptyView(empty);

        getLoaderManager().initLoader(FAVOURITE_LOADER, null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, FavouriteContract.FavouriteEntry.CONTENT_URI, null, null, null, null);
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
