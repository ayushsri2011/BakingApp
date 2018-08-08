package com.nightcrawler.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private recipe Recipe;
    private ListView list;
    private List<String> li;
    private int k = 0;

    OnOpClickListener mCallback;

    public interface OnOpClickListener {
        void onImageSelected(int position);
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnOpClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public ListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


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
        li.add("Recipe Ingredients");

        for (int i = 0; i < Recipe.rsteps.size(); i++)
            li.add(Recipe.rsteps.get(i).shortDescription);

        ArrayAdapter<String> e = new ArrayAdapter<>(getContext(), R.layout.custom_row, li);
        list.setAdapter(e);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCallback.onImageSelected(position);
            }

        });

        return rootView;

    }


}
