package com.example.android.backend;

import java.util.ArrayList;

/**
 * Created by hj on 9/12/15.
 */
public class Shelf {
    private String item;
    private double weight;
    private double humidity;
    private double adjustment;

    public void updateInventory(Inventory inventory){
        inventory.put(new Ingredient(item, getAmount()));
        if (item.equals("empty")){
            this.adjustment = weight;
        }
    }

    public double getAmount(){
        String unit = Ingredient.unitMap.get(item);
        if (unit == null){
            unit = "g";
        }
        switch (unit){
            case "cups":
                switch (item){
                    case "Butter":
                        return getWeight()/227;
                }
            case "":
                switch (item){
                    case "Egg(s)":
                        return Math.round(getWeight() / 57);
                    case "Banana(s)":
                        return Math.round(getWeight() / 100);
                }
        }
        return getWeight();
    }

    public double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(double adjustment) {
        this.adjustment = adjustment;
    }

    public String getItem() {
        return item;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWeight() {
        return weight - adjustment;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String toString(){
        return "weight: " + Double.toString(weight) + " humidity: " + Double.toString(humidity);
    }
}
