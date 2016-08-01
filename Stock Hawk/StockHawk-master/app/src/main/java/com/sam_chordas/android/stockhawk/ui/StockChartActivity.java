package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.RetrieveHistoricalStockTask;


public class StockChartActivity extends AppCompatActivity {

    public static final String SYMBOL_TAG = "SYMBOL";
    public static final String BID_TAG = "BID";
    private String LOG_TAG = StockChartActivity.class.getSimpleName();
    private Intent mServiceIntent;
    private String key;
    private Float currentBid = 0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_chart);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                key= extras.getString(SYMBOL_TAG);
                currentBid = extras.getFloat(BID_TAG);
            }
        }else{
            key = savedInstanceState.getString(SYMBOL_TAG);
            currentBid = savedInstanceState.getFloat(BID_TAG);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(SYMBOL_TAG, key);
        savedInstanceState.putFloat(BID_TAG, currentBid);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState != null) {
            key = savedInstanceState.getString(SYMBOL_TAG);
            currentBid = savedInstanceState.getFloat(BID_TAG);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        new RetrieveHistoricalStockTask(StockChartActivity.this, currentBid ,key).execute("");
    }

}
