package com.zaitoun.talat.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import static com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity.*;

/**
 * An activity that displays the recipe step's video and description instructions.
 * Portrait: Displays the video and description instructions.
 * Landscape: Displays the video in full-screen.
 * It also allows navigation to other recipe step details.
 */
public class RecipeStepViewActivity extends AppCompatActivity {

    public static final String RECIPE_STEP_POSITION_KEY = "RECIPE_STEP_POSITION";

    /* Member variables for caching data */
    private ArrayList<RecipeStep> mRecipeSteps;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /* Get the intent that started the activity */
        Intent intentThatStartedActivity = getIntent();

        /* Check if the intent is valid */
        if (intentThatStartedActivity != null) {
            if (intentThatStartedActivity.hasExtra(EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY) &&
                    intentThatStartedActivity.hasExtra(EXTRA_RECIPE_STEP_POSITION) &&
                    intentThatStartedActivity.hasExtra(EXTRA_RECIPE_NAME_VIEW_ACTIVITY)) {

                /* Get the data from the intent */
                mRecipeSteps = intentThatStartedActivity
                        .getParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY);

                mPosition = intentThatStartedActivity.getIntExtra(EXTRA_RECIPE_STEP_POSITION, 0);
                String recipeName = intentThatStartedActivity.getStringExtra(EXTRA_RECIPE_NAME_VIEW_ACTIVITY);

                /* Set the title of the activity to the recipe name */
                setTitle(recipeName);

                /* Hide the action bar when the device is in landscape */
                if (getResources().getBoolean(R.bool.isLandscape) && actionBar != null) {
                    actionBar.hide();
                }

                /* Get the recipe step that we need to display it's details */
                RecipeStep recipeStep = mRecipeSteps.get(mPosition);

                /* Create new fragments when there is no previous saved instance state */
                if (savedInstanceState == null) {

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    /* Create the fragments and add them to the activity */
                    RecipeStepVideoFragment recipeStepVideoFragment =
                            createRecipeStepVideoFragment(recipeStep);

                    RecipeStepDescriptionFragment recipeStepDescriptionFragment =
                            createRecipeStepDescriptionFragment(recipeStep);

                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_step_video_container, recipeStepVideoFragment)
                            .add(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                            .commit();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_STEP_POSITION_KEY, mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPosition = savedInstanceState.getInt(RECIPE_STEP_POSITION_KEY);
    }

    /**
     * This method navigates to the next step when the button is clicked.
     * @param view: The button that was clicked.
     */
    public void onNext(View view) {

        /* Check that the phone is in portrait */
        if (!getResources().getBoolean(R.bool.isLandscape)) {

            if (mPosition < mRecipeSteps.size() - 1) {

                /* Increment the position so that we can navigate to the next recipe step */
                mPosition++;

                /* Get the recipe step at the new position */
                RecipeStep recipeStep = mRecipeSteps.get(mPosition);

                FragmentManager fragmentManager = getSupportFragmentManager();

                /* Create the fragments and replace the existing fragments with the new ones */
                RecipeStepVideoFragment recipeStepVideoFragment =
                        createRecipeStepVideoFragment(recipeStep);

                RecipeStepDescriptionFragment recipeStepDescriptionFragment =
                        createRecipeStepDescriptionFragment(recipeStep);

                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                        .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                        .commit();
            }
        }
    }

    /**
     * This method navigates to the previous step when the button is clicked.
     * @param view: The button that was clicked.
     */
    public void onPrevious(View view) {

        /* Check that the phone is in portrait */
        if (!getResources().getBoolean(R.bool.isLandscape)) {

            if (mPosition > 0) {

                /* Decrement the position so that we can navigate to the previous recipe step */
                mPosition--;

                /* Get the recipe step at the new position */
                RecipeStep recipeStep = mRecipeSteps.get(mPosition);

                FragmentManager fragmentManager = getSupportFragmentManager();

                /* Create the fragments and replace the existing fragments with the new ones */
                RecipeStepVideoFragment recipeStepVideoFragment =
                        createRecipeStepVideoFragment(recipeStep);

                RecipeStepDescriptionFragment recipeStepDescriptionFragment =
                        createRecipeStepDescriptionFragment(recipeStep);

                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                        .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                        .commit();
            }
        }
    }

    /**
     * This method creates a RecipeStepVideoFragment.
     * @param recipeStep: The recipe step that we need to display it's video.
     */
    private RecipeStepVideoFragment createRecipeStepVideoFragment(RecipeStep recipeStep) {

        /* Create a new RecipeStepVideoFragment */
        RecipeStepVideoFragment fragment = new RecipeStepVideoFragment();

        /* Pass the recipe step video url to the fragment */
        String recipeStepVideoUrl = recipeStep.getVideoUrl();
        Bundle bundle = new Bundle();
        bundle.putString(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY, recipeStepVideoUrl);
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * This method creates a RecipeStepDescriptionFragment.
     * @param recipeStep: The recipe step that we need to display it's description.
     */
    private RecipeStepDescriptionFragment createRecipeStepDescriptionFragment(RecipeStep recipeStep) {

        /* Create a new RecipeStepDescriptionFragment */
        RecipeStepDescriptionFragment fragment = new RecipeStepDescriptionFragment();

        /* Pass the recipe step description to the fragment */
        String description = recipeStep.getDescription();
        Bundle bundle = new Bundle();
        bundle.putString(RECIPE_STEP_DESCRIPTION_BUNDLE_KEY, description);
        fragment.setArguments(bundle);

        return fragment;
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
