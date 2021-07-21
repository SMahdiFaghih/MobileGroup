package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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
    Button FirstSignUp;
    Button FirstLogin;
    TextView firstPlayerNameAfterLogin;
    Button LogoutButton;
    LinearLayout password1;
    TextView secondPlayerNameAfterLogin;
    Button LogoutButton2;
    LinearLayout password2;
    TextView tv1;
    TextView tv2;
    Button SecondSignUp;
    Button SecondLogin;
    private static final String TAG = "MyActivity";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login, container, false);

        FirstPlayerName = view.findViewById(R.id.editTextName);
        FirstPlayerPassword = view.findViewById(R.id.editTextNumberPassword2);
        SecondPlayerName = view.findViewById(R.id.user2editTextName);
        SecondPlayerPassword = view.findViewById(R.id.user2EditTextNumberPassword2);


        FirstSignUp = view.findViewById(R.id.Sign);
        FirstLogin = view.findViewById(R.id.Login);

        tv1 = view.findViewById(R.id.message1);
        tv2 = view.findViewById(R.id.message2);

        SecondLogin =  view.findViewById(R.id.user2Login);
        SecondSignUp =  view.findViewById(R.id.user2Sign);

        firstPlayerNameAfterLogin  = view.findViewById(R.id.nameString1);
        LogoutButton =  view.findViewById(R.id.LogOut_Button1);
        password1 =  view.findViewById(R.id.passwordLine);

        secondPlayerNameAfterLogin  = view.findViewById(R.id.nameString2);
        LogoutButton2 = view.findViewById(R.id.LogOut_Button2);
        password2 = view.findViewById(R.id.user2PasswordLine);

        if(databaseManager.getLoggedInPlayers()[0]!=null && databaseManager.getLoggedInPlayers()[1]!=null)
        {
            ActionsAfterLoginPlayer1(view);
            ActionsAfterLoginPlayer2(view);
            Log.i(TAG,databaseManager.getLoggedInPlayers()[0].getUsername());
            Log.i(TAG,databaseManager.getLoggedInPlayers()[1].getUsername());
        }


        LogoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseManager.logOut(0);
                ActionAfterLogOutPlayer1(v);
                tv1.setText("Player logged out successfully");

            }
        });
        LogoutButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseManager.logOut(1);
                tv2.setText("Player logged out successfully");
                ActionsAfterLogOutPlayer2(v);

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
                ActionsAfterLoginPlayer1(view);

            }
        });
        SecondSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SecondSignText = databaseManager.signUp(SecondPlayerName.getText().toString(),SecondPlayerPassword.getText().toString());
                tv2.setText(SecondSignText);
            }
        });

        SecondLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SecondLoginText =  databaseManager.login(SecondPlayerName.getText().toString(),SecondPlayerPassword.getText().toString(),1);
                tv2.setText(SecondLoginText);
                ActionsAfterLoginPlayer2(v);

            }
        });
        Button MenuButton = (Button) view.findViewById(R.id.MenuButton);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(databaseManager.getLoggedInPlayers()[0]!=null && databaseManager.getLoggedInPlayers()[1]!=null)
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
    public void ActionsAfterLoginPlayer1(View view)
    {
        FirstPlayerName.setVisibility(view.GONE);
        firstPlayerNameAfterLogin.setVisibility(view.VISIBLE);
        firstPlayerNameAfterLogin.setText(databaseManager.getLoggedInPlayers()[0].getUsername());
        FirstSignUp.setVisibility(view.GONE);
        FirstPlayerPassword.setVisibility(view.GONE);
        password1.setVisibility(view.GONE);
        LogoutButton.setVisibility(view.VISIBLE);
        FirstLogin.setVisibility(view.GONE);
    }
    public void ActionAfterLogOutPlayer1(View view)
    {
        FirstSignUp.setVisibility(view.VISIBLE);
        FirstPlayerPassword.setVisibility(view.VISIBLE);
        password1.setVisibility(view.VISIBLE);
        LogoutButton.setVisibility(view.GONE);
        FirstLogin.setVisibility(view.VISIBLE);
        FirstPlayerName.setVisibility(view.VISIBLE);
        firstPlayerNameAfterLogin.setVisibility(view.GONE);

    }
    public void ActionsAfterLoginPlayer2(View view)
    {
        SecondPlayerName.setVisibility(view.GONE);
        secondPlayerNameAfterLogin.setVisibility(view.VISIBLE);
        secondPlayerNameAfterLogin.setText(databaseManager.getLoggedInPlayers()[1].getUsername());
        SecondSignUp.setVisibility(view.GONE);
        SecondPlayerPassword.setVisibility(view.GONE);
        password2.setVisibility(view.GONE);
        LogoutButton2.setVisibility(view.VISIBLE);
        SecondLogin.setVisibility(view.GONE);
    }
    public void ActionsAfterLogOutPlayer2(View view)
    {
        SecondPlayerName.setVisibility(view.VISIBLE);
        secondPlayerNameAfterLogin.setVisibility(view.GONE);
        SecondSignUp.setVisibility(view.VISIBLE);
        SecondPlayerPassword.setVisibility(view.VISIBLE);
        password2.setVisibility(view.VISIBLE);
        LogoutButton2.setVisibility(view.GONE);
        SecondLogin.setVisibility(view.VISIBLE);
    }

}