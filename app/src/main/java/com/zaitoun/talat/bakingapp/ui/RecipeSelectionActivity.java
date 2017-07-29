package com.zaitoun.talat.bakingapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zaitoun.talat.bakingapp.FetchRecipesAsyncTaskLoader;
import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.utils.RecipeJsonUtils;
import com.zaitoun.talat.bakingapp.utils.RecipeNetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

public class RecipeSelectionActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    private static final int RECIPE_LOADER_ID = 0;

    private RecyclerView mRecipeSelectionRecyclerView;
    private RecipeSelectionAdapter mRecipeSelectionAdapter;

    private TextView mNoConnectionTextView;
    private TextView mErrorTextView;
    private ImageView mRefreshImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);

        /* Get a reference to views */
        mRecipeSelectionRecyclerView = (RecyclerView) findViewById(R.id.rv_recipe_selection);
        mNoConnectionTextView = (TextView) findViewById(R.id.tv_no_connection);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);
        mRefreshImageView = (ImageView) findViewById(R.id.iv_refresh);

        mRefreshImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecipes();
            }
        });

        /* Set up LayoutManager for RecyclerView */
        LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipeSelectionRecyclerView.setLayoutManager(layoutManager);

        loadRecipes();
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new FetchRecipesAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {

        /* If there is no error in the data */
        if (recipes != null) {

            mRecipeSelectionRecyclerView.setVisibility(VISIBLE);

            /* Hide error handling views */
            mErrorTextView.setVisibility(INVISIBLE);
            mRefreshImageView.setVisibility(INVISIBLE);

            /* Initialize the adapter for the first time, if its not null, then it is already initialized */
            if (mRecipeSelectionAdapter == null) {
                mRecipeSelectionAdapter = new RecipeSelectionAdapter(recipes, this);
                mRecipeSelectionRecyclerView.setAdapter(mRecipeSelectionAdapter);
            }
        }

        /* If there is error in the data */
        else {
            mRecipeSelectionRecyclerView.setVisibility(GONE);

            /* Show error handling views */
            mErrorTextView.setVisibility(VISIBLE);
            mRefreshImageView.setVisibility(VISIBLE);
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
            mRefreshImageView.setVisibility(INVISIBLE);
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
            mRefreshImageView.setVisibility(VISIBLE);
        }
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