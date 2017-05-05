package com.example.android.backend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hj on 30/11/15.
 */
public class Inventory {
    private HashMap<String, Double> ingredientsList = new HashMap<>();;
    public Inventory(){
        ingredientsList = new HashMap<>();
    }

    public void put(Ingredient ingredient){
        ingredientsList.put(ingredient.toString(), ingredient.getAmount());
    }

    public HashMap<String, Double> getIngredientsList() {
        return ingredientsList;
    }

    public boolean canMake(Recipe recipe, int n){
        for (Ingredient i: recipe.getIngredientsList()) {
            if (!i.getUnit().equals("comment")) {
                if (ingredientsList.containsKey(i.toString())) {
                    if (!i.times(n).isSubset(new Ingredient(i.toString(), ingredientsList.get(i.toString())))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Recipe> getPossible(RecipeBook recipeBook){
        ArrayList<Recipe> ret = new ArrayList<>();
        for (Recipe recipe: recipeBook.getRecipeList()) {
            if (this.canMake(recipe, 1)){
                ret.add(recipe);
            }
        }
        return ret;
    }

    public HashMap<String, Double> getRequired(Recipe recipe, int n){
        HashMap<String, Double> toBuy = new HashMap<>();
        for (Ingredient i: recipe.getIngredientsList()) {
            if (!i.getUnit().equals("comment")) {
                if (ingredientsList.containsKey(i.toString())) {
                    if (!i.times(n).isSubset(new Ingredient(i.toString(), ingredientsList.get(i.toString())))) {
                        toBuy.put(i.toString(), i.times(n).getAmount() - ingredientsList.get(i.toString()));
                    }
                } else {
                    toBuy.put(i.toString(), i.times(n).getAmount());
                }
            }
        }
        return toBuy;
    }
    public ArrayList<String> getinventorylist(){
        ArrayList<String> array = new ArrayList<>();
        for (String s: Ingredient.unitMap.keySet()) {
            array.add(s);
        }
        return array;
    }
}
