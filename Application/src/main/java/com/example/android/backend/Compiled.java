//package com.example.android.backend;
//
//import com.google.gdata.util.ServiceException;
//
//import java.io.IOException;
//
///**
// * Created by hj on 30/11/15.
// */
//public class Compiled {
//    public static void main(String[] args) {
//        Ingredient.constructMap("/Users/hj/Desktop/Units.txt");
//        RecipeBook recipeBook = new RecipeBook("/Users/hj/Desktop/Recipes.txt");
//        SkillTree.constructLinks(recipeBook, "/Users/hj/Desktop/SkillTree.txt");
//        SkillTree mySkills = new SkillTree(recipeBook, 1);
//        mySkills.updateTrue(recipeBook.getRecipe("Cookie"));
//        System.out.println(mySkills.getTree());
//        mySkills.updateTrue(recipeBook.getRecipe("Mint Cookie"));
//        System.out.println(mySkills.getTree());
//        System.out.println(mySkills.getRecommended());
//        mySkills.updateFalse(recipeBook.getRecipe("Mint Cookie"));
//        System.out.println(mySkills.getTree());
//        System.out.println(mySkills.getRecommended());
//        for (Recipe r:mySkills.getAllRecommended()
//             ) {
//            mySkills.updateTrue(r);
//            System.out.println(mySkills.getTree());
//        }
//        Inventory inventory = new Inventory();
//        inventory.put(new Ingredient("Flour", 350));
//        inventory.put(new Ingredient("Butter", 30));
//        System.out.println(inventory.getIngredientsList());
//        inventory.put(new Ingredient("Egg(s)", 3));
//        System.out.println(inventory.getIngredientsList());
//        System.out.println(inventory.getRequired(recipeBook.getRecipe("Cookie"), 100));
//        System.out.println(recipeBook.getRecipe("Cookie").display(100));
//    }
//}


