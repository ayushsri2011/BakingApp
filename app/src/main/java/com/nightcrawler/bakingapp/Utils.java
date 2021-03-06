package com.nightcrawler.bakingapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Utils {


    public static recipe returnRecipe(int k,String response) throws JSONException {
        ArrayList<recipeIngredients> recipeIngredientsArrayList = new ArrayList();
        ArrayList<recipeSteps> recipeStepsArrayList = new ArrayList();
        recipe Recipe = new recipe();

        try {

            final JSONArray jsonArray = new JSONArray(response);

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

    public static String prefResponse(Context context)    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("jsonresponse", "");
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

    public static ArrayList<String> returnDishList(String response) throws JSONException {
        ArrayList<String> al=new ArrayList();
        try {

            final JSONArray jsonArray = new JSONArray(response);

            int ix=jsonArray.length();

            for(int hg=0;hg<ix;hg++) {
                JSONObject obj = jsonArray.getJSONObject(hg);
                al.add(obj.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;

    }

}