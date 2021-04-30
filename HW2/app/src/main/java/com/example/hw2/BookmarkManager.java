package com.example.hw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class BookmarkManager
{
    private static BookmarkManager Instance;
    private FeedReaderDbHelper dbHelper;

    private BookmarkManager()
    {
        //Only to make this class Singleton
    }

    public void initialize(Context context)
    {
        dbHelper = new FeedReaderDbHelper(context);
    }

    public void insertNewLocation(String locationName, String x, String y)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME, locationName);
        values.put(FeedReaderContract.FeedEntry.COLUMN_X, x);
        values.put(FeedReaderContract.FeedEntry.COLUMN_Y, y);

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE, null, values);
    }

    public ArrayList<Location> getAllLocations()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Location> locations = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE, null);
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                String locationName = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME));
                String x = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_X));
                String y = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_Y));
                Location location = new Location(locationName, x, y);
                locations.add(location);

                cursor.moveToNext();
            }
        }
        return locations;
    }

    public void deleteLocation(String locationName)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME + " = ?";
        String[] selectionArgs = { locationName };
        int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE, selection, selectionArgs);
    }

    public static BookmarkManager getInstance()
    {
        if (Instance == null)
        {
            Instance = new BookmarkManager();
        }
        return Instance;
    }
}