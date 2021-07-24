package com.example.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import static com.example.project.R.drawable.circle;
import static com.example.project.R.drawable.multiply;

public class Game extends Fragment {
    DatabaseManager databaseManager= DatabaseManager.getInstance();
    Player first = databaseManager.getLoggedInPlayers()[0];
    Player second  = databaseManager.getLoggedInPlayers()[1];
    int currentIndex = 1;
    int [][] game = new int[3][3];
    private static final String TAG = "MyActivity";
    boolean gameEnded= false;
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;
    ImageButton button6;
    ImageButton button7;
    ImageButton button8;
    ImageButton button9;




    public Game() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        for (int i =0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                game[i][j] = 0;
            }
        }
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
            buttonClicked(0,button1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(1,button2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(2,button3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(3,button4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(4,button5);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(5,button6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(6,button7);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(7,button8);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(8,button9);
            }
        });
        return view;
    }
    public void buttonClicked(int index,ImageButton button){
        int row = Math.round(index/3);
        int column = index%3;
        if (game[row][column] == 0 && !gameEnded)
        {
            if(currentIndex == 1)
            {
                Drawable tempImage = getResources().getDrawable(circle);
                button.setImageDrawable(tempImage);

                game[row][column] = 1;
                currentIndex = 2;
            }
            else
            {
                Drawable tempImage = getResources().getDrawable(multiply);
                button.setImageDrawable(tempImage);
                game[row][column] = 2;
                currentIndex = 1;
            }
            if(((game[0][0] == game[0][1]) && (game[0][0] == game[0][2]) && game[0][0] !=0) ||
                    ((game[1][0] == game[1][1]) && (game[1][0] == game[1][2]) && game[1][0] !=0) ||
                    ((game[2][0] == game[2][1]) && (game[2][0] == game[2][2]) && game[2][0] !=0) ||
                    ((game[0][0] == game[1][0]) && (game[0][0] == game[2][0]) && game[0][0] !=0) ||
                    ((game[0][1] == game[1][1]) && (game[0][1] == game[2][1]) && game[0][1] !=0) ||
                    ((game[0][2] == game[1][2]) && (game[0][2] == game[2][2]) && game[0][2] !=0) ||
                    ((game[0][0] == game[1][1]) && (game[0][0] == game[2][2]) && game[0][0] !=0) ||
                    ((game[0][2] == game[1][1]) && (game[0][2] == game[2][0]) && game[0][2] !=0) )
            {
                if(currentIndex == 1)
                {
                    databaseManager.addGameResult(second,PlayerGameResult.WIN);
                    databaseManager.addGameResult(first,PlayerGameResult.LOSE);
                }
                else
                {
                    databaseManager.addGameResult(first,PlayerGameResult.WIN);
                    databaseManager.addGameResult(second,PlayerGameResult.LOSE);
                }
                gameEnded = true;
            }
            else
            {
                boolean flag = false;
                for (int i =0;i<3;i++)
                {
                    for (int j=0;j<3;j++)
                    {
                        if (game[i][j] == 0)
                        {
                            flag = true;
                        }
                    }
                }
                if(!flag)
                {
                    databaseManager.addGameResult(first,PlayerGameResult.DRAW);
                    databaseManager.addGameResult(second,PlayerGameResult.DRAW);
                    gameEnded = true;
                }
            }
        }
        if(gameEnded)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            if(currentIndex == 1 )
            {
                alert.setTitle(second.getUsername()+" wins");
            }
            else
            {
                alert.setTitle(first.getUsername()+" wins");
            }


             alert.setMessage("Play again?");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    for (int i =0;i<3;i++)
                    {
                        for (int j=0;j<3;j++)
                        {
                            game[i][j] = 0;
                        }
                    }
                    gameEnded = false;
                    button1.setImageDrawable(null);
                    button2.setImageDrawable(null);
                    button3.setImageDrawable(null);
                    button4.setImageDrawable(null);
                    button5.setImageDrawable(null);
                    button6.setImageDrawable(null);
                    button7.setImageDrawable(null);
                    button8.setImageDrawable(null);
                    button9.setImageDrawable(null);
                    
                }
            });

            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Menu menu = new Menu();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.container,menu);
                            transaction.commit();
                        }
                    });

            alert.show();
        }


    }
}