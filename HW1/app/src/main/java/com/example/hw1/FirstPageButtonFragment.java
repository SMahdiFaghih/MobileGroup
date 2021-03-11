package com.example.hw1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstPageButtonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstPageButtonFragment extends Fragment
{
    private String name;
    private Drawable logo;
    private String symbol;

    public FirstPageButtonFragment()
    {
        // Required empty public constructor
    }

    public static FirstPageButtonFragment newInstance(String name, Drawable logo, String symbol)
    {
        FirstPageButtonFragment fragment = new FirstPageButtonFragment();
        fragment.name = name;
        fragment.logo = logo;
        fragment.symbol = symbol;
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
        View view = inflater.inflate(R.layout.first_page_button_fragment, null);

        Button button = (Button) view.findViewById(R.id.crypto_currency_button);
        //ScaleDrawable scaleDrawable = new ScaleDrawable(logo, 0, 1000, 1000);
        button.setCompoundDrawablesWithIntrinsicBounds(logo, null, null, null);
        button.setText(name);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //todo
                Intent intent = new Intent(getActivity(), CurrencyDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("symbol", symbol);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }
}