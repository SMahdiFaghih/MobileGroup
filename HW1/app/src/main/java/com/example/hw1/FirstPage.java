package com.example.hw1;

import android.content.res.Resources;
import android.graphics.*;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstPage extends Fragment
{
    private final int LIMIT = 10;
    private int NextCurrencyToFetchIndex = 1;
    private  ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.first_page_fragment, container, false);
        TextView button = (TextView) view.findViewById(R.id.loadMore);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fetchMoreCurrencies();
            }
        });
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        fetchMoreCurrencies();
    }

    private void fetchMoreCurrencies()
    {
        com.example.hw1.ProgressBar.instance.progressBar.setVisibility(View.VISIBLE);

        OkHttpClient okHttpClient = new OkHttpClient();

        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=" + NextCurrencyToFetchIndex + "&limit=" + LIMIT;

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

        String httpUrl = urlBuilder.build().toString();

        final Request request = new Request.Builder().url(httpUrl).addHeader("X-CMC_PRO_API_KEY", "221937be-173a-4eab-87ad-6050045cf559").build();

        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                Log.v("TAG", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException
            {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);
                }
                else
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                        NextCurrencyToFetchIndex += LIMIT;
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        for (int i = 0; i < LIMIT; i++)
                        {
                            JSONObject currencyData = (JSONObject) jsonObject.getJSONArray("data").get(i);
                            System.out.println(currencyData);
                            int id = currencyData.getInt("id");
                            String name = currencyData.getString("name");
                            String symbol = currencyData.getString("symbol");
                            String logoUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + id + ".png";
                            Drawable logo = getDrawableLogoFromUrl(logoUrl);

                            FirstPageButtonFragment firstPageButtonFragment = FirstPageButtonFragment.newInstance(name + "(" + symbol + ")", logo, symbol);
                            fragmentTransaction.add(R.id.listView, firstPageButtonFragment, "fragment" + i);
                        }
                        fragmentTransaction.commit();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                com.example.hw1.ProgressBar.instance.progressBar.setVisibility(View.GONE);
                            }
                        });

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Drawable getDrawableLogoFromUrl(String url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        Bitmap bitmap = BitmapFactory.decodeStream(input);
        Bitmap bitmapResized =Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        return new BitmapDrawable(Resources.getSystem(), bitmapResized);
    }
}