<<<<<<< HEAD
package com.nightcrawler.bakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nightcrawler.bakingapp.appWidget.CollectionAppWidgetProvider;

import org.json.JSONException;

import java.util.Objects;

public class DbOpertionsAysnc extends AsyncTask {

    int key;
    Context context;
    @Override
    protected Object doInBackground(Object[] objects) {


        recipe Recipe = null;
        try {
            Recipe = Utils.returnRecipe(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int test=context.getContentResolver().delete(Contract.PATH_TODOS_URI,null,null);
        if(test==1)
            Log.v("DbOps","Deletion Successful");

        assert Recipe != null;
        for(int i = 0; i< Objects.requireNonNull(Recipe).rsteps.size(); i++)
        {
            ContentValues contentValues = new ContentValues();
            if(i==0)
                contentValues.put(Contract.COL_TODO_TEXT,Recipe.getName());
            else
                contentValues.put(Contract.COL_TODO_TEXT,Recipe.rsteps.get(i).description);

            context.getContentResolver().insert(Contract.PATH_TODOS_URI, contentValues);
        }

        CollectionAppWidgetProvider.sendRefreshBroadcast(context);
        Log.v("Background Db Ops","Successfully updated favourite dish");

        return null;
    }
}
=======
package com.nightcrawler.bakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nightcrawler.bakingapp.appWidget.CollectionAppWidgetProvider;

import org.json.JSONException;

import java.util.Objects;

public class DbOpertionsAysnc extends AsyncTask {

    int key;
    Context context;
    @Override
    protected Object doInBackground(Object[] objects) {


        recipe Recipe = null;
        try {
            Recipe = Utils.returnRecipe(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int test=context.getContentResolver().delete(Contract.PATH_TODOS_URI,null,null);
        if(test==1)
            Log.v("DbOps","Deletion Successful");

        assert Recipe != null;
        for(int i = 0; i< Objects.requireNonNull(Recipe).rsteps.size(); i++)
        {
            ContentValues contentValues = new ContentValues();
            if(i==0)
                contentValues.put(Contract.COL_TODO_TEXT,Recipe.getName());
            else
                contentValues.put(Contract.COL_TODO_TEXT,Recipe.rsteps.get(i).description);

            context.getContentResolver().insert(Contract.PATH_TODOS_URI, contentValues);
        }

        CollectionAppWidgetProvider.sendRefreshBroadcast(context);
        Log.v("Background Db Ops","Successfully updated favourite dish");

        return null;
    }
}
>>>>>>> 269bddf732f87db51e37ea3d1ff410b61ffbe8dd
