package com.example.hw1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        button.setCompoundDrawablesWithIntrinsicBounds(logo, null, null, null);
        String buttonText = name + "(" + symbol + ")" + "      " + "$" + "1000" + "\n\n" + "%" + "2000" + "      " + "%" + "3000" + "      " + "%" + "4000";
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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