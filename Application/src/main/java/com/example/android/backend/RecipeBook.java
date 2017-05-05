package com.example.android.backend;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by hj on 2/12/15.
 */
public class RecipeBook {
    private HashSet<Recipe> recipeList;
    public RecipeBook(String fileName, Context context){
        recipeList = new HashSet<>();
        try {
            String line;
            String name = "";
            String instructions = "";
            int amount = 0;
            int difficulty = 0;
            Category category = Category.PIE;
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            int counter = 0;
            boolean pass;
            boolean write = false;
            String ingredientName = "";

            InputStream inputStream = context.getResources().getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            boolean makeIngredient = false;
            while ((line = bufferedReader.readLine()) != null){
                line = line.trim();
                pass = false;
                // name, class, amount, diff, ingredients, instr
                switch (line){
                    case "":
                        pass = true;
                        break;
                    case "Name:":
                        instructions = "";
                        makeIngredient = false;
                        ingredients = new ArrayList<>();
                        counter = 0;
                    case "Instructions:":
                    case "Makes:":
                    case "Difficulty:":
                    case "Ingredients:":
                    case "Class:":
                        pass = true;
                        counter += 1;
                        break;
                    case ".ends":
                        pass = true;
                        write = true;
                }

                if (!pass) {
                    switch (counter) {
                        case 1:
                            name = line;
                            break;
                        case 2:
                            category = Category.valueOf(line);
                            break;
                        case 3:
                            amount = Integer.parseInt(line);
                            break;
                        case 4:
                            difficulty = Integer.parseInt(line);
                            break;
                        case 5:
                            if (!makeIngredient){
                                makeIngredient = true;
                                ingredientName = line;
                            } else {
                                if (ingredientName.charAt(ingredientName.length() - 1) == ':'){
                                    ingredients.add(new Ingredient(ingredientName.substring(0,ingredientName.length() - 1), 0, "comment"));
                                }else {
                                    ingredients.add(new Ingredient(ingredientName, Double.parseDouble(line)));
                                }
                                makeIngredient = false;
                            }
                            break;
                        case 6:
                            instructions += line + "\n";
                            break;
                    }
                }

                if (write){
                    write = false;
                    recipeList.add(new Recipe(name, category, instructions, amount, difficulty, ingredients));
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public HashSet<Recipe> getRecipeList() {
        return recipeList;
    }

    public Recipe getRecipe(String name){
        for (Recipe r: recipeList) {
            if (r.toString().equals(name)){
                return r;
            }
        }
        return null;
    }

    public ArrayList<Recipe> getRecipes(Category category, int difficulty){
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe r: recipeList) {
            if (r.getCategory() == category && r.getDifficulty() == difficulty){
                recipes.add(r);
            }
        }
        return recipes;
    }
}
