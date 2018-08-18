package com.nightcrawler.bakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nightcrawler.bakingapp.appWidget.CollectionAppWidgetProvider;

import org.json.JSONException;

import java.util.Objects;

public class DbOpertionsAysnc extends AsyncTask<String,String,String> {

    int key;
    Context context;
    @Override
    protected String doInBackground(String... params) {

        Log.v("DbOps","doInBackground thread");
        recipe Recipe = null;
//        String resp = params[0];
        try {
            String resp=Utils.prefResponse(context);
            Recipe = Utils.returnRecipe(key,resp);


//            Recipe = Utils.returnRecipe(key,resp);
        Log.v("DbOps","Recipe received");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        assert Recipe != null;
        int test=context.getContentResolver().delete(Contract.PATH_TODOS_URI,null,null);
        if(test==1)
            Log.v("DbOps","Deletion Successful");
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

