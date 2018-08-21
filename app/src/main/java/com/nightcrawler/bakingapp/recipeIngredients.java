package com.nightcrawler.bakingapp;

public class recipeIngredients {

    String quantity, measure, ingredient;

    recipeIngredients(String quantity, String measure, String ingredient) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.quantity = quantity;
    }
}