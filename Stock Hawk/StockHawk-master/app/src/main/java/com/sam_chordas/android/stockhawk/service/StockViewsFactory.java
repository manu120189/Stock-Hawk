package com.sam_chordas.android.stockhawk.service;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

public class StockViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public static String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer",
            "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae",
            "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat",
            "placerat", "ante",
            "porttitor", "sodales",
            "pellentesque", "augue",
            "purus"};
    private Context ctxt=null;
    private int appWidgetId;

    public static void setItems(String[] items) {
        StockViewsFactory.items = items;
    }

    public StockViewsFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;

        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return(items.length);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.widget_collection_item);

        String item = items[position];
        String[] split = item.split(":");
        row.setTextViewText(R.id.stock_symbol, split[0]);
        row.setTextViewText(R.id.change, split[1]);


//        Intent i=new Intent();
//        Bundle extras=new Bundle();

//        extras.putString(StockWidgetProvider.EXTRA_WORD, items[position]);
//        i.putExtras(extras);
//        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }
}