package com.zaitoun.talat.bakingapp.model;


public class Ingredient {

    private final String ingredientName;
    private final double quantity;
    private final String measurementUnit;

    public Ingredient(String ingredientName, double quantity, String measurementUnit) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
    }
}