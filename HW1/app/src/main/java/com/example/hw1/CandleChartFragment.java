package com.example.hw1;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CandleChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandleChartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CandleStickChart candleStickChart;
    private TextView currencyName;
    private String currentRange = "weekly";
    private ProgressBar candleProgressBar;

    public enum Range {
        weekly,
        oneMonth,
    }

    public CandleChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandleChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CandleChartFragment newInstance(String param1, String param2) {
        CandleChartFragment fragment = new CandleChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candle_chart, container, false);
        candleStickChart = view.findViewById(R.id.candle_stick);
        candleProgressBar = getActivity().findViewById(R.id.CandleProgressBar);
        currencyName = view.findViewById(R.id.candleChartCurrencySymbol);
//        candleProgressBar = view.findViewById(R.id.CandleProgressBar);
        Bundle bundle = getActivity().getIntent().getExtras();

        Button weekly = view.findViewById(R.id.weeklyView);

        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentRange.equals("weekly")) {
                    currentRange = "weekly";
                    getCandles(bundle.getString("symbol"), Range.weekly);
                }
            }
        });

        Button monthly = view.findViewById(R.id.monthlyView);
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentRange.equals("monthly")) {
                    currentRange = "monthly";
                    getCandles(bundle.getString("symbol"), Range.oneMonth);
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = getActivity().getIntent().getExtras();

        getCandles(bundle.getString("symbol"), Range.weekly);
    }

    public String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c);
    }

// برای دریافت کندل های روزانه به مدت یک هفته پارامتر دوم را "هفته ای" بدهید و
// برای دریافت کندل های روزانه به مدت یک ماه پارامتر دوم را "یک ماه" بدهید
// پارامتر اول هم نماد سکه مورد نظر خواهد بود

    public void getCandles(String symbol, Range range) {
        com.example.hw1.ProgressBar.instance.progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        OkHttpClient okHttpClient = new OkHttpClient();

        currencyName.setText(symbol);

        String miniUrl;
        final String description;
        switch (range) {

            case weekly:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=7"));
                description = "Daily candles from now";
                break;

            case oneMonth:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=30"));
                description = "Daily candles from now";
                break;

            default:
                miniUrl = "";
                description = "";

        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://rest.coinapi.io/v1/ohlcv/".concat(symbol).concat("/USD/history?".concat(miniUrl)))
                .newBuilder();

        String url = urlBuilder.build().toString();

        final Request request = new Request.Builder().url(url)
                .addHeader("X-CoinAPI-Key", "2C63C54B-6EAA-4D1B-8A08-B197309F3A90")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    extractCandlesFromResponse(response.body().string(), description);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            com.example.hw1.ProgressBar.instance.progressBar.setVisibility(View.GONE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    });
                }
            }
        });

    }

    private void extractCandlesFromResponse(String data, String description) {
        try {
            System.out.println(data);
            JSONArray candlesArrayDate = new JSONArray(data);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

            candleStickChart.setHighlightPerDragEnabled(true);

            candleStickChart.setDrawBorders(true);

            candleStickChart.setBorderColor(Color.BLACK);

            YAxis yAxis = candleStickChart.getAxisLeft();
            YAxis rightAxis = candleStickChart.getAxisRight();
            yAxis.setDrawGridLines(false);
            rightAxis.setDrawGridLines(false);
            candleStickChart.requestDisallowInterceptTouchEvent(true);

            XAxis xAxis = candleStickChart.getXAxis();

            xAxis.setDrawGridLines(false);// disable x axis grid lines
            xAxis.setDrawLabels(true);
            rightAxis.setTextColor(Color.BLACK);
            yAxis.setDrawLabels(true);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setAvoidFirstLastClipping(true);

            Legend l = candleStickChart.getLegend();
            l.setEnabled(true);

            ArrayList<CandleEntry> yValsCandleStick = new ArrayList<>();
            for (int i = 0; i < candlesArrayDate.length(); i++) {
                JSONObject candleData = (JSONObject) candlesArrayDate.get(i);
                Float candlePriceOpen = Float.parseFloat(candleData.getString("price_open"));
                Float candlePriceClose = Float.parseFloat(candleData.getString("price_close"));
                Float candlePriceHigh = Float.parseFloat(candleData.getString("price_high"));
                Float candlePriceLow = Float.parseFloat(candleData.getString("price_low"));
                yValsCandleStick.add(new CandleEntry(i, candlePriceHigh, candlePriceLow,
                        candlePriceOpen, candlePriceClose));
            }

            CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet");
            set1.setColor(Color.rgb(80, 80, 80));
            set1.setShadowColor(Color.BLACK);
            set1.setShadowWidth(2f);
            set1.setDecreasingColor(Color.RED);
            set1.setDecreasingPaintStyle(Paint.Style.FILL);
            set1.setIncreasingColor(Color.GREEN);
            set1.setIncreasingPaintStyle(Paint.Style.FILL);
            set1.setNeutralColor(Color.LTGRAY);
            set1.setDrawValues(false);

            // create a data object with the datasets
            CandleData chartData = new CandleData(set1);

            // set data
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    candleStickChart.setData(chartData);
                    candleStickChart.getDescription().setText(description);
                    candleStickChart.invalidate();
                }
            });

            fragmentTransaction.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}