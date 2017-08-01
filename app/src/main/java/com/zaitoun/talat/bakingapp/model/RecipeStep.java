package com.zaitoun.talat.bakingapp.model;


import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable {

    private final String shortDescription;
    private final String description;
    private final String videoUrl;
    private final String thumbnailUrl;

    public RecipeStep(String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    private RecipeStep(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    public static final Parcelable.Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {

        @Override
        public RecipeStep createFromParcel(Parcel source) {
            return new RecipeStep(source);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };
}