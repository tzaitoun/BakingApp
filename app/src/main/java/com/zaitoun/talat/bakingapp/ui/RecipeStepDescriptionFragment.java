package com.zaitoun.talat.bakingapp.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaitoun.talat.bakingapp.R;

import static com.zaitoun.talat.bakingapp.ui.RecipeStepViewActivity.*;

public class RecipeStepDescriptionFragment extends Fragment {

    private TextView mRecipeStepDescriptionTextView;

    public RecipeStepDescriptionFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_recipe_step_description, container, false);

        mRecipeStepDescriptionTextView = (TextView) view.findViewById(R.id.tv_recipe_step_description);

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(RECIPE_STEP_DESCRIPTION_KEY)) {
            String description = bundle.getString(RECIPE_STEP_DESCRIPTION_KEY);

            mRecipeStepDescriptionTextView.setText(description);
        }

        return view;
    }
}
