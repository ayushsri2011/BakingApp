package com.nightcrawler.bakingapp;

import java.util.ArrayList;

public class recipe {

    ArrayList<recipeSteps> rsteps;
    String name;
    String id;
    String servings;
    ArrayList<recipeIngredients> rIngredients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setrIngredients(ArrayList<recipeIngredients> rIngredients) {
        this.rIngredients = rIngredients;
    }

    public void setRsteps(ArrayList<recipeSteps> rsteps) {
        this.rsteps = rsteps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

}
