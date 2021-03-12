package com.example.hw1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProgressBar extends Fragment
{
    public static ProgressBar instance = null;
    public android.widget.ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SetProgressBar(getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        SetProgressBar(view);
        return view;
    }

    private void SetProgressBar(View view)
    {
        instance = this;
        progressBar = (android.widget.ProgressBar) view.findViewById(R.id.pBar);
    }
}