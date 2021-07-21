package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    DatabaseManager databaseManager= DatabaseManager.getInstance();
    String firstSignText = "";
    String firstLoginText = "";
    String SecondSignText = "";
    String SecondLoginText = "";
    EditText FirstPlayerName ;
    EditText FirstPlayerPassword;
    EditText SecondPlayerName;
    EditText SecondPlayerPassword;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        FirstPlayerName =(EditText) view.findViewById(R.id.editTextName);
        FirstPlayerPassword =(EditText) view.findViewById(R.id.editTextNumberPassword2);
        SecondPlayerName = (EditText) view.findViewById(R.id.user2editTextName);
        SecondPlayerPassword =(EditText) view.findViewById(R.id.user2EditTextNumberPassword2);


        Button FirstSignUp = (Button) view.findViewById(R.id.Sign);

        Button FirstLogin = (Button) view.findViewById(R.id.Login);
        TextView tv1 = (TextView)view.findViewById(R.id.message1);
        TextView tv2 = (TextView)view.findViewById(R.id.message2);

        TextView firstPlayerNameAfterLogin  = (TextView) view.findViewById(R.id.nameString1);
        Button LogoutButton = (Button) view.findViewById(R.id.LogOut_Button1);
        LinearLayout password1 = (LinearLayout) view.findViewById(R.id.passwordLine);

        TextView secondPlayerNameAfterLogin  = (TextView) view.findViewById(R.id.nameString2);
        Button LogoutButton2 = (Button) view.findViewById(R.id.LogOut_Button2);

        LinearLayout password2 = (LinearLayout) view.findViewById(R.id.user2PasswordLine);


        LogoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseManager.logOut(0);
                FirstSignUp.setVisibility(view.VISIBLE);
                FirstPlayerPassword.setVisibility(view.VISIBLE);
                password1.setVisibility(view.VISIBLE);
                LogoutButton.setVisibility(view.GONE);
                FirstLogin.setVisibility(view.VISIBLE);
                FirstPlayerName.setVisibility(view.VISIBLE);
                firstPlayerNameAfterLogin.setVisibility(view.GONE);
                tv1.setText("Player logged out successfully");
            }
        });
        FirstSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstSignText = databaseManager.signUp(FirstPlayerName.getText().toString(),FirstPlayerPassword.getText().toString());

                tv1.setText(firstSignText);
            }
        });

        FirstLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                firstLoginText= databaseManager.login(FirstPlayerName.getText().toString(),FirstPlayerPassword.getText().toString(),0);
                tv1.setText(firstLoginText);
                FirstPlayerName.setVisibility(view.GONE);
                firstPlayerNameAfterLogin.setVisibility(view.VISIBLE);
                firstPlayerNameAfterLogin.setText(databaseManager.getLoggedInPlayers()[0].getUsername());
                FirstSignUp.setVisibility(view.GONE);
                FirstPlayerPassword.setVisibility(view.GONE);
                password1.setVisibility(view.GONE);
                LogoutButton.setVisibility(view.VISIBLE);
                FirstLogin.setVisibility(view.GONE);

            }
        });

        Button SecondSignUp = (Button) view.findViewById(R.id.user2Sign);
        SecondSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SecondSignText = databaseManager.signUp(SecondPlayerName.getText().toString(),SecondPlayerPassword.getText().toString());
                tv2.setText(SecondSignText);
            }
        });
        Button SecondLogin = (Button) view.findViewById(R.id.user2Login);
        SecondLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SecondLoginText =  databaseManager.login(SecondPlayerName.getText().toString(),SecondPlayerPassword.getText().toString(),1);
                tv2.setText(SecondLoginText);

            }
        });
        Button MenuButton = (Button) view.findViewById(R.id.MenuButton);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SecondLoginText.equalsIgnoreCase("Player logged in successfully") && firstLoginText.equalsIgnoreCase("Player logged in successfully"))
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