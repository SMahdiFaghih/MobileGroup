package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    public static MediaPlayer backgroundMusic;
    public static MediaPlayer victorySound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        backgroundMusic = MediaPlayer.create(getApplicationContext(),R.raw.star_sky);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        victorySound = MediaPlayer.create(getApplicationContext(),R.raw.victory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager.getInstance().initialize(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).commit();
    }
}