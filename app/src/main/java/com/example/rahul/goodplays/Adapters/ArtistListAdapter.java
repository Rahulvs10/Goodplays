package com.example.rahul.goodplays.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rahul.goodplays.Model.ArtistList;
import com.example.rahul.goodplays.Model.PrimaryGenres;
import com.example.rahul.goodplays.R;

import java.util.List;

public class ArtistListAdapter extends ArrayAdapter<ArtistList> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItemView = convertView;
        if(ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.artist_list_item, parent, false);
        }
        ArtistList current = getItem(position);

        TextView artistName =  ListItemView.findViewById(R.id.name);
        artistName.setText(current.getArtist().getArtistName());

        TextView artistGenre =  ListItemView.findViewById(R.id.genre);

        PrimaryGenres primaryGenres = current.getArtist().getPrimaryGenres();
        
        if(primaryGenres.getMusicGenreList().size()!=0){
            String val = "";
            for(int i = 0; i < primaryGenres.getMusicGenreList().size(); i++){
                val = val + primaryGenres.getMusicGenreList().get(i).getMusicGenre().getMusicGenreName()+", ";
            }
            if(val.equals(""))
                artistGenre.setText(val+"");
            else
                artistGenre.setText(val.substring(0,val.length() - 2)+"");
        }
        else
            artistGenre.setText("");
        
        TextView artistRating =  ListItemView.findViewById(R.id.rating);
        artistRating.setText(current.getArtist().getArtistRating()+"");

        return ListItemView;
    }

    public ArtistListAdapter(@NonNull Context context, int resource, @NonNull List<ArtistList> objects) {
        super(context, 0, objects);
    }
}
