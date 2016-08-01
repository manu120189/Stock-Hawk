package com.sam_chordas.android.stockhawk.service;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StockWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        Cursor initQueryCursor = context.getContentResolver().query(
                QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL, QuoteColumns.CHANGE},
                null,
                null,
                null);

        ArrayList<String> stocks = new ArrayList<>();
        if (initQueryCursor != null){
            DatabaseUtils.dumpCursor(initQueryCursor);
            initQueryCursor.moveToFirst();
            for (int j = 0; j < initQueryCursor.getCount(); j++){
                int symbolIndex = initQueryCursor.getColumnIndex("symbol");
                int changeIndex = initQueryCursor.getColumnIndex("change");
                String symbol = initQueryCursor.getString(symbolIndex);
                String change = initQueryCursor.getString(changeIndex);
                stocks.add(symbol + ":" + change);
                initQueryCursor.moveToNext();
            }
        }
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            Intent svcIntent = new Intent(context, WidgetService.class);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            svcIntent.putStringArrayListExtra("STOCKS", stocks);

            RemoteViews widget = new RemoteViews(context.getPackageName(),
                    R.layout.widget_collection);

            widget.setRemoteAdapter(R.id.widget_list, svcIntent);

            Intent intent = new Intent(context, StockWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, widget);
        }
    }
}
