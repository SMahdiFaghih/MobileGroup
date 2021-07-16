package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class LeaderboardPageFragment extends Fragment
{
    private static LeaderboardPageFragment instance;
    private ArrayList<Player> players;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_leaderboard_page, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        showAllBookmarks();
    }

    private void showAllBookmarks()
    {
        players = DatabaseManager.getInstance().getAllPlayers();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Player player : players)
        {
            LeaderboardEntryFragment bookmarkFragment = LeaderboardEntryFragment.newInstance(player);
            fragmentTransaction.add(R.id.ranks, bookmarkFragment, "fragment " + player.getUsername());
        }
        fragmentTransaction.commit();
    }

    public static LeaderboardPageFragment getInstance()
    {
        return instance;
    }
}