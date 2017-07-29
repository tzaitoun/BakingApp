package com.zaitoun.talat.bakingapp.model;


import java.util.ArrayList;

public class Recipe {

    private final String recipeName;
    private final ArrayList<Ingredient> ingredients;
    private final ArrayList<RecipeStep> recipeSteps;
    private final int servings;
    private final String image;

    public Recipe(String recipeName, ArrayList<Ingredient> ingredients,
                  ArrayList<RecipeStep> recipeSteps, int servings, String image) {

        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
        this.servings = servings;
        this.image = image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}