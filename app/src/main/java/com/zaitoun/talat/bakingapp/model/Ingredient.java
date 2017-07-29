package com.zaitoun.talat.bakingapp.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private final String ingredientName;
    private final double quantity;
    private final String measurementUnit;

    public Ingredient(String ingredientName, double quantity, String measurementUnit) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
    }

    private Ingredient(Parcel in) {
        ingredientName = in.readString();
        quantity = in.readDouble();
        measurementUnit = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientName);
        dest.writeDouble(quantity);
        dest.writeString(measurementUnit);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}