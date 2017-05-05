package com.example.android.resources;

/**
 * Created by Amos on 31/10/15.
 */
public class RecommendedDataProvider {
    private int picture_resource;
    private String recipename;
    private String level;
    private String category;

    public RecommendedDataProvider(int picture_resource, String Recipename, String Category,String Level){
        this.setRecipename(Recipename);
        this.setPicture_resource(picture_resource);
        this.setlLevel(Level);
        this.setCategory(Category);
    }

    public int getPicture_resource() {
        return picture_resource;
    }

    public void setPicture_resource(int picture_resource) {
        this.picture_resource = picture_resource;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getLevel() {
        return level;
    }

    public void setlLevel(String level) {
        this.level = level;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
