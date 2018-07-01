package com.example.rahul.goodplays.Data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.rahul.goodplays.R;

public class ListCursorAdapter extends CursorAdapter {

    public ListCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fav_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView name =  view.findViewById(R.id.favName);
        TextView artist = view.findViewById(R.id.favArtist);
        TextView album = view.findViewById(R.id.favAlbum);
        TextView genre = view.findViewById(R.id.favGenre);
        TextView rating = view.findViewById(R.id.favRating);
        TextView year = view.findViewById(R.id.favYear);
        TextView type = view.findViewById(R.id.favType);
        TextView length = view.findViewById(R.id.favLength);

        String favType = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.TYPE));
        String favName = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.NAME));
        String favAlbum = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.ALBUM));
        String favArtist = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.ARTIST));
        String favYear = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.YEAR));
        String favLength = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.LENGTH));
        int favRating = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.RATING));
        String favGenre = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteContract.FavouriteEntry.GENRE));

            name.setText(favName);

            if(favAlbum == null)
                album.setVisibility(View.GONE);
            else
                album.setText(favAlbum);

            if(favGenre == null)
                genre.setVisibility(View.GONE);
            else
                genre.setText(favGenre);

            if(favYear == null)
                year.setVisibility(View.GONE);
            else
                year.setText(favYear);

            if(favLength == null)
                length.setVisibility(View.GONE);
            else
                length.setText(favLength);

            if(favArtist == null)
                artist.setVisibility(View.GONE);
            else
                artist.setText(favArtist);

            rating.setText(String.valueOf(favRating)+"/100");
            type.setText(favType);

    }
}
