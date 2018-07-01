package com.example.rahul.goodplays.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rahul.goodplays.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tracks(View view){
        Intent intent = new Intent(this,TracksActivity.class);
        startActivity(intent);

    }

    public void artists(View view){
        Intent intent = new Intent(this,ArtistsActivity.class);
        startActivity(intent);
    }

    public void favourite(View view){
        Intent intent = new Intent(this,FavouriteActivity.class);
        startActivity(intent);
    }
}
