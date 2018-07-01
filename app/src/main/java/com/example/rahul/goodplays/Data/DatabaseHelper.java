package com.example.rahul.goodplays.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*Name of database*/
    public static final String DATABASE_NAME = "Favourite_Database.db";
    /*database version*/
    public static final int DATABASE_VERSION = 1;


    /*Public Constructor*/
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create the table
        String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + FavouriteContract.FavouriteEntry.TABLE_NAME + " ( "
                + FavouriteContract.FavouriteEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FavouriteContract.FavouriteEntry.NAME + " TEXT NOT NULL, "
                + FavouriteContract.FavouriteEntry.RATING + " INTEGER, "
                + FavouriteContract.FavouriteEntry.GENRE + " TEXT, "
                + FavouriteContract.FavouriteEntry.YEAR + " TEXT, "
                + FavouriteContract.FavouriteEntry.APIID + " INTEGER, "
                + FavouriteContract.FavouriteEntry.ALBUM + " TEXT, "
                + FavouriteContract.FavouriteEntry.ARTIST + " TEXT, "
                + FavouriteContract.FavouriteEntry.LENGTH + " TEXT, "
                + FavouriteContract.FavouriteEntry.TYPE + " TEXT);";
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

