package com.zaitoun.talat.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity;

import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.EXTRA_INGREDIENTS_ARRAY;
import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.EXTRA_RECIPE_NAME;
import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.EXTRA_RECIPE_STEPS_ARRAY;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe, String ingredients) {

        /* Create an intent to launch RecipeStepSelectionActivity */
        Intent intent = new Intent(context, RecipeStepSelectionActivity.class);

        /* Pass all the relevant data */
        intent.putExtra(EXTRA_RECIPE_NAME, recipe.getRecipeName());
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS_ARRAY, recipe.getIngredients());
        intent.putParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY, recipe.getRecipeSteps());

        /* Wrap the intent in a pending intent */
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        views.setTextViewText(R.id.tv_ingredients_widget, ingredients);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, Recipe recipe, String ingredients) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe, ingredients);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}
}

