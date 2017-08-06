package com.zaitoun.talat.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.ui.RecipeStepSelectionActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.EXTRA_INGREDIENTS_ARRAY;
import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.EXTRA_RECIPE_NAME;
import static com.zaitoun.talat.bakingapp.ui.RecipeSelectionActivity.EXTRA_RECIPE_STEPS_ARRAY;


public class IngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe, String ingredients) {

        /* Create an intent to launch RecipeStepSelectionActivity and pass the relevant data */
        Intent intent = new Intent(context, RecipeStepSelectionActivity.class);
        intent.putExtra(EXTRA_RECIPE_NAME, recipe.getRecipeName());
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENTS_ARRAY, recipe.getIngredients());
        intent.putParcelableArrayListExtra(EXTRA_RECIPE_STEPS_ARRAY, recipe.getRecipeSteps());

        /* Wrap the intent in a pending intent */
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /* Create an intent for the next button */
        Intent intentNext = new Intent(context, IngredientsService.class);
        intentNext.setAction(IngredientsService.ACTION_NEXT_RECIPE_INGREDIENTS);
        PendingIntent pendingIntentNext = PendingIntent.getService(context, 0, intentNext, 0);

        /* Create an intent for the prev button */
        Intent intentPrev = new Intent(context, IngredientsService.class);
        intentPrev.setAction(IngredientsService.ACTION_PREV_RECIPE_INGREDIENTS);
        PendingIntent pendingIntentPrev = PendingIntent.getService(context, 0, intentPrev, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        /* Set OnClickPendingIntents on the views */
        views.setOnClickPendingIntent(R.id.tv_ingredients_widget, pendingIntent);
        views.setOnClickPendingIntent(R.id.iv_next, pendingIntentNext);
        views.setOnClickPendingIntent(R.id.iv_prev, pendingIntentPrev);

        /* Set the text of the text views */
        views.setTextViewText(R.id.tv_ingredients_widget, ingredients);
        views.setTextViewText(R.id.tv_recipe_name_widget, recipe.getRecipeName());

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /* Update all the instances of the widget */
    static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, Recipe recipe, String ingredients) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe, ingredients);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        /* Get the shared preferences */
        SharedPreferences sharedPreferences
                = context.getSharedPreferences(context.getString(R.string.preferences_key), MODE_PRIVATE);

        /* If the shared preferences has been created, initialize the widget with the ingredients */
        if (sharedPreferences.contains(context.getString(R.string.position))) {
            IngredientsService.startActionInitializeIngredients(context);
        }
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}
}

