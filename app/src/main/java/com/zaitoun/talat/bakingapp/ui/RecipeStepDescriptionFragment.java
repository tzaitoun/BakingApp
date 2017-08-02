package com.zaitoun.talat.bakingapp.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaitoun.talat.bakingapp.R;

import static com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity.RECIPE_STEP_DESCRIPTION_BUNDLE_KEY;

/**
 * A Fragment that displays a recipe step's description.
 */
public class RecipeStepDescriptionFragment extends Fragment {

    public RecipeStepDescriptionFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Inflate the layout of the fragment */
        final View view = inflater.inflate(R.layout.fragment_recipe_step_description, container, false);

        /* Get a reference to the view */
        TextView recipeStepDescriptionTextView = (TextView) view.findViewById(R.id.tv_recipe_step_description);

        Bundle bundle = getArguments();

        /* Check if the bundle is valid */
        if (bundle != null && bundle.containsKey(RECIPE_STEP_DESCRIPTION_BUNDLE_KEY)) {
            String description = bundle.getString(RECIPE_STEP_DESCRIPTION_BUNDLE_KEY);

            /* Display the description in the TextView */
            recipeStepDescriptionTextView.setText(description);
        }

        return view;
    }
}
