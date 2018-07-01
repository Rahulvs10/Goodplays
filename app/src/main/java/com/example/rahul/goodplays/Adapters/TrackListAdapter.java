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

import com.example.rahul.goodplays.Model.TrackList;
import com.example.rahul.goodplays.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackListAdapter extends ArrayAdapter<TrackList> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItemView = convertView;
        if(ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.track_list_item, parent, false);
        }
        TrackList current = getItem(position);

        TextView trackName =  ListItemView.findViewById(R.id.name);
        TextView artistName =  ListItemView.findViewById(R.id.artist);
        TextView releaseYear =  ListItemView.findViewById(R.id.year);
        ImageView imageView =  ListItemView.findViewById(R.id.IMG);

        trackName.setText(current.getTrack().getTrackName());
        artistName.setText(current.getTrack().getArtistName());

        if(!((current.getTrack().getFirstReleaseDate()).equals("")))
            releaseYear.setText((current.getTrack().getFirstReleaseDate()).substring(0,4));

        Picasso.get().load(current.getTrack().getAlbumCoverart100x100()).into(imageView);

        return ListItemView;
    }

    public TrackListAdapter(@NonNull Context context, int resource, @NonNull List<TrackList> objects) {
        super(context, 0, objects);
    }
}
