package com.zaitoun.talat.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import static com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity.*;

public class RecipeStepViewActivity extends AppCompatActivity {

    public static final String RECIPE_STEP_VIDEO_URL_KEY = "RECIPE_STEP_VIDEO_URL";
    public static final String RECIPE_STEP_DESCRIPTION_KEY = "RECIPE_STEP_DESCRIPTION";

    private ArrayList<RecipeStep> mRecipeSteps;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        if (savedInstanceState == null) {

            Intent intentThatStartedActivity = getIntent();

            if (intentThatStartedActivity != null) {
                if (intentThatStartedActivity.hasExtra(RECIPE_STEPS_ARRAY_INTENT_KEY)
                        && intentThatStartedActivity.hasExtra(RECIPE_STEP_INTENT_KEY)) {

                    /* Get the data from the intent */
                    mRecipeSteps = intentThatStartedActivity
                            .getParcelableArrayListExtra(RECIPE_STEPS_ARRAY_INTENT_KEY);

                    mPosition = intentThatStartedActivity
                            .getIntExtra(RECIPE_STEP_INTENT_KEY, 0);

                    RecipeStep recipeStep = mRecipeSteps.get(mPosition);

                    /* Get the fragment manager and add the fragments to the activity */
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    /* Create a new fragment for the video */
                    RecipeStepVideoFragment recipeStepVideoFragment = new RecipeStepVideoFragment();

                    /* Pass the relevant data */
                    String recipeStepVideoUrl = recipeStep.getVideoUrl();
                    Bundle bundle = new Bundle();
                    bundle.putString(RECIPE_STEP_VIDEO_URL_KEY, recipeStepVideoUrl);
                    recipeStepVideoFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_step_video_container, recipeStepVideoFragment)
                            .commit();

                    /* Create a new fragment for the description */
                    RecipeStepDescriptionFragment recipeStepDescriptionFragment
                            = new RecipeStepDescriptionFragment();

                    String description = recipeStep.getDescription();
                    Bundle descriptionBundle = new Bundle();
                    descriptionBundle.putString(RECIPE_STEP_DESCRIPTION_KEY, description);
                    recipeStepDescriptionFragment.setArguments(descriptionBundle);

                    fragmentManager.beginTransaction()
                            .add(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                            .commit();
                }
            }
        }
    }

    public void onNext(View view) {
        if (mPosition < mRecipeSteps.size() - 1) {
            mPosition++;

            RecipeStep recipeStep = mRecipeSteps.get(mPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeStepVideoFragment recipeStepVideoFragment = new RecipeStepVideoFragment();

            /* Pass the relevant data */
            String recipeStepVideoUrl = recipeStep.getVideoUrl();
            Bundle bundle = new Bundle();
            bundle.putString(RECIPE_STEP_VIDEO_URL_KEY, recipeStepVideoUrl);
            recipeStepVideoFragment.setArguments(bundle);

            RecipeStepDescriptionFragment recipeStepDescriptionFragment
                    = new RecipeStepDescriptionFragment();

            String description = recipeStep.getDescription();
            Bundle descriptionBundle = new Bundle();
            descriptionBundle.putString(RECIPE_STEP_DESCRIPTION_KEY, description);
            recipeStepDescriptionFragment.setArguments(descriptionBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                    .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                    .commit();
        }
    }

    public void onPrevious(View view) {

        if (mPosition > 0) {
            mPosition--;

            RecipeStep recipeStep = mRecipeSteps.get(mPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();

            RecipeStepVideoFragment recipeStepVideoFragment = new RecipeStepVideoFragment();

            /* Pass the relevant data */
            String recipeStepVideoUrl = recipeStep.getVideoUrl();
            Bundle bundle = new Bundle();
            bundle.putString(RECIPE_STEP_VIDEO_URL_KEY, recipeStepVideoUrl);
            recipeStepVideoFragment.setArguments(bundle);

            RecipeStepDescriptionFragment recipeStepDescriptionFragment
                    = new RecipeStepDescriptionFragment();

            String description = recipeStep.getDescription();
            Bundle descriptionBundle = new Bundle();
            descriptionBundle.putString(RECIPE_STEP_DESCRIPTION_KEY, description);
            recipeStepDescriptionFragment.setArguments(descriptionBundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_video_container, recipeStepVideoFragment)
                    .replace(R.id.recipe_step_description_container, recipeStepDescriptionFragment)
                    .commit();
        }
    }
}
