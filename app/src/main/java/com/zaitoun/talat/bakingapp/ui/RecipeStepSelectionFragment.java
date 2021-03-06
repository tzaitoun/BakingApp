package com.zaitoun.talat.bakingapp.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Ingredient;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;
import static com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity.*;

/**
 * A Fragment that displays a recipe's steps in a RecyclerView.
 */
public class RecipeStepSelectionFragment extends Fragment
        implements RecipeStepSelectionAdapter.RecipeStepClickListener {

    /* The Activity that implements the callback */
    private RecipeStepSelectionCallback mCallback;

    public RecipeStepSelectionFragment(){}

    /* Allows the fragment to communicate back to the activity */
    public interface RecipeStepSelectionCallback {
        void onRecipeStepSelectionClick(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /* This makes sure that the host activity has implemented the callback interface
         * If not, it throws an exception
         */
        try {
            mCallback = (RecipeStepSelectionCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecipeStepSelectionCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Inflate the layout of the fragment */
        final View view = inflater.inflate(R.layout.fragment_recipe_step_selection, container, false);

        /* Get a reference to the view */
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_recipe_step_selection);

        /* Set up the recycler view's layout manager */
        LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        /* Get the data passed from the activity */
        Bundle bundle = getArguments();

        /* Check that the data is valid */
        if (bundle != null) {
            if (bundle.containsKey(INGREDIENTS_ARRAY_BUNDLE_KEY) &&
                    bundle.containsKey(RECIPE_STEPS_ARRAY_BUNDLE_KEY)) {

                /* Get the data from the bundle */
                ArrayList<Ingredient> ingredients =
                        bundle.getParcelableArrayList(INGREDIENTS_ARRAY_BUNDLE_KEY);

                ArrayList<RecipeStep> recipeSteps =
                        bundle.getParcelableArrayList(RECIPE_STEPS_ARRAY_BUNDLE_KEY);

                /* Create a new adapter and pass the data to it */
                RecipeStepSelectionAdapter adapter =
                        new RecipeStepSelectionAdapter(ingredients, recipeSteps, getContext(), this);

                /* Set the recycler view's adapter */
                recyclerView.setAdapter(adapter);
            }
        }

        return view;
    }

    @Override
    public void onRecipeStepClickListener(int position) {
        mCallback.onRecipeStepSelectionClick(position);
    }
}
