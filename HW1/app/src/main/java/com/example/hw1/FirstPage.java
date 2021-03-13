package com.example.hw1;

import android.content.Context;
import android.content.ContextWrapper;
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
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstPage extends Fragment {
    private final int LIMIT = 10;
    private int NextCurrencyToFetchIndex = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_page_fragment, container, false);
        TextView button = (TextView) view.findViewById(R.id.loadMore);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMoreCurrencies();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NextCurrencyToFetchIndex == 1) {
            fetchMoreCurrencies();
        }
    }

    private void fetchMoreCurrencies() {
        ProgressBar.instance.progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=" + NextCurrencyToFetchIndex + "&limit=" + LIMIT;
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        String httpUrl = urlBuilder.build().toString();
        final Request request = new Request.Builder().url(httpUrl).addHeader("X-CMC_PRO_API_KEY", "221937be-173a-4eab-87ad-6050045cf559").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.v("TAG", e.getMessage());

                File file = new File(getContext().getFilesDir().getPath() + "/mydir/chachedData");
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                    }
                    br.close();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                try {
                    JSONObject jsnobject = new JSONObject(String.valueOf(text));
                    JSONArray jsonArray = jsnobject.getJSONArray("data");
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);
                        System.out.println(explrObject.toString());
                        System.out.println("/*/*/*/*");
                        int id = explrObject.getInt("id");
                        String name = explrObject.getString("name");
                        String symbol = explrObject.getString("symbol");
                        JSONObject quote = explrObject.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        int price = (int) USD.getDouble("price");
                        int percentChange1H = (int) USD.getDouble("percent_change_1h");
                        int percentChange24H = (int) USD.getDouble("percent_change_24h");
                        int percentChange7D = (int) USD.getDouble("percent_change_7d");
                        String logoUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + id + ".png";


                        //path to /data/data/yourapp/app_data/dirName

                        File mypath=new File(getContext().getFilesDir() + "/mydir","image"+ id);
                        Drawable logo = Drawable.createFromPath(mypath.toString());

                        FirstPageButtonFragment firstPageButtonFragment = FirstPageButtonFragment.newInstance(name, logo, symbol, price, percentChange1H, percentChange24H, percentChange7D);
                        fragmentTransaction.add(R.id.listView, firstPageButtonFragment, "fragment" + i);
                    }
                    fragmentTransaction.commit();
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }



            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                        NextCurrencyToFetchIndex += LIMIT;
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        JSONObject toBeSaved = new JSONObject();
                        JSONArray temp = new JSONArray();

                        for (int i = 0; i < LIMIT; i++) {
                            JSONObject currencyData = (JSONObject) jsonObject.getJSONArray("data").get(i);
                            JSONObject fileData = new JSONObject();
                            System.out.println(currencyData);
                            int id = currencyData.getInt("id");
                            String name = currencyData.getString("name");
                            String symbol = currencyData.getString("symbol");
                            JSONObject quote = currencyData.getJSONObject("quote");
                            JSONObject USD = quote.getJSONObject("USD");
                            int price = (int) USD.getDouble("price");
                            int percentChange1H = (int) USD.getDouble("percent_change_1h");
                            int percentChange24H = (int) USD.getDouble("percent_change_24h");
                            int percentChange7D = (int) USD.getDouble("percent_change_7d");
                            fileData.put("id", id);
                            fileData.put("price", price);
                            fileData.put("percentChange1H", percentChange1H);
                            fileData.put("percentChange24H", percentChange24H);
                            fileData.put("percentChange7D", percentChange7D);
                            temp.put(fileData);

                            String logoUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/" + id + ".png";
                            Drawable logo = getDrawableLogoFromUrl(logoUrl,id);

                            FirstPageButtonFragment firstPageButtonFragment = FirstPageButtonFragment.newInstance(name, logo, symbol, price, percentChange1H, percentChange24H, percentChange7D);
                            fragmentTransaction.add(R.id.listView, firstPageButtonFragment, "fragment" + i);
                        }

                        toBeSaved.put("data",temp);
                        System.out.println(toBeSaved.toString());
                        System.out.println("*************************");
                        writeFileOnInternalStorage(getContext(), "chachedData", toBeSaved.toString());
                        fragmentTransaction.commit();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBar.instance.progressBar.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody) {
        File dir = new File(mcoContext.getFilesDir(), "mydir");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(mcoContext.getFilesDir());
        System.out.println("+++++++++++++++++++++++++++");
    }

    private void saveBitmapToFile(String fileName, Bitmap bm,
                             Bitmap.CompressFormat format, int quality) {

        File dir = new File(getContext().getFilesDir(), "mydir");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File imageFile = new File(dir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format,quality,fos);

            fos.close();
            System.out.println(imageFile);
            System.out.println("111111111");
        }
        catch (IOException e) {
            Log.e("app",e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    private Drawable getDrawableLogoFromUrl(String url,int id) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        Bitmap bitmap = BitmapFactory.decodeStream(input);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        saveBitmapToFile("image" +id,bitmap,Bitmap.CompressFormat.PNG,100);
        return new BitmapDrawable(Resources.getSystem(), bitmapResized);
    }
}