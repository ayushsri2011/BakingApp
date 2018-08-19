package com.nightcrawler.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    private recipe Recipe;
    private CustomAdapter adapter;

    @BindView(R.id.bohe)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        Intent i = getIntent();
        Bundle args = i.getBundleExtra("BUNDLE");
        int k = args.getInt("KEY", 1);


//        ArrayList<String> List=new ArrayList();
        try {
            String resp = Utils.prefResponse(this);
            Recipe = Utils.returnRecipe(k, resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> optionslist = new ArrayList<>();
        optionslist.add("Recipe Ingredients");

        for (int q = 0; q < Recipe.rsteps.size(); q++) {
            optionslist.add(Recipe.rsteps.get(q).shortDescription);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(Main2Activity.this, 2));
        adapter = new CustomAdapter(this);
        adapter.setStage(1);
        adapter.setKey(k);
        populateRecyclerViewValues(optionslist);


    }

    private void populateRecyclerViewValues(ArrayList<String> dList) {

        adapter.setListContent(dList);
        recyclerView.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
