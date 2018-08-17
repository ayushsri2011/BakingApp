package com.nightcrawler.bakingapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Utils {


    public static recipe returnRecipe(int k) throws JSONException {
        ArrayList<recipeIngredients> recipeIngredientsArrayList = new ArrayList();
        ArrayList<recipeSteps> recipeStepsArrayList = new ArrayList();
        recipe Recipe = new recipe();
        try {
            String address="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
            Query q = new Query();
            try {
                q.execute(address).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            final JSONArray jsonArray = new JSONArray(q.local_response);


            int i = k - 1;

            JSONObject obj = jsonArray.getJSONObject(i);
            System.out.println("Name of recipe::" + obj.getString("name"));
            JSONArray ingredients = new JSONArray(obj.getString("ingredients"));
            for (int j = 0; j < ingredients.length(); j++) {

                JSONObject c = ingredients.getJSONObject(j);
                String quantity = c.getString("quantity");
                String measure = c.getString("measure");
                String ingredient = c.getString("ingredient");
                recipeIngredients cp = new recipeIngredients(quantity, measure, ingredient);
                recipeIngredientsArrayList.add(cp);
            }
            JSONArray rsteps = new JSONArray(obj.getString("steps"));
            for (int j = 0; j < rsteps.length(); j++) {

                JSONObject c = rsteps.getJSONObject(j);
                String id = c.getString("id");
                String shortDescription = c.getString("shortDescription");
                String description = c.getString("description");
                String videoURL = c.getString("videoURL");
                String thumbnailURL = c.getString("thumbnailURL");
                recipeSteps cp = new recipeSteps(id, shortDescription, description, videoURL, thumbnailURL);
                recipeStepsArrayList.add(cp);
            }

            Recipe.setRsteps(recipeStepsArrayList);
            Recipe.setId(obj.getString("id"));
            Recipe.setName(obj.getString("name"));
            Recipe.setrIngredients(recipeIngredientsArrayList);
            Recipe.setServings(obj.getString("servings"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Recipe;

    }

    public static boolean checkConnectivity(Context context){
        try{
            ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = Objects.requireNonNull(conMgr).getActiveNetworkInfo();
            return netInfo != null;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
