package com.zaitoun.talat.bakingapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.utils.RecipeJsonUtils;
import com.zaitoun.talat.bakingapp.utils.RecipeNetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class FetchRecipesAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    /* An array used to cache our recipes */
    private ArrayList<Recipe> mRecipes;

    public FetchRecipesAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {

        /* If the data was cached, deliver result to activity */
        if (mRecipes != null) {
            deliverResult(mRecipes);
        }

        /* Else load it from online */
        else {
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
