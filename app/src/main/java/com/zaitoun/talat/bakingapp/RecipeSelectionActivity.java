package com.zaitoun.talat.bakingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zaitoun.talat.bakingapp.model.Recipe;
import com.zaitoun.talat.bakingapp.utils.RecipeJsonUtils;
import com.zaitoun.talat.bakingapp.utils.RecipeNetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class RecipeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);
    }
}