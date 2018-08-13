package com.nightcrawler.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nightcrawler.bakingapp.appWidget.CollectionAppWidgetProvider;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    private Intent intent;
    private Bundle args;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t1 = findViewById(R.id.dish1);
        TextView t2 = findViewById(R.id.dish2);
        TextView t3 = findViewById(R.id.dish3);
        TextView t4 = findViewById(R.id.dish4);


        intent = new Intent(MainActivity.this, DetailsActivity.class);

        t1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                args = new Bundle();
                args.putInt("KEY", 1);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);

            }
        });
        t2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("KEY", 2);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("t3", "");
                args = new Bundle();
                args.putInt("KEY", 3);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);

            }
        });

        t4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                args = new Bundle();
                args.putInt("KEY", 4);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("firstStart", 0);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);

        Log.v("OnetimeActivity","Running first time");

        if (firstStart) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();

            DbOpertionsAysnc dbOpertionsAysnc = new DbOpertionsAysnc();
            dbOpertionsAysnc.context = getBaseContext();
            dbOpertionsAysnc.key = 1;

            AsyncTask execute = dbOpertionsAysnc.execute();


        }

        CollectionAppWidgetProvider.sendRefreshBroadcast(getBaseContext());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == R.id.action_settings) {
            Intent startSettingsActivity=new Intent(this,SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("categoriesKey"))
        {
            Toast.makeText(this, "Preference changed", Toast.LENGTH_SHORT).show();
            DbOpertionsAysnc dbOpertionsAysnc=new DbOpertionsAysnc();
            dbOpertionsAysnc.context=getBaseContext();
            dbOpertionsAysnc.key=Integer.parseInt(sharedPreferences.getString(getString(R.string.movies_categories_key),"1"));

            AsyncTask execute = dbOpertionsAysnc.execute();

//            loadList(sharedPreferences);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

        @Override
    public void onDestroy() {
        super.onDestroy();
        //unregister the preference change listener
            PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    private void loadList(SharedPreferences sharedPreferences)
    {
//        movies_categories_key
//        String t=sharedPreferences.getString(getString(R.string.movies_categories_key));
//        Toast.makeText(this, sharedPreferences.getString(getString(R.string.movies_categories_key),"1"), Toast.LENGTH_SHORT).show();
        String choice=sharedPreferences.getString(getString(R.string.movies_categories_key),"1");
        updateDb(choice);
    }

    private void updateDb(String choice)
    {
            getContentResolver().delete(Contract.PATH_TODOS_URI,null,null);


        try {
            recipe Recipe = Utils.returnRecipe(Integer.parseInt(choice));

            for(int i=0;i<Recipe.rsteps.size();i++)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Contract.COL_TODO_TEXT,Recipe.rsteps.get(i).description);
                getContentResolver().insert(Contract.PATH_TODOS_URI, contentValues);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}