package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeaderboardEntryFragment extends Fragment
{
    private Player player;
    private int rank;

    public static LeaderboardEntryFragment newInstance(Player player, int rank)
    {
        LeaderboardEntryFragment fragment = new LeaderboardEntryFragment();
        fragment.player = player;
        fragment.rank = rank;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_leaderboard_entry, null);

        TextView rankTextView = (TextView) view.findViewById(R.id.rank);
        rankTextView.setText(String.valueOf(rank));

        TextView usernameTextView = (TextView) view.findViewById(R.id.username);
        usernameTextView.setText(player.getUsername());

        TextView winsTextView = (TextView) view.findViewById(R.id.wins);
        winsTextView.setText(String.valueOf(player.getWins()));

        TextView drawsTextView = (TextView) view.findViewById(R.id.draws);
        drawsTextView.setText(String.valueOf(player.getDraws()));

        TextView losesTextView = (TextView) view.findViewById(R.id.loses);
        losesTextView.setText(String.valueOf(player.getLoses()));

        return view;
    }
}