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

public class LoginFragment extends Fragment
{
    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private EditText firstPlayerName;
    private EditText firstPlayerPassword;
    private EditText secondPlayerName;
    private EditText secondPlayerPassword;
    private Button firstPlayerSignUpButton;
    private Button firstPlayerLoginButton;
    private Button secondPlayerSignUpButton;
    private Button secondPlayerLoginButton;
    private TextView firstPlayerUsernameAfterLogin;
    private Button firstPlayerLogoutButton;
    private LinearLayout firstPlayerPasswordLine;
    private TextView secondPlayerUsernameAfterLogin;
    private Button secondPlayerLogoutButton;
    private LinearLayout secondPlayerPasswordLine;
    private TextView firstPlayerLogMessageText;
    private TextView secondPlayerLogMessageText;
    private TextView firstPlayerPasswordTitle;
    private TextView secondPlayerPasswordTitle;
    private TextView firstPlayerUsernameTitle;
    private TextView secondPlayerUsernameTitle;
    private Button gotoMainMenuButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_login, container, false);

        SetComponents(view);

        if(databaseManager.getLoggedInPlayers()[0] != null && databaseManager.getLoggedInPlayers()[1] != null)
        {
            ActionsAfterLoginPlayer1(view);
            ActionsAfterLoginPlayer2(view);
            gotoMainMenuButton.setVisibility(view.VISIBLE);
        }

        SetGotoMainMenuButtonOnClickAction();
        SetFirstPlayerButtonsOnClickAction(view);
        SetSecondPlayerButtonsOnClickAction(view);

        return view;
    }

    private void ActionsAfterLoginPlayer1(View view)
    {
        firstPlayerUsernameTitle.setVisibility(view.GONE);
        firstPlayerPasswordTitle.setVisibility(view.GONE);
        firstPlayerName.setVisibility(view.GONE);
        firstPlayerUsernameAfterLogin.setVisibility(view.VISIBLE);
        firstPlayerUsernameAfterLogin.setText(databaseManager.getLoggedInPlayers()[0].getUsername());
        firstPlayerSignUpButton.setVisibility(view.GONE);
        firstPlayerPassword.setVisibility(view.GONE);
        firstPlayerPasswordLine.setVisibility(view.GONE);
        firstPlayerLogoutButton.setVisibility(view.VISIBLE);
        firstPlayerLoginButton.setVisibility(view.GONE);
    }

    private void ActionAfterLogOutPlayer1(View view)
    {
        firstPlayerUsernameTitle.setVisibility(view.VISIBLE);
        firstPlayerPasswordTitle.setVisibility(view.VISIBLE);
        firstPlayerSignUpButton.setVisibility(view.VISIBLE);
        firstPlayerPassword.setVisibility(view.VISIBLE);
        firstPlayerPasswordLine.setVisibility(view.VISIBLE);
        firstPlayerLogoutButton.setVisibility(view.GONE);
        firstPlayerLoginButton.setVisibility(view.VISIBLE);
        firstPlayerName.setVisibility(view.VISIBLE);
        firstPlayerUsernameAfterLogin.setVisibility(view.GONE);

    }

    private void ActionsAfterLoginPlayer2(View view)
    {
        secondPlayerUsernameTitle.setVisibility(view.GONE);
        secondPlayerPasswordTitle.setVisibility(view.GONE);
        secondPlayerName.setVisibility(view.GONE);
        secondPlayerUsernameAfterLogin.setVisibility(view.VISIBLE);
        secondPlayerUsernameAfterLogin.setText(databaseManager.getLoggedInPlayers()[1].getUsername());
        secondPlayerSignUpButton.setVisibility(view.GONE);
        secondPlayerPassword.setVisibility(view.GONE);
        secondPlayerPasswordLine.setVisibility(view.GONE);
        secondPlayerLogoutButton.setVisibility(view.VISIBLE);
        secondPlayerLoginButton.setVisibility(view.GONE);
    }

    private void ActionsAfterLogOutPlayer2(View view)
    {
        secondPlayerUsernameTitle.setVisibility(view.VISIBLE);
        secondPlayerPasswordTitle.setVisibility(view.VISIBLE);
        secondPlayerName.setVisibility(view.VISIBLE);
        secondPlayerUsernameAfterLogin.setVisibility(view.GONE);
        secondPlayerSignUpButton.setVisibility(view.VISIBLE);
        secondPlayerPassword.setVisibility(view.VISIBLE);
        secondPlayerPasswordLine.setVisibility(view.VISIBLE);
        secondPlayerLogoutButton.setVisibility(view.GONE);
        secondPlayerLoginButton.setVisibility(view.VISIBLE);
    }

    private void SetSecondPlayerButtonsOnClickAction(View view)
    {
        secondPlayerSignUpButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String SecondPlayerSignUpText = databaseManager.signUp(secondPlayerName.getText().toString(), secondPlayerPassword.getText().toString());
                secondPlayerLogMessageText.setText(SecondPlayerSignUpText);
                if (SecondPlayerSignUpText.equals("Player logged in successfully"))
                {
                    secondPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Green));
                }
                else
                {
                    secondPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Red));
                }
            }
        });

        secondPlayerLoginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String SecondPlayerLoginText = databaseManager.login(secondPlayerName.getText().toString(), secondPlayerPassword.getText().toString(),1);
                secondPlayerLogMessageText.setText(SecondPlayerLoginText);
                if(databaseManager.getLoggedInPlayers()[0]!=null && databaseManager.getLoggedInPlayers()[1]!=null)
                {
                    gotoMainMenuButton.setVisibility(v.VISIBLE);
                }
                if (SecondPlayerLoginText.equals("Player logged in successfully"))
                {
                    ActionsAfterLoginPlayer2(v);
                    secondPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Green));
                }
                else
                {
                    secondPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Red));
                }

            }
        });

        secondPlayerLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                databaseManager.logOut(1);
                secondPlayerLogMessageText.setText("Player logged out successfully");
                ActionsAfterLogOutPlayer2(v);

            }
        });
    }

    private void SetFirstPlayerButtonsOnClickAction(View view)
    {
        firstPlayerSignUpButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String firstPlayerSignUpText = databaseManager.signUp(firstPlayerName.getText().toString(), firstPlayerPassword.getText().toString());
                firstPlayerLogMessageText.setText(firstPlayerSignUpText);
                if (firstPlayerSignUpText.equals("Player added successfully"))
                {
                    firstPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Green));
                }
                else
                {
                    firstPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Red));
                }
            }
        });

        firstPlayerLoginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String firstPlayerLoginText = databaseManager.login(firstPlayerName.getText().toString(), firstPlayerPassword.getText().toString(),0);
                firstPlayerLogMessageText.setText(firstPlayerLoginText);
                if(databaseManager.getLoggedInPlayers()[0]!=null && databaseManager.getLoggedInPlayers()[1]!=null)
                {
                    gotoMainMenuButton.setVisibility(v.VISIBLE);
                }
                if (firstPlayerLoginText.equals("Player logged in successfully"))
                {
                    ActionsAfterLoginPlayer1(view);
                    firstPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Green));
                }
                else
                {
                    firstPlayerLogMessageText.setTextColor(getResources().getColor(R.color.Red));
                }

            }
        });

        firstPlayerLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                databaseManager.logOut(0);
                ActionAfterLogOutPlayer1(v);
                firstPlayerLogMessageText.setText("Player logged out successfully");
            }
        });
    }

    private void SetGotoMainMenuButtonOnClickAction()
    {
        gotoMainMenuButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(databaseManager.getLoggedInPlayers()[0] != null && databaseManager.getLoggedInPlayers()[1] != null)
                {
                    Menu menu = new Menu();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,menu);
                    transaction.commit();
                }
            }
        });
    }

    private void SetComponents(View view)
    {
        firstPlayerName = view.findViewById(R.id.editTextName);
        firstPlayerPassword = view.findViewById(R.id.editTextNumberPassword2);
        secondPlayerName = view.findViewById(R.id.user2editTextName);
        secondPlayerPassword = view.findViewById(R.id.user2EditTextNumberPassword2);

        firstPlayerSignUpButton = view.findViewById(R.id.Sign);
        firstPlayerLoginButton = view.findViewById(R.id.Login);

        firstPlayerLogMessageText = view.findViewById(R.id.message1);
        secondPlayerLogMessageText = view.findViewById(R.id.message2);

        secondPlayerLoginButton =  view.findViewById(R.id.user2Login);
        secondPlayerSignUpButton =  view.findViewById(R.id.user2Sign);
        firstPlayerUsernameTitle = view.findViewById(R.id.name);
        secondPlayerUsernameTitle = view.findViewById(R.id.user2Name);
        firstPlayerPasswordTitle = view.findViewById(R.id.password);
        secondPlayerPasswordTitle = view.findViewById(R.id.user2Password);

        firstPlayerUsernameAfterLogin = view.findViewById(R.id.nameString1);
        firstPlayerLogoutButton =  view.findViewById(R.id.LogOut_Button1);
        firstPlayerPasswordLine =  view.findViewById(R.id.passwordLine);

        secondPlayerUsernameAfterLogin = view.findViewById(R.id.nameString2);
        secondPlayerLogoutButton = view.findViewById(R.id.LogOut_Button2);
        secondPlayerPasswordLine = view.findViewById(R.id.user2PasswordLine);

        gotoMainMenuButton = view.findViewById(R.id.MenuButton);
    }

}