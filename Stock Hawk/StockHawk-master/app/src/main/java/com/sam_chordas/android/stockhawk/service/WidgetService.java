package com.sam_chordas.android.stockhawk.service;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        ArrayList<String> stocks = intent.getStringArrayListExtra("STOCKS");
        StockViewsFactory stockViewsFactory = new StockViewsFactory(this.getApplicationContext(),
                intent);
        String[] array = stocks.toArray(new String[stocks.size()]);
        stockViewsFactory.setItems(array);
        stockViewsFactory.onDataSetChanged();
        return stockViewsFactory;
    }
}