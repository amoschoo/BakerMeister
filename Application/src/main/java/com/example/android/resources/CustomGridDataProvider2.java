package com.example.android.resources;

public class CustomGridDataProvider2 {

    private String name_id;
    private Integer image_id;

    public CustomGridDataProvider2(String name_id,Integer image_id)
    {
        this.setName_id(name_id);
        this.setImage_id(image_id);
    }

    public Integer getImage_id(){ return image_id;}

    public void setImage_id(Integer image_id){this.image_id = image_id; }

    public String getName_id(){ return name_id;}

    public void setName_id (String name_id) {this.name_id = name_id; }


}
