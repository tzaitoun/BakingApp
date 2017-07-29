package com.zaitoun.talat.bakingapp.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zaitoun.talat.bakingapp.R;
import com.zaitoun.talat.bakingapp.model.Recipe;

import java.util.ArrayList;

public class RecipeSelectionAdapter extends RecyclerView.Adapter<RecipeSelectionAdapter.RecipeSelectionViewHolder> {

    private ArrayList<Recipe> mRecipes;
    private Context mContext;

    public RecipeSelectionAdapter(ArrayList<Recipe> recipes, Context context) {

        /* Set the adapter's data */
        mRecipes = recipes;
        mContext = context;
    }

    @Override
    public RecipeSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /* Get the context and the layout inflater */
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the view from the layout file */
        View view = inflater.inflate(R.layout.recipe_selection_item, parent, false);

        return new RecipeSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeSelectionViewHolder holder, int position) {
        /* Bind the recipe data to the view holder depending on the position */
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        /* Return the number of recipes to display, which is the size of the array */
        return mRecipes.size();
    }

    public class RecipeSelectionViewHolder extends RecyclerView.ViewHolder {

        private TextView mRecipeNameTextView;
        private ImageView mRecipeImageView;
        private TextView mServingTextView;

        public RecipeSelectionViewHolder(View itemView) {
            super(itemView);

            /* Get a reference to each view */
            mRecipeNameTextView = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            mRecipeImageView = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
            mServingTextView = (TextView) itemView.findViewById(R.id.tv_serving_size);
        }

        public void bind(int position) {

            /* Get the recipe */
            Recipe recipe = mRecipes.get(position);

            /* Set the recipe name */
            String recipeName = recipe.getRecipeName();
            mRecipeNameTextView.setText(recipeName);

            /* Set the serving size */
            int servingSize = recipe.getServings();
            mServingTextView.setText("Servings: " + servingSize);

            String recipeImage = recipe.getImage();

            /* If an image is available, show it */
            if (recipeImage != null && !recipeImage.isEmpty()) {
                mRecipeImageView.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(recipeImage).into(mRecipeImageView);
            }

            /* If there is no image, hide the image view */
            else {
                mRecipeImageView.setVisibility(View.GONE);
            }
        }
    }
}
