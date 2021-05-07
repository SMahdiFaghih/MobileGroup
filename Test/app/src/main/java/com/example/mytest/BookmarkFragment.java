package com.example.mytest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BookmarkFragment extends Fragment
{
    private Location location;

    public static BookmarkFragment newInstance(Location location)
    {
        BookmarkFragment fragment = new BookmarkFragment();
        fragment.location = location;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_bookmark, null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.bookmark_linear_layout);
        linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO show this bookmark on map
//                SecondFragment.newInstance(location.getX(), location.getY());
                SecondFragment.getInstance().setPosition(location.getX(), location.getY());
                getActivity().findViewById(R.id.secondFragment).callOnClick();
            }
        });

        ImageView trashBinIcon = (ImageView) view.findViewById(R.id.trashBinIcon);
        trashBinIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BookmarkManager.getInstance().deleteLocation(location.getLocationName());
                BookmarkPageFragment.getInstance().deleteBookmark(BookmarkFragment.this);
            }
        });

        TextView locationNameTextView = (TextView) view.findViewById(R.id.location_name);
        locationNameTextView.setText(location.getLocationName());

        TextView xTextView = (TextView) view.findViewById(R.id.location_x);
        xTextView.setText(location.getX());

        TextView yTextView = (TextView) view.findViewById(R.id.location_y);
        yTextView.setText(location.getY());

        return view;
    }
}