package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Menu extends Fragment {


    public Menu() {
        // Required empty public constructor
    }

    public static Menu getInstance()
    {
        Menu menu = new Menu();

        return menu;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, null);
        Button Game_Button = (Button) view.findViewById(R.id.Game_Button);
        Game_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
        Button LeaderBoard_Button = (Button) view.findViewById(R.id.LeaderBoard_Button);
        LeaderBoard_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
        Button Profile_Button = (Button) view.findViewById(R.id.Profile_Button);
        Profile_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
        Button LogOut_Button = (Button) view.findViewById(R.id.LogOut_Button);
        LogOut_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
        Button Exit_Button = (Button) view.findViewById(R.id.Exit_Button);
        Exit_Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });


        return view;
    }

    public void initialize(MainActivity mainActivity) {
    }
}