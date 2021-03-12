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
    private int currentPrice;
    private int percentChange1H;
    private int percentChange24H;
    private int percentChange7D;

    public FirstPageButtonFragment()
    {
        // Required empty public constructor
    }

    public static FirstPageButtonFragment newInstance(String name, Drawable logo, String symbol, int currentPrice, int percentChange1H, int percentChange24H, int percentChange7D)
    {
        FirstPageButtonFragment fragment = new FirstPageButtonFragment();
        fragment.name = name;
        fragment.logo = logo;
        fragment.symbol = symbol;
        fragment.currentPrice = currentPrice;
        fragment.percentChange1H = percentChange1H;
        fragment.percentChange24H = percentChange24H;
        fragment.percentChange7D = percentChange7D;
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
        String buttonText = name + "(" + symbol + ")" + "       " + currentPrice+ "$" + "\n\n" + "1h: " + percentChange1H + "%       " + "1D: " + percentChange24H + "%       " + "7D: " + percentChange7D + "%";
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