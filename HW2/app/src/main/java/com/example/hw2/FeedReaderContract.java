package com.example.hw2;

import android.provider.BaseColumns;

public final class FeedReaderContract
{
    private FeedReaderContract()
    {
        //No instance of this class
    }

    public static class FeedEntry implements BaseColumns
    {
        public static final String TABLE = "bookmark";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_X = "x";
        public static final String COLUMN_Y = "y";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + FeedEntry.TABLE + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE," +
                    FeedEntry.COLUMN_X + " TEXT NOT NULL," +
                    FeedEntry.COLUMN_Y + " TEXT NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE;
}