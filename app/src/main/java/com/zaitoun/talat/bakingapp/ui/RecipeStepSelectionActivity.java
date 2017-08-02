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

/**
 * An activity that allows users to select a recipe step and see its details.
 * Single-pane (Phones): A new activity is started to view the recipe step's details.
 * Multi-pane (Tablets): The recipe step's details are all shown in this activity.
 */
public class RecipeStepSelectionActivity extends AppCompatActivity
        implements RecipeStepSelectionFragment.RecipeStepSelectionCallback {

    /* Bundle Keys: data that will be passed to fragments */
    public static final String INGREDIENTS_ARRAY_BUNDLE_KEY = "INGREDIENTS_BUNDLE_KEY";
    public static final String RECIPE_STEPS_ARRAY_BUNDLE_KEY = "RECIPE_STEPS_BUNDLE_KEY";
    public static final String RECIPE_STEP_VIDEO_URL_BUNDLE_KEY = "RECIPE_STEP_VIDEO_URL_BUNDLE_KEY";
    public static final String RECIPE_STEP_DESCRIPTION_BUNDLE_KEY = "RECIPE_STEP_DESCRIPTION_BUNDLE_KEY";

    /* Intent Extras: data that will be passed to the started activity */
    public static final String EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY
            = "com.zaitoun.talat.bakingapp.RECIPE_STEPS_ARRAY_VIEW_ACTIVITY";

    public static final String EXTRA_RECIPE_STEP_POSITION
            = "com.zaitoun.talat.bakingapp.RECIPE_STEP_POSITION";

    public static final String EXTRA_RECIPE_NAME_VIEW_ACTIVITY
            = "com.zaitoun.talat.bakingapp.RECIPE_NAME_VIEW_ACTIVITY";

    /* Member variables to cache data */
    private ArrayList<RecipeStep> mRecipeSteps;
    private ArrayList<Ingredient> mIngredients;
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
                mRecipeName = intentThatStartedActivity.getStringExtra(EXTRA_RECIPE_NAME);
                mIngredients = intentThatStartedActivity.getParcelableArrayListExtra(EXTRA_INGREDIENTS_ARRAY);
                mRecipeSteps = intentThatStartedActivity.getParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY);

                /* Set the title of the activity to the name of the recipe */
                setTitle(mRecipeName);

                /* Create new fragments when there is no previous saved instance state */
                if (savedInstanceState == null) {

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    /* If the device is a tablet */
                    if (getResources().getBoolean(R.bool.isTablet)) {

                        /* We will initialize the fragments with the first recipe step details */
                        RecipeStep recipeStep = mRecipeSteps.get(0);

                        /* Create the fragments and add them to the activity */
                        RecipeStepSelectionFragment recipeStepSelectionFragment =
                                createRecipeStepSelectionFragment();

                        RecipeStepVideoFragment recipeStepVideoFragment =
                                createRecipeStepVideoFragment(recipeStep);

                        RecipeStepDescriptionFragment recipeStepDescriptionFragment =
                                createRecipeStepDescriptionFragment(recipeStep);

                        fragmentManager.beginTransaction()
                                .add(R.id.recipe_step_selection_container, recipeStepSelectionFragment)
                                .add(R.id.recipe_step_video_container, recipeStepVideoFragment)
                                .add(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                                .commit();
                    }

                    /* If the device is a phone */
                    else {

                        /* Create the fragment and add it to the activity */
                        RecipeStepSelectionFragment recipeStepSelectionFragment =
                                createRecipeStepSelectionFragment();

                        fragmentManager.beginTransaction()
                                .add(R.id.recipe_step_selection_container, recipeStepSelectionFragment)
                                .commit();
                    }
                }
            }
        }
    }
    /**
     * This method responds when a user clicks a recipe step in the RecipeStepSelectionFragment.
     * Single-pane (Phone): Starts a RecipeStepViewActivity to display the data.
     * Multi-pane (Tablet): Replaces the existing fragments with fragments that display the correct data.
     * @param position The position of the item that was clicked in the RecyclerView, we have to
     *                 account for the view which is reserved for the ingredients.
     */
    @Override
    public void onRecipeStepSelectionClick(int position) {

        /* If the device is a tablet */
        if (getResources().getBoolean(R.bool.isTablet)) {

            /* Get the recipe step that was clicked on */
            RecipeStep recipeStep = mRecipeSteps.get(position - RESERVED_VIEW_FOR_INGREDIENTS);

            /* Create the fragments by passing the correct recipe step to display */
            RecipeStepVideoFragment recipeStepVideoFragment =
                    createRecipeStepVideoFragment(recipeStep);

            RecipeStepDescriptionFragment recipeStepDescriptionFragment =
                    createRecipeStepDescriptionFragment(recipeStep);

            /* Get the fragment manager and replace the existing fragments with the new ones */
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                    .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                    .commit();
        }

        /* If the device is a phone */
        else {

            /* Create an intent to launch RecipeStepViewActivity */
            Intent intent = new Intent(RecipeStepSelectionActivity.this, RecipeStepViewActivity.class);

            intent.putParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY, mRecipeSteps);
            intent.putExtra(EXTRA_RECIPE_STEP_POSITION, position - RESERVED_VIEW_FOR_INGREDIENTS);
            intent.putExtra(EXTRA_RECIPE_NAME_VIEW_ACTIVITY, mRecipeName);

            startActivity(intent);
        }
    }

    /**
     * This method creates a RecipeStepSelectionFragment.
     */
    private RecipeStepSelectionFragment createRecipeStepSelectionFragment() {

        /* Create a new RecipeStepSelectionFragment */
        RecipeStepSelectionFragment fragment = new RecipeStepSelectionFragment();

        /* Pass the recipe ingredients and steps to the fragment */
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS_ARRAY_BUNDLE_KEY, mIngredients);
        bundle.putParcelableArrayList(RECIPE_STEPS_ARRAY_BUNDLE_KEY, mRecipeSteps);
        fragment.setArguments(bundle);

        return fragment;
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