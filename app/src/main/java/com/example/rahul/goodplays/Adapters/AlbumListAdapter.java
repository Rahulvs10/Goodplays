package com.example.rahul.goodplays.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.goodplays.Model.AlbumList;
import com.example.rahul.goodplays.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumListAdapter extends ArrayAdapter<AlbumList> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItemView = convertView;
        if(ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.track_list_item, parent, false);
        }
        AlbumList current = getItem(position);

        TextView albumName =  ListItemView.findViewById(R.id.name);
        TextView artistName =  ListItemView.findViewById(R.id.artist);
        TextView releaseYear =  ListItemView.findViewById(R.id.year);
        ImageView imageView =  ListItemView.findViewById(R.id.IMG);

        albumName.setText(current.getAlbum().getAlbumName());
        artistName.setText(current.getAlbum().getArtistName());

        if(!((current.getAlbum().getAlbumReleaseDate()).equals("")))
            releaseYear.setText((current.getAlbum().getAlbumReleaseDate()).substring(0,4));

        Picasso.get().load(current.getAlbum().getAlbumCoverart100x100()).into(imageView);

        return ListItemView;
    }

    public AlbumListAdapter(@NonNull Context context, int resource, @NonNull List<AlbumList> objects) {
        super(context, resource, objects);
    }
}
