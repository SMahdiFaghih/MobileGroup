package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    DatabaseManager databaseManager= DatabaseManager.getInstance();
    String firstSignText = "";
    String firstLoginText = "";
    String SecondSignText = "";
    String SecondLoginText = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        EditText FirstPlayerName =(EditText) view.findViewById(R.id.editTextName);
        EditText FirstPlayerPassword =(EditText) view.findViewById(R.id.editTextNumberPassword2);
        EditText SecondPlayerName = (EditText) view.findViewById(R.id.user2editTextName);
        EditText SecondPlayerPassword =(EditText) view.findViewById(R.id.user2EditTextNumberPassword2);


        Button FirstSignUp = (Button) view.findViewById(R.id.Sign);
        TextView tv1 = (TextView)view.findViewById(R.id.message1);
        FirstSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstSignText = databaseManager.signUp(FirstPlayerName.getText().toString(),FirstPlayerPassword.getText().toString());

                tv1.setText(firstSignText);
            }
        });
        Button FirstLogin = (Button) view.findViewById(R.id.Login);
        FirstLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstLoginText= databaseManager.login(FirstPlayerName.getText().toString(),FirstPlayerPassword.getText().toString(),0);
                tv1.setText(firstLoginText);
            }
        });

        Button SecondSignUp = (Button) view.findViewById(R.id.user2Sign);
        SecondSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SecondSignText = databaseManager.signUp(SecondPlayerName.getText().toString(),SecondPlayerPassword.getText().toString());
            }
        });
        Button SecondLogin = (Button) view.findViewById(R.id.user2Login);
        SecondLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SecondLoginText =  databaseManager.login(SecondPlayerName.getText().toString(),SecondPlayerPassword.getText().toString(),1);
            }
        });
        Button MenuButton = (Button) view.findViewById(R.id.MenuButton);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SecondLoginText.equals("Player logged in successfully") && firstLoginText.equals("firstLoginText"))
                {
                    Menu menu = new Menu();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,menu);
                    transaction.commit();
                }
            }
        });



        return view;
    }
}