package com.example.android.backend;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by hj on 30/11/15.
 */
public class Recipe {
    private Category category;
    private String name;
    private ArrayList<Ingredient> ingredientsList;
    private String instructions;
    private int amount;
    private int difficulty;

    Recipe(String name, Category category,String instructions, int amount, int difficulty, ArrayList<Ingredient> ingredients){
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.amount = amount;
        ingredientsList = ingredients;
    }

    public String display(int base){
        String retVal = "Makes " + amount * base + "\n" + "\n";
        retVal += "Ingredients:" + "\n";
        for (Ingredient i : ingredientsList) {
            if (i.getUnit().equals("comment")){
                retVal += "\n" + i.toString() + "\n" + "\n";
            }else {
                retVal += i.getAmount() * base;
                if (!i.getUnit().equals("")) {
                    retVal += " " + i.getUnit();
                }
                retVal += " " + i.toString() + "\n";
            }
        }
        retVal += "\n" + "Instructions:" + "\n";
        retVal += instructions;
        return retVal;
    }

    public Category getCategory() {
        return category;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public ArrayList<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public String toString(){
        return name;
    }
}
