package com.nightcrawler.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class IngredientsFragment extends Fragment {
    private recipe Recipe;
    private ListView list;
    private List<String> li;

    public IngredientsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        int k = 0;
        Bundle bundle = getArguments();
        if (bundle != null)
            k = bundle.getInt("KEY");
        try {
            Recipe = Utils.returnRecipe(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        list = rootView.findViewById(R.id.listView1);

        li = new ArrayList<>();

        for (int i = 0; i < Recipe.rIngredients.size(); i++) {
            li.add(Recipe.rIngredients.get(i).ingredient +
                    "-" + Recipe.rIngredients.get(i).quantity + " " +
                    Recipe.rIngredients.get(i).measure);
        }

        ArrayAdapter<String> e = new ArrayAdapter<>(getContext(), R.layout.custom_row, li);
        list.setAdapter(e);


        return rootView;

    }

}
