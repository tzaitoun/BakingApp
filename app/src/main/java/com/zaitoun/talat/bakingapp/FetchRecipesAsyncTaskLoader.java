package com.zaitoun.talat.bakingapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.utils.RecipeJsonUtils;
import com.zaitoun.talat.bakingapp.utils.RecipeNetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class FetchRecipesAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    /* An array used to cache our recipes */
    private ArrayList<Recipe> mRecipes;

    private ProgressBar mProgressBar;

    public FetchRecipesAsyncTaskLoader(Context context, ProgressBar progressBar) {
        super(context);
        mProgressBar = progressBar;
    }

    @Override
    protected void onStartLoading() {

        /* If the data was cached, deliver result to activity */
        if (mRecipes != null) {
            deliverResult(mRecipes);
        }

        /* Else load it from online */
        else {
            /* Show the progress bar */
            mProgressBar.setVisibility(View.VISIBLE);
            forceLoad();
        }
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {

        /* Get the recipe data from the url, parse it, and put it into mRecipes */
        try {
            URL url = new URL(RecipeNetworkUtils.RECIPE_URL);
            String jsonRecipeString = RecipeNetworkUtils.getResponseFromHttpUrl(url);
            mRecipes = RecipeJsonUtils.getRecipesFromJson(jsonRecipeString);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        if (mRecipes != null) {

            /* Get the context and shared preferences */
            Context context = getContext();
            SharedPreferences sharedPreferences =
                    context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE);

            /* Create a new Gson object to parse our recipe object into a json string */
            Gson gson = new Gson();

            /* Get the shared preferences editor */
            SharedPreferences.Editor editor = sharedPreferences.edit();

            /* If this is the first time creating the shared preferences, initialize the position.
             * This position determines which recipe ingredients to show in the widget.
             */
            if (!sharedPreferences.contains(context.getString(R.string.position))) {
                editor.putInt(context.getString(R.string.position), 0);
            }

            /* Store the length of the recipes array */
            editor.putInt(context.getString(R.string.length), mRecipes.size());

            /* Write each recipe json string to shared preferences */
            for (int i = 0; i < mRecipes.size(); i++) {
                String jsonRecipe = gson.toJson(mRecipes.get(i));
                editor.putString(Integer.toString(i), jsonRecipe);
            }

            editor.commit();

            IngredientsService.startActionInitializeIngredients(getContext());
        }

        return mRecipes;
    }

    /**
     * Sends the result of the load to the registered listener.
     *
     * @param data The result of the load
     */
    @Override
    public void deliverResult(ArrayList<Recipe> data) {
        mRecipes = data;
        super.deliverResult(data);
    }
}
