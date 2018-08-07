package com.nightcrawler.bakingapp.appWidget;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.nightcrawler.bakingapp.Contract;
import com.nightcrawler.bakingapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<String> widgetList;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        updateWidgetListView();
    }

    private void updateWidgetListView() {

        widgetList = new ArrayList<>();
//        Uri uri = Contract.PATH_TODOS_URI;
        try {
            Cursor cursor=mContext.getContentResolver().query(Contract.PATH_TODOS_URI,null,null,
                    null,null);

            cursor.moveToFirst();

            if (cursor.getCount() == 0) {
                widgetList.add("No entry");
            } else {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    widgetList.add(cursor.getString(1));
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.v("Failed to fetch values", "Fail");
            e.printStackTrace();
            String[] widgetFruitsArray = {"String 1", "String 2", "String 3"};
            Log.v("Finally Section Run", "");
            this.widgetList = new ArrayList<String>(Arrays.asList(widgetFruitsArray));
        }


    }

    @Override
    public void onDataSetChanged() {

        updateWidgetListView();
    }

    @Override
    public void onDestroy() {
        widgetList.clear();
    }

    @Override
    public int getCount() {
        return widgetList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, widgetList.get(position));

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("Extra", widgetList.get(1));
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);


        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}