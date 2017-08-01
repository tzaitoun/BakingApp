package com.zaitoun.talat.bakingapp.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Ingredient;
import com.zaitoun.talat.bakingapp.model.RecipeStep;

import java.util.ArrayList;


public class RecipeStepSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* These are to differentiate between the 2 different views */
    private static final int INGREDIENTS_VIEW_HOLDER = 0;
    private static final int RECIPE_STEPS_VIEW_HOLDER = 1;

    /* There is a single view reserved for the ingredients in the recycler view, so it counts as 1
     * towards the item count.
     */
    public static final int RESERVED_VIEW_FOR_INGREDIENTS = 1;

    private RecipeStepClickListener mRecipeStepClickListener;

    private ArrayList<Ingredient> mIngredients;
    private ArrayList<RecipeStep> mRecipeSteps;
    private Context mContext;

    public RecipeStepSelectionAdapter(ArrayList<Ingredient> ingredients, ArrayList<RecipeStep> recipeSteps,
                                      Context context, RecipeStepClickListener recipeStepClickListener) {
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
        mContext = context;
        mRecipeStepClickListener = recipeStepClickListener;
    }

    public interface RecipeStepClickListener {
        public void onRecipeStepClickListener(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        /* Depending on the view type, return the appropriate inflated layout */
        switch (viewType) {
            case INGREDIENTS_VIEW_HOLDER:
                return new IngredientsViewHolder(inflater.inflate(R.layout.ingredients_item, parent, false));

            case RECIPE_STEPS_VIEW_HOLDER:
                return new RecipeStepSelectionViewHolder(inflater.inflate(R.layout.recipe_step_selection_item, parent, false));

            default:
                Log.d("Invalid View Type", "onCreateViewHolder");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        /* Depending on the view type, bind the appropriate data */
        switch (holder.getItemViewType()) {
            case INGREDIENTS_VIEW_HOLDER:
                ((IngredientsViewHolder) holder).bind();
                break;

            case RECIPE_STEPS_VIEW_HOLDER:
                ((RecipeStepSelectionViewHolder) holder).bind(position);
                break;

            default:
                Log.d("Invalid View Type", "onBindViewHolder");
        }
    }

    /* Our RecyclerView will show the ingredients in one view and each recipe step in one view */
    @Override
    public int getItemCount() {
        return mRecipeSteps.size() + RESERVED_VIEW_FOR_INGREDIENTS;
    }

    @Override
    public int getItemViewType(int position) {

        /* The position 0 is reserved for the ingredients */
        if (position == 0) {
            return INGREDIENTS_VIEW_HOLDER;
        }

        /* The other positions are for each recipe step */
        else {
            return RECIPE_STEPS_VIEW_HOLDER;
        }
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredientsListTextView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            mIngredientsListTextView = (TextView) itemView.findViewById(R.id.tv_ingredients_list);
        }

        public void bind() {

            String ingredientsString = "";

            for (int i = 0; i < mIngredients.size(); i++) {

                Ingredient ingredient = mIngredients.get(i);
                ingredientsString += ingredient.getQuantity() + " " + ingredient.getMeasurementUnit()
                        + " " + ingredient.getIngredientName() + "\n";
            }

            mIngredientsListTextView.setText(ingredientsString);
        }
    }

    public class RecipeStepSelectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRecipeStepShortDescriptionTextView;

        public RecipeStepSelectionViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mRecipeStepShortDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_recipe_step_short_description);
        }

        public void bind(int position) {
            mRecipeStepShortDescriptionTextView.setText(position + ") " + mRecipeSteps
                    .get(position - RESERVED_VIEW_FOR_INGREDIENTS).getShortDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mRecipeStepClickListener.onRecipeStepClickListener(position);
        }
    }
}
