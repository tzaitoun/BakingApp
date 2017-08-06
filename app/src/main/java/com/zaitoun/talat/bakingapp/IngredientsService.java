package com.zaitoun.talat.bakingapp;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.zaitoun.talat.bakingapp.model.Ingredient;
import com.zaitoun.talat.bakingapp.model.Recipe;

import java.util.ArrayList;

public class IngredientsService extends IntentService {

    public static final String ACTION_INITIALIZE_INGREDIENTS
            = "com.zaitoun.talat.bakingapp.action_initialize_ingredients";

    public static final String ACTION_NEXT_RECIPE_INGREDIENTS
            = "com.zaitoun.talat.bakingapp.action_next_recipe_ingredients";

    public static final String ACTION_PREV_RECIPE_INGREDIENTS
            = "com.zaitoun.talat.bakingapp.action_prev_recipe_ingredients";

    public IngredientsService() {
        super(IngredientsService.class.getSimpleName());
    }

    public static void startActionInitializeIngredients(Context context) {
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(ACTION_INITIALIZE_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();

            if (ACTION_INITIALIZE_INGREDIENTS.equals(action)) {
                handleActionInitializeIngredients();
            }

            else if (ACTION_NEXT_RECIPE_INGREDIENTS.equals(action)) {
                handleActionNextRecipeIngredients();
            }

            else if (ACTION_PREV_RECIPE_INGREDIENTS.equals(action)) {
                handleActionPrevRecipeIngredients();
            }
        }
    }

    /* This is called to initialize the widget */
    private void handleActionInitializeIngredients() {

        /* Get the shared preferences */
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preferences_key), MODE_PRIVATE);

        /* Get the position of the recipe ingredients to display */
        int position = sharedPreferences.getInt(getString(R.string.position), 0);

        /* Get the recipe json string from shared preferences */
        String recipeJsonString = sharedPreferences.getString(Integer.toString(position), null);

        /* Create a Gson to parse our json data into a Recipe object */
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeJsonString, Recipe.class);

        /* Format the ingredients */
        String ingredients = getIngredientsString(recipe.getIngredients());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        IngredientsWidget.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds, recipe, ingredients);
    }

    /* This is called when the next button is clicked to update the widget */
    private void handleActionNextRecipeIngredients() {

        /* Get the shared preferences */
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preferences_key), MODE_PRIVATE);

        /* Get the last recipe ingredients position and the length of recipe array */
        int position = sharedPreferences.getInt(getString(R.string.position), 0);
        int length = sharedPreferences.getInt(getString(R.string.length), 0);

        /* Move to the next recipe and if its the last recipe move back to the first one */
        if (position < length - 1) {
            position++;
        } else {
            position = 0;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.position), position);
        editor.commit();

        /* Get the recipe json string from shared preferences */
        String recipeJsonString = sharedPreferences.getString(Integer.toString(position), null);

        /* Create a Gson to parse our json data into a Recipe object */
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeJsonString, Recipe.class);

        /* Format the ingredients */
        String ingredients = getIngredientsString(recipe.getIngredients());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        IngredientsWidget.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds, recipe, ingredients);
    }

    /* This is called when the prev button is clicked to update the widget */
    private void handleActionPrevRecipeIngredients() {

        /* Get the shared preferences */
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preferences_key), MODE_PRIVATE);

        /* Get the last recipe ingredients position and the length of recipe array */
        int position = sharedPreferences.getInt(getString(R.string.position), 0);
        int length = sharedPreferences.getInt(getString(R.string.length), 0);

        /* Move to the prev recipe and if its the first recipe, move to the last one */
        if (position > 0) {
            position--;
        } else {
            position = length - 1;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.position), position);
        editor.commit();

        /* Get the recipe json string from shared preferences */
        String recipeJsonString = sharedPreferences.getString(Integer.toString(position), null);

        /* Create a Gson to parse our json data into a Recipe object */
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(recipeJsonString, Recipe.class);

        /* Format the ingredients */
        String ingredients = getIngredientsString(recipe.getIngredients());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        IngredientsWidget.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds, recipe, ingredients);
    }

    /* Parses the ingredients and formats them into user-friendly string */
    private String getIngredientsString(ArrayList<Ingredient> ingredients) {

        String ingredientsString = "";

        for (int i = 0; i < ingredients.size(); i++) {

            if (i == ingredients.size() - 1) {
                Ingredient ingredient = ingredients.get(i);
                ingredientsString += ingredient.getQuantity() + " " +
                        (ingredient.getMeasurementUnit()).toLowerCase()
                        + " " + ingredient.getIngredientName();
            }

            else {
                Ingredient ingredient = ingredients.get(i);
                ingredientsString += ingredient.getQuantity() + " " +
                        (ingredient.getMeasurementUnit()).toLowerCase()
                        + " " + ingredient.getIngredientName() + "\n";
            }
        }

        return  ingredientsString;
    }
}
