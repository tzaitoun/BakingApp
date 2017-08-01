package com.zaitoun.talat.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Ingredient;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.*;
import static com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionAdapter.*;

public class RecipeStepSelectionActivity extends AppCompatActivity
        implements RecipeStepSelectionFragment.RecipeStepSelectionCallback {

    /* Bundle Keys */
    public static final String INGREDIENTS_ARRAY_BUNDLE_KEY = "INGREDIENTS_BUNDLE_KEY";
    public static final String RECIPE_STEPS_ARRAY_BUNDLE_KEY = "RECIPE_STEPS_BUNDLE_KEY";

    /* Intent Extras */
    public static final String EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY
            = "com.zaitoun.talat.bakingapp.RECIPE_STEPS_ARRAY_VIEW_ACTIVITY";

    public static final String EXTRA_RECIPE_STEP_POSITION
            = "com.zaitoun.talat.bakingapp.RECIPE_STEP_POSITION";

    public static final String EXTRA_RECIPE_NAME_VIEW_ACTIVITY
            = "com.zaitoun.talat.bakingapp.RECIPE_NAME_VIEW_ACTIVITY";

    private ArrayList<RecipeStep> mRecipeSteps;
    private String mRecipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_selection);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /* Get the intent that started this activity */
        Intent intentThatStartedActivity = getIntent();

        /* Verify that the intent is valid */
        if (intentThatStartedActivity != null) {
            if (intentThatStartedActivity.hasExtra(EXTRA_RECIPE_NAME) &&
                    intentThatStartedActivity.hasExtra(EXTRA_INGREDIENTS_ARRAY) &&
                    intentThatStartedActivity.hasExtra(EXTRA_RECIPE_STEPS_ARRAY)) {

                /* Get the data from the intent */
                String recipeName = intentThatStartedActivity.getStringExtra(EXTRA_RECIPE_NAME);

                ArrayList<Ingredient> ingredients =
                        intentThatStartedActivity.getParcelableArrayListExtra(EXTRA_INGREDIENTS_ARRAY);

                ArrayList<RecipeStep> recipeSteps =
                        intentThatStartedActivity.getParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY);

                /* Cache the recipe steps and recipe name */
                mRecipeSteps = recipeSteps;
                mRecipeName = recipeName;

                /* Set the title to the name of the recipe */
                setTitle(recipeName);

                /* Create a new fragment */
                RecipeStepSelectionFragment fragment = new RecipeStepSelectionFragment();

                /* Pass the data to the fragment */
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(INGREDIENTS_ARRAY_BUNDLE_KEY, ingredients);
                bundle.putParcelableArrayList(RECIPE_STEPS_ARRAY_BUNDLE_KEY, recipeSteps);
                fragment.setArguments(bundle);

                /* Get the fragment manager and add the fragment to the activity */
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.recipe_step_selection_container, fragment)
                        .commit();
            }
        }
    }

    @Override
    public void onRecipeStepSelectionClick(int position) {

        /* Create an intent to launch RecipeStepViewActivity */
        Intent intent = new Intent(RecipeStepSelectionActivity.this, RecipeStepViewActivity.class);

        /* Put the relevant data */
        intent.putParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY, mRecipeSteps);
        intent.putExtra(EXTRA_RECIPE_STEP_POSITION, position - RESERVED_VIEW_FOR_INGREDIENTS);
        intent.putExtra(EXTRA_RECIPE_NAME_VIEW_ACTIVITY, mRecipeName);

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}