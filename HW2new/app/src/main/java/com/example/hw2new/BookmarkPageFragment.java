package com.example.hw2new;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BookmarkPageFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_bookmark_page, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        ShowAllBookmarks();
    }

    private void ShowAllBookmarks()
    {
        ArrayList<Location> locations = BookmarkManager.getInstance().getAllLocations();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for (Location location : locations)
        {
            BookmarkFragment bookmarkFragment = BookmarkFragment.newInstance(location);
            fragmentTransaction.add(R.id.bookmarksList, bookmarkFragment, "fragment " + location.getLocationName());
        }
        fragmentTransaction.commit();
    }
}