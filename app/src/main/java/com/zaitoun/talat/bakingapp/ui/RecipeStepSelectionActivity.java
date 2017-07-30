package com.zaitoun.talat.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Ingredient;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.*;

public class RecipeStepSelectionActivity extends AppCompatActivity {

    /* Keys for bundle */
    public static final String INGREDIENTS_ARRAY_BUNDLE_KEY = "INGREDIENTS_BUNDLE_KEY";
    public static final String RECIPE_STEPS_ARRAY_BUNDLE_KEY = "RECIPE_STEPS_BUNDLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_selection);

        /* Get the intent that started this activity */
        Intent intentThatStartedActivity = getIntent();

        /* Verify that the intent is valid */
        if (intentThatStartedActivity != null) {
            if (intentThatStartedActivity.hasExtra(RECIPE_NAME_KEY) &&
                    intentThatStartedActivity.hasExtra(INGREDIENTS_ARRAY_KEY) &&
                    intentThatStartedActivity.hasExtra(RECIPE_STEPS_ARRAY_KEY)) {

                /* Get the data from the intent */
                String recipeName = intentThatStartedActivity.getStringExtra(RECIPE_NAME_KEY);

                ArrayList<Ingredient> ingredients =
                        intentThatStartedActivity.getParcelableArrayListExtra(INGREDIENTS_ARRAY_KEY);

                ArrayList<RecipeStep> recipeSteps =
                        intentThatStartedActivity.getParcelableArrayListExtra(RECIPE_STEPS_ARRAY_KEY);

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
}