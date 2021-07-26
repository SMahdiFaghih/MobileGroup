package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch switchButton = view.findViewById(R.id.music_switch);
        switchButton.setChecked(MainActivity.backgroundMusic.isPlaying());
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    MainActivity.backgroundMusic.start();
                }
                else
                {
                    MainActivity.backgroundMusic.pause();
                }
            }
        });

        Button backButton = view.findViewById(R.id.leaderboard_back_button);
        backButton.setOnClickListener(v ->
        {
            Menu menu = new Menu();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, menu);
            transaction.commit();
        });

        return view;
    }
}