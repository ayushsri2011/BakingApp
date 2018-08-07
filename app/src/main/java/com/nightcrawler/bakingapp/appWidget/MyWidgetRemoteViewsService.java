package com.nightcrawler.bakingapp.appWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;
import com.nightcrawler.bakingapp.appWidget.MyWidgetRemoteViewsFactory;


public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}