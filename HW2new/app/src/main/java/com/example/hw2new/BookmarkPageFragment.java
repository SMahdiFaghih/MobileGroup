package com.example.hw2new;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;

public class BookmarkPageFragment extends Fragment
{
    private ArrayList<Location> locations;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_bookmark_page, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                showSearchedLocations(searchView.getQuery().toString());
                return true;
            }
        });

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        showAllBookmarks();
    }

    private void showAllBookmarks()
    {
        locations = BookmarkManager.getInstance().getAllLocations();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for (Location location : locations)
        {
            BookmarkFragment bookmarkFragment = BookmarkFragment.newInstance(location);
            fragmentTransaction.add(R.id.bookmarksList, bookmarkFragment, "fragment " + location.getLocationName());
        }
        fragmentTransaction.commit();
    }

    private void showSearchedLocations(String searchedString)
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for (Location location : locations)
        {
            if (location.getLocationName().contains(searchedString))
            {
                BookmarkFragment bookmarkFragment = BookmarkFragment.newInstance(location);
                fragmentTransaction.add(R.id.bookmarksList, bookmarkFragment, "fragment " + location.getLocationName());
            }
        }
        fragmentTransaction.commit();
    }
}