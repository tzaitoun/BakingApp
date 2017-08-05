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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IngredientsService extends IntentService {

    public static final String ACTION_INITIALIZE_INGREDIENTS
            = "com.zaitoun.talat.bakingapp.action_initialize_ingredients";

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
        }
    }

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
