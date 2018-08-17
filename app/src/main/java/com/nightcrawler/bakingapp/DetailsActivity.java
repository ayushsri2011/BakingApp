package com.nightcrawler.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity implements ListFragment.OnOpClickListener {
    recipe Recipe;
    private boolean mTwoPane;

    FragmentManager fragmentManager = getSupportFragmentManager();
    ListFragment listFragment = new ListFragment();
    DetailsFragment detailsFragment = new DetailsFragment();
    IngredientsFragment ingredientsFragment = new IngredientsFragment();

    String temp;
    int k;  // k is to keep track which recipe has been choosen
    int lastClicked = -1;  // to keep track which element was last clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        Bundle args = i.getBundleExtra("BUNDLE");
        k = args.getInt("KEY", 1);

        try {
            Recipe = Utils.returnRecipe(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_details);
        Bundle bundle = new Bundle();
        bundle.putInt("KEY", k);


        if (findViewById(R.id.android_me_linear_layout) != null) {
            //2 Pane view
            mTwoPane = true;

            if (savedInstanceState == null) {

                lastClicked = 0;
                listFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.master_list_fragment, listFragment).commit();


                ingredientsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.videoContainer, ingredientsFragment).commit();

                temp = "Servings - " + Recipe.getServings();
                detailsFragment.setT(temp);
                fragmentManager.beginTransaction()
                        .add(R.id.detailsContainer, detailsFragment).commit();

            }

        } else {
            //Single Pane
            ListFragment listFragment = new ListFragment();
            listFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.master_list_fragment, listFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // the UI component values are saved here.
        outState.putString("temp", temp);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            temp = savedInstanceState.getString("temp", temp);
            DetailsFragment d = new DetailsFragment();
            d.setT(temp);
            fragmentManager.beginTransaction()
                    .replace(R.id.detailsContainer, d).commit();
        }

    }

    @Override
    public void onImageSelected(int position) {
        lastClicked = position;

        Bundle bundle = new Bundle();
        bundle.putInt("KEY", k);

        if (mTwoPane) {
            DetailsFragment det = new DetailsFragment();
            if (position == 0) {
                temp = "Servings - " + Recipe.getServings();
                det.setT(temp);


                ingredientsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.videoContainer, ingredientsFragment).commit();

            } else {
                temp = Recipe.rsteps.get(position - 1).description;
                det.setT(temp);

                bundle.putString("videoURL", Recipe.rsteps.get(position - 1).videoURL);
                VideoFragment vFragment = new VideoFragment();
                vFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.videoContainer, vFragment).commit();

            }

            fragmentManager.beginTransaction().replace(R.id.detailsContainer, det).commit();
        } else {
            //Single Pane
            if (position == 0) {
                Bundle args;
                Intent intent = new Intent(this, IngredientsList.class);
                args = new Bundle();
                args.putInt("KEY", k);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            } else {
                Bundle args;
                Intent intent = new Intent(this, playVideo.class);
                args = new Bundle();
                args.putInt("currentposition", position);
                args.putInt("KEY", k);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("ishi","DetailsActivity onPause()");
    }
}
