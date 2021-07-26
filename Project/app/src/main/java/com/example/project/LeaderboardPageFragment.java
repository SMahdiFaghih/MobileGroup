package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class LeaderboardPageFragment extends Fragment
{
    private ArrayList<Player> players;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_leaderboard_page, container, false);

        Button backButton = (Button) view.findViewById(R.id.leaderboard_back_button);
        backButton.setOnClickListener(v ->
        {
            Menu menu = new Menu();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, menu);
            transaction.commit();
        });

        return view;
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
            LeaderboardEntryFragment bookmarkFragment = LeaderboardEntryFragment.newInstance(player, players.indexOf(player) + 1);
            fragmentTransaction.add(R.id.ranks, bookmarkFragment, "fragment " + player.getUsername());
        }
        fragmentTransaction.commit();
    }
}