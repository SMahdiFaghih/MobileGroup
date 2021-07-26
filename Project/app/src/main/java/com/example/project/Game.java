package com.example.project;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.project.R.drawable.circle;
import static com.example.project.R.drawable.multiply;

public class Game extends Fragment
{
    private final DatabaseManager databaseManager = DatabaseManager.getInstance();
    private final Player firstPlayer = databaseManager.getLoggedInPlayers()[0];
    private final Player secondPlayer = databaseManager.getLoggedInPlayers()[1];
    private final int [][] gameTable = new int[3][3];
    private int currentTokenIndex = 1;
    private int onTableTokensCount = 0;
    private boolean isGameEnded = false;
    private boolean isGameResultDraw = false;
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    private ImageButton button5;
    private ImageButton button6;
    private ImageButton button7;
    private ImageButton button8;
    private ImageButton button9;
    private TextView firstPlayerUsername;
    private TextView secondPlayerUsername;
    private TextView firstPlayerWins;
    private TextView secondPlayerWins;

    public Game()
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
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        firstPlayerUsername = view.findViewById(R.id.FirstPLayerText);
        secondPlayerUsername = view.findViewById(R.id.SecondPlayerText);
        firstPlayerWins = view.findViewById(R.id.scoreOfFirst);
        secondPlayerWins = view.findViewById(R.id.scoreOfSecond);

        setGameTableButtonsOnClickAction(view);
        setDefaultProperties();

        Button backButton = view.findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Menu menu = new Menu();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,menu);
            transaction.commit();
        });

        return view;
    }

    public void onGameTableButtonClicked(int index, ImageButton button)
    {
        int row = Math.round(index / 3);
        int column = index % 3;
        if (gameTable[row][column] == 0 && !isGameEnded)
        {
            onTableTokensCount ++;
            if(currentTokenIndex == 1)
            {
                Drawable tempImage = getResources().getDrawable(circle);
                button.setImageDrawable(tempImage);
                gameTable[row][column] = 1;
                currentTokenIndex = 2;
                secondPlayerUsername.setTextColor(Color.parseColor("#000000"));
                firstPlayerUsername.setTextColor(Color.parseColor("#AC9F9F"));
            }
            else
            {
                Drawable tempImage = getResources().getDrawable(multiply);
                button.setImageDrawable(tempImage);
                gameTable[row][column] = 2;
                currentTokenIndex = 1;
                firstPlayerUsername.setTextColor(Color.parseColor("#000000"));
                secondPlayerUsername.setTextColor(Color.parseColor("#AC9F9F"));
            }
            if(checkIfPlayerWins())
            {
                if(currentTokenIndex == 1)
                {
                    databaseManager.addGameResult(secondPlayer,PlayerGameResult.WIN);
                    databaseManager.addGameResult(firstPlayer,PlayerGameResult.LOSE);
                }
                else
                {
                    databaseManager.addGameResult(firstPlayer,PlayerGameResult.WIN);
                    databaseManager.addGameResult(secondPlayer,PlayerGameResult.LOSE);
                }
                isGameEnded = true;
                MainActivity.victorySound.start();
            }
            else if (onTableTokensCount == 9)
            {
                {
                    databaseManager.addGameResult(firstPlayer,PlayerGameResult.DRAW);
                    databaseManager.addGameResult(secondPlayer,PlayerGameResult.DRAW);

                    isGameEnded = true;
                    isGameResultDraw = true;
                }
            }
        }
        if(isGameEnded)
        {
            setAndShowEndGameAlert();
        }
    }

    private void setAndShowEndGameAlert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        if(!isGameResultDraw)
        {
            if(currentTokenIndex == 1 )
            {
                alert.setTitle(secondPlayer.getUsername()+" wins");
            }
            else
            {
                alert.setTitle(firstPlayer.getUsername()+" wins");
            }
        }
        else
        {
            alert.setTitle("The game ended in a draw");
        }
        alert.setMessage("Do You Want To Play again?");

        alert.setPositiveButton("Ok", (dialog, whichButton) ->
        {
            setDefaultProperties();
        });
        alert.setNegativeButton("No", (dialog, whichButton) ->
        {
            Menu menu = new Menu();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container,menu);
            transaction.commit();
        });

        alert.show();
    }

    private boolean checkIfPlayerWins()
    {
        return ((gameTable[0][0] == gameTable[0][1]) && (gameTable[0][0] == gameTable[0][2]) && gameTable[0][0] !=0) ||
                ((gameTable[1][0] == gameTable[1][1]) && (gameTable[1][0] == gameTable[1][2]) && gameTable[1][0] !=0) ||
                ((gameTable[2][0] == gameTable[2][1]) && (gameTable[2][0] == gameTable[2][2]) && gameTable[2][0] !=0) ||
                ((gameTable[0][0] == gameTable[1][0]) && (gameTable[0][0] == gameTable[2][0]) && gameTable[0][0] !=0) ||
                ((gameTable[0][1] == gameTable[1][1]) && (gameTable[0][1] == gameTable[2][1]) && gameTable[0][1] !=0) ||
                ((gameTable[0][2] == gameTable[1][2]) && (gameTable[0][2] == gameTable[2][2]) && gameTable[0][2] !=0) ||
                ((gameTable[0][0] == gameTable[1][1]) && (gameTable[0][0] == gameTable[2][2]) && gameTable[0][0] !=0) ||
                ((gameTable[0][2] == gameTable[1][1]) && (gameTable[0][2] == gameTable[2][0]) && gameTable[0][2] !=0);
    }

    private void setDefaultProperties()
    {
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                gameTable[i][j] = 0;
            }
        }

        button1.setImageDrawable(null);
        button2.setImageDrawable(null);
        button3.setImageDrawable(null);
        button4.setImageDrawable(null);
        button5.setImageDrawable(null);
        button6.setImageDrawable(null);
        button7.setImageDrawable(null);
        button8.setImageDrawable(null);
        button9.setImageDrawable(null);

        isGameEnded = false;
        isGameResultDraw = false;
        currentTokenIndex = 1;
        onTableTokensCount = 0;

        firstPlayerUsername.setText(firstPlayer.getUsername());
        firstPlayerUsername.setTextColor(Color.parseColor("#000000"));
        secondPlayerUsername.setTextColor(Color.parseColor("#AC9F9F"));
        secondPlayerUsername.setText(secondPlayer.getUsername());

        String text = "Wins: " + firstPlayer.getWins();
        firstPlayerWins.setText(text);
        firstPlayerWins.setTextColor(Color.parseColor("#000000"));

        String text2 = "Wins: " + secondPlayer.getWins();
        secondPlayerWins.setText(text2);
        secondPlayerWins.setTextColor(Color.parseColor("#000000"));
    }

    private void setGameTableButtonsOnClickAction(View view)
    {
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(0,button1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(1,button2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(2,button3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(3,button4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(4,button5);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(5,button6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(6,button7);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(7,button8);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onGameTableButtonClicked(8,button9);
            }
        });
    }
}