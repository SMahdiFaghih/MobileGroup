package com.example.project;

import android.provider.BaseColumns;

public final class FeedReaderContract
{
    private FeedReaderContract()
    {
        //No instance of this class
    }

    public static class FeedEntry implements BaseColumns
    {
        public static final String TABLE = "player";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_WIN = "win";
        public static final String COLUMN_DRAW = "draw";
        public static final String COLUMN_LOSE = "lose";
    }

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + FeedEntry.TABLE + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.COLUMN_USERNAME + " TEXT NOT NULL UNIQUE," +
                    FeedEntry.COLUMN_PASSWORD + " TEXT NOT NULL," +
                    FeedEntry.COLUMN_WIN + " INTEGER DEFAULT 0," +
                    FeedEntry.COLUMN_DRAW + " INTEGER DEFAULT 0," +
                    FeedEntry.COLUMN_LOSE + " INTEGER DEFAULT 0)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE;
}