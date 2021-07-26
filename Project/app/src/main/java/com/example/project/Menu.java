package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Menu extends Fragment
{
    public Menu()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu, null);

        SetButtonsOnClickAction(view);

        return view;
    }

    private void SetButtonsOnClickAction(View view)
    {
        Button Game_Button = view.findViewById(R.id.Game_Button);
        Game_Button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Game game = new Game();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, game);
                transaction.commit();
            }
        });

        Button LeaderBoard_Button = view.findViewById(R.id.LeaderBoard_Button);
        LeaderBoard_Button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                LeaderboardPageFragment LeaderBoard = new LeaderboardPageFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, LeaderBoard);
                transaction.commit();
            }
        });

        Button Settings_Button = view.findViewById(R.id.Settings_Button);
        Settings_Button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                SettingsFragment settings = new SettingsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, settings);
                transaction.commit();
            }
        });

        Button LogOut_Button = view.findViewById(R.id.LogOut_Button);
        LogOut_Button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                LoginFragment login = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container,login);
                transaction.commit();
            }
        });

        Button Exit_Button = (Button) view.findViewById(R.id.Exit_Button);
        Exit_Button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                getActivity().finish();
                System.exit(0);
            }
        });
    }
}