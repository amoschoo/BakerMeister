package com.example.android.resources;

import android.app.Application;

import com.example.android.backend.Category;
import com.example.android.backend.Inventory;
import com.example.android.backend.Recipe;
import com.example.android.backend.RecipeBook;
import com.example.android.backend.Shelf;
import com.example.android.backend.SkillTree;
import com.example.android.slidingtabsbasic.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amos on 2/11/15.
 */
public class globals extends Application {
    static HashMap<String,Integer> images;
    static
    {
        images = new HashMap<String, Integer>();
        images.put("Mint Cookie", R.drawable.cookie);
        images.put("Cookie", R.drawable.cookie);
        images.put("Cake",R.drawable.cake);
        images.put("Pastry",R.drawable.cake);
        images.put("Pie",R.drawable.cake);
        //Bread
        images.put("Brioche",R.drawable.brioche);
        images.put("Ciabatta",R.drawable.ciabatte);
        images.put("Classic Banana Bread",R.drawable.classicbananabread);
        images.put("Easy white bread",R.drawable.easywhitebread);
        images.put("Focaccia",R.drawable.focaccia);
        images.put("French Baguettes",R.drawable.frenchbaguette);
        images.put("No-Knead Bread",R.drawable.nokneadbread);
        images.put("No Knead Cinnamon Rolls",R.drawable.nokneadcinnamonrolls);
        images.put("Cinnamon Raisin Bread",R.drawable.raisinbread);

        //CAC
        images.put("Almond Brittle",R.drawable.almondbrittle);
        images.put("Caramel Candies",R.drawable.caramelcandies);
        images.put("Caramel filled truffles",R.drawable.caramelfilledchocolatetruffles);
        images.put("Chocolate Bark",R.drawable.chocolatebark);
        images.put("Chocolate Truffles",R.drawable.chocolatetruffles);
        images.put("Marshmallows",R.drawable.marshmallow);
        images.put("Nougat",R.drawable.nougat);
        images.put("Oreo Truffles",R.drawable.oreotruffles);
        images.put("Peanut Butter Cups",R.drawable.peanutbuttercups);

        //Cakes
        images.put("Angel Food Cake",R.drawable.angelfoodcake);
        images.put("Chocolate Cake",R.drawable.chocolatecake);
        images.put("Double chocolate mousse entremets",R.drawable.doublechocolatemousseentremets);
        images.put("Easy Cupcakes",R.drawable.easycupcakes);
        images.put("Irish cream and chocolate cheesecake",R.drawable.irishcreamandchocolatecheesecake);
        images.put("Mango Mousse Cake",R.drawable.mangomoussecake);
        images.put("Passionfruit Meringue Cake",R.drawable.passionfruitmeringuecake);
        images.put("Pound Cake",R.drawable.poundcake);
        images.put("Red Velvet Cake",R.drawable.redvelvetcake);

        //Cookie
        images.put("Dutch Apple Pie Cookies",R.drawable.applepiecookie);
        images.put("Butter Cookies",R.drawable.buttercookies);
        images.put("Chocolate Pistachio Meringues",R.drawable.chocolatepistachiomeringues);
        images.put("Lemon Macarons",R.drawable.lemonmacarons);
        images.put("Ultimate Chocolate Chip Cookies",R.drawable.linglisultimatechocolatechipcookies);
        images.put("Peanut Butter Cookies",R.drawable.peanutbuttercookies);
        images.put("Salted Caramel Molasses Cookies",R.drawable.saltedcaramelcookie);
        images.put("Snickerdoodles",R.drawable.snickerdoodles);
        images.put("Soft and Chewy Chocolate Chip Cookies",R.drawable.softandchewychocolatechip);

        //Custard
        images.put("Creme Anglaise",R.drawable.cremeanglaise);
        images.put("Creme Brulee",R.drawable.cremebrulee);
        images.put("Custard Bread Pudding",R.drawable.custardbreadpudding);
        images.put("Eggnog",R.drawable.eggnog);
        images.put("Leche Flan",R.drawable.lecheflan);
        images.put("Pastry Cream",R.drawable.pastrycream);
        images.put("Portugese Egg Tarts",R.drawable.portugeseeggtarts);
        images.put("Sauteed Apples with Custard",R.drawable.saltedcaramelcookie);
        images.put("Vanilla Custard Slice",R.drawable.vanillacustardslice);

        //Pastry
        images.put("Apple Turnovers",R.drawable.appleturnovers);
        images.put("Eclairs",R.drawable.eclairs);
        images.put("Ham and Cheese Puff",R.drawable.hamcheesepuff);
        images.put("Ice Cream Puff",R.drawable.icecreampuff);
        images.put("Lemon Cream Puffs",R.drawable.lemoncreampuff);
        images.put("Mille Feuille",R.drawable.millefeuille);
        images.put("Pear Galette",R.drawable.peargalette);
        images.put("Sausage Rolls",R.drawable.sausagerolls);
        images.put("Tuna Puff",R.drawable.tunapuff);

        //Pie
        images.put("Apple Pie",R.drawable.applepie);
        images.put("Banoffee pie",R.drawable.banoffeepie);
        images.put("Blueberry Pie",R.drawable.hamcheesepuff);
        images.put("Chocolate Pie",R.drawable.blueberrypie);
        images.put("Key Lime Pie",R.drawable.keylimepie);
        images.put("Lemon Meringue Pie",R.drawable.lemonmeringuepie);
        images.put("Peach Pie",R.drawable.peachpie);
        images.put("Peanut Butter Pie",R.drawable.peanutbutterpie);
        images.put("Strawberry Cream Cheese Pie",R.drawable.strawberrycreamcheesepie);


    }

    static HashMap<String, Shelf> identifier;
    static String recipename = "blank";
    static Category category;
    static int startingskilllevel=1;
    static ArrayList<Recipe> recommendedarray=null;
    static RecipeBook recipeBook;
    static SkillTree skillTree;
    static Inventory inventory = new Inventory();

    public static String getRecipename(){return recipename;}
    public static void setRecipename(String s){recipename=s;}

    public static Category getCategory(){return category;}
    public static void setCategory(Category s){category=s;}

    public static int getStartingskilllevel(){return startingskilllevel;}
    public static void setStartingskilllevel(int s){startingskilllevel=s;}

    public static ArrayList<Recipe> getRecommendedarray(){return recommendedarray;}
    public static void setRecommendedarray(ArrayList<Recipe> R){recommendedarray=R;}

    public static RecipeBook getRecipeBook(){return recipeBook;}
    public static void setRecipeBook(RecipeBook RB){recipeBook=RB;}

    public static SkillTree getSkillTree(){return skillTree;}
    public static void setSkillTree(SkillTree ST){skillTree=ST;}

    public static HashMap getImages(){return images;}


    public static HashMap<String, Shelf> getIdentifier() {return identifier;}
    public static void setIdentifier(HashMap<String, Shelf> identifier) {globals.identifier = identifier;}

    public static Inventory getInventory() {return inventory;}
    public static void setInventory(Inventory inventory) {globals.inventory = inventory;}
}
