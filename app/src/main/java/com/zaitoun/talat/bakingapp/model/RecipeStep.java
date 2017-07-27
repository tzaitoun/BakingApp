package com.zaitoun.talat.bakingapp.model;


public class RecipeStep {

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
}