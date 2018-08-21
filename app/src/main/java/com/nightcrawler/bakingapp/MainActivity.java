package com.nightcrawler.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, OnTaskCompleted {


    private Intent intent;
    private Bundle args;
    private CustomAdapter adapter;
    private ArrayList<String> dishList;
    // --Commented out by Inspection (8/18/2018 11:41 PM):Toolbar toolbar;
    @BindView(R.id.bohe)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Resources res = getBaseContext().getResources();
        String address = res.getString(R.string.address);
        SharedPreferences sharedPreferences = getSharedPreferences("firstStart", 0);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);


        if (Utils.checkConnectivity(this)) {
            Query q = new Query(MainActivity.this);
            q.execute(address);
        } else
            Toast.makeText(MainActivity.this, "Ensure data connectivity", Toast.LENGTH_SHORT).show();


        try {
            dishList = Utils.returnDishList(Utils.prefResponse(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        adapter = new CustomAdapter(this);
        populateRecyclerViewValues(dishList);
    }

    public void startRV() {
        try {
            dishList = Utils.returnDishList(Utils.prefResponse(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        adapter = new CustomAdapter(this);
        adapter.setStage(0);
        populateRecyclerViewValues(dishList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (Utils.checkConnectivity(getBaseContext()))
            getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("categoriesKey")) {
            Toast.makeText(this, "Preference changed", Toast.LENGTH_SHORT).show();
            DbOpertionsAysnc dbOpertionsAysnc = new DbOpertionsAysnc();
            dbOpertionsAysnc.context = getBaseContext();
            dbOpertionsAysnc.key = Integer.parseInt(sharedPreferences.getString(getString(R.string.movies_categories_key), "1"));

            AsyncTask execute = dbOpertionsAysnc.execute();

//            loadList(sharedPreferences);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //unregister the preference change listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onTaskCompleted(String response) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("jsonresponse", response);
        editor.apply();
        editor.putBoolean("firstStart", false);
        editor.apply();
        startRV();

    }

    private void populateRecyclerViewValues(ArrayList<String> dishList) {

        adapter.setListContent(dishList);
        recyclerView.setAdapter(adapter);
    }
}

//COMMENTED CODE
//    private void loadList(SharedPreferences sharedPreferences) {
////        movies_categories_key
////        String t=sharedPreferences.getString(getString(R.string.movies_categories_key));
////        Toast.makeText(this, sharedPreferences.getString(getString(R.string.movies_categories_key),"1"), Toast.LENGTH_SHORT).show();
//        String choice = sharedPreferences.getString(getString(R.string.movies_categories_key), "1");
//        updateDb(choice);
//    }

//    private void updateDb(String choice) {
//        getContentResolver().delete(Contract.PATH_TODOS_URI, null, null);
//
//
//        try {
//            String resp = Utils.prefResponse(this);
//            recipe Recipe = Utils.returnRecipe(Integer.parseInt(choice), resp);
//
//            for (int i = 0; i < Recipe.rsteps.size(); i++) {
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(Contract.COL_TODO_TEXT, Recipe.rsteps.get(i).description);
//                getContentResolver().insert(Contract.PATH_TODOS_URI, contentValues);
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//------------------------------
//        intent = new Intent(MainActivity.this, DetailsActivity.class);

//        t1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utils.checkConnectivity(getBaseContext())) {
//                    args = new Bundle();
//                    args.putInt("KEY", 1);
//                    intent.putExtra("BUNDLE", args);
//                    startActivity(intent);
//                } else
//                    Toast.makeText(MainActivity.this, "Ensure data connectivity", Toast.LENGTH_SHORT).show();
//            }
//        });
//        t2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utils.checkConnectivity(getBaseContext())) {
//                    Bundle args = new Bundle();
//                    args.putInt("KEY", 2);
//                    intent.putExtra("BUNDLE", args);
//                    startActivity(intent);
//                } else
//                    Toast.makeText(MainActivity.this, "Ensure data connectivity", Toast.LENGTH_SHORT).show();
//            }
//        });
//        t3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utils.checkConnectivity(getBaseContext())) {
//                    Log.v("t3", "");
//                    args = new Bundle();
//                    args.putInt("KEY", 3);
//                    intent.putExtra("BUNDLE", args);
//                    startActivity(intent);
//                } else
//                    Toast.makeText(MainActivity.this, "Ensure data connectivity", Toast.LENGTH_SHORT).show();
//            }
//        });
//        t4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Utils.checkConnectivity(getBaseContext())) {
//                    args = new Bundle();
//                    args.putInt("KEY", 4);
//                    intent.putExtra("BUNDLE", args);
//                    startActivity(intent);
//                } else
//                    Toast.makeText(MainActivity.this, "Ensure data connectivity", Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView= findViewById(R.id.bohe);
//            String address = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
//            CollectionAppWidgetProvider.sendRefreshBroadcast(getBaseContext());


