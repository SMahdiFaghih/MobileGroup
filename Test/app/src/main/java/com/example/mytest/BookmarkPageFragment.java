package com.example.mytest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;

public class BookmarkPageFragment extends Fragment
{
    private static BookmarkPageFragment instance;
    private ArrayList<Location> locations;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance = this;
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
                System.out.println("Searched query: " + query);
                showSearchedLocations(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                System.out.println("Searched Text: " + newText);
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

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Location location : locations)
        {
            BookmarkFragment bookmarkFragment = BookmarkFragment.newInstance(location);
            fragmentTransaction.add(R.id.bookmarksList, bookmarkFragment, "fragment " + location.getLocationName());
        }
        fragmentTransaction.commit();
    }

    private void showHiddenBookmarks()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Location location : locations)
        {
            fragmentTransaction.show(fragmentManager.findFragmentByTag("fragment " + location.getLocationName()));
        }
        fragmentTransaction.commit();
    }

    private void showSearchedLocations(String searchedString)
    {
        if (searchedString == null || searchedString.equals(""))
        {
            showHiddenBookmarks();
            return;
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Location location : locations)
        {
            if (!location.getLocationName().toLowerCase().contains(searchedString.toLowerCase()))
            {
                Fragment fragment = fragmentManager.findFragmentByTag("fragment " + location.getLocationName());
                if (fragment != null)
                {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        fragmentTransaction.commit();
    }

    public void deleteBookmark(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }

    public static BookmarkPageFragment getInstance()
    {
        return instance;
    }
}