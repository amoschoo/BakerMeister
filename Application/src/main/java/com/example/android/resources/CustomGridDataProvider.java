package com.example.android.resources;

public class CustomGridDataProvider {

    private String name_id;
    private Integer image_id;
    private String amount_id;

    public CustomGridDataProvider(String name_id,  String amount_id, Integer image_id)
    {
        this.setName_id(name_id);
        this.setImage_id(image_id);
        this.setAmount_id(amount_id);
    }

    public Integer getImage_id(){ return image_id;}

    public void setImage_id(Integer image_id){this.image_id = image_id; }

    public String getName_id(){ return name_id;}

    public void setName_id (String name_id) {this.name_id = name_id; }

    public String getAmount_id(){ return amount_id;}

    public void setAmount_id (String amount_id) {this.amount_id = amount_id; }

}
