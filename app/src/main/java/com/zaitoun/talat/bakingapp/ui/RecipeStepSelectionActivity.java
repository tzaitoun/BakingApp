package com.zaitoun.talat.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zaitoun.talat.bakingapp.R;

import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.*;

public class RecipeStepSelectionActivity extends AppCompatActivity {

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


            }
        }
    }
}
