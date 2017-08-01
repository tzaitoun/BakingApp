package com.zaitoun.talat.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zaitoun.talat.bakingapp.FetchRecipesAsyncTaskLoader;
import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Recipe;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/* This Activity is responsible for displaying the recipes from the network and allowing navigation
 * to a recipe's details when a recipe is selected.
 */
public class RecipeSelectionActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        RecipeSelectionAdapter.ItemOnClickListener {

    /* Intent Extras */
    public static final String EXTRA_RECIPE_NAME = "com.zaitoun.talat.bakingapp.RECIPE_NAME";
    public static final String EXTRA_INGREDIENTS_ARRAY = "com.zaitoun.talat.bakingapp.INGREDIENTS_ARRAY";
    public static final String EXTRA_RECIPE_STEPS_ARRAY = "com.zaitoun.talat.bakingapp.RECIPE_STEPS_ARRAY";

    /* ID for the loader */
    private static final int RECIPE_LOADER_ID = 0;

    private RecyclerView mRecipeSelectionRecyclerView;
    private RecipeSelectionAdapter mRecipeSelectionAdapter;

    private ArrayList<Recipe> mRecipes;

    private TextView mNoConnectionTextView;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);

        /* Get a reference to views */
        mRecipeSelectionRecyclerView = (RecyclerView) findViewById(R.id.rv_recipe_selection);
        mNoConnectionTextView = (TextView) findViewById(R.id.tv_no_connection);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        /* Set up LayoutManager for RecyclerView */
        LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipeSelectionRecyclerView.setLayoutManager(layoutManager);

        loadRecipes();
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new FetchRecipesAsyncTaskLoader(this, mProgressBar);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {

        /* Hide the progress bar since we retrieved our data */
        mProgressBar.setVisibility(INVISIBLE);

        /* If there is no error in the data */
        if (recipes != null) {

            mRecipeSelectionRecyclerView.setVisibility(VISIBLE);

            /* Hide error handling views */
            mErrorTextView.setVisibility(INVISIBLE);

            /* Cache the recipes so we have access to them in the activity */
            mRecipes = recipes;

            /* Initialize the adapter for the first time, if its not null, then it is already initialized */
            if (mRecipeSelectionAdapter == null) {
                mRecipeSelectionAdapter = new RecipeSelectionAdapter(recipes, this, this);
                mRecipeSelectionRecyclerView.setAdapter(mRecipeSelectionAdapter);
            }
        }

        /* If there is error in the data */
        else {
            mRecipeSelectionRecyclerView.setVisibility(GONE);

            /* Show error handling views */
            mErrorTextView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {
    }

    private void loadRecipes() {

        /* If the device is online, load recipes from online */
        if (isOnline()) {
            mRecipeSelectionRecyclerView.setVisibility(VISIBLE);

            /* Hide error handling views */
            mNoConnectionTextView.setVisibility(INVISIBLE);
            mErrorTextView.setVisibility(INVISIBLE);

            /* Initialize loader */
            getSupportLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
        }

        /* If device is offline */
        else {
            mRecipeSelectionRecyclerView.setVisibility(GONE);
            mErrorTextView.setVisibility(INVISIBLE);

            /* Show error handling views */
            mNoConnectionTextView.setVisibility(VISIBLE);
        }
    }

    /* When a recipe is clicked in the RecyclerView, launch an intent */
    @Override
    public void onItemClick(int position) {

        /* Get the recipe that was clicked */
        Recipe recipe = mRecipes.get(position);

        /* Create a new intent that will launch RecipeStepSelectionActivity */
        Intent intent = new Intent(RecipeSelectionActivity.this, RecipeStepSelectionActivity.class);

        /* Pass all the relevant data */
        intent.putExtra(EXTRA_RECIPE_NAME, recipe.getRecipeName());
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS_ARRAY, recipe.getIngredients());
        intent.putParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY, recipe.getRecipeSteps());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /* When the refresh button is clicked, load the recipes from the network */
        if (id == R.id.refresh) {
            loadRecipes();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if the device has a network connection.
     * Author: @stackoverflow by gar
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}