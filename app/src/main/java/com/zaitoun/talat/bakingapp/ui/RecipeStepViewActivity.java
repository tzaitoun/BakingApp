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

public class RecipeStepViewActivity extends AppCompatActivity {

    /* Bundle Keys */
    public static final String RECIPE_STEP_VIDEO_URL_BUNDLE_KEY = "RECIPE_STEP_VIDEO_URL_BUNDLE_KEY";
    public static final String RECIPE_STEP_DESCRIPTION_BUNDLE_KEY = "RECIPE_STEP_DESCRIPTION_BUNDLE_KEY";

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

        if (savedInstanceState == null) {

            /* Get the intent that started the activity */
            Intent intentThatStartedActivity = getIntent();

            /* Check that it is valid */
            if (intentThatStartedActivity != null) {
                if (intentThatStartedActivity.hasExtra(EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY)
                        && intentThatStartedActivity.hasExtra(EXTRA_RECIPE_STEP_POSITION)
                        && intentThatStartedActivity.hasExtra(EXTRA_RECIPE_NAME_VIEW_ACTIVITY)) {

                    /* Get the data from the intent */
                    mRecipeSteps = intentThatStartedActivity
                            .getParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY_VIEW_ACTIVITY);

                    mPosition = intentThatStartedActivity
                            .getIntExtra(EXTRA_RECIPE_STEP_POSITION, 0);

                    String recipeName = intentThatStartedActivity
                            .getStringExtra(EXTRA_RECIPE_NAME_VIEW_ACTIVITY);

                    /* Set the title to the recipe name */
                    setTitle(recipeName);

                    /* Get the recipe step */
                    RecipeStep recipeStep = mRecipeSteps.get(mPosition);

                    /* Get the fragment manager and add the fragments to the activity */
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    /* Create a new fragment for the video */
                    RecipeStepVideoFragment recipeStepVideoFragment = new RecipeStepVideoFragment();

                    /* Pass the relevant data */
                    String recipeStepVideoUrl = recipeStep.getVideoUrl();
                    Bundle bundle = new Bundle();
                    bundle.putString(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY, recipeStepVideoUrl);
                    recipeStepVideoFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_step_video_container, recipeStepVideoFragment)
                            .commit();

                    /* Create a new fragment for the description */
                    RecipeStepDescriptionFragment recipeStepDescriptionFragment
                            = new RecipeStepDescriptionFragment();

                    /* Pass the relevant data */
                    String description = recipeStep.getDescription();
                    Bundle descriptionBundle = new Bundle();
                    descriptionBundle.putString(RECIPE_STEP_DESCRIPTION_BUNDLE_KEY, description);
                    recipeStepDescriptionFragment.setArguments(descriptionBundle);

                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                            .commit();
                }
            }
        }
    }

    /* When clicked, it shows the next recipe step */
    public void onNext(View view) {
        if (mPosition < mRecipeSteps.size() - 1) {
            mPosition++;

            RecipeStep recipeStep = mRecipeSteps.get(mPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeStepVideoFragment recipeStepVideoFragment = new RecipeStepVideoFragment();

            /* Pass the relevant data */
            String recipeStepVideoUrl = recipeStep.getVideoUrl();
            Bundle bundle = new Bundle();
            bundle.putString(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY, recipeStepVideoUrl);
            recipeStepVideoFragment.setArguments(bundle);

            RecipeStepDescriptionFragment recipeStepDescriptionFragment
                    = new RecipeStepDescriptionFragment();

            String description = recipeStep.getDescription();
            Bundle descriptionBundle = new Bundle();
            descriptionBundle.putString(RECIPE_STEP_DESCRIPTION_BUNDLE_KEY, description);
            recipeStepDescriptionFragment.setArguments(descriptionBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                    .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                    .commit();
        }
    }

    /* When clicked, it shows the previous recipe step */
    public void onPrevious(View view) {

        if (mPosition > 0) {
            mPosition--;

            RecipeStep recipeStep = mRecipeSteps.get(mPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeStepVideoFragment recipeStepVideoFragment = new RecipeStepVideoFragment();

            /* Pass the relevant data */
            String recipeStepVideoUrl = recipeStep.getVideoUrl();
            Bundle bundle = new Bundle();
            bundle.putString(RECIPE_STEP_VIDEO_URL_BUNDLE_KEY, recipeStepVideoUrl);
            recipeStepVideoFragment.setArguments(bundle);

            RecipeStepDescriptionFragment recipeStepDescriptionFragment
                    = new RecipeStepDescriptionFragment();

            String description = recipeStep.getDescription();
            Bundle descriptionBundle = new Bundle();
            descriptionBundle.putString(RECIPE_STEP_DESCRIPTION_BUNDLE_KEY, description);
            recipeStepDescriptionFragment.setArguments(descriptionBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                    .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
