package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private static MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        backgroundMusic = MediaPlayer.create(getApplicationContext(),R.raw.star_sky);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager.getInstance().initialize(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).commit();
    }
}