package com.example.rahul.goodplays.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class FavouriteContract {

    private FavouriteContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.rahul.goodplays";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_Favourites = "Favourites";

    public static final class FavouriteEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Favourites);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Favourites;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Favourites;


        public static final String TABLE_NAME = "Favourites";

        public static final String ID = BaseColumns._ID;
        public static final String NAME = "Name";
        public static final String TYPE = "Type";
        public static final String YEAR = "Year";
        public static final String GENRE = "Genre";
        public static final String RATING = "Rating";
        public static final String APIID = "Api_ID";
        public static final String ALBUM = "Album";
        public static final String ARTIST = "Artist";
        public static final String LENGTH = "Length";

    }

}
