package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseManager
{
    private static DatabaseManager Instance;
    private FeedReaderDbHelper dbHelper;
    private Player[] loggedInPlayers = new Player[2];


    private DatabaseManager()
    {
        //Only to make this class Singleton
    }

    public void initialize(Context context)
    {
        dbHelper = new FeedReaderDbHelper(context);
    }

    public String signUp(String username, String password)
    {
        if (checkPlayerExists((username)))
        {
            return "Player exists with this username";
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_USERNAME, username);
        values.put(FeedReaderContract.FeedEntry.COLUMN_PASSWORD, password);

        db.insert(FeedReaderContract.FeedEntry.TABLE, null, values);

        return "Player added successfully";
    }

    private boolean checkPlayerExists(String username)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE + " WHERE username = ? LIMIT 1", new String[] {username});
        if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public String login(String username, String password, int playerIndex)
    {
        if (loggedInPlayers[playerIndex] != null)
        {
            return "Player" + (playerIndex + 1) + " has already logged in.";
        }
        int otherPlayerIndex = (playerIndex + 1) % 2;
        if (loggedInPlayers[otherPlayerIndex] != null && loggedInPlayers[otherPlayerIndex].getUsername().equals(username))
        {
            return "Player with this username has already logged in.";
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE + " WHERE username = ? AND password = ? LIMIT 1", new String[] {username, password});
        if (cursor.moveToFirst())
        {
            int wins = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_WIN));
            int draws = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_DRAW));
            int loses = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_LOSE));
            Player player = new Player(username, wins, draws, loses);
            loggedInPlayers[playerIndex] = player;

            cursor.close();
            return "Player logged in successfully";
        }
        cursor.close();
        return "Wrong username or Password. Please try again.";
    }

    public ArrayList<Player> getAllPlayers()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<Player> players = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE, null);
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                String username = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_USERNAME));
                int wins = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_WIN));
                int draws = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_DRAW));
                int loses = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_LOSE));
                Player player = new Player(username, wins, draws, loses);
                players.add(player);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return players;
    }

    public void addGameResult(Player player, PlayerGameResult playerGameResult)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        switch (playerGameResult)
        {
            case WIN:
                values.put(FeedReaderContract.FeedEntry.COLUMN_WIN, player.getWins() + 1);
                break;
            case DRAW:
                values.put(FeedReaderContract.FeedEntry.COLUMN_DRAW, player.getDraws() + 1);
                break;
            case LOSE:
                values.put(FeedReaderContract.FeedEntry.COLUMN_LOSE, player.getLoses() + 1);
                break;
        }
        db.update(FeedReaderContract.FeedEntry.TABLE, values, "username = ?", new String[]{player.getUsername()});
    }

    public void logOut(int playerIndex)
    {
        loggedInPlayers[playerIndex] = null;
    }

    public static DatabaseManager getInstance()
    {
        if (Instance == null)
        {
            Instance = new DatabaseManager();
        }
        return Instance;
    }
}