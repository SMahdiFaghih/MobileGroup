package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DatabaseManager.getInstance().initialize(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new Menu()).commit();
    }
}