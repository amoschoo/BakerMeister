package com.example.android.backend;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hj on 30/11/15.
 */
public class SkillTree {
    static HashMap<ArrayList<Recipe>, ArrayList<Recipe>> link = new HashMap<>();
    private HashMap<Recipe, Bool> tree;
    public SkillTree(RecipeBook recipeBook, int level){
        tree = new HashMap<>();
        for (Recipe r : recipeBook.getRecipeList()) {
            if (r.getDifficulty() > level) {
                tree.put(r, Bool.UNACCESSIBLE);
            } else {
                tree.put(r, Bool.ACCESSIBLE);
            }
        }
    }

    public HashMap<Recipe, Bool> getTree() {
        return tree;
    }

    public void updateTrue(Recipe recipe){
        tree.put(recipe, Bool.COMPLETED);
        update();
    }

    public void updateFalse(Recipe recipe){
        tree.put(recipe, Bool.ACCESSIBLE);
        update();
    }

    private void update(){
        for (ArrayList<Recipe> recipes: link.keySet()) {
            boolean put = true;
            for (Recipe r : recipes) {
                if (tree.get(r) != Bool.COMPLETED) {
                    put = false;
                }
            }
            if (put){
                for (Recipe nextR: link.get(recipes)) {
                    if (tree.get(nextR) == Bool.UNACCESSIBLE) {
                        tree.put(nextR, Bool.ACCESSIBLE);
                    }
                }
            }else{
                for (Recipe nextR: link.get(recipes)) {
                    if (tree.get(nextR) == Bool.ACCESSIBLE) {
                        tree.put(nextR, Bool.UNACCESSIBLE);
                    }
                }
            }
        }
    }

    public ArrayList<Recipe> getRecommended(){
        ArrayList<Recipe> ret = new ArrayList<>();
        for (Category category: Category.values()) {
            ret.add(getRecommended(category));
        }
        return ret;
    }

    public Recipe getRecommended(Category category){
        Log.i("getrecipename","pre");
        Log.i("getrecipename",category.toString());
        Log.i("tree",tree.keySet().toString());
        for (Recipe r: tree.keySet()) {
            try {
                Log.i("getrecipename", r.toString());
                if (tree.get(r) == Bool.ACCESSIBLE && r.getCategory() == category) {
                    return r;
                }
            } catch (NullPointerException e){

            }
        }
        return null;
    }

    public ArrayList<Recipe> getAllRecommended(){
        ArrayList<Recipe> recommended = new ArrayList<>();
        for (Recipe r: tree.keySet()) {
            if (tree.get(r) == Bool.ACCESSIBLE){
                recommended.add(r);
            }
        }
        return recommended;
    }

    public static void constructLinks(RecipeBook recipeBook, String fileName,Context context){
        try {
            String line;
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int counter = 0;
            ArrayList<String> s1 = new ArrayList<>();
            ArrayList<String> s2 = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null){
                line = line.trim();
                boolean pass = false;
                if (line.equals("")){
                    pass = true;
                }
                switch (line){
                    case "{":
                        counter = 1;
                        s1 = new ArrayList<>();
                        s2 = new ArrayList<>();
                        pass = true;
                        break;
                    case "}":
                        ArrayList<Recipe> r1 = new ArrayList<>();
                        ArrayList<Recipe> r2 = new ArrayList<>();
                        for (Recipe r : recipeBook.getRecipeList()) {
                            for (String s : s1) {
                                if (s.equals(r.toString())) {
                                    r1.add(r);
                                }
                            }
                            for (String ss : s2) {
                                if (ss.equals(r.toString())) {
                                    r2.add(r);
                                }
                            }
                        }
                        link.put(r1, r2);
                        pass = true;
                        break;
                }
                if (!pass){
                    if (counter == 1){
                        counter = 2;
                        String[] recipes = line.split(",");
                        s1.addAll(Arrays.asList(recipes));
                    }
                    else {
                        if (counter == 2) {
                            counter = 0;
                            String[] recipes = line.split(",");
                            s2.addAll(Arrays.asList(recipes));
                        }
                    }
                }
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
