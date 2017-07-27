package com.zaitoun.talat.bakingapp.utils;


import android.support.annotation.Nullable;

import com.zaitoun.talat.bakingapp.model.Ingredient;
import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeJsonUtils {

    /* Recipe json keys */
    private static final String NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String STEPS = "steps";
    private static final String SERVINGS = "servings";
    private static final String IMAGE = "image";

    /* Ingredient json keys */
    private static final String INGREDIENT = "ingredient";
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";

    /* Steps json keys */
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";


    /* Fetches recipes */
    @Nullable
    public static ArrayList<Recipe> getRecipesFromJson(String jsonString) throws JSONException {

        JSONArray jsonRecipeArray = new JSONArray(jsonString);

        if (jsonRecipeArray.length() == 0) {
            return null;
        }

        ArrayList<Recipe> recipes = new ArrayList<>(jsonRecipeArray.length());

        for (int i = 0; i < jsonRecipeArray.length(); i++) {

            JSONObject jsonRecipe = jsonRecipeArray.getJSONObject(i);

            String recipeName = jsonRecipe.getString(NAME);
            ArrayList<Ingredient> ingredients = getIngredientsFromJson(jsonRecipe.getJSONArray(INGREDIENTS));
            ArrayList<RecipeStep> recipeSteps = getRecipeStepsFromJson(jsonRecipe.getJSONArray(STEPS));
            int servings = jsonRecipe.getInt(SERVINGS);
            String image = jsonRecipe.getString(IMAGE);

            Recipe recipe = new Recipe(recipeName, ingredients, recipeSteps, servings, image);

            recipes.add(i, recipe);
        }

        return recipes;
    }

    /* Fetch ingredients of a recipe */
    @Nullable
    private static ArrayList<Ingredient> getIngredientsFromJson(JSONArray jsonIngredientArray)
            throws JSONException {

        if (jsonIngredientArray.length() == 0) {
            return null;
        }

        ArrayList<Ingredient> ingredients = new ArrayList<>(jsonIngredientArray.length());

        for (int i = 0; i < jsonIngredientArray.length(); i++) {

            JSONObject jsonIngredient = jsonIngredientArray.getJSONObject(i);

            String ingredientName = jsonIngredient.getString(INGREDIENT);
            double quantity = jsonIngredient.getDouble(QUANTITY);
            String measurementUnit = jsonIngredient.getString(MEASURE);

            Ingredient ingredient = new Ingredient(ingredientName, quantity, measurementUnit);

            ingredients.add(i, ingredient);
        }

        return ingredients;
    }

    /* Fetches recipe steps of a recipe */
    @Nullable
    private static ArrayList<RecipeStep> getRecipeStepsFromJson(JSONArray jsonRecipeStepArray)
            throws JSONException {

        if (jsonRecipeStepArray.length() == 0) {
            return null;
        }

        ArrayList<RecipeStep> recipeSteps = new ArrayList<>(jsonRecipeStepArray.length());

        for (int i = 0; i < jsonRecipeStepArray.length(); i++) {

            JSONObject jsonRecipeStep = jsonRecipeStepArray.getJSONObject(i);

            String shortDescription = jsonRecipeStep.getString(SHORT_DESCRIPTION);
            String description = jsonRecipeStep.getString(DESCRIPTION);
            String videoUrl = jsonRecipeStep.getString(VIDEO_URL);
            String thumbnailUrl = jsonRecipeStep.getString(THUMBNAIL_URL);

            RecipeStep recipeStep = new RecipeStep(shortDescription, description, videoUrl, thumbnailUrl);

            recipeSteps.add(i, recipeStep);
        }

        return recipeSteps;
    }
}