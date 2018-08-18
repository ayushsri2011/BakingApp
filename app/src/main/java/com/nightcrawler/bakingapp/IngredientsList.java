package com.nightcrawler.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IngredientsList extends AppCompatActivity {

    ListView list;
    List<String> li;
    recipe Recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        int k = args.getInt("KEY", 1);

        try {
            String resp=Utils.prefResponse(getBaseContext());
            Recipe = Utils.returnRecipe(k,resp);
//            Recipe = Utils.returnRecipe(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        list = findViewById(R.id.listView2);

        li = new ArrayList<>();
//        li.add("Recipe Ingredients");
        int x = 0;
        for (; x < Recipe.rIngredients.size(); x++) {
            li.add(Recipe.rIngredients.get(x).ingredient +
                    "-" + Recipe.rIngredients.get(x).quantity + " " +
                    Recipe.rIngredients.get(x).measure);
        }

        ArrayAdapter<String> e = new ArrayAdapter<>(this, R.layout.custom_row, li);
        list.setAdapter(e);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
