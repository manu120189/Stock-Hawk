package com.sam_chordas.android.stockhawk.service;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.rest.Utils;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;
import com.sam_chordas.android.stockhawk.ui.PlaceholderFragment;
import com.sam_chordas.android.stockhawk.ui.StockChartActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RetrieveHistoricalStockTask extends AsyncTask<String, Void, ArrayList> {

    private String key;
    private Float currentBid = 0F;

    public static final int COUNT_OF_DAYS = 10;
    private OkHttpClient client = new OkHttpClient();
    private Context mContext;
    private StringBuilder mStoredSymbols = new StringBuilder();
    private boolean isUpdate;

    public RetrieveHistoricalStockTask(Context mContext, Float currentBid, String key) {
        this.mContext = mContext;
        this.currentBid = currentBid;
        this.key = key;
    }

    String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private ArrayList getHistoricalData() {
        if (key == null)
            return null;
        Cursor initQueryCursor;
        StringBuilder urlStringBuilder = new StringBuilder();
        try {
            Date startDate = null;
            Date endDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, -COUNT_OF_DAYS);
            startDate = c.getTime();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

            // Base URL for the Yahoo query
            String symbol = key;
            String startDateParam = format1.format(startDate);//"2012-09-11";
            String endDateParam = format1.format(endDate);
            ;
            String format = "json";
            urlStringBuilder.append("http://query.yahooapis.com/v1/public/yql?q=" +
                    "%20select%20*%20from%20yahoo.finance.historicaldata" +
                    "%20where%20symbol%20=%20%22" +
                    symbol +
                    "%22%20and" +
                    "%20startDate%20=%20%22" +
                    startDateParam +
                    "%22%20and%20endDate%20=%20%22" +
                    endDateParam +
                    "%22%20" +
                    "&format=" +
                    format +
                    "%20&diagnostics=true%20&env=store://datatables.org/alltableswithkeys%20&callback=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String urlString;
        String getResponse;
        ArrayList result = null;

        if (urlStringBuilder != null) {
            urlString = urlStringBuilder.toString();
            try {
                getResponse = fetchData(urlString);
                result = Utils.historicalQuoteJsonToList(getResponse);
                //do something with getResponse
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    protected ArrayList doInBackground(String... urls) {
        return getHistoricalData();
    }

    protected void onPostExecute(ArrayList result) {
        if (result == null)
            return;

        PlaceholderFragment fragment = PlaceholderFragment.newInstance();
        fragment.setResult(result);
        fragment.setCurrentBid(currentBid);
        fragment.setKeyName(key);
        StockChartActivity stockChartActivity = (StockChartActivity) mContext;
        stockChartActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}